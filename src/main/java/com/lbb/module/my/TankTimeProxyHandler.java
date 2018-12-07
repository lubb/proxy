package com.lbb.module.my; 
import java.lang.reflect.Method;
public class TankTimeProxyHandler implements com.lbb.module.my.Moveable {
  private com.lbb.module.my.InvocationHandler h;
  public TankTimeProxyHandler(InvocationHandler h) {
      this.h = h;
  }
  @Override
  public void  stop() {
     try{
         Method md = com.lbb.module.my.Moveable.class.getMethod("stop");
         h.invoke(this,md);
     }catch(Exception e){
         e.printStackTrace();
     }
  }
  @Override
  public void  move() {
     try{
         Method md = com.lbb.module.my.Moveable.class.getMethod("move");
         h.invoke(this,md);
     }catch(Exception e){
         e.printStackTrace();
     }
  }

}