package com.zl.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zl.demo.common.util.CryptUtil;
import com.zl.demo.common.util.ImageUtils;
import com.zl.demo.component.SzValidator;
import com.zl.demo.dto.Apple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    /**
     * 验证帮助
     */
    @Resource
    protected SzValidator szValidator;


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Test
    public void testMax(){
        List<GoodsDo> goodsList = new ArrayList<>();
        GoodsDo d1 = new GoodsDo("1",300L);
        GoodsDo d2 = new GoodsDo("2",400L);
        goodsList.add(d1);
        goodsList.add(d2);
        GoodsDo goodsDo = goodsList.stream()
        .max(Comparator.comparing(GoodsDo::getSalePrice)).get();
        System.out.println(goodsDo);
    }

    @Data
    @AllArgsConstructor
    class GoodsDo{
        private String goodsCode;
        private Long salePrice;
    }

    @Test
    public void testCompare(){
        boolean a = false;
        String j = getJ(a);
        System.out.println(a);
    }

    private String getJ(boolean a) {
        a = true;
        return "123";
    }


    @Test
    public void testAddDate(){
        Date afterDate = new Date(new Date().getTime() + 600000);
        System.out.println(afterDate);
    }

    @Test
    public void testLong(){
        Long a = 1L;
        add(a);
        System.out.println(a);
    }

    private void add(Long a) {
        a = a+1L;
    }


    @Test
    public void testJson(){
        PayedMsgDto payedMsgDto = new PayedMsgDto();
        List<String> list = new ArrayList<>();
        list.add("299514967027");
        payedMsgDto.setOrderCodes(list);
        System.out.println(JSON.toJSONString(payedMsgDto));
        System.out.println(new BigDecimal(0L).movePointLeft(3).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
    @Data
    public static class PayedMsgDto{
        private List<String> orderCodes;
    }

    @Test
    public void testBigDecimal(){
        BigDecimal a = new BigDecimal("0");
        BigDecimal b = new BigDecimal("10");
        BigDecimal total = a.subtract(b);
        /**20200927 新增逻辑：如果语音加项包金额为空,且prePayAmt不为0，默认选择*/
        if(total.equals(new BigDecimal("0"))){
            if(b.compareTo(new BigDecimal("0"))>0){
                System.out.println("true");
            }
        }
    }


    @Test
    public void testSub1(){
        List<String> orderCodes = new ArrayList<>();
        orderCodes.add("596987982112");
        PayedMsgDto payedMsgDto = new PayedMsgDto();
        payedMsgDto.setOrderCodes(orderCodes);
        String str = JSON.toJSONString(payedMsgDto);
        Map map = (Map) JSON.parse(JSON.toJSONString(payedMsgDto));
        orderCodes = (List<String>) map.get("orderCodes");
        System.out.println(orderCodes);
    }


    @Test
    public void testFilter(){
        List<Apple> list = new ArrayList<>();
        Apple apple = new Apple();
        apple.setName("1");
        Apple apple1 = new Apple();
        apple1.setName("0");
        Apple apple2 = new Apple();
        apple2.setName("0");
        list.add(apple);
        list.add(apple1);
        list.add(apple2);
        list.add(apple2);
        List<Apple> aa = list.stream().sorted(Comparator.comparing(Apple::getName)).collect(Collectors.toList());
        System.out.println(aa);
    }

    /**
     * lambda分页
     */
    @Test
    public void testLambdaLimit(){
        List<Apple> list = new ArrayList<>();
        Apple apple = new Apple();
        apple.setName("apple");
        Apple apple1 = new Apple();
        apple1.setName("apple");
        Apple apple2 = new Apple();
        apple2.setName("apple2");
        list.add(apple);
        list.add(apple1);
        list.add(apple2);
        list.add(apple2);
        List<String> result = list.stream().map(Apple::getName).collect(Collectors.toList());
        System.out.println(result);
    }

    @Test
    public void testCutImage() throws  IOException {
        File input = new File("C:\\Users\\Shanzhen\\Desktop\\1.jpg");
        File output = new File("C:\\Users\\Shanzhen\\Desktop\\3.jpg");
        BufferedImage image = ImageIO.read(input);


        BufferedImage result = ImageUtils.imageCutByRectangle(image, 750,1206);
        String suffix = input.getName().substring(input.getName().lastIndexOf(".") + 1);
        ImageIO.write(result, suffix, output);

    }


    @Test
    public void test1123(){
       List<UserBo> list = new ArrayList<>();
       list.add(new UserBo(1,"2"));
       list.add(new UserBo(2,"2"));
       list.add(new UserBo(2,"2"));
       list.add(new UserBo(3,"2"));
       list.add(new UserBo(3,"2"));
       list.add(new UserBo(3,"2"));
       list.add(new UserBo(4,"2"));
       list = list.stream().distinct().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }

    private void getTest(String a) {
        a="123";
    }

    @Test
    public void testSubString(){
        String str = "asdasdasdqwexznmiwjrer_conf={'5001254'}wqevcvxvqweqweaasdknk_conf={'3000'}asdkqwhheasdnn_conf={'5001'}";
        String tmp = str;
        int count = 0;
        for (int i = 0; i < way2(str,"_conf={"); i++) {
            tmp = tmp.substring(tmp.indexOf("_conf={")+8,tmp.length());
            String number = tmp.substring(0,tmp.indexOf("'}"));
            if(Integer.parseInt(number)>5000)count++;
        }
        System.out.println(count);
    }

    public static int way2(String st,String M) {
        int count = (st.length()-st.replace(M, "").length())/M.length();
        return count;
    }


    @Test
    public void test1122(){
        HashMap<String,String> list = new HashMap();
        list.put("11","instit/brand/logo/meinian.jpg");
        list.put("12","instit/brand/logo/ruici.jpg");
        list.put("13","instit/brand/logo/aikang.jpg");
        list.put("14","instit/brand/logo/cimin.jpg");
        List<String> list1 = new ArrayList<>();
        list1.add("11");
        list1.add("12");
        list1.add("13");
        list1.add("14");
        System.out.println(list1.contains("14"));
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void tes1t(){
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap<>();
        hashMap.put("dimensions",Arrays.asList("pro","预约人数","体检人数"));
        List<Map<String,String>> list = new ArrayList<>();
        LinkedHashMap<String,String> a = new LinkedHashMap();
        a.put("pro","机构1");
        a.put("预约人数","43");
        a.put("体检人数","22");
        list.add(a);
        LinkedHashMap<String,String> b = new LinkedHashMap();
        b.put("pro","机构2");
        b.put("预约人数","433");
        b.put("体检人数","222");
        list.add(b);
        hashMap.put("source",list);
        System.out.println(JSON.toJSONString(hashMap));
    }


    @Test
    public void test111333(){
        List<UserBo> list = new ArrayList<>();
        list.add(new UserBo(100, ""));
        list.add(new UserBo(100, ""));
        list.add(new UserBo(200, "Aao"));
        list.add(new UserBo(300, "Mahesh"));
        List<String> re = list.stream().map(UserBo::getUserName).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(re));
    }

    /**
     * 复杂的lambda
     */
    @Test
    public void testComLambda(){
        List<UserBo> list = new ArrayList<>();
        list.add(new UserBo(100, "Mohan"));
        list.add(new UserBo(100, "Sohan"));
        list.add(new UserBo(200, "Aao"));
        list.add(new UserBo(300, "Mahesh"));
        list.add(new UserBo(400, "Mahesh"));
        list.add(new UserBo(1000, "Mahesh"));
        getOperationByShipmentIds(list);
        getNotIncludeOperationByShipmentIds(list);
        getSum(list);
    }

    private void getSum(List<UserBo> list) {
        final int sum = list.stream().mapToInt(obj -> obj.getUserId()).sum();
        log.info("list算总数为{}",sum);
    }


    /**
     * 过滤+分组查询
     * @param list
     */
    private void getNotIncludeOperationByShipmentIds(List<UserBo> list) {
        List<Integer> ids = Arrays.asList(100,300);
        List<UserBo> tmp = list;
        List<UserBo> map = tmp
                .stream()
                .filter(op->!ids.contains(op.getUserId()))
                .collect(Collectors.toList());
        log.info("getNotIncludeOperationByShipmentIds"+JSON.toJSONString(map));
    }

    /***
     * 传入数组ids，在list<Obj>上操作，找出Obj中id想匹配的，并且按照id进行collect成map(这里假设找出来的按照id不重复）
     * @param list
     */
    public void getOperationByShipmentIds(List<UserBo> list){
        List<Integer> ids = Arrays.asList(100,300);
        List<UserBo> tmp = list;
        Map<Integer, List<UserBo>> map = tmp
                .stream()
                .filter(op->ids.contains(op.getUserId()))
                .collect(groupingBy(UserBo::getUserId));
        log.info("getOperationByShipmentIds"+JSON.toJSONString(map));
    }

    @Test
    public void testLambda(){
        List<UserBo> list = new ArrayList<>();
        list.add(new UserBo(100, "Mohan"));
        list.add(new UserBo(100, "Sohan"));
        list.add(new UserBo(300, "Mahesh"));
        //排序
        List<UserBo> sorts = list
                .stream()
                .sorted(Comparator.comparing(UserBo::getUserId).reversed())
                .collect(Collectors.toList());
        log.info("list排序"+JSON.toJSONString(sorts));
        //过滤
        List<UserBo> filters = list.stream().filter(u->u.getUserId()==100).collect(Collectors.toList());
        log.info("list过滤"+JSON.toJSONString(filters));
        //分组
        Map<Integer,List<UserBo>> map = list.stream().collect(groupingBy(UserBo::getUserId));
        //根据某个属性去重
        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection
                (()->new TreeSet<>(Comparator.comparing(o->o.getUserId()))),ArrayList::new));
        log.info("list去重"+JSON.toJSONString(list));
        log.info("list分组"+JSON.toJSONString(map));
    }

    @Test
    public void test111(){

        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");

        List<String> list2 = new ArrayList<String>();
        list2.add("B");
        list2.add("E");

        list1.retainAll(list2);
        System.out.println(list1);
    }

    @Test
    public void testMail(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,8).toUpperCase();
        System.out.println(uuid);
    }
    /**
     * 判断是否为邮箱
     * @param str
     * @return
     */
    public boolean isEmail(String str) {
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    @Test
    public void testSortStack(){
        Stack<String> stack = new Stack<String>();
        List<String> list  = new ArrayList<>();
        List<String> list2  = new ArrayList<>();
        List<String> addGoods = new ArrayList<>();
        String[] shu = new String[]{"4", "5", "6"};
        String[] shu2 = new String[]{"0", "1", "2", "3"};

        f(list,stack,shu,1,0,0); // 从这个数组3个数中选择1个
        f(list2,stack,shu2,2,0,0); // 从这个数组4个数中选择2个
        List<String[]> dataList = new ArrayList<String[]>();
        String[] s1 = list.toArray(new String[0]);
        String[] s2 = list2.toArray(new String[0]);
        dataList.add(s1);
        dataList.add(s2);
        List<String[]> resultList = ArrayCombination.combination(dataList, 0, null);
        resultList.forEach(str->{
            addGoods.add(org.apache.commons.lang3.StringUtils.join(str,","));
        });
        addGoods.forEach(addGood->{
            System.out.println(addGood);
        });

    }
    /**
     *
     * @param shu  元素
     * @param targ  要选多少个元素
     * @param has   当前有多少个元素
     * @param cur   当前选到的下标
     *
     * 1    2   3     //开始下标到2
     * 1    2   4     //然后从3开始
     */
    private static void f(List<String> list,Stack<String> stack,String[] shu, int targ, int has, int cur) {
        if(has == targ) {
            String[] o1 = stack.toArray(new String[0]);
            list.add(org.apache.commons.lang3.StringUtils.join(o1,","));
            return;
        }

        for(int i=cur;i<shu.length;i++) {
            if(!stack.contains(shu[i])) {
                stack.add(shu[i]);
                f(list,stack,shu, targ, has+1, i);
                stack.pop();
            }
        }

    }

    /**
     * 新密码的规则8-30位，包括字母、数字和特殊符号的组合（三种都需要包含）
     */
    @Test
    public void testSub(){
        String reg = "001001";

        System.out.println(reg.substring(0,100));
    }

    /**
     * 新密码的规则8-30位，包括字母、数字和特殊符号的组合（三种都需要包含）
     */
    @Test
    public void testPsd(){
        String reg = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W]).{8,30}$";
        String str = "@111111a";
        System.out.println(str.matches(reg));
    }

    @Test
    public void sort(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("d");
        list.add("a");
        list.add("a");
        list.add("e");
        list.add("e");
        list.add("b");
        list.add("d");
        list.remove("d");


        System.out.println(list);
    }


    @Test
    public void testExecute(){
        String orderCode = "123121";
        taskExecutor.execute(()->{
            refundTask(orderCode);
        });
    }

    public void refundTask(String orderCode){
        log.info("异步执行完成orderCode"+orderCode);
    }

    @Test
    public void testDecimalFormat(){
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> tempCitysLists = Arrays.asList(
                new BigDecimal("0.79").doubleValue(),
                new BigDecimal("0.01").doubleValue(),
                new BigDecimal("12.01").doubleValue(),
                new BigDecimal("100").doubleValue(),
                new BigDecimal("98").doubleValue(),
                new BigDecimal("100.0").doubleValue(),
                new BigDecimal("0.00").doubleValue(),
                new BigDecimal("0").doubleValue()
        );
        for (int i = 0; i < tempCitysLists.size(); i++) {
            double tmp = tempCitysLists.get(i);
            String rate = df.format(tmp);
            System.out.println(rate);
        }
    }


    @Test
    public void testList(){
        List<String> finalOrderlist = Arrays.asList("","","1","2");
        finalOrderlist = finalOrderlist.stream().collect(  Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.toString()))),
                ArrayList::new));
        System.out.println(finalOrderlist.size());
    }
    /**
     * 校验参数合法性
     *
     * @param o
     */
    protected void checkParam(Object o) {

        /** 校验参数 */
        Map<String, String> errorMsg = szValidator.vailed(o);

        Preconditions.checkArgument(errorMsg.size() == 0,
                errorMsg);
    }

    @Test
    public void testAspect(){
        Apple apple = new Apple();
        apple.setName("苹果");
        apple.setSize("200g");
        apple.setAmount("1吨");
        apple.setPrice("5");
        checkParam(apple);
        CryptUtil.enCrypt(apple,"");
        System.out.println(apple);
        CryptUtil.deCrypt(apple,"");
        System.out.println(apple);
    }



    @Test
    public void testSplit(){
//        String reportName = "xx公司#团检白皮书";
//        String titleName = "";
//        if(StringUtils.isNotBlank(reportName)){
//            String[] split = reportName.split("#");
//            if(split!=null&&split.length>=2){
//                reportName = split[0];
//                titleName = split[1];
//            }
//        }
//        System.out.println(reportName);
//        System.out.println(titleName);
        List<City> citys = new ArrayList<>();
        citys.add(new City("北京2",new BigDecimal(2)
                .multiply(new BigDecimal(100)).divide(new BigDecimal(14),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
        citys.add(new City("北京14",new BigDecimal(14)
                .multiply(new BigDecimal(100)).divide(new BigDecimal(14),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
        citys.add(new City("北京24",new BigDecimal(24)
                .multiply(new BigDecimal(100)).divide(new BigDecimal(14),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
        citys.add(new City("北京33",new BigDecimal(33)
                .multiply(new BigDecimal(100)).divide(new BigDecimal(14),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
        citys.add(new City("北京7",new BigDecimal(7)
                .multiply(new BigDecimal(100)).divide(new BigDecimal(14),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
        citys = citys.stream().sorted(Comparator.comparing(City::getCityRate).reversed()).collect(Collectors.toList());
        citys.forEach(city -> System.out.println(city.toString()));
    }

    @Test
    public void testSort(){

    }



    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();

        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByValue()
                        .reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    @Data
    @ToString
    public static class City implements Serializable {

        public City(String cityName,double cityRate){
            this.cityName = cityName;
            this.cityRate = cityRate;
        }
        /*城市名称*/
        private String cityName;
        /*城市比例*/
        private double cityRate;
    }


    public static String T_money(int money) {
        int num=0;
        String[] MoneyChinese= {"零","一","二","三","四","五","六","七","八","九"};//汉字一到九
        String[] ChineseNum= {"","拾","百","千","万","亿"};//汉字单位
        System.out.println(ChineseNum[0]);
        Integer Money=new Integer(money);//转化为Integer方便转发类型
        char[] Moneynum=Money.toString().toCharArray();//转换成字符串方便转换成整形
        String[] MoneyChineseNum =new String[Moneynum.length];//用来存放转换后的整形数组
        for(int i=0;i<Moneynum.length;i++) {
            num=Moneynum[i]-48;//转换成整形
            MoneyChineseNum[i]=MoneyChinese[num];//用来映射汉子一到九
        }
        StringBuffer MoneyTime=new StringBuffer();//字符缓冲区方便添加
        int nums=0;//统计要出现的”万“
        int Numss=0;//统计要出现的”亿“
        for(int i=MoneyChineseNum.length-1;i>=0;i--) {

            if(!MoneyChineseNum[i].equals("零"))
            {
                if(!ChineseNum[nums].equals("万"))
                    MoneyTime.append(ChineseNum[nums]);
            }

            if(nums==4&&Numss==0)//添加“万”字因为万字必须出现（必能想千、百、拾，前面有零而省去）
            {
                MoneyTime.append(ChineseNum[nums]);
                nums=0;
                Numss=1;
                if(!MoneyChineseNum[i].equals("零"))
                {MoneyTime.append(MoneyChineseNum[i]);}//如果"万"字前有"零"除去万字前的 "零"
            }
            else if(nums==4&&Numss==1)//添加“亿”字因为万字必须出现（必能想千、百、拾，前面有零而省去）
            {
                MoneyTime.append(ChineseNum[nums+1]);
                nums=0;
                Numss=0;
                if(!MoneyChineseNum[i].equals("零"))
                {MoneyTime.append(MoneyChineseNum[i]);}//如果"亿"字前有"零"除去亿字前的 "零"
            }
            else {
                MoneyTime.append(MoneyChineseNum[i]);
            }
            ++nums;
        }
        return MoneyTime.reverse().toString();
    }

    @Test
    public void testDouble(){
        for (int i = 0; i < 20; i++) {
            System.out.println(int2chineseNum(i+1));
        }
    }
    public static String int2chineseNum(int src) {
        final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String unit[] = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String dst = "";
        int count = 0;
        while(src > 0) {
            dst = (num[src % 10] + unit[count]) + dst;
            src = src / 10;
            count++;
        }
        return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }

    @Test
    void contextLoads() {
        int batchControl = 5;
        List<String> faOrderSyncList = new ArrayList<>();
        List<String> orderList = Arrays.asList("a","b","c","d","e","f");
        int length = orderList.size();
        int pageCount = (int)Math.ceil(1.0*length/batchControl);
        if(pageCount<=1){
            /*需要推送的数据*/
            faOrderSyncList.addAll(orderList);
        }else {
            for (int i =0;i<pageCount;i++){
                if(((i+1)*batchControl)<=length){
                    faOrderSyncList.addAll(orderList.subList(i*batchControl,(i+1)*batchControl));
                }else {
                    faOrderSyncList.addAll(orderList.subList(i*batchControl,length));
                }
            }
        }
        faOrderSyncList.stream().forEach(a->{System.out.println(a.toString());});
    }


    @Test
    void test(){
        List<String> orderList = Arrays.asList("a","b","c","d","e","f");
        orderList = orderList.subList(1,5);
        orderList.stream().forEach(a->{System.out.print(a);});
    }

    @Test
    void test1(){
        List<String> list = new ArrayList<>();
        list = list.stream().limit(1).collect(Collectors.toList());
    }


    @Test
    public void testStream(){
//        List<String> list = Arrays.asList("a,b,c,d,a");
//        List<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toList());
//        System.out.println(collect);
//        Stream<List<Integer>> inputStream = Stream.of(
//                Arrays.asList(1),
//                Arrays.asList(2, 3),
//                Arrays.asList(4, 5, 6)
//        );
//        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream().map(n->n+1));
//        List<Integer> list =outputStream.collect(Collectors.toList());
//        System.out.println(list.toString());
//        Stream<List<String>> stream = Stream.of(Arrays.asList("a","c","d","e","f","g"),Arrays.asList("p","i","e","w","q"),Arrays.asList("z","x","c"));
//        Stream<String> stringStream = stream.flatMap((childList) -> childList.stream().map(String::toUpperCase));
//        stringStream.forEach(s->System.out.println(s));
//        Integer[] sixNums = {1,2,3,4,5,6};
//        Integer[] evens = Stream.of(sixNums).filter(n->n%2==0).toArray(Integer[]::new);
//        Arrays.stream(evens).forEach(s->System.out.println(s));
        List<UserBo> list = new ArrayList<>();
        list.add(new UserBo(100, "Mohan"));
        list.add(new UserBo(100, "Sohan"));
        list.add(new UserBo(300, "Mahesh"));
        /*Collection.toMap(map的key,map的value,(v1, v2) -> v1中如果v1与v2的key值相同，选择v1作为那个key所对应的value值)*/
        Map<Integer, Object> map=list.stream().collect(Collectors.toMap(UserBo::getUserId, v -> v, (v1, v2)-> v2));
        map.forEach((v1, v2) -> System.out.println("Key: " + v1 +", value: "+ v2));

    }

    class UserBo{
        private int UserId;
        private String UserName;
        public UserBo(int userId, String userName) {
            super();
            UserId = userId;
            UserName = userName;
        }
        public int getUserId() {
            return UserId;
        }
        public void setUserId(int userId) {
            UserId = userId;
        }
        public String getUserName() {
            return UserName;
        }
        public void setUserName(String userName) {
            UserName = userName;
        }
        @Override
        public String toString() {
            return "UserBo [UserId=" + UserId + ", UserName=" + UserName + "]";
        }

    }

    @Test
    void test3(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        List<Integer> temp = list;

        temp=list.subList(5,list.size());
        temp.forEach(i->{
            System.out.println(i);
        });

        temp=list.subList(0,5);
        temp.forEach(i->{
            System.out.println(i);
        });
    }


}
