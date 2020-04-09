package com.example.reflect;

/**
 * @author vincent
 */
public class TestClass {
    private String MSG = "Original";

    public String getMSG() {
        return MSG;
    }

    private void privateMethod(String head, int tail) {
        System.out.println(head + tail);
    }
}
