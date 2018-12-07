package com.lbb.module.my;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyHandler {

    public static Object newProxyInstance(Class interfze, InvocationHandler h) throws Exception
    {
        String rt = "\n\r";
        // 拼接"实现接口方法"的字符串
        String methodStr = "";
        for (Method m : interfze.getMethods()){
            // 取出方法的修饰符和返回值类型
            methodStr += ""
                    + "  @Override" + rt
                    + "  public void  " + m.getName()+ "() {" + rt
                    + "     try{" + rt
                    + "         Method md = "+ interfze.getName()+".class.getMethod(\"" + m.getName() + "\");"+ rt
                    + "         h.invoke(this,md);" + rt
                    + "     }catch(Exception e){" + rt
                    + "         e.printStackTrace();"+ rt
                    + "     }"+ rt
                    + "  }"+ rt;
        }
        // 动态代理文件的源码
        String str = "package com.lbb.module.my; " + rt +
                     "import java.lang.reflect.Method;"+ rt +
                     "public class TankTimeProxyHandler implements " + interfze.getName() + " {" + rt +
                     "  private com.lbb.module.my.InvocationHandler h;" + rt +
                     "  public TankTimeProxyHandler(InvocationHandler h) {" + rt +
                     "      this.h = h;" + rt +
                     "  }" + rt +
                        methodStr + rt +
                     "}";
        // 把源码写到java文件里
        String fileName = System.getProperty("user.dir")+"/src/main/java/com/lbb/module/my/TankTimeProxyHandler.java";
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
        Class c = uc.loadClass("com.lbb.module.my.TankTimeProxyHandler");
        // 生成实例
        // return c.newInstance();
        // //c.newInstance()会调用无参数的Construtor，若类没有无参的Constructor时会出错
        Constructor ctr = c.getConstructor(InvocationHandler.class);
        return ctr.newInstance(h);
    }
}
