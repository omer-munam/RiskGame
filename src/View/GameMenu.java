package View;

import java.util.Scanner;

public class GameMenu {
    public static Scanner SCANNER;
    public static void main(String[] args) {
        SCANNER = new Scanner(System.in);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    chooseMap();
                    break;
                case 3:
                    addPlayer();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the game. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("===== Game Menu =====");
        System.out.println("1. Start Game"); // 1.Start game, 2.Edit map, 3. View Map, 4.Exit
        System.out.println("2. Choose Map"); // Start Game - Choose map, add player, orders
        System.out.println("3. Add Models.Player");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    public static int getUserChoice(Scanner scanner) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        return choice;
    }

    public static void startGame() {
        System.out.println("Starting the game...");
        // Implement game start logic here
    }

    public static void chooseMap() {
        System.out.println("Choosing a map...");
        // Implement map selection logic here
    }

    public static void addPlayer() {
        System.out.println("Adding a player...");
        // Implement player addition logic here
    }
}
