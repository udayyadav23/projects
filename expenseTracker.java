import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Expense implements Serializable {
    private Date date;
    private String category;
    private double amount;

    public Expense(Date date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

public class expenseTracker {
    private List<User> users;
    private List<Expense> expenses;

    public expenseTracker() {
        users = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void addExpense(Date date, String category, double amount) {
        expenses.add(new Expense(date, category, amount));
    }

    public void displayExpenses() {
        for (Expense expense : expenses) {
            System.out.println("Date: " + expense.getDate() + ", Category: " + expense.getCategory() + ", Amount: $" + expense.getAmount());
        }
    }

    public void displayCategorySummation() {
        Map<String, Double> categorySum = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();
            categorySum.put(category, categorySum.getOrDefault(category, 0.0) + amount);
        }

        for (Map.Entry<String, Double> entry : categorySum.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total Expense: $" + entry.getValue());
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(users);
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            users = (List<User>) ois.readObject();
            expenses = (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        expenseTracker tracker = new expenseTracker();
        Scanner scanner = new Scanner(System.in);

        // User registration
        System.out.println("Welcome to Expense Tracker!");
        System.out.println("Please register to continue.");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        tracker.registerUser(username, password);
        System.out.println("Registration successful!");

        // User login
        System.out.println("Please log in to proceed.");
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();
        if (!tracker.loginUser(loginUsername, loginPassword)) {
            System.out.println("Invalid username or password. Exiting...");
            return;
        }
        System.out.println("Login successful!");

        // Load expenses from file (if available)
        tracker.loadFromFile("expenses.dat");

        // Main menu
        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Category-wise Summation");
            System.out.println("4. Save and Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // Add Expense
                    System.out.println("\nEnter expense details:");
                    System.out.print("Date (YYYY-MM-DD): ");
                    String dateString = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Amount: $");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character

                    // Convert date string to Date object
                    Date date;
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                        continue;
                    }

                    tracker.addExpense(date, category, amount);
                    System.out.println("Expense added successfully!");
                    break;
                case 2:
                    // View Expenses
                    System.out.println("\nAll Expenses:");
                    tracker.displayExpenses();
                    break;
                case 3:
                    // View Category-wise Summation
                    System.out.println("\nCategory-wise Summation:");
                    tracker.displayCategorySummation();
                    break;
                case 4:
                    // Save and Exit
                    tracker.saveToFile("expenses.dat");
                    System.out.println("Expense data saved. Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}
