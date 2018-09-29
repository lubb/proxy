package com.lbb.module.proxy.test;

import com.lbb.module.proxy.dynamic.DynamicProxyHandler;
import com.lbb.module.proxy.service.UserService;
import com.lbb.module.proxy.service.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    public static void main(String args[]){
        UserService userService = new UserServiceImpl();
        InvocationHandler handler = new DynamicProxyHandler(userService);
        UserService u = (UserService) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{UserService.class} ,handler);
        u.addUser();
    }
}
