package com.lbb.module.my; 
public class TankTimeProxy implements com.lbb.module.my.Moveable {
  private com.lbb.module.my.Moveable m;
  public TankTimeProxy(com.lbb.module.my.Moveable m) {
  this.m = m;
}
@Override
public void  stop() {
   System.out.println("Time Proxy start...........");
   long start = System.currentTimeMillis();
   m.stop();
   long end = System.currentTimeMillis();
   System.out.println("花费时间："+(end - start));
   System.out.println("Time Proxy end...........");
}@Override
public void  move() {
   System.out.println("Time Proxy start...........");
   long start = System.currentTimeMillis();
   m.move();
   long end = System.currentTimeMillis();
   System.out.println("花费时间："+(end - start));
   System.out.println("Time Proxy end...........");
}
}