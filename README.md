# Start My Java Class Bytes

## 使用HSDB

> 位置`lib/sa-jdi.jar`

``` shell
 #使用方法： 
  java -cp sa-jdi.jar sun.jvm.hotspot.HSDB   
```

> 注意：检查`sawindbg.dll`文件是否存在于`jre/bin`中

## Java字节码工具

- ASM
- javassist

## Instrumentation机制

> 使用场景
> - APM工具：Pinpoint SkyWalking newrelic
> - 热部署工具： HotSwap Jrebel
> - Java诊断工具: Arthas

> 使用方式：
> - 在JVM启动的时候添加一个Agent的jar包
> > 1. 添加 -javaagent参数来实现  
       例如：java -javaagent:myagent.jar MyMain
> > 2. 在Agent的Jar包中指定MANIFEST.MF文件的Premain-Class
> > 3. 在Agent类中实现premain的静态方法
> - JVM运行以后任意时刻通过Attach API远程加载Agent的jar包

# 注：

> 1. 绝大部分使用框架都是使用字节码编程生产代理类，来避免使用反射进行操作
