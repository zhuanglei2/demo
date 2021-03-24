package com.zl.demo.countdownlatch;

import com.alibaba.fastjson.JSON;
import com.zl.demo.http.HttpClientUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/6 15:38
 */
public class CountDownLatchTest {

    //并发数
    private static final int threadNum = 10;

    //倒计时数 发令枪 用于制造线程的并发执行
    private static CountDownLatch cdl = new CountDownLatch(threadNum);

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<threadNum;i++){
            HttpTest httpTest = new HttpTest(i);
            new Thread(httpTest).start();
        }
        cdl.await();
    }

    @Data
    public static class HttpTest implements Runnable{

        private Object object;

        public HttpTest(Object object){
            this.object = object;
        }


        @Override
        public void run() {
            cdl.countDown();
            System.out.println("第"+JSON.toJSONString(object)+"次并发测试的任务开始");
            try {
                Map<String,Object> map = new HashMap<>();
                map.put("szUserId","14110080728");
                map.put("szUserToken","2abc23224ed705ddd8ea044511f4758a");
                map.put("platformName","SHANZHEN");
                map.put("institCode","INS473668310");
                map.put("familyId",3016489L);
                map.put("batchCode","bGcOhhYUzX");
                map.put("isFamily",0);
                map.put("isNewAccount","1");
                map.put("needAuthFlag",1);
                map.put("reserveTime","2021-03-24");

                String resp = HttpClientUtils.post("http://47.100.93.10:8140/sz-biz-gi/order/exam/placeTobOrderB2B",map,"UTF-8");
                System.out.println("接口请求测试返回,resp:"+JSON.toJSONString(resp));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("第"+JSON.toJSONString(object)+"次并发测试的任务结束");
        }
    }
}
