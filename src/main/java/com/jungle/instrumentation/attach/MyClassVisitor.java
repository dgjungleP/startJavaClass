package com.jungle.instrumentation.attach;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyClassVisitor extends ClassVisitor {

    public MyClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

        MethodVisitor method = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("foo".equals(name)) {
            return new MyMethodVisitor(Opcodes.ASM7, method, access, name, descriptor);
        }
        return method;
    }
}
