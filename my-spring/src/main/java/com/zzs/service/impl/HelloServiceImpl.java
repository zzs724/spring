package com.zzs.service.impl;

import com.zzs.service.IHelloService;

public class HelloServiceImpl implements IHelloService {
    @Override
    public void sayHi() {
        System.out.println("hhhhhhhh");
    }

    public HelloServiceImpl() {
        System.out.println("HelloServiceImpl...init...");
    }
}
