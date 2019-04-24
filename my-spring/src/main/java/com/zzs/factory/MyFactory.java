package com.zzs.factory;

import com.zzs.service.IHelloService;
import com.zzs.service.impl.HelloServiceImpl;

public class MyFactory {
    public static IHelloService getHelloService(){
        return new HelloServiceImpl();
    }

}
