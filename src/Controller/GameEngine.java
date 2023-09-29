package Controller;
import java.util.Scanner;
import Resources.Commands;
import java.util.regex.*;

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
    public static Scanner SCANNER;
    public void start_game()
    {
        SCANNER = new Scanner(System.in);
        try {

            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║      Welcome to the WarZone Game!      ║");
            System.out.println("╚════════════════════════════════════════╝");

            System.out.print("Enter a command to proceed: \n");
            System.out.print("Possible commands are: \n");
            System.out.print("- editmap [filename]\n");
            System.out.print("- loadmap [filename]\n");
            System.out.print("- showmap all\n");
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                String userInput = scanner.nextLine();
                String[] words = userInput.split("\\s+");

                if (userInput.toLowerCase().contains(Commands.LOAD_MAP_COMMAND))
                {
                    if (words.length == 2 && words[0].equalsIgnoreCase(Commands.LOAD_MAP_COMMAND) && words[1].matches("(?i).+\\.map"))
                    {
                        System.out.print( words[1] + " loaded successfully!\n\nEnter a command to proceed:\nPossible commands are:\n");
                        System.out.print("- gameplayer -add [playername]\n");
                        System.out.print("- gameplayer -remove [playername]\n");
                        System.out.print("- assigncountries\n");
                        System.out.print("- showmap\n");

                        while (true)
                        {
                            userInput = scanner.nextLine();
                            words = userInput.split("\\s+");

                            if (userInput.toLowerCase().contains("gameplayer"))
                            {
                                if (userInput.toLowerCase().startsWith(Commands.PLAYER_ADD_COMMAND) && words.length == 3)
                                {
                                    System.out.print("You're in: PLAYER_ADD_COMMAND");

                                    // Write code here

                                    break;
                                }
                                else if (userInput.toLowerCase().startsWith(Commands.PLAYER_REMOVE_COMMAND) && words.length == 3)
                                {
                                    System.out.print("You're in: PLAYER_REMOVE_COMMAND");

                                    // Write code here

                                    break;
                                }
                                else
                                    System.out.print("Invalid Command! Correct syntax: gameplayer -add [playername] -remove [playername]\n");
                            }
                            else if (userInput.equalsIgnoreCase(Commands.ASSIGN_COUNTRIES_COMMAND))
                            {
                                System.out.print("You're in: ASSIGN_COUNTRIES_COMMAND");

                                // Write code here

                                break;
                            }
                            else if (userInput.equalsIgnoreCase(Commands.SHOW_MAP_COMMAND))
                            {
                                System.out.print("You're in: SHOW_MAP_COMMAND");

                                // Write code here

                                break;
                            }
                            else
                                System.out.print("Invalid Command. Try again with the correct syntax!\n");
                        }
                        break;
                    }
                    else
                        System.out.print("Invalid Command! Correct syntax: loadmap [filename]\n");
                }
                else if (userInput.equalsIgnoreCase(Commands.SHOW_ALL_MAPS_COMMAND))
                {
                    System.out.print("You're in: SHOW_ALL_MAPS_COMMAND");

                    // Write code here

                    break;

                } else if (words.length == 2 && words[0].equalsIgnoreCase(Commands.EDIT_MAP_COMMAND) && words[1].matches("(?i).+\\.map"))
                {
                    System.out.print("You're in: EDIT_MAP_COMMAND");

                    // Write code here

                    MapEditor editor = new MapEditor();
                    editor.editMapEntry();
                    break;

                } else
                {
                    System.out.print("Sorry, I couldn't understand the command you entered.\nTry again with the correct syntax!\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong!\n");
        }
    }
}
