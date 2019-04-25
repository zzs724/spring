package com.zzs.utils;

public class Logger {
    /**
     * 打印日志：在切入点方法执行之前执行(切入点方法就是业务层方法）
     */
    public void beforePrintLog() {
        System.out.println("...beforePrintLog...");
    }

    public void afterPrintLog() {
        System.out.println("...afterPrintLog...");
    }

    public void afterThrowPrintLog() {
        System.out.println("...afterThrowPrintLog...");
    }

    public void afterReturnPrintLog() {
        System.out.println("...afterReturnPrintLog...");
    }

    public void arountPrintLog() {
        System.out.println("...arountPrintLog...");
    }

}
