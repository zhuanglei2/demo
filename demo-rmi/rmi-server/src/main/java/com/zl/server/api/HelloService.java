package com.zl.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/23 9:47
 */
public interface HelloService extends Remote {

    String sayHello(String someOne)throws RemoteException;
}
