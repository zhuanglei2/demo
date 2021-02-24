package com.zl.client.client;


import com.zl.server.api.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * rmi是基于java的远程方法调用技术，是java特有的一种rpc的实现
 * rmi客服端方法
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/23 9:52
 */
public class ClientMain {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //服务引入
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8801/helloService");
        //调用远程方法
        System.out.println("RMI 服务器的返回结果是："+helloService.sayHello("zhuanglei"));
    }
}
