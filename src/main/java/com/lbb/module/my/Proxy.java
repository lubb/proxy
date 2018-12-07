package com.lbb.module.my;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {

    public static Object newProxyInstance(Class interfze) throws Exception
    {
        String rt = "\n\r";
        // 拼接"实现接口方法"的字符串
        String methodStr = "";
        for (Method m : interfze.getMethods()){
            // 取出方法的修饰符和返回值类型
            methodStr += ""
                    + "@Override" + rt
                    + "public void  " + m.getName()+ "() {" + rt
                    + "   System.out.println(\"Time Proxy start...........\");" + rt
                    + "   long start = System.currentTimeMillis();" + rt
                    + "   m." + m.getName() + "();" + rt
                    + "   long end = System.currentTimeMillis();" + rt
                    + "   System.out.println(\"花费时间：\"+(end - start));" + rt
                    + "   System.out.println(\"Time Proxy end...........\");" + rt
                    + "}";
        }
        // 动态代理文件的源码
        String str = "package com.lbb.module.my; " + rt +
                     "public class TankTimeProxy implements " + interfze.getName() + " {" + rt +
                     "  private " + interfze.getName() + " m;" + rt +
                     "  public TankTimeProxy(" + interfze.getName() + " m) {" + rt + "  this.m = m;" + rt + "}" + rt +
                            methodStr + rt +
                     "}";
        // 把源码写到java文件里
        String fileName = System.getProperty("user.dir")+"/src/main/java/com/lbb/module/my/TankTimeProxy.java";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        fw.write(str);
        fw.flush();
        fw.close();

        // 编译源码，生成class,注意编译环境要换成jdk才有compiler,单纯的jre没有compiler，会空指针错误
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        // 文件管事器
        StandardJavaFileManager fileMgr = jc.getStandardFileManager(null, null, null);
        // 编译单元
        Iterable units = fileMgr.getJavaFileObjects(file);
        // 编译任务
        JavaCompiler.CompilationTask t = jc.getTask(null, fileMgr, null, null, null, units);
        // 编译
        t.call();
        fileMgr.close();

        // 把类load到内存里src\com\weiqinshian\proxy
        URL[] urls = new URL[]
                { new URL("file:/" + System.getProperty("user.dir")+"/src") };
        System.out.println("file:/" + System.getProperty("user.dir")+"/src");
        URLClassLoader uc = new URLClassLoader(urls);
        Class c = uc.loadClass("com.lbb.module.my.TankTimeProxy");
        // 生成实例
        // return c.newInstance();
        // //c.newInstance()会调用无参数的Construtor，若类没有无参的Constructor时会出错
        Constructor ctr = c.getConstructor(Moveable.class);
        return ctr.newInstance(new Tank());
    }
}
