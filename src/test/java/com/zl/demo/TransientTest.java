package com.zl.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.*;

/**
 * @Author zhuanglei
 * @Date 2021/6/13 10:01 上午
 * @Version 1.0
 */
public class TransientTest {
    public static void main(String[] args) {
        User1 user1 = new User1();
        user1.setName("123");
        user1.setAge(12);
        String s = JSON.toJSONString(user1);
        System.out.println("序列化后"+s);
        System.out.println("反序列化"+JSON.toJSONString(JSON.parseObject(s,User1.class)));
        User2 user2 = new User2();
        user2.setAge(18);
        user2.setName("变化后的");
        System.out.println(JSON.toJSONString(user2));
        System.out.println("反序列化"+JSON.toJSONString(JSON.parseObject(JSON.toJSONString(user2),User2.class)));
    }




}
@Data
class User1 implements Serializable {
    public transient String name;
    public Integer age;
}
@Data
class User2 implements Externalizable {
    public String name;
    public Integer age;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }
}
