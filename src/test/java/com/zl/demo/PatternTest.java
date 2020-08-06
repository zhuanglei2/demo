package com.zl.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import sun.util.calendar.CalendarUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/5 14:02
 */
@Slf4j
@SpringBootTest
public class PatternTest {

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
