package View;
import java.util.Scanner;
import Resources.Commands;


/**
 * The GameEngine class represents the startup phase of the game. It serves as the central
 * component responsible for redirecting user requests to relevant functionality of the game.
 * This class acts as the core of the game's execution and coordinates the various components
 * to provide an interactive gaming experience.
 *
 * <p>
 * The GameEngine class encapsulates the game's main loop and input handling. It initializes and manages the game's state,
 * updates the game logic, and handles user input events.
 *
 * <p>
 * Developers can extend this class to customize and add game-specific functionality. By
 * overriding the appropriate methods, you can integrate your game logic seamlessly.
 *
 * @author Adeel Saleem
 * @version 1.0
 * @since 2023-09-26
 */
public class GameEngine {
    public void start_game()
    {
        try {
            System.out.print("Welcome to the WarZone Game!\n");
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                System.out.print("Enter a command to proceed: \n");
                System.out.print("Possible commands are: \n");
                System.out.print("editmap [filename]\n");
                System.out.print("loadmap [filename]\n");
                System.out.print("showmapp all\n");

                String userInput = scanner.nextLine();
                String[] words = userInput.split("\\s+");

                if (userInput.toLowerCase().startsWith(Commands.LOAD_MAP_COMMAND))
                {
                    if (words.length == 2 && words[0].equalsIgnoreCase(Commands.LOAD_MAP_COMMAND) && words[1].matches("(?i).+\\\\.map"))
                    {
                        System.out.print("Map loaded successfully! Possible commands are: \n");
                        System.out.print("editmap [filename]\n");
                        System.out.print("loadmap [filename]\n");
                        System.out.print("showmapp\n");
                        // Write code here

                        System.out.print("Write command to add/remove players: ");
                        while (true)
                        {
                            userInput = scanner.nextLine();
                            words = userInput.split("\\s+");
                            if (userInput.toLowerCase().startsWith(Commands.PLAYER_ADD_REMOVE_COMMAND)) {
                                System.out.print("You're in: PLAYER_ADD_REMOVE_COMMAND");

                                // Write code here

                                break;
                            }
                        }

                        System.out.print("Write command to assign countries to each player: ");
                        while (true)
                        {
                            userInput = scanner.nextLine();
                            if (userInput.equalsIgnoreCase(Commands.ASSIGN_COUNTRIES_COMMAND)) {
                                System.out.print("You're in: ASSIGN_COUNTRIES_COMMAND");

                                // Write code here

                                break;
                            }
                            else
                                System.out.print("Invalid Command! Correct syntax: assigncountries");

                        }
                        break;

                    }
                    else
                        System.out.print("Invalid Command! Correct syntax: editmap [filename]");
                }

                else if (words[0].equalsIgnoreCase(Commands.SHOW_MAP_COMMAND)) {
                    System.out.print("You're in: SHOW_MAP_COMMAND");
                    break;

                } else if (words.length == 2 && words[0].equalsIgnoreCase(Commands.EDIT_MAP_COMMAND) && words[1].matches("(?i).+\\\\.map")) {
                    System.out.print("You're in: EDIT_MAP_COMMAND");
                    break;

                } else  {
                    System.out.print("Sorry I could!");

                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }
}
