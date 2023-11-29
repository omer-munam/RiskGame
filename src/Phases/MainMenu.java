package Phases;

import Adapter.MapEditorAdapter;
import Controller.GameEngine;
import Controller.MapEditor;
import Models.BehaviourStrategies.BehaviourStrategy;
import Models.BehaviourStrategies.BenevolentStrategy;
import Models.Player;
import Models.WarMap;
import Resources.Commands;
import logging.LogWriter;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Phase for the main menu
 */
public class MainMenu extends Phase {
    /**
     * Constructor for the MainMenu phase
     *
     * @param p_ge The GameEngine
     */
    public MainMenu(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("Main Menu Phase");
    }

    /**
     * Display Options for the MainMenu Phase
     */
    @Override
    public void displayOptions() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      Welcome to the WarZone Game!      ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Enter a command to proceed: \n");
        System.out.print("Possible commands are: \n");
        System.out.print("- editmap\n");
        System.out.print("- loadmap [filename]\n");
        System.out.print("- showmapall\n");
        System.out.print("- loadgame [filename]\n");
        System.out.print("- tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns\n");
        System.out.print("- quit\n");
        d_ge.get_PlayersList().clear();
        d_ge.set_currentMap(new WarMap());
    }

    /**
     * Load Map for the Main Menu Phase
     *
     * @throws IOException Exception if IO error occurs
     */
    @Override
    public void loadMap() throws IOException {
        String[] l_words = d_ge.getCurrentInput().split("\\s+");
        if (l_words.length == 2 && l_words[0].equalsIgnoreCase(Commands.LOAD_MAP_COMMAND) && l_words[1].matches("(?i).+\\.map")) {
            ArrayList<String> l_listOfMaps = d_ge.getAllMapsList();
            d_ge.set_currentMap(null);
            d_ge.set_currentMap(new WarMap());
            if (l_listOfMaps.contains(l_words[1])) {
                boolean l_isAbleToReadMap = MapEditor.readMap(l_words[1], d_ge.get_currentMap());
                if (!l_isAbleToReadMap) {
                    System.out.print("\n Unable to read " + l_words[1] + "!\n");
                    return;
                }
                boolean l_isValidMap = d_ge.get_currentMap().validateMap();
                if (!l_isValidMap) {
                    System.out.print("\n" + l_words[1] + " is not a valid map! Try fixing it manually or select some other map!\n");
                    return;
                }
                System.out.print(l_words[1] + " loaded successfully!\n");
                d_logentrybuffer.writeLog(l_words[1] + " loaded successfully.");
                this.next();
            } else {
                System.out.print("\nUnable to find " + l_words[1] + " in our maps directory. Enter the correct spelling or select some other map!\n");

            }
        } else if (l_words.length == 2 && l_words[0].equalsIgnoreCase(Commands.LOAD_MAP_COMMAND) && l_words[1].matches("(?i).+\\.conquest")) {
            ArrayList<String> l_listOfMaps = d_ge.getAllMapsList();
            d_ge.set_currentMap(null);
            d_ge.set_currentMap(new WarMap());
            if (l_listOfMaps.contains(l_words[1])) {
                boolean l_isAbleToReadMap = MapEditorAdapter.readMap(l_words[1], d_ge.get_currentMap());
                if (!l_isAbleToReadMap) {
                    System.out.print("\n Unable to read " + l_words[1] + "!\n");
                    return;
                }

                boolean l_isValidMap = d_ge.get_currentMap().validateMap();
                if (!l_isValidMap) {
                    System.out.print("\n" + l_words[1] + " is not a valid map! Try fixing it manually or select some other map!\n");
                    return;
                }
                System.out.print(l_words[1] + " loaded successfully!\n");
                d_logentrybuffer.writeLog(l_words[1] + " loaded successfully.");
                this.next();
            } else {
                System.out.print("\nUnable to find " + l_words[1] + " in our maps directory. Enter the correct spelling or select some other map!\n");

            }
        } else {
            System.out.println("Loadmap using the command loadmap [mapname]");
        }
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editCountry() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editContinent() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void deploy() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void issueOrder() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void endGame() {
        printInvalidCommandMessage();
    }

    /**
     * Shows all maps from the main menu
     */
    public void showAllMaps() {
        System.out.println("\nHere is the list of all the available maps:");
        MapEditor.showAllMaps();
        d_logentrybuffer.writeLog("showmap command runned successfully");
    }

    /**
     * The next function for the main menu phase
     *
     * @throws IOException Exception if IO error occurs
     */
    @Override
    public void next() throws IOException {
        if (d_ge.getCurrentInput().toLowerCase().contains(Commands.LOAD_MAP_COMMAND)) {
            d_ge.setPhase(new Startup(d_ge));
        }
        if (d_ge.getCurrentInput().toLowerCase().contains(Commands.EDIT_MAP_COMMAND)) {
            d_logentrybuffer.writeLog("editmap command runned successfully");
            d_ge.setPhase(new Preload(d_ge));

        }
        if (d_ge.getCurrentInput().toLowerCase().contains("quit")) {
            System.out.println("Exiting program");
            try {
                LogWriter.getInstance().d_info.close();
                exit(0);
            } catch (IOException e) {
                System.out.println("I/O exception closing BufferedWriter");
            }
        }
    }

    /**
     * Prints invalid state message
     */
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Loads a game
     */
    public void loadGame() {
        String[] l_input = d_ge.getCurrentInput().split(" ");
        if (l_input.length > 1) {
            d_ge.loadGame(l_input[1]);
        } else {
            System.out.println("No save file specified");
        }
    }

    /**
     * Prints invalid state message
     */
    public void saveGame() {
        printInvalidCommandMessage();
    }

    /**
     * Runs a tournament
     */
    public void runTournament() throws IOException {
        String[] l_input = d_ge.getCurrentInput().split(" ");
        ArrayList<String> l_maps = new ArrayList<>();
        ArrayList<String> l_strategies = new ArrayList<>();
        int l_games = 0;
        int l_turns = 0;
        String l_currentState = "Start";
        for (String l_s : l_input) {
            if (l_currentState.equals("Start")) {
                if (l_s.equalsIgnoreCase("tournament")) {

                }
                if (l_s.equalsIgnoreCase("-M")) {
                    l_currentState = "Maps";
                    continue;
                }
            }
            if (l_currentState.equalsIgnoreCase("Maps")) {
                if (l_s.equalsIgnoreCase("-P")) {
                    l_currentState = "Players";
                    continue;
                } else {
                    l_maps.add(l_s);
                }
            }
            if (l_currentState.equalsIgnoreCase("Players")) {
                if (l_s.equalsIgnoreCase("-G")) {
                    l_currentState = "Games";
                    continue;
                } else if (l_s.equalsIgnoreCase("benevolent")) {
                    l_strategies.add("Benevolent");
                } else if (l_s.equalsIgnoreCase("cheater")) {
                    l_strategies.add("Cheater");
                } else if (l_s.equalsIgnoreCase("random")) {
                    l_strategies.add("Random");
                } else if (l_s.equalsIgnoreCase("aggressive")) {
                    l_strategies.add("Aggressive");
                } else {

                    System.out.println("You have entered an invalid player type");
                    return;
                }
            }
            if (l_currentState.equalsIgnoreCase("Games")) {
                if (l_s.equalsIgnoreCase("-D")) {
                    l_currentState = "Turns";
                    continue;
                } else {
                    try {
                        l_games = Integer.valueOf(l_s);
                    } catch (NumberFormatException e) {
                        System.out.println("String entered for number of games");
                        return;
                    }
                }
            }
            if (l_currentState.equalsIgnoreCase("Turns")) {
                try {
                    l_turns = Integer.valueOf(l_s);
                } catch (NumberFormatException e) {
                    System.out.println("String entered for number of turns");
                    return;
                }
            }
        }
        d_ge.runTournament(l_maps, l_strategies, l_games, l_turns);
    }
}
