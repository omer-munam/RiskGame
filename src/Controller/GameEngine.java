package Controller;

import Adapter.MapEditorAdapter;
import Adapter.MapEditorConquest;
import Models.BehaviourStrategies.*;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.AssignReinforcements;
import Phases.MainMenu;
import Phases.Phase;
import Phases.Startup;
import Resources.Cards;
import Resources.Commands;
import logging.LogEntryBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    /**
     * Static scanner instance to be used all over the project.
     */
    public static Scanner SCANNER;
    /**
     * Instance of the GameEngine
     */
    private static GameEngine Instance;
    /**
     * The list of players populated by the user.
     */
    private final List<Player> d_playersList = new ArrayList<>();
    /**
     * Players who have finished their turns
     */
    private final Set<Player> d_finishedPlayers = new HashSet<>();
    /**
     * The current phase of the GameEngine
     */
    private Phase d_gamePhase;
    /**
     * The current input of the user
     */
    private String d_currentInput = "";
    /**
     * The current player taking their turn
     */
    private Player d_currentPlayer = new Player("Default");
    /**
     * The index of the current player in the list of players
     */
    private int d_currentPlayerIndex = 0;
    /**
     * Current map that is loaded after the loadmap command.
     */
    private WarMap d_currentMap = new WarMap();
    /**
     * Instance of the log entry buffer
     */
    LogEntryBuffer d_logentrybuffer = LogEntryBuffer.getInstance();
    /**
     * GameEngine Constructor
     */
    private GameEngine() {
        d_gamePhase = new MainMenu(this);
    }

    /**
     * Function for accessing the GameEngine instance
     *
     * @return the instance of the GameEngine
     */
    public static GameEngine getInstance() {
        if (Instance == null)
            Instance = new GameEngine();
        return Instance;
    }

    /**
     * @return The list of players who have finished their turns
     */
    public Set<Player> get_FinishedPlayers() {
        return d_finishedPlayers;
    }

    /**
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return d_currentPlayer;
    }

    /**
     * Sets a new current player
     *
     * @param p_player The player to be set as the current player
     */
    public void setCurrentPlayer(Player p_player) {
        d_currentPlayer = p_player;
        d_currentPlayerIndex = d_playersList.indexOf(p_player);
    }

    /**
     * A function to set the current player to the next player
     */
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

    /**
     * @return The current phase of the GameEngine
     */
    public Phase getPhase() {
        return d_gamePhase;
    }

    /**
     * @param p_phase The phase to set the GameEngine too
     */
    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

    /**
     * @return The current input of the GameEngine
     */
    public String getCurrentInput() {
        return d_currentInput;
    }

    /**
     * @param p_input The input to set as the game engines current input
     */
    public void setCurrentInput(String p_input) {
        d_currentInput = p_input;
    }

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


                if ((d_gamePhase.getClass().getSimpleName().equals("AssignReinforcements") || d_gamePhase.getClass().getSimpleName().equals("IssueOrders")) && !d_currentPlayer.getD_behaviourStrategy().getClass().getSimpleName().equals("HumanStrategy")) {
                    if (d_gamePhase.getClass().getSimpleName().equals("AssignReinforcements")) {
                        setCurrentInput("deploy");
                        d_gamePhase.deploy();
                    }
                    if (d_gamePhase.getClass().getSimpleName().equals("IssueOrders")) {
                        d_gamePhase.issueOrder();
                        setCurrentInput("execute");
                        d_gamePhase.next();
                    }

                } else {

                    d_currentInput = SCANNER.nextLine();
                    String[] l_words = d_currentInput.split("\\s+");
                    switch (l_words[0].toLowerCase()) {
                        case "loadgame":
                            d_gamePhase.loadGame();
                            break;
                        case "savegame":
                            d_gamePhase.saveGame();
                            break;
                        case "tournament":

                            d_gamePhase.runTournament();

                            break;
                        case Commands.LOAD_MAP_COMMAND:
                            d_gamePhase.loadMap();
                            break;
                        case "gameplayer":
                            d_gamePhase.setPlayers();
                            break;
                        case Commands.ASSIGN_COUNTRIES_COMMAND:
                            d_gamePhase.assignCountries();
                            if (!d_playersList.isEmpty()) {
                                d_currentPlayer = d_playersList.get(0);
                            }
                            break;
                        case Commands.SHOW_MAP_COMMAND:
                            d_gamePhase.showMap();
                            break;
                        case "goback":
                            d_gamePhase.next();
                            break;
                        case Commands.EXECUTE:
                        case "quit":
                            d_gamePhase.next();
                            break;
                        case Commands.EDIT_MAP_COMMAND:
                            if (l_words.length > 1)
                                d_gamePhase.loadMap();
                            else
                                d_gamePhase.next();
                            break;
                        case Commands.SHOW_ALL_MAPS_COMMAND:
                            d_gamePhase.showAllMaps();
                            break;
                        case Commands.DEPLOY_COMMAND:
                            d_gamePhase.deploy();
                            break;
                        case Commands.ADVANCE_ORDER:
                        case Commands.BOMB_ORDER:
                        case Commands.BLOCKADE_ORDER:
                        case Commands.AIRLIFT_ORDER:
                        case Commands.DIPLOMACY_ORDER:
                            d_gamePhase.issueOrder();
                            break;
                        case "editcontinent":
                            d_gamePhase.editContinent();
                            break;
                        case "editcountry":
                            d_gamePhase.editCountry();
                            break;
                        case "editneighbor":
                            d_gamePhase.editNeighbours();
                            break;
                        case "validatemap":
                            d_gamePhase.validateMap();
                            break;
                        case "savemap":
                            d_gamePhase.saveMap();
                            break;
                        default:
                            System.out.print("Sorry, I couldn't understand the command you entered.\nTry again with the correct syntax!\n");
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String start_tournament_game(int p_turns) throws IOException {
        this.setPhase(new Startup(this));
        this.setCurrentInput("assigncountries");
        int l_turnCounter = 1;
        d_gamePhase.assignCountries();

        if (!d_playersList.isEmpty()) {
            d_currentPlayer = d_playersList.get(0);
        }
        this.setPhase(new AssignReinforcements(this));
        while (true) {


            if (d_gamePhase.getClass().getSimpleName().equals("AssignReinforcements")) {
                setCurrentInput("deploy");
                d_gamePhase.deploy();
            }
            if (d_gamePhase.getClass().getSimpleName().equals("IssueOrders")) {
                d_gamePhase.issueOrder();
                setCurrentInput("execute");
                d_gamePhase.next();
            }
            if (d_gamePhase.getClass().getSimpleName().equalsIgnoreCase("OrderExecution")) {
                d_gamePhase.displayOptions();
                l_turnCounter++;
                System.out.println("Current Turn is " + l_turnCounter);
            }

            if (this.get_PlayersList().size() == 1) return this.get_PlayersList().get(0).get_playerName();


            if (l_turnCounter >= p_turns) {
                this.setPhase(new MainMenu(this));
                return "Draw";
            }
        }
    }

    /**
     * This function is called after the command 'assigncountries' is given. It uses the players list and the countries present in the Map class
     * to assign the countries equally to all the players. After assigning the countries this function sends the control over to the MainGameLoop class.
     *
     * @param p_test This boolean is for test only. Keep false otherwise.
     * @return True if countries could be assigned
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
                        System.out.println(d_currentMap.get_countries().get(l_CountryIndex) + " has been assigned to " + player.get_playerName());
                        d_logentrybuffer.writeLog(d_currentMap.get_countries().get(l_CountryIndex) + " has been assigned to " + player.get_playerName());
                        d_currentMap.get_countries().get(l_CountryIndex).setD_ownerPlayer(player);
                        l_CountryAssigned.put(l_CountryIndex, true);
                        break;
                    }
                }
            }
        }
        System.out.println("Assigned " + l_NumOfCountries + " Countries to players.");
        return !p_test;
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
        setPlayerStrategy(l_newPlayer);
        d_playersList.add(l_newPlayer);
        System.out.println("Player " + p_InputPlayerName + " added successfully.");
    }

    /**
     * Function to add a player with given input strategy
     *
     * @param p_InputPlayerName name of player
     * @param p_InputStrategy   the input strategy
     */
    public void addPlayer(String p_InputPlayerName, String p_InputStrategy) {
        for (Player player : d_playersList) {
            String l_ExistingPlayerName = player.get_playerName();

            if (l_ExistingPlayerName.equals((p_InputPlayerName))) {
                System.out.println("Player " + p_InputPlayerName + " already exists.");
                return;
            }
        }
        Player l_newPlayer = new Player(p_InputPlayerName);
        l_newPlayer.set_playerName(p_InputPlayerName);
        if (p_InputStrategy.equalsIgnoreCase("human"))
            l_newPlayer.setD_behaviourStrategy(new HumanStrategy(l_newPlayer));
        if (p_InputStrategy.equalsIgnoreCase("cheater"))
            l_newPlayer.setD_behaviourStrategy(new CheaterStrategy(l_newPlayer));
        if (p_InputStrategy.equalsIgnoreCase("benevolent"))
            l_newPlayer.setD_behaviourStrategy(new BenevolentStrategy(l_newPlayer));
        if (p_InputStrategy.equalsIgnoreCase("random"))
            l_newPlayer.setD_behaviourStrategy(new RandomStrategy(l_newPlayer));
        if (p_InputStrategy.equalsIgnoreCase("aggressive"))
            l_newPlayer.setD_behaviourStrategy(new AggressiveStrategy(l_newPlayer));
        if (l_newPlayer.getD_behaviourStrategy() == null) {
            System.out.println("Invalid behaviour specified player not added");
            return;
        }
        d_playersList.add(l_newPlayer);
        System.out.println("Player " + p_InputPlayerName + " added successfully.");
    }

    /**
     * Function called while adding a player to set its behaviour.
     *
     * @param l_newPlayer the player for which to set the strategy.
     */
    private void setPlayerStrategy(Player l_newPlayer) {
        BehaviourStrategy strategy = null;
        while (strategy == null){
            System.out.println("Enter the desired behavior strategy for " + l_newPlayer.get_playerName() + ":");
            switch (SCANNER.nextLine()){
                case "human":
                    strategy = new HumanStrategy(l_newPlayer);
                    break;
                case "aggressive":
                    strategy = new AggressiveStrategy(l_newPlayer);
                    break;
                case "benevolent":
                    strategy = new BenevolentStrategy(l_newPlayer);
                    break;
                case "cheater":
                    strategy = new CheaterStrategy(l_newPlayer);
                    break;
                case "random":
                    strategy = new RandomStrategy(l_newPlayer);
                    break;
                default:
                    System.out.println("Invalid Strategy Entered. Please try again...\n");
            }
        }
        l_newPlayer.setD_behaviourStrategy(strategy);
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

    /**
     * Saves a game to a specified game file
     *
     * @param p_filename The save file
     */
    public void saveGame(String p_filename) {
        try {
            FileWriter l_saveFile = new FileWriter(System.getProperty("user.dir") + "\\Src\\Resources\\Saves\\" + p_filename);
            l_saveFile.write(d_currentMap.get_mapName() + "\n");
            l_saveFile.write(d_playersList.size() + "\n");
            for (Player p : d_playersList) {
                l_saveFile.write(p.get_playerName() + " " + p.getD_behaviourStrategy().getClass().getSimpleName() + " " + p.get_playerCountries().size() + " " + (p.get_playerCards().size()) + "\n");
                for (Country l_c : p.get_playerCountries()) {
                    l_saveFile.write(l_c.get_countryID() + " " + l_c.get_numOfArmies() + "\n");
                }
                for (Cards l_c : p.get_playerCards()) {
                    l_saveFile.write(l_c.toString() + "\n");
                }
            }
            l_saveFile.close();
        } catch (IOException l_e) {
            l_e.printStackTrace();
        }

    }

    /**
     * Loads a game from a save file
     * @param p_filename The save file
     */
    public void loadGame(String p_filename) {
        try {
            BufferedReader l_bufferReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Src\\Resources\\Saves\\" + p_filename));
            String l_line = l_bufferReader.readLine();
            WarMap l_gameMap = new WarMap();
            if (l_line.matches("(?i).+\\.map")) {
                MapEditor l_mapReader = new MapEditor();
                l_gameMap = l_mapReader.readMap(l_line);
            }
            if (l_line.matches("(?i).+\\.conquest")) {
                MapEditor l_mapReader = new MapEditorAdapter(new MapEditorConquest());
                l_gameMap = l_mapReader.readMap(l_line);
            }
            set_currentMap(l_gameMap);
            l_line = l_bufferReader.readLine();
            int l_playerCount = Integer.valueOf(l_line);
            ArrayList<Player> l_inputPlayerList = new ArrayList<>();
            for (int l_i = 0; l_i < l_playerCount; l_i++) {
                l_line = l_bufferReader.readLine();
                String[] l_inputArray = l_line.split(" ");
                Player l_inputPlayer = new Player(l_inputArray[0]);
                switch (l_inputArray[1]) {
                    case "HumanStrategy":
                        l_inputPlayer.setD_behaviourStrategy(new HumanStrategy(l_inputPlayer));
                        break;
                    case "AggressiveStrategy":
                        l_inputPlayer.setD_behaviourStrategy(new AggressiveStrategy(l_inputPlayer));
                        break;
                    case "BenevolentStrategy":
                        l_inputPlayer.setD_behaviourStrategy(new BenevolentStrategy(l_inputPlayer));
                        break;
                    case "CheaterStrategy":
                        l_inputPlayer.setD_behaviourStrategy(new CheaterStrategy(l_inputPlayer));
                        break;
                    case "RandomStrategy":
                        l_inputPlayer.setD_behaviourStrategy(new RandomStrategy(l_inputPlayer));
                        break;
                }
                int l_numberOfCountries = Integer.valueOf(l_inputArray[2]);
                ArrayList<Country> l_inputPlayerCountries = new ArrayList<>();
                int l_numberOfCards = Integer.valueOf(l_inputArray[3]);
                for (int l_j = 0; l_j < l_numberOfCountries; l_j++) {
                    l_line = l_bufferReader.readLine();
                    l_inputArray = l_line.split(" ");
                    l_inputPlayerCountries.add(d_currentMap.get_countries().get(Integer.valueOf(l_inputArray[0])));

                    l_inputPlayerCountries.get(l_j).set_numOfArmies(Integer.parseInt(l_inputArray[1]));
                    l_inputPlayerCountries.get(l_j).setD_ownerPlayer(l_inputPlayer);
                }
                l_inputPlayer.set_playerCountries(l_inputPlayerCountries);
                for (int l_z = 0; l_z < l_numberOfCards; l_z++) {
                    l_inputPlayer.get_playerCards().add(Cards.valueOf(l_bufferReader.readLine()));
                }
                l_inputPlayer.set_diplomacy_list(new ArrayList<String>());
                d_playersList.add(l_inputPlayer);

            }
            for (Player l_player : d_playersList) {
                l_player.set_numOfReinforcements(this.getNumOfReinforcements(l_player));
                d_logentrybuffer.writeLog("assigned " + l_player.get_playerName() + " " + l_player.get_numOfReinforcements() + " no of reinforcement armies.");
                System.out.println("Assigned `" + l_player.get_numOfReinforcements() + "` reinforcements to player: " + l_player.get_playerName());
            }
            setCurrentPlayer(d_playersList.get(0));
            setPhase(new AssignReinforcements(this));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs a tournament
     *
     * @param p_maps       The maps to be ran
     * @param p_strategies The strategies of the player
     * @param p_games      The number of games
     * @param p_maxturns   The max amount of turns
     */
    public void runTournament(ArrayList<String> p_maps, ArrayList<String> p_strategies, int p_games, int p_maxturns) {
        if (p_maps.size() < 1 || p_maps.size() > 5) {
            System.out.println("Invalid amount of maps");
            return;
        }
        if (p_strategies.size() < 2 || p_strategies.size() > 4) {
            System.out.println("Invalid amount of players");
            return;
        }
        if (p_games < 1 || p_games > 5) {
            System.out.println("Invalid number of games per map");
            return;
        }
        if (p_maxturns < 10 || p_maxturns > 50) {
            System.out.println("Invalid number of turns per game");
            return;
        }
        try{
        Player l_inputPlayer;
        ArrayList<String> l_results = new ArrayList<>();
        for (int l_z = 0; l_z < p_maps.size(); l_z++) {
            WarMap l_inputMap = new WarMap();

            if (p_maps.get(l_z).matches("(?i).+\\.map")) {
                MapEditor l_mapReader = new MapEditor();
                l_inputMap = l_mapReader.readMap(p_maps.get(l_z));
            } else if (p_maps.get(l_z).matches("(?i).+\\.conquest")) {
                MapEditor l_mapReader = new MapEditorAdapter(new MapEditorConquest());
                l_inputMap = l_mapReader.readMap(p_maps.get(l_z));
            } else {
                System.out.println("Improper map format entered, exiting tournament");
                return;
            }
            ;
            if (!l_inputMap.validateMap()) {
                System.out.println("You have loaded an invalid map, exiting tournament");
                return;
            }
            this.set_currentMap(l_inputMap);
            System.out.println("Starting games for map " + l_inputMap.get_mapName());
            for (int l_i = 0; l_i < p_games; l_i++) {
                this.get_PlayersList().clear();
                ArrayList<Player> l_players = new ArrayList<Player>();
                for (int l_j = 0; l_j < p_strategies.size(); l_j++) {
                    l_inputPlayer = new Player(p_strategies.get(l_j));
                    if (p_strategies.get(l_j).equalsIgnoreCase("Benevolent")) {
                        l_inputPlayer.setD_behaviourStrategy(new BenevolentStrategy(l_inputPlayer));
                    }
                    if (p_strategies.get(l_j).equalsIgnoreCase("Cheater")) {
                        l_inputPlayer.setD_behaviourStrategy(new CheaterStrategy(l_inputPlayer));
                    }
                    if (p_strategies.get(l_j).equalsIgnoreCase("Aggressive")) {
                        l_inputPlayer.setD_behaviourStrategy(new AggressiveStrategy(l_inputPlayer));
                    }
                    if (p_strategies.get(l_j).equalsIgnoreCase("Random")) {
                        l_inputPlayer.setD_behaviourStrategy(new RandomStrategy(l_inputPlayer));

                    }
                    l_players.add(l_inputPlayer);
                }
                this.set_PlayersList(l_players);

                l_results.add(start_tournament_game(p_maxturns));

            }
        }
        System.out.println("Printing results");
        System.out.println();

        for (int l_i = 0; l_i < p_maps.size(); l_i++) {
            System.out.print(p_maps.get(l_i) + ": ");
            for (int l_j = 0; l_j < p_games; l_j++) {
                System.out.print(l_results.get(l_i * p_games + l_j) + "    ");
            }
            System.out.println();
        }
        System.out.println();
            System.out.println("Done printing results");
        } catch (Exception e) {
            System.out.println("Error while running tournament");
        }
    }
}

