package com.jungle.instrumentation.base;

import java.lang.instrument.Instrumentation;

public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MyClassFileTransformer(), true);
    }

    public static void main(String[] args) {
        MyClassFileTransformer transformer = new MyClassFileTransformer();
        System.out.println(transformer);
    }
}
