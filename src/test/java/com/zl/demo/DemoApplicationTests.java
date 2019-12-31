package com.zl.demo;

import com.google.common.base.Preconditions;
import com.zl.demo.common.CryptUtil;
import com.zl.demo.component.SzValidator;
import com.zl.demo.dto.Apple;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

    /**
     * 验证帮助
     */
    @Resource
    protected SzValidator szValidator;
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
