package Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
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
 * @author Shezin Saleem
 * @author Omer Munam
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
    private WarMap d_currentMap = new WarMap();
    public void start_game()
    {
        SCANNER = new Scanner(System.in);
        try {

            while (true)
            {
                System.out.println("\n╔════════════════════════════════════════╗");
                System.out.println("║      Welcome to the WarZone Game!      ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.print("Enter a command to proceed: \n");
                System.out.print("Possible commands are: \n");
                System.out.print("- editmap\n");
                System.out.print("- loadmap [filename]\n");
                System.out.print("- showmap all\n");
                System.out.print("- quit\n");

                d_playersList.clear();

                String l_userInput = SCANNER.nextLine();
                String[] l_words = l_userInput.split("\\s+");

                if (l_userInput.toLowerCase().contains(Commands.LOAD_MAP_COMMAND))
                {
                    if (l_words.length == 2 && l_words[0].equalsIgnoreCase(Commands.LOAD_MAP_COMMAND) && l_words[1].matches("(?i).+\\.map"))
                    {
                        ArrayList<String> l_listOfMaps = getAllMapsList();
                        if (l_listOfMaps.contains(l_words[1]))
                        {
                            boolean l_isValidMap = d_currentMap.validateMap();
                            if (!l_isValidMap)
                            {
                                System.out.print("\n" + l_words[1] + " is not a valid map! Try fixing it manually or select some other map!\n");
                                continue;
                            }
                            boolean l_isAbleToReadMap = MapEditor.readmap(l_words[1], d_currentMap);
                            if (!l_isAbleToReadMap)
                            {
                                System.out.print("\n Unable to read " + l_words[1] + "!\n");
                                continue;
                            }
                            System.out.print( l_words[1] + " loaded successfully!\n");
                        }
                        else
                        {
                            System.out.print("\nUnable to find " + l_words[1] + " in our maps directory. Enter the correct spelling or select some other map!\n");
                            continue;
                        }

                        while (true)
                        {
                            System.out.print("\nEnter a command to proceed:\nPossible commands are:\n");
                            System.out.print("- gameplayer -add [playername]\n");
                            System.out.print("- gameplayer -remove [playername]\n");
                            System.out.print("- assigncountries\n");
                            System.out.print("- showmap\n");
                            System.out.print("- go back\n");
                            l_userInput = SCANNER.nextLine();
                            l_words = l_userInput.split("\\s+");

                            if (l_userInput.toLowerCase().contains("gameplayer"))
                            {
                                if (l_userInput.toLowerCase().startsWith(Commands.PLAYER_ADD_COMMAND) && l_words.length == 3)
                                {
                                    addPlayer(l_words[2]);
                                }
                                else if (l_userInput.toLowerCase().startsWith(Commands.PLAYER_REMOVE_COMMAND) && l_words.length == 3)
                                {
                                    removePlayer(l_words[2]);
                                }
                                else
                                    System.out.print("Invalid Command! Correct syntax: gameplayer -add [playername] -remove [playername]\n");
                            }
                            else if (l_userInput.equalsIgnoreCase(Commands.ASSIGN_COUNTRIES_COMMAND))
                            {
                                assignCountries();
                            }
                            else if (l_userInput.equalsIgnoreCase(Commands.SHOW_MAP_COMMAND))
                            {
                                d_currentMap.showMap();
                            }
                            else if (l_userInput.equalsIgnoreCase("go back"))
                            {
                                break;
                            }
                            else
                                System.out.print("Invalid Command. Try again with the correct command syntax!\n");
                        }
                    }
                    else
                        System.out.print("Invalid Command! Correct syntax: loadmap [filename]\n");
                }
                else if (l_userInput.equalsIgnoreCase(Commands.SHOW_ALL_MAPS_COMMAND))
                {
                    System.out.print("\nHere is the list of all the available maps:");

                    MapEditor.showAllMaps();
                }
                else if (l_words.length == 1 && l_words[0].equalsIgnoreCase(Commands.EDIT_MAP_COMMAND))
                {
                    MapEditor editor = new MapEditor();
                    editor.editMapEntry();
                }
                else if (l_userInput.equalsIgnoreCase("quit"))
                {
                    break;
                }
                else
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
        if (d_playersList.size() < 2){
            System.out.println("Please add at least 2 players using the 'gameplayer -add' command.");
            return;
        }
        else if (d_currentMap.get_countries().size() < d_playersList.size()){
            System.out.println("The players added exceed the number of countries in the map. Number of countries: " + d_currentMap.get_countries().size());
            System.out.println("Please remove extra players using the 'gameplayer -remove' command.");
            return;
        }
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

    /**
     * This function is called after the command 'addPlayer' is given. If a player already exist it displays 'Player Already Exist',
     * otherwise it adds the new player to the d_playersList and updates the d_playersList
     *
     * @param p_InputPlayerName The name of the player to add
     */
    public void addPlayer(String p_InputPlayerName){
        for (Player player : d_playersList) {
            String l_ExistingPlayerName = player.get_playerName();

            if (l_ExistingPlayerName.equals((p_InputPlayerName))) {
                System.out.println("Player " + p_InputPlayerName + " already exists.");
                return;
            }
        }

        Player l_newPlayer = new Player(p_InputPlayerName);
        l_newPlayer.set_playerName(p_InputPlayerName);
        d_playersList.add(l_newPlayer);
        System.out.println("Player " + p_InputPlayerName + " added successfully.");
    }


    /**
     * This function is called after the command 'removePlayer' is given. If a player already exist it removes the player from list and
     * displays 'Player Removed Successfully', otherwise displays 'Player doesn't exist'.
     *
     * @param p_InputPlayerName The name of the player to remove
     */

    private void removePlayer(String p_InputPlayerName){
        for (Player l_player : d_playersList) {
            if (l_player.get_playerName().equals(p_InputPlayerName)) {
                l_player.set_playerName(null);
                System.out.println("Player " + p_InputPlayerName + " removed successfully");
                return;
            }
        }
        System.out.println("Player " + p_InputPlayerName + " not found");
    }

    /**
     * Retrieves a list of filenames from the specified directory containing maps.
     *
     * This method scans a directory for map files and returns a list of their filenames.
     *
     * @return An ArrayList containing the names of map files in the directory.
     * @see Commands#MAPS_DIRECTORY_PATH
     */
    private ArrayList<String> getAllMapsList()
    {
        // Create a File object for the directory
        File l_directory = new File(Commands.MAPS_DIRECTORY_PATH);

        ArrayList<String> l_maplist = new ArrayList<String>();

        // Check if the directory exists
        if (l_directory.exists() && l_directory.isDirectory()) {
            // List all files in the directory
            File[] files = l_directory.listFiles();

            if (files != null) {
                // Iterate through the list of files and print their names
                for (File file : files) {
                    if (file.isFile()) {
                        l_maplist.add(file.getName());
                    }
                }
            }
        }
        else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }

        return l_maplist;
    }
}
