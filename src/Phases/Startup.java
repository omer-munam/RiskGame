package Phases;

import Controller.GameEngine;
import Models.Player;
import Resources.Commands;
import logging.LogWriter;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * Phase for starting up gameplay
 */
public class Startup extends Play {
    /**
     * The constructor of the Startup phase
     *
     * @param p_ge The game engine of the phase
     */
    public Startup(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("STARTUP PHASE");
    }

    /**
     * The display options of the startup phase
     */
    @Override
    public void displayOptions() {
        System.out.print("\nEnter a command to proceed:\nPossible commands are:\n");
        System.out.print("- gameplayer -add [playername]\n");
        System.out.print("- gameplayer -remove [playername]\n");
        System.out.print("- assigncountries\n");
        System.out.print("- showmap\n");
        System.out.print("- goback\n");
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    /**
     * The showmap function of the startup phase
     */
    @Override
    public void showMap() {
        d_logentrybuffer.writeLog("showmap command runned successfully.");
        d_ge.get_currentMap().showMap();
    }

    /**
     * The set players function of the startup phase
     */
    @Override
    public void setPlayers() {
        String[] l_words = d_ge.getCurrentInput().split("\\s+");
        if (d_ge.getCurrentInput().toLowerCase().startsWith(Commands.PLAYER_EDIT_COMMAND) && l_words.length >= 3) {
            for (int l_i = 1; l_i < l_words.length; l_i++) {
                if (l_words[l_i].equals("-add")) {
                    l_i++;
                    if (l_i < l_words.length) {
                        d_ge.addPlayer(l_words[l_i]);
                        d_logentrybuffer.writeLog("gameplayer " + l_words[l_i] + " added successfully");
                    } else {
                        System.out.println("Reached end of command while parsing");
                    }
                }
                if (l_words[l_i].equals("-remove")) {
                    l_i++;
                    if (l_i < l_words.length) {
                        d_ge.removePlayer(l_words[l_i]);
                        d_logentrybuffer.writeLog("gameplayer " + l_words[l_i] + " removed successfully");
                    } else {
                        System.out.println("Reached end of command while parsing");

                    }
                }
            }
        } else
            System.out.print("Invalid Command! Correct syntax: gameplayer -add [playername] -remove [playername]\n");
    }

    /**
     * The assigncountries function of the startup phase
     */
    @Override
    public void assignCountries() {
        if (d_ge.assignCountries(false)) ;
        d_logentrybuffer.writeLog("assigncountries command runned successfully.");
        this.next();
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
    public void endGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveGame() {
        printInvalidCommandMessage();
    }

    /**
     * The next function of the stateup phase
     */
    @Override
    public void next() {
        if (d_ge.getCurrentInput().equalsIgnoreCase("goback")) {
            d_ge.setPhase(new MainMenu(d_ge));
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
        if (d_ge.getCurrentInput().equalsIgnoreCase("assigncountries")) {
            if (d_ge.get_PlayersList().size() >= 2) {
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║      Game Starts... Get Ready...       ║");
                System.out.println("╚════════════════════════════════════════╝");
                d_ge.setPhase(new AssignReinforcements(d_ge));
                d_logentrybuffer.writeLog("ASSIGNREINFORCEMENTS PHASE");
                System.out.println("Assigning Reinforcements....");
                System.out.println("_________________________________________");
                for (Player player : d_ge.get_PlayersList()) {
                    player.set_numOfReinforcements(d_ge.getNumOfReinforcements(player));
                    System.out.println("Assigned `" + player.get_numOfReinforcements() + "` reinforcements to player: " + player.get_playerName());
                    d_logentrybuffer.writeLog("assigned " + player.get_playerName() + " " + player.get_numOfReinforcements() + " no of reinforcement armies.");
                }
                System.out.println("\n_________________________________________");
                System.out.println("Taking orders from each player....");
                System.out.println("_________________________________________");
                d_logentrybuffer.writeLog("ISSUEORDERS PHASE");
            }
        }
    }
}
