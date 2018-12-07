package com.lbb.module.my;

import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {

    // 保留被代理的对象
    private Object target;

    public TimeHandler(Object target)
    {
        this.target = target;
    }

    @Override
    public void invoke(Object o, Method m) {
        System.out.println("Time Proxy start...........");
        System.out.println(o.getClass().getName());
        try{
            m.invoke(target);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Time Proxy end...........");
    }
}
