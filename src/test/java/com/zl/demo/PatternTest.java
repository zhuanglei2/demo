package com.zl.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zl.demo.common.util.ImgBase64Util;
import com.zl.demo.common.util.JaxpUtil;
import com.zl.demo.dto.PMResponseInfo;
import com.zl.demo.dto.wechat.WeChatTextMsg;
import com.zl.demo.pattern.abstractFactory.AbstractFactory;
import com.zl.demo.pattern.abstractFactory.Color;
import com.zl.demo.pattern.abstractFactory.FactoryProducer;
import com.zl.demo.pattern.abstractFactory.Shape;
import com.zl.demo.pattern.adapter.AudioPlayer;
import com.zl.demo.pattern.adapter.impl.MediaAdapter;
import com.zl.demo.pattern.bridge.Circle;
import com.zl.demo.pattern.bridge.impl.GreenCircle;
import com.zl.demo.pattern.bridge.impl.RedCircle;
import com.zl.demo.pattern.build.Meal;
import com.zl.demo.pattern.build.MealBuilder;
import com.zl.demo.pattern.chain.AbstractLogger;
import com.zl.demo.pattern.chain.ConsoleLogger;
import com.zl.demo.pattern.chain.ErrorLogger;
import com.zl.demo.pattern.chain.FileLogger;
import com.zl.demo.pattern.composite.Employee;
import com.zl.demo.pattern.filter.Criteria;
import com.zl.demo.pattern.filter.Person;
import com.zl.demo.pattern.filter.impl.*;
import com.zl.demo.pattern.prototype.ShapeCache;
import com.zl.demo.pattern.strategy.StrategyContext;
import com.zl.demo.pattern.strategy.impl.OperationAddByStrategy;
import com.zl.demo.pattern.strategy.impl.OperationMultiplyByStrategy;
import com.zl.demo.pattern.strategy.impl.OperationSubtractByStrategy;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Encoder;
import sun.util.calendar.CalendarUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:02
 */
@Slf4j
@SpringBootTest
public class PatternTest {

    private static List<IndicationBo> indicationBos = new ArrayList<>();

    /**
     * 支付宝超期时间 3年
     */
    private final Integer aliPayExpireMonths = 36;
    /**
     * 微信超期时间1年
     */
    private final Integer wechatPayExpireMonths = 12;

