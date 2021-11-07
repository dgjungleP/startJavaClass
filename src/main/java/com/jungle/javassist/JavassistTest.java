package com.jungle.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

import java.io.IOException;

public class JavassistTest {
    public static void main(String[] args) throws CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass makeClass = pool.makeClass("com.jungle.Hello");
        makeClass.writeFile("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\javassist");
    }
}
