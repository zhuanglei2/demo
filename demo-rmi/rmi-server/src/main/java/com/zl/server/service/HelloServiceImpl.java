package com.zl.server.service;

import com.zl.server.api.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/23 9:48
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {


    private static final long serialVersionUID = -3821657536224287282L;

    public HelloServiceImpl() throws RemoteException {
        super();
    }


    @Override
    public String sayHello(String someOne) throws RemoteException{
        return "Hello,"+someOne;
    }
}
