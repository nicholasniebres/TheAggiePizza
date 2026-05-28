package com.pluralsight.ui.enums;

import java.util.Arrays;
import java.util.Optional;

public enum MenuOption {

    ADD_PIZZA(1, "Add Pizza"),
    ADD_DRINK(2, "Add Drink"),
    ADD_GARLIC_KNOTS(3, "Add Garlic Knots"),
    CHECKOUT(4, "Checkout"),
    CANCEL_ORDER(0, "Cancel Order");

    private final int code;
    private final String label;

    MenuOption(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() { return code; }
    public String getLabel() { return label; }

    public static Optional<MenuOption> fromCode(int code) {
        return Arrays.stream(values()).filter(o -> o.code == code).findFirst();
    }

}
