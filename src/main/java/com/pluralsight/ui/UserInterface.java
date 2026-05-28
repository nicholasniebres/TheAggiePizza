package com.pluralsight.ui;

import com.pluralsight.ui.enums.MenuOption;

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

            // handleMenuChoice(option);

            // Break back to main home system loop if checked out successfully
            if (option == MenuOption.CHECKOUT && currentOrder.isEmpty()) {
                break;
            }
        }
    }

}
