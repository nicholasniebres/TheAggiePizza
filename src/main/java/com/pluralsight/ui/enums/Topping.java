package com.pluralsight.ui.enums;

public enum Topping {
    // Meats (Premium)
    PEPPERONI(ToppingType.MEAT), SAUSAGE(ToppingType.MEAT), HAM(ToppingType.MEAT),
    BACON(ToppingType.MEAT), CHICKEN(ToppingType.MEAT), MEATBALL(ToppingType.MEAT),

    // Cheeses (Premium)
    MOZZARELLA(ToppingType.CHEESE), PARMESAN(ToppingType.CHEESE), RICOTTA(ToppingType.CHEESE),
    GOAT_CHEESE(ToppingType.CHEESE), BUFFALO(ToppingType.CHEESE),

    // Regular Toppings (Included/Free)
    ONIONS(ToppingType.REGULAR), MUSHROOMS(ToppingType.REGULAR), BELL_PEPPERS(ToppingType.REGULAR),
    OLIVES(ToppingType.REGULAR), TOMATOES(ToppingType.REGULAR), SPINACH(ToppingType.REGULAR),
    BASIL(ToppingType.REGULAR), PINEAPPLE(ToppingType.REGULAR), ANCHOVIES(ToppingType.REGULAR),

    // Sauces & Sides
    MARINARA(ToppingType.SAUCE), ALFREDO(ToppingType.SAUCE), PESTO(ToppingType.SAUCE),
    BBQ(ToppingType.SAUCE), BUFFALO_SAUCE(ToppingType.SAUCE), OLIVE_OIL(ToppingType.SAUCE),
    RED_PEPPER(ToppingType.SIDE), PARMESAN_SIDE(ToppingType.SIDE);

    private final ToppingType type;

    Topping(ToppingType type) {
        this.type = type;
    }

    public ToppingType getType() { return type; }

    // Dynamic cost calculator based on Pizza Size and whether it is an extra copy
    public double getCost(Size size, boolean isExtra) {
        if (type == ToppingType.REGULAR || type == ToppingType.SAUCE || type == ToppingType.SIDE) {
            return 0.0; // Always included
        }

        if (type == ToppingType.MEAT) {
            if (!isExtra) {
                return switch (size) { case PERSONAL -> 1.00; case MEDIUM -> 2.00; case LARGE -> 3.00; };
            } else {
                return switch (size) { case PERSONAL -> 0.50; case MEDIUM -> 1.00; case LARGE -> 1.50; };
            }
        }

        if (type == ToppingType.CHEESE) {
            if (!isExtra) {
                return switch (size) { case PERSONAL -> 0.75; case MEDIUM -> 1.50; case LARGE -> 2.25; };
            } else {
                return switch (size) { case PERSONAL -> 0.30; case MEDIUM -> 0.60; case LARGE -> 0.90; };
            }
        }
        return 0.0;
    }
}