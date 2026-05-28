package com.pluralsight.models;

import com.pluralsight.ui.Orderable;
import com.pluralsight.ui.enums.DrinkSize;


public class Drink implements Orderable {
    private final DrinkSize size;
    private final String flavor;

    public Drink(DrinkSize size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    @Override
    public double getPrice() { return size.getPrice(); }

    @Override
    public String getDescription() { return size + " " + flavor + " Drink"; }
}
