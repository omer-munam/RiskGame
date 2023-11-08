package Phases;

import Controller.GameEngine;
import Controller.MapEditor;
import Models.Player;
import Resources.Commands;

public class Startup extends Play {
    public Startup(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("STARTUP PHASE");
    }

    @Override
    public void displayOptions() {
        System.out.print("\nEnter a command to proceed:\nPossible commands are:\n");
        System.out.print("- gameplayer -add [playername]\n");
        System.out.print("- gameplayer -remove [playername]\n");
        System.out.print("- assigncountries\n");
        System.out.print("- showmap\n");
        System.out.print("- go back\n");
    }

    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {
        d_ge.get_currentMap().showMap();
    }

    @Override
    public void setPlayers() {
        String[] l_words = d_ge.getCurrentInput().split("\\s+");
        if (d_ge.getCurrentInput().toLowerCase().startsWith(Commands.PLAYER_EDIT_COMMAND) && l_words.length >= 3) {
            for (int l_i = 1; l_i < l_words.length; l_i++) {
                if (l_words[l_i].equals("-add")) {
                    l_i++;
                    if (l_i < l_words.length) {
                        d_ge.addPlayer(l_words[l_i]);
                        d_logentrybuffer.writeLog("gameplayer "+l_words[l_i]+" added successfully");
                    } else {
                        System.out.println("Reached end of command while parsing");
                    }
                }
                if (l_words[l_i].equals("-remove")) {
                    l_i++;
                    if (l_i < l_words.length) {
                        d_ge.removePlayer(l_words[l_i]);
                        d_logentrybuffer.writeLog("gameplayer "+l_words[l_i]+" removed successfully");
                    } else {
                        System.out.println("Reached end of command while parsing");

                    }
                }
            }
        } else
            System.out.print("Invalid Command! Correct syntax: gameplayer -add [playername] -remove [playername]\n");
    }

    @Override
    public void assignCountries() {
        if (d_ge.assignCountries(false)) ;

        this.next();
    }

    @Override
    public void deploy() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        if (d_ge.getCurrentInput().equalsIgnoreCase("go back")) {
            d_ge.setPhase(new MainMenu(d_ge));
        }
        if (d_ge.getCurrentInput().equalsIgnoreCase("assigncountries")) {
            if (d_ge.get_PlayersList().size() >= 2) {
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║      Game Starts... Get Ready...       ║");
                System.out.println("╚════════════════════════════════════════╝");
                d_ge.setPhase(new AssignReinforcements(d_ge));
                System.out.println("Assigning Reinforcements....");
                System.out.println("_________________________________________");
                for (Player player : d_ge.get_PlayersList()) {
                    player.set_numOfReinforcements(d_ge.getNumOfReinforcements(player));
                    System.out.println("Assigned `" + player.get_numOfReinforcements() + "` reinforcements to player: " + player.get_playerName());
                }
                System.out.println("\n_________________________________________");
                System.out.println("Taking orders from each player....");
                System.out.println("_________________________________________");
            }
        }
    }
}
