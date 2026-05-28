package com.pluralsight.models;

import com.pluralsight.ui.Orderable;
import com.pluralsight.ui.enums.Crust;
import com.pluralsight.ui.enums.Size;
import com.pluralsight.ui.enums.Topping;

import java.util.ArrayList;
import java.util.List;

public class Pizza implements Orderable {
    private final Size size;
    private final Crust crust;
    private final boolean stuffedCrust;
    private final List<Topping> baseToppings = new ArrayList<>();
    private final List<Topping> extraToppings = new ArrayList<>();

    public Pizza(Size size, Crust crust, boolean stuffedCrust) {
        this.size = size;
        this.crust = crust;
        this.stuffedCrust = stuffedCrust;
    }

    public void addTopping(Topping topping) {
        // If it's already there, any secondary addition counts as an "Extra" cost
        if (baseToppings.contains(topping)) {
            extraToppings.add(topping);
        } else {
            baseToppings.add(topping);
        }
    }

    @Override
    public double getPrice() {
        double total = size.getBasePrice();
        for (Topping t : baseToppings) {
            total += t.getCost(size, false);
        }
        for (Topping t : extraToppings) {
            total += t.getCost(size, true);
        }
        if (stuffedCrust) {
            total += 2.50; // Stuffed crust flat surcharge
        }
        return total;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(size).append(" (").append(size.getLabel()).append(") ")
                .append(crust).append(" Crust Pizza");
        if (stuffedCrust) sb.append(" [Stuffed Crust]");
        sb.append("\n    Base Toppings: ").append(baseToppings);
        if (!extraToppings.isEmpty()) {
            sb.append("\n    Extra Toppings: ").append(extraToppings);
        }
        return sb.toString();
    }
}