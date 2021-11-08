package com.jungle.instrumentation.attach;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class MyAttachMain {
    public static void main(String[] args) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        VirtualMachine machine = VirtualMachine.attach(args[0]);
        try {
            machine.loadAgent("F:\\project\\startJavaClass\\target\\myAgent.jar");
            machine.startLocalManagementAgent();
        } finally {
            machine.detach();
        }
    }
}
