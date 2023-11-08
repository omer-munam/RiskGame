package Phases;

import Controller.GameEngine;

public abstract class Edit extends Phase {
    /**
     * Constructor for Edit Phase
     *
     * @param p_ge The GameEngine of the phase
     */
    public Edit(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Prints invalid state message
     */
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void attack() {
        printInvalidCommandMessage();

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
    public void deploy() {
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
    public void reinforce() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void fortify() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    public void endGame() {
        printInvalidCommandMessage();
    }
}
