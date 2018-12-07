package com.lbb.module.my;

public class Tank  implements Moveable {

    @Override
    public void move() {
        System.out.println("tank move........");
    }

    public void stop()
    {
        System.out.println("Tank stopping.......");
    }
}
