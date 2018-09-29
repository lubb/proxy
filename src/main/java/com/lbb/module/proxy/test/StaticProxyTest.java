package com.lbb.module.proxy.test;

import com.lbb.module.proxy.service.UserService;
import com.lbb.module.proxy.service.UserServiceImpl;
import com.lbb.module.proxy.statics.UserStaticProxy;

public class StaticProxyTest {

    public static void main(String args[]){
        UserService userService = new UserServiceImpl();
        UserService u = new UserStaticProxy(userService);
        u.addUser();
    }
}
