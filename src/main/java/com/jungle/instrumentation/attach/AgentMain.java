package com.jungle.instrumentation.attach;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation instr) throws UnmodifiableClassException {
        System.out.println("agentmain called.");
        instr.addTransformer(new MyClassFileTransformer(), true);
        Class[] classes = instr.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals("com.jungle.instrumentation.attach.MyTestMain")) {
                System.out.println("Reloading: " + clazz.getName());
                instr.retransformClasses(clazz);
                break;
            }
        }
    }
}
