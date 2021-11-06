package com.jungle.asm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.InputStream;

public class TestASM {
    public static void main(String[] args) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/jungle/asm/MyTest.class");
        byte[] bytes = IoUtil.readBytes(stream);
        ClassReader reader = new ClassReader(bytes);
//        useCore(reader);
        useTree(reader);

    }

    private static void useTree(ClassReader reader) {
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
        for (FieldNode field : node.fields) {
            System.out.println("Field name :" + field.name);
        }
        for (MethodNode method : node.methods) {
            System.out.println("Method name :" + method.name);
        }
        FieldNode fieldNode = new FieldNode(Opcodes.ACC_PUBLIC, "k", "Ljava/lang/String;", null, null);
        node.fields.add(fieldNode);
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        FileUtil.writeBytes(writer.toByteArray(), new File("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\asm\\NewCLassTree.class"));
    }

    private static void useCore(ClassReader reader) {
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.println("Field name :" + name);
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("Method name :" + name);
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
                FieldVisitor field = cv.visitField(Opcodes.ACC_PUBLIC, "k", "Ljava/lang/String;", null, null);
                if (field != null) {
                    field.visitEnd();
                }
            }
        };
        reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
        FileUtil.writeBytes(writer.toByteArray(), new File("F:\\project\\startJavaClass\\src\\main\\java\\com\\jungle\\asm\\NewCLassCore.class"));
    }
}
