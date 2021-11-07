package com.jungle.asm.method;

import cn.hutool.core.io.BufferUtil;
import cn.hutool.core.io.FileUtil;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;

public class AddPrintForMethod {
    public static void main(String[] args) {
        byte[] bytes = FileUtil.readBytes("com/jungle/asm/method/MethodChange.class");
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM7, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                MethodVisitor method = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!"print".equals(name)) {
                    return method;
                } else {
                    return new AdviceAdapter(Opcodes.ASM7, method, access, name, descriptor) {
                        Label start = new Label();

                        @Override
                        protected void onMethodEnter() {
                            super.onMethodEnter();
                            mv.visitLabel(start);
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("enter " + name);
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        }

                        @Override
                        public void visitMaxs(int maxStack, int maxLocals) {
                            Label end = new Label();
                            mv.visitTryCatchBlock(start, end, end, null);
                            mv.visitLabel(end);
                            finallyBlock(Opcodes.ATHROW);
                            mv.visitInsn(Opcodes.ATHROW);
                            super.visitMaxs(maxStack, maxLocals);
                        }

                        private void finallyBlock(int opcode) {
                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            if (opcode == Opcodes.ATHROW) {
                                mv.visitLdcInsn("Exception exit " + name);
                            } else {
                                mv.visitLdcInsn("exit " + name);
                            }
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                        }

                        @Override
                        protected void onMethodExit(int opcode) {
                            super.onMethodExit(opcode);
                            if (opcode != Opcodes.ATHROW) {
                                finallyBlock(opcode);
                            }
                        }
                    };
                }
            }
        };
        reader.accept(visitor, 0);
        byte[] newBytes = writer.toByteArray();
        FileUtil.writeBytes(newBytes, new File("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\asm\\method\\MethodChange.class"));

    }
}
