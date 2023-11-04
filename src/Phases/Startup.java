package Phases;

import Controller.GameEngine;
import Controller.MapEditor;
import Resources.Commands;

public class Startup extends Play {
    public Startup(GameEngine p_ge) {
        super(p_ge);
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
                    } else {
                        System.out.println("Reached end of command while parsing");
                    }
                }
                if (l_words[l_i].equals("-remove")) {
                    l_i++;
                    if (l_i < l_words.length) {
                        d_ge.removePlayer(l_words[l_i]);
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
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      Game Starts... Get Ready...       ║");
        System.out.println("╚════════════════════════════════════════╝");
        this.next();
    }

    @Override
    public void attack() {
        printInvalidCommandMessage();
    }

    @Override
    public void reinforce() {
        printInvalidCommandMessage();
    }

    @Override
    public void fortify() {
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
            d_ge.setPhase(new AssignReinforcements(d_ge));
        }
    }
}
