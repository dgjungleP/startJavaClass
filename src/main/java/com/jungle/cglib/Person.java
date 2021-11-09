package com.jungle.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Person {
    public void doJob(String jobName) {
        System.out.println("Who is the class :" + getClass());
        System.out.println("Doing the job :" + jobName);
    }

    public void eat() {

    }

    public void sleep() {

    }

    static {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./out");
    }

    public static void main(String[] args) {
        MethodInterceptor interceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println(">>>>Before interceptor : ");
                Object o = proxy.invokeSuper(obj, args);
                System.out.println("<<<<After interceptor : ");
                return o;
            }
        };

        Person person = (Person) Enhancer.create(Person.class, interceptor);
        person.doJob("coding");
    }
}
