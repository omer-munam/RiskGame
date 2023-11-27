package Phases;

import Controller.GameEngine;
import Resources.Cards;
import logging.LogWriter;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * Phase for issuing orders
 */
public class IssueOrders extends OrderPhase {
    /**
     * Constructor for the IssueOrders phase
     *
     * @param p_ge The GameEngine
     */
    public IssueOrders(GameEngine p_ge) {
        super(p_ge);
        p_ge.getCurrentPlayer().get_diplomacy_list().clear();
    }

    /**
     * Display Options for the IssueOrders phase
     */
    @Override
    public void displayOptions() {
        System.out.println("Taking commands for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Please provide a command to execute or type execute to finish giving commands");
        if (d_ge.getCurrentPlayer().get_playerCards().size() > 0) {
            System.out.println("You have the following cards available:");
            for (Cards l_c : d_ge.getCurrentPlayer().get_playerCards()) {
                System.out.println(l_c);
            }
        }
        System.out.println("Possible commands are: \n" +
                "diplomacy targetplayer\n" +
                "blockade targetID\n" +
                "bomb targetID\n" +
                "advance sourceID targetID armies\n" +
                "airlift sourceID targetID armies\n");
    }

    /**
     * Prints invalid state message
     */
    public void deploy() {
        printInvalidCommandMessage();
    }

    /**
     * Issue Order function for the IssueOrders phase
     */
    @Override
    public void issueOrder() {
        d_ge.getCurrentPlayer().issue_order();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void endGame() {
        printInvalidCommandMessage();
    }

    /**
     * Saves the current game
     */
    @Override
    public void saveGame() {
        String[] commandTokens = d_ge.getCurrentInput().split(" ");
        if (commandTokens.length > 1) {
            d_ge.saveGame(commandTokens[1]);
        } else {
            System.out.println("Cannot save game as you did not specify a savefile");
        }

    }

    @Override
    public void next() {
        if (d_ge.getCurrentInput().toLowerCase().contains("quit")) {
            System.out.println("Exiting program");
            try {
                LogWriter.getInstance().d_info.close();
                exit(0);
            } catch (IOException e) {
                System.out.println("I/O exception closing BufferedWriter");
            }
        }
        if (d_ge.getCurrentInput().toLowerCase().contains("execute")) {
            if (d_ge.getCurrentPlayer().equals(d_ge.get_PlayersList().get(d_ge.get_PlayersList().size() - 1))) {
                d_ge.nextPlayer();
                d_ge.setPhase(new OrderExecution(d_ge));
            } else {
                d_ge.nextPlayer();
            }
        }
    }
}
