package com.sun;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.util.Context;

public class HelloJavac {
    public static void foo() {
        int a = 0;
        int b = 6;
        int c = 130;
        int d = 33000;
    }

    public static void main(String[] args) {
        ScannerFactory factory = ScannerFactory.instance(new Context());
        Scanner scanner = factory.newScanner(" int k = i + j;", false);
        scanner.nextToken();
        System.out.println(scanner.token().kind);
        scanner.nextToken();
        System.out.println(scanner.token().name());
        scanner.nextToken();
        System.out.println(scanner.token().kind);
        scanner.nextToken();
        System.out.println(scanner.token().name());
        scanner.nextToken();
        System.out.println(scanner.token().kind);
        scanner.nextToken();
        System.out.println(scanner.token().name());
        scanner.nextToken();
        System.out.println(scanner.token().kind);
    }

}
