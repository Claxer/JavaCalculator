package com.calculator;

public class MemoryManager {

    private double memory;

    public MemoryManager() {
        memory = 0;
    }

    public void memoryClear() {
        memory = 0;
    }

    public double memoryRecall() {
        return memory;
    }

    public void memoryAdd(double value) {
        memory += value;
    }

    public void memorySubtract(double value) {
        memory -= value;
    }

    public boolean hasMemory() {
        return memory != 0;
    }

    public void memoryStore(double value) {
        memory = value;
    }
}
