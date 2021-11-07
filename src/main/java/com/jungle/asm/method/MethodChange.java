package com.jungle.asm.method;

public class MethodChange {

    public static void main(String[] args) {
        System.out.println(new MethodChange().foo(1));
        new MethodChange().print();
    }

    public int foo(int i) {
        return i;
    }

    public void print() {
        System.out.println("Hello asm");
        int b = 1 / 0;
    }
}
