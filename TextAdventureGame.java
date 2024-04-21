import java.util.Scanner;

public class TextAdventureGame {
    // Define game states
    private static final int STATE_START = 0;
    private static final int STATE_FOREST = 1;
    private static final int STATE_TOWN = 2;
    private static final int STATE_CASTLE = 3;
    private static final int STATE_END = 4;

    // Define player inventory
    private static boolean hasSword = false;
    private static boolean hasKey = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gameState = STATE_START;

        while (gameState != STATE_END) {
            switch (gameState) {
                case STATE_START:
                    gameState = startGame(scanner);
                    break;
                case STATE_FOREST:
                    gameState = exploreForest(scanner);
                    break;
                case STATE_TOWN:
                    gameState = visitTown(scanner);
                    break;
                case STATE_CASTLE:
                    gameState = exploreCastle(scanner);
                    break;
                default:
                    System.out.println("Invalid game state.");
                    gameState = STATE_END;
                    break;
            }
        }

        scanner.close();
    }

    // Start of the game
    private static int startGame(Scanner scanner) {
        System.out.println("Welcome to the Text Adventure Game!");
        System.out.println("You wake up in a forest with no memory of how you got there.");
        System.out.println("What will you do?");
        System.out.println("1. Explore the forest");
        System.out.println("2. Stay where you are");

        int choice = getUserChoice(scanner, 2);
        if (choice == 1) {
            return STATE_FOREST;
        } else {
            System.out.println("You decide to stay in the forest.");
            return STATE_END;
        }
    }

    // Explore the forest
    private static int exploreForest(Scanner scanner) {
        System.out.println("You are now exploring the forest.");
        System.out.println("You come across a hidden path. Do you want to follow it?");
        System.out.println("1. Follow the path");
        System.out.println("2. Stay in the forest");

        int choice = getUserChoice(scanner, 2);
        if (choice == 1) {
            return STATE_TOWN;
        } else {
            System.out.println("You decide to stay in the forest.");
            return STATE_END;
        }
    }

    // Visit the town
    private static int visitTown(Scanner scanner) {
        System.out.println("You arrive at a small town at the edge of the forest.");
        System.out.println("You see a market, an inn, and a castle in the distance.");
        System.out.println("What will you do?");
        System.out.println("1. Explore the market");
        System.out.println("2. Visit the inn");
        System.out.println("3. Head towards the castle");

        int choice = getUserChoice(scanner, 3);
        switch (choice) {
            case 1:
                exploreMarket(scanner);
                break;
            case 2:
                visitInn(scanner);
                break;
            case 3:
                return STATE_CASTLE;
        }

        return STATE_TOWN;
    }

    // Explore the market
    private static void exploreMarket(Scanner scanner) {
        System.out.println("You explore the market and find a merchant selling weapons.");
        System.out.println("Do you want to buy a sword?");
        System.out.println("1. Buy the sword");
        System.out.println("2. Leave the market");

        int choice = getUserChoice(scanner, 2);
        if (choice == 1) {
            hasSword = true;
            System.out.println("You buy the sword from the merchant.");
        } else {
            System.out.println("You decide to leave the market.");
        }
    }

    // Visit the inn
    private static void visitInn(Scanner scanner) {
        System.out.println("You enter the inn and meet a mysterious traveler.");
        System.out.println("He tells you about a sorcerer who is terrorizing the land.");
        System.out.println("He says the sorcerer's castle is heavily guarded and locked with a magical key.");
        System.out.println("You decide to embark on a quest to defeat the sorcerer and save the kingdom.");
        System.out.println("You thank the traveler and head towards the castle.");

        hasKey = true; // Player receives the magical key
    }

    // Explore the castle
    private static int exploreCastle(Scanner scanner) {
        System.out.println("You arrive at the sorcerer's castle.");
        System.out.println("The castle is surrounded by dark clouds and guarded by fearsome creatures.");
        System.out.println("What will you do?");
        System.out.println("1. Sneak into the castle");
        System.out.println("2. Challenge the guards head-on");
        System.out.println("3. Turn back");

        int choice = getUserChoice(scanner, 3);
        switch (choice) {
            case 1:
                if (hasSword && hasKey) {
                    System.out.println("You use the sword to defeat the guards and the key to unlock the castle.");
                    System.out.println("You confront the sorcerer in his throne room and defeat him with your bravery and skill.");
                    System.out.println("Congratulations! You have saved the kingdom from the sorcerer's tyranny.");
                } else {
                    System.out.println("You try to sneak into the castle, but you are caught by the guards and thrown into the dungeon.");
                    System.out.println("Game over!");
                    return STATE_END;
                }
                break;
            case 2:
                System.out.println("You charge at the guards, but they overpower you and capture you.");
                System.out.println("Game over!");
                return STATE_END;
            case 3:
                System.out.println("You decide to turn back and rethink your strategy.");
                return STATE_TOWN;
        }

        return STATE_END;
    }

    // Helper method to get user input
    private static int getUserChoice(Scanner scanner, int maxChoice) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= maxChoice) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + maxChoice + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the input buffer
            }
        }
        return choice;
    }
}
