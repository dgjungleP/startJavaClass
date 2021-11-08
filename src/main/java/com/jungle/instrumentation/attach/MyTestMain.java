package com.jungle.instrumentation.attach;

import cn.hutool.core.util.ReUtil;

import java.util.concurrent.TimeUnit;

public class MyTestMain {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println(foo());
            TimeUnit.SECONDS.sleep(3);
        }
    }

    private static int foo() {
        return 100;
    }
}
