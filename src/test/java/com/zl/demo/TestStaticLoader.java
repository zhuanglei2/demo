package com.zl.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author zhuanglei
 * @Date 2021/4/26 2:31 下午
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class TestStaticLoader {

    @Test
    public void test(){
        Class<?> class0 = TestStaticLoader.class;
        try{
            System.out.println(class0.getClassLoader() instanceof MyClassLoader);
            Class<?> class1 = class0.getClassLoader().loadClass("com.zl.demo.TestStaticLoader");
            ClassLoader classLoader = new MyClassLoader();
            Class<?> class2 = classLoader.loadClass("com.zl.demo.TestStaticLoader");

            //不同的类加载器加载的类，是相同的，原因：双亲委派模型未被破坏
            System.out.println(class1.hashCode());
            System.out.println(class2.hashCode());
            //类加载器
            System.out.println(class1.getClassLoader());
            System.out.println(class2.getClassLoader());
            System.out.println(class1.equals(class2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //自定义一个类加载器从指定磁盘目录加载类
    public class MyClassLoader extends ClassLoader {
        //不破坏双亲委派模型
        @Override
        protected Class<?> findClass(String name) {
            String myPath = "/Users/zhuanglei/IdeaProjects/demo/target/test-classes/" + name.replace(".","/") + ".class";
            System.out.println(myPath);
            byte[] classBytes = null;
            FileInputStream in = null;

            try {
                File file = new File(myPath);
                in = new FileInputStream(file);
                classBytes = new byte[(int) file.length()];
                in.read(classBytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
            return clazz;
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            //破坏了双亲委派模型，因此拿不到对应的object类
            String myPath = "/Users/zhuanglei/IdeaProjects/demo/target/test-classes/" + name.replace(".","/") + ".class";
            System.out.println(myPath);
            byte[] classBytes = null;
            FileInputStream in = null;

            try {
                File file = new File(myPath);
                in = new FileInputStream(file);
                classBytes = new byte[(int) file.length()];
                in.read(classBytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();
            Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
            return clazz;
        }
    }

}
