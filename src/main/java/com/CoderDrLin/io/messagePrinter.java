package com.CoderDrLin.io;

public class messagePrinter {
    public static void print(String ...args){
        for (String arg : args) {
            System.out.print(arg+"\t");
        }
        System.out.println("\n");
    }
}
