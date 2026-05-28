package com.pluralsight.models;


import com.pluralsight.ui.Orderable;

public class GarlicKnots implements Orderable {
    @Override
    public double getPrice() { return 1.50; } // From structural spreadsheet screenshot

    @Override
    public String getDescription() { return "Order of Garlic Knots"; }
}