package com.pluralsight.ui;

import com.pluralsight.models.Drink;
import com.pluralsight.models.GarlicKnots;
import com.pluralsight.models.Pizza;
import com.pluralsight.ui.enums.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterface {


    private final Scanner scanner = new Scanner(System.in);
    private final List<Orderable> currentOrder = new ArrayList<>();


    private int getIntInput() {
        try {
            int val = scanner.nextInt();
            scanner.nextLine(); // clear stream buffer
            return val;
        } catch (Exception e) {
            scanner.nextLine(); // break error catch loop
            return -1;
        }
    }

    private void displayHeader() {
        System.out.println("\n=============================================");
        System.out.println("          AGGIES PIZZA HOME MENU             ");
        System.out.println("=============================================");
    }

    public void display() {
        while (true) {
            displayHeader();
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            int choice = getIntInput();

            if (choice == 1) {
                runOrderMenu();
            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void runOrderMenu() {
        while (true) {
            System.out.println("\n--- Order Screen ---");
            for (MenuOption option : MenuOption.values()) {
                System.out.printf("%d) %s%n", option.getCode(), option.getLabel());
            }
            System.out.print("Select an option: ");
            int choice = getIntInput();

            Optional<MenuOption> optionOpt = MenuOption.fromCode(choice);
            if (optionOpt.isEmpty()) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            MenuOption option = optionOpt.get();
            if (option == MenuOption.CANCEL_ORDER) {
                currentOrder.clear();
                System.out.println("Order explicitly discarded.");
                break;
            }

            handleMenuChoice(option);

            // Break back to main home system loop if checked out successfully
            if (option == MenuOption.CHECKOUT && currentOrder.isEmpty()) {
                break;
            }
        }
    }

    private void handleMenuChoice(MenuOption option) {
        switch (option) {
            case ADD_PIZZA -> processAddPizza();
            case ADD_DRINK -> processAddDrink();
            case ADD_GARLIC_KNOTS -> processAddGarlicKnots();
            case CHECKOUT -> processCheckout();
        }
    }

    private void processAddPizza() {
        System.out.println("\n--- Create Your Pizza ---");
        System.out.println("Sizes available: PERSONAL (8\"), MEDIUM (12\"), LARGE (16\")");
        System.out.print("Select size: ");
        Size size = Size.valueOf(scanner.nextLine().toUpperCase().trim());

        System.out.println("Crusts available: THIN, REGULAR, THICK, CAULIFLOWER");
        System.out.print("Select crust type: ");
        Crust crust = Crust.valueOf(scanner.nextLine().toUpperCase().trim());

        System.out.print("Would you like stuffed crust? (true/false): ");
        boolean stuffed = Boolean.parseBoolean(scanner.nextLine().trim());

        Pizza pizza = new Pizza(size, crust, stuffed);

        System.out.println("\nAdd toppings. Type 'DONE' to finish customization.");
        System.out.println("Options: PEPPERONI, SAUSAGE, HAM, MOZZARELLA, RICOTTA, ONIONS, MUSHROOMS, MARINARA, etc.");
        while (true) {
            System.out.print("Enter topping name: ");
            String input = scanner.nextLine().toUpperCase().replace(" ", "_").trim();
            if (input.equals("DONE")) break;
            try {
                pizza.addTopping(Topping.valueOf(input));
            } catch (IllegalArgumentException e) {
                System.out.println("Topping not recognized. Try again.");
            }
        }

        // Feature Rule: Display newest items on top/first
        currentOrder.add(0, pizza);
    }

    private void processAddDrink() {
        System.out.print("Select drink size (SMALL, MEDIUM, LARGE): ");
        DrinkSize size = DrinkSize.valueOf(scanner.nextLine().toUpperCase().trim());
        System.out.print("Enter flavor: ");
        String flavor = scanner.nextLine();

        currentOrder.add(0, new Drink(size, flavor));
    }

    private void processAddGarlicKnots() {
        currentOrder.add(0, new GarlicKnots());
        System.out.println("Garlic Knots added to basket.");
    }

    private void processCheckout() {
        // Business Rule Exception constraint handling
        long pizzaCount = currentOrder.stream().filter(item -> item instanceof Pizza).count();
        if (currentOrder.isEmpty()) {
            System.out.println("Cannot checkout an empty basket!");
            return;
        }
        if (pizzaCount == 0 && currentOrder.isEmpty()) {
            System.out.println("Empty orders must contain at least one Side or Drink item to proceed.");
            return;
        }

        System.out.println("\n=============================");
        System.out.println("     YOUR ORDER SUMMARY      ");
        System.out.println("=============================");
        double total = 0;
        for (Orderable item : currentOrder) {
            System.out.printf("%s%n -> Price: $%.2f%n%n", item.getDescription(), item.getPrice());
            total += item.getPrice();
        }
        System.out.printf("Total Cost: $%.2f%n", total);
        System.out.println("=============================");

        System.out.print("Confirm purchase? (yes/cancel): ");
        String confirmation = scanner.nextLine().toLowerCase().trim();

        if (confirmation.equals("yes")) {
            saveReceipt(total);
            currentOrder.clear();
        } else {
            currentOrder.clear();
            System.out.println("Order cancelled and dropped.");
        }
    }

    private void saveReceipt(double total) {
        // Build formatted dynamically generated file name rule: yyyyMMdd-HHmmss.txt
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String filename = now.format(formatter) + ".txt";

        // Establish output receipts directory storage structure
        File directory = new File("receipts");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("=====================================");
            writer.println("         AGGIES PIZZA RECEIPT        ");
            writer.printf(" Date/Time: %s%n", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=====================================");
            for (Orderable item : currentOrder) {
                writer.printf("%s - $%.2f%n", item.getDescription(), item.getPrice());
            }
            writer.println("-------------------------------------");
            writer.printf("TOTAL PAID: $%.2f%n", total);
            writer.println("=====================================");
            System.out.println("Receipt successfully exported to: " + file.getPath());
        } catch (IOException e) {
            System.out.println("Fatal: Could not issue transactional text backup. " + e.getMessage());
        }
    }

}

