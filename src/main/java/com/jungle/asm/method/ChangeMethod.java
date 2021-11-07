package com.jungle.asm.method;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.objectweb.asm.*;

import java.io.File;
import java.io.InputStream;

public class ChangeMethod {

    public static void main(String[] args) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/jungle/asm/method/MethodChange.class");
        byte[] bytes = IoUtil.readBytes(stream);
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM7, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("foo")) {
                    return null;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                MethodVisitor method = cv.visitMethod(Opcodes.ACC_PUBLIC, "foo", "(I)I", null, null);
                method.visitCode();
                method.visitVarInsn(Opcodes.ILOAD, 1);
                method.visitIntInsn(Opcodes.BIPUSH, 100);
                method.visitInsn(Opcodes.IADD);
                method.visitInsn(Opcodes.IRETURN);
                method.visitMaxs(0, 0);
                method.visitEnd();
            }
        };
        reader.accept(visitor, 0);
        byte[] newBytes = writer.toByteArray();
        FileUtil.writeBytes(newBytes, new File("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\asm\\method\\MethodChange.class"));

    }
}
