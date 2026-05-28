package com.pluralsight.ui.enums;

public enum Size {
    PERSONAL("8\"", 8.50),
    MEDIUM("12\"", 12.00),
    LARGE("16\"", 16.50);

    private final String label;
    private final double basePrice;

    Size(String label, double basePrice) {
        this.label = label;
        this.basePrice = basePrice;
    }
    public String getLabel() { return label; }
    public double getBasePrice() { return basePrice; }
}