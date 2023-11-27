package Phases;

import Controller.GameEngine;

/**
 * Phase class to be inherited by all phases involved in playing the game
 */
public abstract class Play extends Phase {
    /**
     * Constructor of the Play phase
     *
     * @param p_ge The Game Engine of the play phase
     */
    public Play(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Prints invalid state message
     */
    public void editCountry() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void editContinent() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void showAllMaps() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Shows the current map during play phases
     */
    public void showMap() {
        d_ge.get_currentMap().showMap();
    }

    /**
     * Prints invalid state message
     */
    public void issueOrder() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void loadGame() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void runTournament() {
        printInvalidCommandMessage();
    }
}

