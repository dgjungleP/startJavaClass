package com.jungle.instrumentation.base;

public class MyTest {
    public static void main(String[] args) {
        new MyTest().foo();
    }

    private void foo() {
        bar1();
        bar2();
    }

    private void bar2() {
    }

    private void bar1() {
    }

}
