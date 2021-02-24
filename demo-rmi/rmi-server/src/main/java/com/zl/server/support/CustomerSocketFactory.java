package com.zl.server.support;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

/**
 * 指定通讯端口，防止被防火墙拦截
 * @author zhuangl
 * @version 1.0
 * @date 2021/2/23 10:18
 */
public class CustomerSocketFactory extends RMISocketFactory {
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host,port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        if(port==0){
            port = 8501;
        }
        System.out.println("rmi notify port:"+port);
        return new ServerSocket(port);
    }
}
