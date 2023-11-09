package Phases;

import Controller.GameEngine;
import Controller.MapEditor;
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
}