    @Test
    public void testLong(){
        long i = 2<<2;
        long start = System.currentTimeMillis();
        System.out.println(i*2);
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public void testStream(){
        long a = (48L+58000L)/2;
        long b = new BigDecimal(a)
                .multiply(new BigDecimal(1.2))
                .setScale(2,BigDecimal.ROUND_HALF_UP).longValue();
        System.out.println(b);
        System.out.println(new BigDecimal(b).divide(new BigDecimal(1000),BigDecimal.ROUND_UP).multiply(new BigDecimal(1000)).longValue());
    }

    @Test
    public void testBig(){

        Long a = 0L;
        System.out.println(a==0);

        System.out.println(new BigDecimal("416010").divide(new BigDecimal(1000),BigDecimal.ROUND_UP)
                .multiply(new BigDecimal(1000)).longValue());
    }

    @Test
    public void testTime(){
        String str = "2019-03-03";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date paySuccTime = null;
        try {
            paySuccTime = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date expireTime = getDateWithAddMonths(paySuccTime, aliPayExpireMonths);
//        if (v.getPayPlatform().equals("WXPAY")) {
//            expireTime = getDateWithAddMonths(paySuccTime, wechatPayExpireMonths);
//        }
        AtomicBoolean isExpire = new AtomicBoolean(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(11, 23);
        Date time = calendar.getTime();
        if (time.after(expireTime)){
            isExpire.set(true);
        }
        System.out.println(isExpire.get());
    }

    public static final Date getDateWithAddMonths(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return calendar.getTime();
    }

    @Builder
    @Data
    static class A{
        private Boolean ifA = false;
        private String last;
    }

    @Test
    public void testUUID(){
       List<String> list = Arrays.asList("GDS532947726","GDS131995632","GDS609505131","GDS817060610");
        System.out.println(list.subList(0,2));
        System.out.println(list.subList(2,4));
    }

    @Test
    public void readSimple(){
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "C:\\Users\\Shanzhen\\Desktop\\json按照指标汇总.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, IndicationIndex.class, new DemoDataListener()).sheet().doRead();
    }

    // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
    class DemoDataListener extends AnalysisEventListener<IndicationIndex> {
        List<IndicationIndex> list = new ArrayList<>();
        /**
         * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
         */
        private final int BATCH_COUNT = 500;

        public DemoDataListener() {
        }

        /**
         * 这个每一条数据解析都会来调用
         *
         * @param data
         *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
         * @param context
         */
        @Override
        public void invoke(IndicationIndex data, AnalysisContext context) {
//            log.info("解析到一条数据:{}", JSON.toJSONString(data));
            list.add(data);
        }

        /**
         * 所有数据解析完成了 都会来调用
         *
         * @param context
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 这里也要保存数据，确保最后遗留的数据也存储到数据库
            saveData();

            list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(IndicationIndex::getTitle))),ArrayList::new));
            list.forEach(indicationIndex -> {
                IndicationBo tmp = new IndicationBo();
                BeanUtils.copyProperties(indicationIndex,tmp);
                indicationBos.add(tmp);
            });
            System.out.println(JSON.toJSONString(indicationBos));
        }

        /**
         * 加上存储数据库
         */
        private void saveData() {
            log.info("{}条数据，开始存储数据库！", list.size());
            log.info("存储数据库成功！");
        }
    }

    @Test
    public void simpleWrite02() throws IOException {
        File file = new File("C:\\Users\\Shanzhen\\Desktop\\健康百科\\xh.json");
        FileReader fileReader = new FileReader(file);
        Reader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
        List<Kk> indications = JSONArray.parseArray(jsonStr,Kk.class);
        List<IndicationIndex> indicationIndices = new ArrayList<>();

        System.out.println(JSON.toJSONString(indications));
        indications.forEach(i->{
            final KkContent content = i.getContent();
            final List<KkContext> contents = content.getContents();
            contents.forEach(c->{
                final String subtitle = c.getSubtitle();
                final List<Description> description = c.getDescription();
                for (int j = 1; j < description.size(); j++) {
                    final List<Detail> details = description.get(j).getDetails();
                    final String name = description.get(j).getName();
                    details.forEach(d->{
                        IndicationIndex tmp = new IndicationIndex();
                        tmp.setSummary(description.get(0).getDetails().get(0).getDetail());
                        tmp.setTitle(subtitle);
                        tmp.setName(name);
                        tmp.setTexts(d.getDetail());
                        indicationIndices.add(tmp);
                    });
                }
            });
        });


        // 写法1
        String fileName = "C:\\Users\\Shanzhen\\Desktop\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, IndicationIndex.class).sheet("模板").doWrite(indicationIndices);
    }

    @Test
    public void simpleWrite() throws IOException {
        File file = new File("C:\\Users\\Shanzhen\\Desktop\\健康百科\\20201215.json");
        FileReader fileReader = new FileReader(file);
        Reader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
        List<Indication> indications = JSONArray.parseArray(jsonStr,Indication.class);

        System.out.println(JSON.toJSONString(indications));
        List<IndicationIndex> indicationIndices = new ArrayList<>();

        indications.forEach(i->{
            Content content = i.getContent();
            String title = content.getTitle();
            String summary = content.getSummary();
            List<Paragraphs> paragraphs = i.getContent().getParagraphs();
            paragraphs.forEach(p->{
                IndicationIndex tmp = new IndicationIndex();
                tmp.setSummary(summary);
                tmp.setTitle(title);
                tmp.setName(p.getTitle());
                tmp.setTexts(StringUtils.join(p.getTexts(),";"));
                indicationIndices.add(tmp);
            });
        });


        // 写法1
        String fileName = "C:\\Users\\Shanzhen\\Desktop\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, IndicationIndex.class).sheet("模板").doWrite(indicationIndices);
    }
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }



    @Data
    public class DemoData {
        @ExcelProperty("字符串标题")
        private String string;
        @ExcelProperty("日期标题")
        private Date date;
        @ExcelProperty("数字标题")
        private Double doubleData;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }
    @Test
    public void toBase64(){
        final String imgStr = ImgBase64Util.getImgStr("C:\\Users\\Shanzhen\\Desktop\\1.gif");
        log.info("{}",imgStr);
//        InputStream in = null;
//        byte[] data = null;
//        try {
//            in = new FileInputStream("C:\\Users\\Shanzhen\\Desktop\\1.gif");
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        // 对字节数组进行Base64编码，得到Base64编码的字符串
//        BASE64Encoder encoder = new BASE64Encoder();
//        String base64Str = encoder.encode(data);
        File file = new File("C:\\Users\\Shanzhen\\Desktop\\1.txt");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(imgStr);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Indication implements Serializable{
        @ExcelProperty("编号")
        private String _id;
        @ExcelProperty("内容")
        private Content content;

    }
    @Data
    public static class Content implements Serializable{
        private List<Paragraphs> paragraphs;
        private String summary;
        private String title;
    }
    @Data
    public static class Paragraphs implements Serializable{
        private List<String> texts;
        private String title;
    }

    @Data
    public static class Kk implements Serializable{
        private String _id;
        private KkContent content;
    }

    @Data
    public static class KkContent implements Serializable{
        private String title;
        private List<KkContext> contents;
    }

    @Data
    public static class KkContext implements Serializable{
        private List<Description> description;
        private String subtitle;
    }

    @Data
    public static class Description implements Serializable{
        private List<Detail> details;
        private String name;
    }

    @Data
    public static class Detail implements Serializable{
        private String detail;
    }
    @Data
    public static class IndicationIndex implements Serializable{
        @ColumnWidth(15)
        @ExcelProperty("指标名称")
        private String title;
        @ColumnWidth(80)
        @ExcelProperty("指标说明")
        private String summary;
        @ColumnWidth(15)
        @ExcelProperty("模块名称")
        private String name;
        @ColumnWidth(200)
        @ExcelProperty("模块内容")
        private String texts;
    }
    @Data
    public static class IndicationBo implements Serializable{
        @ColumnWidth(15)
        @ExcelProperty("指标名称")
        private String title;
        @ColumnWidth(80)
        @ExcelProperty("指标说明")
        private String summary;
        @ColumnWidth(15)
        @ExcelProperty("模块名称")
        private String name;

    }

    @Test
    public void toXml() throws Exception {
        WeChatTextMsg weChatTextMsg = new WeChatTextMsg();
        weChatTextMsg.setToUserName("123");
        weChatTextMsg.setContent("content");
        weChatTextMsg.setCreateTime("2020年11月10日09:20:28");
        weChatTextMsg.setFromUserName("zhuanglei");
        weChatTextMsg.setMsgType("1");

        String msg = JaxpUtil.toXML(weChatTextMsg);
        log.info("result:{}",JSON.toJSONString(msg));
//        final PMResponseInfo pmResponseInfo1 = JaxpUtil.fromXML(msg, PMResponseInfo.class);
//        log.info("object:{}",JSON.toJSONString(pmResponseInfo1));
    }


    @Test
    public void testCom(){
        List<Long> orderAddGoodsBos = new ArrayList<>();
        orderAddGoodsBos.add(1L);
        orderAddGoodsBos.add(2L);
        orderAddGoodsBos.add(3L);
        orderAddGoodsBos.add(4L);

        Long addGoodsPrice = 0L;
        addGoodsPrice = orderAddGoodsBos.stream()
                .mapToLong(p -> {
                    return p.longValue()+1;
                })
                .sum();
        System.out.println(addGoodsPrice);
    }

    @Test
    public void testJson(){
        String str = "";
        String brandCodeStr = StringUtils.join(Arrays.asList(str),"\\|");
        System.out.println(brandCodeStr);
        System.out.println(CollectionUtils.isEmpty(Arrays.asList(str)));
    }

    @Test
    public void testArrayList(){
        String text = "/img/banner/banner_1.jpg,/img/banner/banner_2.jpg,/img/banner/banner_3.jpg";
        String text1 = "/img/banner/banner_1.jpg";
        final String[] split = text.split(",");
        System.out.println(JSON.toJSONString(split));
        List<String> bannersList = Arrays.asList(text1);
        System.out.println(JSON.toJSONString(bannersList));
    }

    @Test
    public void sort() throws InterruptedException {
        List<Date1> list = new ArrayList<>();
        Date1 d = new Date1();
        Date1 d2 = new Date1();
        d.setA("GDS846707560");
        d2.setA("GDS253059089");
        list.add(d);
        list.add(d2);

        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Date1::getA))), ArrayList::new));
        System.out.println(JSON.toJSONString(list));
    }

    @Data
    public class Date1{
        String a ;

    }

    @Test
    public void test123() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(1594310400000L);
        System.out.println(sdf.format(date));
        String dateStr = sdf.format(date);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -1);
        String newDateStr = sdf.format(calendar.getTime());
        newDateStr = newDateStr + " " + "12:00:00";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sdf1.parse(newDateStr);
        Date now = new Date();
        System.out.println(!(now.getTime() < d1.getTime()));
    }

    private void de(Long a, Long b){
        if(a>=b){
            b = 0L;
        }else {
            b -= a;
        }
    }

    private void add(Long a, Dest b){
        b.setB(a+b.getB());
    }

    public static final Date getDateWithAddHours(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(11, amount);
        return calendar.getTime();
    }
    public static final Date getDateWithAddDays(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, amount);
        return calendar.getTime();
    }

    public static final Date getWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }
    @Data
    public class Dest{
        long b;
    }

    /**
     * 策略模式
     */
    @Test
    public void testStrategy(){
        int numb1 = 10;
        int numb2 = 2;
        StrategyContext strategyContext = new StrategyContext(new OperationAddByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
        strategyContext = new StrategyContext(new OperationSubtractByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
        strategyContext = new StrategyContext(new OperationMultiplyByStrategy());
        strategyContext.executeStrategy(numb1,numb2);
    }

    /**
     * 抽象工厂模式
     */
    @Test
    public void testAbstactFactory(){
        //获取形状工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");
        //获取形状为 Circle 的对象
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        //调用 Circle 的 draw 方法
        shape1.draw();
        //获取形状为 Rectangle 的对象
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        //调用 Rectangle 的 draw 方法
        shape2.draw();
        //获取形状为 Square 的对象
        Shape shape3 = shapeFactory.getShape("SQUARE");
        //调用 Square 的 draw 方法
        shape3.draw();
        //获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");
        //获取颜色为 Red 的对象
        Color color1 = colorFactory.getColor("RED");
        //调用 Red 的 fill 方法
        color1.fill();
        //获取颜色为 Green 的对象
        Color color2 = colorFactory.getColor("Green");
        //调用 Green 的 fill 方法
        color2.fill();
        //获取颜色为 BLACK 的对象
        Color color3 = colorFactory.getColor("BLACK");
        //调用 Blue 的 fill 方法
        color3.fill();
    }

    /**
     * 建造者模式
     */
    @Test
    public void testBuild(){
        MealBuilder mealBuilder = new MealBuilder();

        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost: " +vegMeal.getCost());

        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost: " +nonVegMeal.getCost());
    }

    /**
     * 责任链模式
     */
    @Test
    public void testChainPattern(){
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        errorLogger.logMessage(AbstractLogger.INFO, "This is an information.");
        System.out.println("INFO的责任链完成");
        errorLogger.logMessage(AbstractLogger.DEBUG,
                "This is a debug level information.");
        System.out.println("DEBUG的责任链完成");
        errorLogger.logMessage(AbstractLogger.ERROR,
                "This is an error information.");
        System.out.println("ERROR的责任链完成");
    }

    /**
     * 适配者模式
     */
    @Test
    public void testAdapter(){
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }

    /**
     * 桥接模式
     */
    @Test
    public void testBridge(){
        com.zl.demo.pattern.bridge.Shape redCircle = new Circle(100,100, 10, new RedCircle());
        com.zl.demo.pattern.bridge.Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }

    /**
     * 过滤器模式
     */
    @Test
    public void filterBrige(){
        List<Person> persons = new ArrayList<Person>();

        persons.add(new Person("Robert","Male", "Single"));
        persons.add(new Person("John","Male", "Married"));
        persons.add(new Person("Laura","Female", "Married"));
        persons.add(new Person("Diana","Female", "Single"));
        persons.add(new Person("Mike","Male", "Single"));
        persons.add(new Person("Bobby","Male", "Single"));

        Criteria male = new CriteriaMale();
        Criteria female = new CriteriaFemale();
        Criteria single = new CriteriaSingle();
        Criteria singleMale = new AndCriteria(single, male);
        Criteria singleOrFemale = new OrCriteria(single, female);

        System.out.println("Males: ");
        printPersons(male.meetCriteria(persons));

        System.out.println("\nFemales: ");
        printPersons(female.meetCriteria(persons));

        System.out.println("\nSingle Males: ");
        printPersons(singleMale.meetCriteria(persons));

        System.out.println("\nSingle Or Females: ");
        printPersons(singleOrFemale.meetCriteria(persons));
    }
    public static void printPersons(List<Person> persons){
        for (Person person : persons) {
            System.out.println("Person : [ Name : " + person.getName()
                    +", Gender : " + person.getGender()
                    +", Marital Status : " + person.getMaritalStatus()
                    +" ]");
        }
    }

    @Test
    public void testPrototype(){
        ShapeCache.loadCache();

        com.zl.demo.pattern.prototype.Shape clonedShape = (com.zl.demo.pattern.prototype.Shape) ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());

        com.zl.demo.pattern.prototype.Shape clonedShape2 = (com.zl.demo.pattern.prototype.Shape) ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        com.zl.demo.pattern.prototype.Shape clonedShape3 = (com.zl.demo.pattern.prototype.Shape) ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
    }

    @Test
    public void testComposite(){
        Employee CEO = new Employee("John","CEO", 30000);

        Employee headSales = new Employee("Robert","Head Sales", 20000);

        Employee headMarketing = new Employee("Michel","Head Marketing", 20000);

        Employee clerk1 = new Employee("Laura","Marketing", 10000);
        Employee clerk2 = new Employee("Bob","Marketing", 10000);

        Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob","Sales", 10000);

        CEO.add(headSales);
        CEO.add(headMarketing);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        //打印该组织的所有员工
        System.out.println(CEO);
        for (Employee headEmployee : CEO.getSubordinates()) {
            System.out.println("headEmployee:"+headEmployee);
            for (Employee employee : headEmployee.getSubordinates()) {
                System.out.println("employee:"+employee);
            }
        }
    }
}
