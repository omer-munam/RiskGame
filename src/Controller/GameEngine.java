package Controller;

import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.MainMenu;
import Phases.Phase;
import Resources.Commands;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    public GameEngine() {
        d_gamePhase = new MainMenu(this);
    }

    private Set<Player> d_finishedPlayers = new HashSet<>();


    public Set<Player> get_FinishedPlayers() {
        return d_finishedPlayers;
    }


    private Phase d_gamePhase;
    private String d_currentInput = "";
    private Player d_currentPlayer = new Player("Default");
    private int d_currentPlayerIndex = 0;

    public Player getCurrentPlayer() {
        return d_currentPlayer;
    }

    public void setCurrentPlayer(Player p_player) {
        d_currentPlayer = p_player;
        d_currentPlayerIndex = d_playersList.indexOf(p_player);
    }

    public void nextPlayer() {
        if (d_finishedPlayers.size() == d_playersList.size()) {
            System.out.println("Cannot go to next player as all players are finished");
        } else {
            while (true) {
        if (d_currentPlayerIndex == d_playersList.size() - 1) {
            d_currentPlayerIndex = 0;
            d_currentPlayer = d_playersList.get(d_currentPlayerIndex);
            if (!d_finishedPlayers.contains(d_currentPlayer)) {
                break;
            }
        } else {
            d_currentPlayerIndex++;
            d_currentPlayer = d_playersList.get(d_currentPlayerIndex);
            if (!d_finishedPlayers.contains(d_currentPlayer)) {
                break;
            }
        }
            }
        }
    }

    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

    public String getCurrentInput() {
        return d_currentInput;
    }
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

    /**
     * @return the current loaded map
     */
    public WarMap get_currentMap() {
        return d_currentMap;
    }

    /**
     * @param p_map the map you wish to load
     */
    public void set_currentMap(WarMap p_map) {
        d_currentMap = p_map;
    }

    /**
     * @return the list of players
     */
    public List<Player> get_PlayersList() {
        return d_playersList;
    }

    /**
     * @param p_playersList the list of players
     */
    public void set_PlayersList(List<Player> p_playersList) {
        d_playersList.clear();
        if (p_playersList != null) {
            d_playersList.addAll(p_playersList);
        }
    }

    /**
     * Contains the main logic for the WarZone game and passes control to other aspects of the program when certain commands are entered.
     */
    public void start_game() {
        SCANNER = new Scanner(System.in);
        try {

            while (true) {
                d_gamePhase.displayOptions();

                d_currentInput = SCANNER.nextLine();
                String[] l_words = d_currentInput.split("\\s+");

                if (d_currentInput.toLowerCase().contains(Commands.LOAD_MAP_COMMAND)) {
                    d_gamePhase.loadMap();
                } else if (d_currentInput.toLowerCase().contains("gameplayer")) {
                    d_gamePhase.setPlayers();
                } else if (d_currentInput.equalsIgnoreCase(Commands.ASSIGN_COUNTRIES_COMMAND)) {
                    d_gamePhase.assignCountries();
                    if (d_playersList.size() != 0) {
                        d_currentPlayer = d_playersList.get(0);
                    }

                } else if (d_currentInput.equalsIgnoreCase(Commands.SHOW_MAP_COMMAND)) {
                    d_gamePhase.showMap();
                } else if (d_currentInput.equalsIgnoreCase("go back")) {
                    d_gamePhase.next();
                } else if (d_currentInput.equalsIgnoreCase(Commands.SHOW_ALL_MAPS_COMMAND)) {
                    d_gamePhase.showAllMaps();

                } else if (d_currentInput.equalsIgnoreCase(Commands.EDIT_MAP_COMMAND)) {
                    d_gamePhase.next();
                } else if (d_currentInput.toLowerCase().contains(Commands.EDIT_MAP_COMMAND)) {
                    d_gamePhase.loadMap();
                } else if (d_currentInput.toLowerCase().contains(Commands.DEPLOY_COMMAND)) {
                    d_gamePhase.deploy();
                } else if (d_currentInput.toLowerCase().contains(Commands.EXECUTE)) {
                    d_gamePhase.next();
                } else if (d_currentInput.toLowerCase().contains("editcontinent")) {
                    d_gamePhase.editContinent();
                } else if (d_currentInput.toLowerCase().contains("editcountry")) {
                    d_gamePhase.editCountry();
                } else if (d_currentInput.toLowerCase().contains("editneighbor")) {
                    d_gamePhase.editNeighbours();
                } else if (d_currentInput.toLowerCase().contains("validatemap")) {
                    d_gamePhase.validateMap();
                } else if (d_currentInput.toLowerCase().contains("savemap")) {
                    d_gamePhase.saveMap();
                } else if (d_currentInput.equalsIgnoreCase("quit")) {
                    d_gamePhase.next();
                } else {
                    System.out.print("Sorry, I couldn't understand the command you entered.\nTry again with the correct syntax!\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function is called after the command 'assigncountries' is given. It uses the players list and the countries present in the Map class
     * to assign the countries equally to all the players. After assigning the countries this function sends the control over to the MainGameLoop class.
     *
     * @param p_test This boolean is for test only. Keep false otherwise.
     */
    public boolean assignCountries(boolean p_test) {
        if (d_playersList.size() < 2) {
            System.out.println("Please add at least 2 players using the 'gameplayer -add' command.");
            return false;
        } else if (d_currentMap.get_countries().size() < d_playersList.size()) {
            System.out.println("The players added exceed the number of countries in the map. Number of countries: " + d_currentMap.get_countries().size());
            System.out.println("Please remove extra players using the 'gameplayer -remove' command.");
            return false;
        }
        System.out.println("Assigning Countries To Players.");
        int l_NumOfCountries = d_currentMap.get_countries().size();
        HashMap<Integer, Boolean> l_CountryAssigned = new HashMap<Integer, Boolean>();
        for (Integer l_countryId : d_currentMap.get_countries().keySet()) l_CountryAssigned.put(l_countryId, false);

        Random l_RandomIndexCountry = new Random();
        int l_CountryIndex;
        for (int i = 0; i < l_NumOfCountries; ) {
            for (int j = 0; j < d_playersList.size() && i < l_NumOfCountries; j++, i++) {
                Player player = d_playersList.get(j);
                while (true) {
                    l_CountryIndex = l_RandomIndexCountry.nextInt(l_NumOfCountries) + 1;
                    if (!l_CountryAssigned.get(l_CountryIndex)) {
                        player.get_playerCountries().add(d_currentMap.get_countries().get(l_CountryIndex));
                        l_CountryAssigned.put(l_CountryIndex, true);
                        break;
                    }
                }
            }
        }
        System.out.println("Assigned " + l_NumOfCountries + " Countries to players.");
        if (p_test)
            return false;

        return true;
    }

    /**
     * This function is called after the command 'addPlayer' is given. If a player already exist it displays 'Player Already Exist',
     * otherwise it adds the new player to the d_playersList and updates the d_playersList
     *
     * @param p_InputPlayerName The name of the player to add
     */
    public void addPlayer(String p_InputPlayerName) {
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

    public void removePlayer(String p_InputPlayerName) {
        if (d_playersList.removeIf(player ->
                player.get_playerName().equals(p_InputPlayerName))) {
            System.out.println("Player " + p_InputPlayerName + " removed successfully");
            return;
        }
        System.out.println("Player " + p_InputPlayerName + " not found");
    }

    /**
     * Retrieves a list of filenames from the specified directory containing maps.
     * <p>
     * This method scans a directory for map files and returns a list of their filenames.
     *
     * @return An ArrayList containing the names of map files in the directory.
     * @see Commands#MAPS_DIRECTORY_PATH
     */
    public ArrayList<String> getAllMapsList() {
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
        } else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }

        return l_maplist;
    }

    /**
     * This method is used to calculate how many reinforcements are to be assigned to the given player based on the continents they hold.
     *
     * @param p_player The player for which we need to get number of reinforcements.
     * @return NumberOfReinforcements
     */
    public int getNumOfReinforcements(Player p_player) {
        int l_baseReinforcements = 5;
        d_currentMap.get_countries();
        p_player.get_playerCountries();
        HashMap<Integer, ArrayList<Integer>> l_continent_countries = new HashMap<>();
        for (Country l_c : d_currentMap.get_countries().values()) {
            l_continent_countries.putIfAbsent(l_c.getContinentID(), new ArrayList<Integer>());
            l_continent_countries.get(l_c.getContinentID()).add(l_c.get_countryID());
        }


        HashSet<Integer> l_full_continents = new HashSet<>();
        ArrayList<Integer> l_player_country_ids = new ArrayList<>();
        for (Country l_country : p_player.get_playerCountries()) {
            l_player_country_ids.add(l_country.get_countryID());
        }

        for (ArrayList<Integer> l_c : l_continent_countries.values()) {
            for (int l_i : l_c) {
                if (l_player_country_ids.contains(l_i)) {
                    l_full_continents.add(d_currentMap.get_countries().get(l_i).getContinentID());
                } else {
                    l_full_continents.remove(d_currentMap.get_countries().get(l_i).getContinentID());
                    break;
                }
            }
        }
        for (int l_i : l_full_continents) {
            l_baseReinforcements += d_currentMap.get_continents().get(l_i).get_armyBonus();
        }
        return l_baseReinforcements;
    }

}
