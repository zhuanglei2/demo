package com.zl.demo.classloader;

import java.io.*;

/**
 * 如果要符合双亲委派模型，重写findclass方法，要破坏的化，重写loadClass方法（双亲委派的模型实现）
 * @Author zhuanglei
 * @Date 2021/4/26 11:53 上午
 * @Version 1.0
 */
public class PathClassLoader extends ClassLoader{
    private String classPath;
    PathClassLoader(String classPath){
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getData(name);
        if(classData == null){
            throw new ClassNotFoundException();
        }else {
            return defineClass(name,classData,0,classData.length);
        }
    }

    private byte[] getData(String name) {
        String path = classPath+ File.separatorChar
                +name.replace('.',File.separatorChar)+".class";
        try{
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int num = 0;
            while((num = is.read(bytes))!=-1){
                stream.write(bytes,0,num);
            }
            return stream.toByteArray();
        }catch (Exception e){

        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader cl = new PathClassLoader("/Users/zhuanglei/IdeaProjects/demo/");
        Class c = cl.loadClass("com.zl.demo.classloader.TestLoader");
        System.out.println(c.newInstance());
    }
}
