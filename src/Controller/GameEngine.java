package Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Models.Country;
import Models.Player;
import Models.WarMap;
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
    /**
     * Static scanner instance to be used all over the project.
     */
    public static Scanner SCANNER;
    /**
     * The list of players populated by the user.
     */
    private final List<Player> d_playersList = new ArrayList<>();
    /**
     * Current map that is loaded after the loadmap command.
     */
    private WarMap d_currentMap;
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
            while (true)
            {
                String userInput = SCANNER.nextLine();
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
                            userInput = SCANNER.nextLine();
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
                                assignCountries();
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

    /**
     * This function is called after the command 'assigncountries' is given. It uses the players list and the countries present in the Map class
     * to assign the countries equally to all the players. After assigning the countries this function sends the control over to the MainGameLoop class.
     */
    private void assignCountries() {
        System.out.println("Assigning Countries To Players.");
        int l_NumOfCountries = d_currentMap.get_countries().size();
        int l_NumOfCountriesToAssign = l_NumOfCountries / d_playersList.size();
        int j = 0;
        for (int k = 0; k < d_playersList.size(); k++) {
            for (int i = 0; i < l_NumOfCountriesToAssign; i++){
                d_playersList.get(k).get_playerCountries().add((Country) d_currentMap.get_countries().values().toArray()[j]);
                j++;
            }
            if (k + 1 == d_playersList.size() && j - 1 != l_NumOfCountries){
                while (j < l_NumOfCountries){
                    d_playersList.get(k).get_playerCountries().add((Country) d_currentMap.get_countries().values().toArray()[j]);
                    j++;
                }
            }
        }
        System.out.println("Assigned " + l_NumOfCountries + " Countries to players.");
        MainGameLoop l_gameLoop = new MainGameLoop(d_currentMap, d_playersList);
        l_gameLoop.run_game_loop();
    }
}
