package com.zzs.utils;

import org.aspectj.lang.ProceedingJoinPoint;

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

    /**
     *
     */
    public Object arountPrintLog(ProceedingJoinPoint point) {
        Object reValue = null;
        try {
            Object[] args = point.getArgs();
            System.out.println("...arountPrintLog...before");
            reValue = point.proceed(args);//明确调用切入点方法、也就是业务层方法
            System.out.println("...arountPrintLog...after_return");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("...arountPrintLog...after_Throw");
        } finally {
            System.out.println("...arountPrintLog...after");
        }

        return reValue;
    }

}
