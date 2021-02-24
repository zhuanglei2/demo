package com.zl.server.listener;

import com.zl.server.api.HelloService;
import com.zl.server.service.HelloServiceImpl;
import com.zl.server.support.CustomerSocketFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/23 10:01
 */
public class ServerMain {

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        HelloService helloService = new HelloServiceImpl();
        //注册服务
        LocateRegistry.createRegistry(8801);
        //指定通讯端口，防止被防火墙拦截
        RMISocketFactory.setSocketFactory(new CustomerSocketFactory());
        Naming.bind("rmi://localhost:8801/helloService",helloService);
        System.out.println("ServerMain provide RPC service now");
    }
}
