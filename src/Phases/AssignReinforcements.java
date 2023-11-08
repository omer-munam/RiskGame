package Phases;

import Controller.GameEngine;
import logging.LogWriter;

import java.io.IOException;

import static java.lang.System.exit;

public class AssignReinforcements extends OrderPhase {
    /**
     * Constructor for AssignReinforcements phase
     *
     * @param p_ge Game Engine
     */
    public AssignReinforcements(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Display Options for AssignReinforcements phase.
     */
    @Override
    public void displayOptions() {

        System.out.println("Please issue deploy orders for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Remaining reinforcements: " + d_ge.getCurrentPlayer().get_numOfReinforcements());


    }

    /**
     * Deploy for AssignReinforcements phase
     */
    public void deploy() {
        d_ge.getCurrentPlayer().issue_order();

        if (d_ge.getCurrentPlayer().get_numOfReinforcements() == 0) {
            System.out.println("_____________________________________________");
            next();
        }
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
     * Next function for Assign Reinforcements phase
     */
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
        if (d_ge.getCurrentPlayer().equals(d_ge.get_PlayersList().get(d_ge.get_PlayersList().size() - 1))) {
            d_ge.nextPlayer();
            d_ge.setPhase(new IssueOrders(d_ge));
        } else {
            d_ge.nextPlayer();

        }
    }
}
