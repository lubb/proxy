package com.lbb.module.proxy.statics;

import com.lbb.module.proxy.service.UserService;

public class UserStaticProxy implements UserService {

    public UserService userService;

    public UserStaticProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addUser() {
        System.out.println("create session");
        userService.addUser();
        System.out.println("add tranaction commit");
    }
}
