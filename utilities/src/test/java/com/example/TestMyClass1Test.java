package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyClass1Test {
    @Test
    public void testMainMethod() {
        String expectedOutput = "Hello, MyClass1!";
        assertEquals(expectedOutput, "Hello, MyClass1!");
    }
}