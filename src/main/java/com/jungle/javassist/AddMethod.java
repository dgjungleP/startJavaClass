package com.jungle.javassist;

import javassist.*;

import java.io.IOException;

public class AddMethod {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        ClassPath path = pool.insertClassPath("com/jungle/javassist/MyTest.java");
        CtClass test = pool.get("com.jungle.javassist.MyTest");
        CtMethod method = new CtMethod(CtClass.intType, "foo", new CtClass[]{CtClass.intType, CtClass.intType}, test);
        method.setModifiers(Modifier.PUBLIC);
        method.setBody("return 0;");
        test.addMethod(method);
        test.writeFile("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\javassist");
    }
}
