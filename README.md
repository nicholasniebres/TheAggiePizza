# Aggies Pizza - Console Ordering Application

Welcome to **Aggies Pizza**, a robust, object-oriented console application designed to handle customer food orders, customize pizzas dynamically with tiered pricing models, and automatically generate transactional, time-stamped text receipts. 

This project was built from scratch using clean Java architectural principles, leveraging interfaces, inheritance structures, polymorphism, and state-tracking enums.

---

## 🍕 Features

* **Interactive Home Screen:** Clean workflow switching mechanisms letting customers spin up new orders or exit safely.
* **Smart Cart Ordering Logic:** * Displays items in chronological order (**newest additions first**).
  * Prevents processing or checking out an empty cart.
  * Restricts orders containing 0 pizzas from checking out unless a drink or a side of garlic knots is present.
* **Granular Pizza Customization:**
  * Choice of 3 base sizes: Personal (8"), Medium (12"), or Large (16").
  * Crust selection including Thin, Regular, Thick, or Cauliflower with optional flat-rate Stuffed Crust upgrades.
  * Tiered topping tracking system dividing items into premium category groups (Meats/Cheeses) and standard groups (Regular/Sauces/Sides).
  * Dynamic pricing that scales premium base costs based on the pizza size and tracks recursive duplicate items as reduced-rate "Extra Toppings".
* **Automated File I/O Receipt Engine:** Compiles complete subtotal configurations upon confirmation and generates custom transactional files saved automatically to a localized `/receipts` folder using a `yyyyMMdd-HHmmss.txt` naming standard.

---

## 📐 Architecture & Structural Flow

The application relies heavily on **Polymorphism**. The core user interface controls order states entirely via a single, decoupled `OrderItem` interface. This ensures that adding new physical products or sides in the future won't break the current core checking/billing loops.

### System Class Diagram


<img width="966" height="1036" alt="Screenshot 2026-05-26 232517" src="https://github.com/user-attachments/assets/a3bc6eac-640e-4ffc-9cf6-0a1740596aea" />


How the Components Interact
UserInterface: Coordinates everything. It manages the app's standard console loops, scans user numerical selections, and translates inputs into explicit MenuOption constants via MenuOption.fromCode().

OrderItem (The Polymorphic Blueprint): An interface demanding a .getDescription() and .getPrice(). The terminal cart stores a single generic list (List<OrderItem> currentOrder). Whether an item is a customized multi-topping pizza or a simple fountain drink, the interface treats them identically at checkout.

Pizza Class & Topping Enums: The Pizza object handles calculations locally. When a user adds a topping, the pizza checks if that item already exists in its list. If it does, it dynamically passes a flag (isExtra = true) down to the Topping pricing matrix to charge the correct fractional "Extra" surcharge specified by the shop menu guidelines.

🛠️ Installation & Getting Started
Prerequisites
Java Development Kit (JDK) 17 or higher.

An IDE (such as IntelliJ IDEA, Eclipse, or VS Code) or Maven installed via CLI.

Running the Application
Clone this repository to your local system environment:

Bash
git clone [https://github.com/YOUR_GITHUB_USERNAME/AggiesPizza.git](https://github.com/YOUR_GITHUB_USERNAME/AggiesPizza.git)
Navigate into the root project directory folder:

Bash
cd AggiesPizza
Compile and launch the application directly using your terminal:

Bash
javac -d out src/com/pluralsight/*.java
java -cp out com.pluralsight.Main
📁 Storage Architecture & Output File Examples
When an order successfully goes through, a unique text file gets outputted safely into a root storage directory called /receipts.

File Path Example:

C:\your-project-directory\AggiesPizza\receipts\20260526-230143.txt

=====================================
         AGGIES PIZZA RECEIPT        
 Date/Time: 2026-05-26 23:01:43
=====================================
Order of Garlic Knots - $1.50
LARGE (16") THICK Crust Pizza [Stuffed Crust]
    Base Toppings: [PEPPERONI, MOZZARELLA, MARINARA]
    Extra Toppings: [PEPPERONI] - $22.00
MEDIUM Medium Sprite Drink - $2.50
-------------------------------------
TOTAL PAID: $26.00
=====================================
