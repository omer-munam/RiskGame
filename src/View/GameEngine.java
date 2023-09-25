package View;
import java.util.Scanner;
import Resources.Commands;
public class GameEngine {
    public void start_game()
    {
        try {
            System.out.print("Welcome to the WarZone Game!");
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                System.out.print("Enter a Command to proceed: ");
                String userInput = scanner.nextLine();

                if (userInput.toLowerCase().startsWith(Commands.LOAD_MAP_COMMAND))
                {
                    System.out.print("You're in: LOAD_MAP_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.SHOW_MAP_COMMAND)) {
                    System.out.print("You're in: SHOW_MAP_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.VALIDATE_MAP_COMMAND)) {
                    System.out.print("You're in: VALIDATE_MAP_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.SAVE_MAP_COMMAND)) {
                    System.out.print("You're in: SAVE_MAP_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.EDIT_MAP_COMMAND)) {
                    System.out.print("You're in: EDIT_MAP_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.ASSIGN_COUNTRIES_COMMAND)) {
                    System.out.print("You're in: ASSIGN_COUNTRIES_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.PLAYER_ADD_REMOVE_COMMAND)) {
                    System.out.print("You're in: PLAYER_ADD_REMOVE_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.EDIT_CONTINENT_COMMAND)) {
                    System.out.print("You're in: EDIT_CONTINENT_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.EDIT_COUNTRY_COMMAND)) {
                    System.out.print("You're in: EDIT_COUNTRY_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.EDIT_NEIGHBOR_COMMAND)) {
                    System.out.print("You're in: EDIT_NEIGHBOR_COMMAND");
                    break;

                } else if (userInput.toLowerCase().startsWith(Commands.DEPLOY_COMMAND)) {
                    System.out.print("You're in: DEPLOY_COMMAND");
                    break;

                } else  {
                    System.out.print("Invalid Command!");

                }
            }

        } catch (Exception e) {
            // Handle the exception if the user enters something that's not an integer
            System.out.println("Invalid input. ERRORRRRR!!!!!!!!!!!!");
        }
    }
}
