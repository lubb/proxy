package com.lbb.module.my;

public class Test {

    public static void main(String[] args) throws Exception
    {
        Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class);
        Moveable m2 = (Moveable) ProxyHandler.newProxyInstance(Moveable.class, new TimeHandler(new Tank()));
        m2.move();// 感觉没有生成任何代理类
    }

}
