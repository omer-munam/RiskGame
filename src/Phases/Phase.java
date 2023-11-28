package Phases;

import Controller.GameEngine;
import logging.LogEntryBuffer;

import java.io.IOException;

/**
 * Base phase class to be inherited by all other phases
 */
public abstract class Phase {
    /**
     * The Game Engine of the Phases
     */
    GameEngine d_ge;
    /**
     * The Log Entry Buffer of the Phases
     */
    LogEntryBuffer d_logentrybuffer = LogEntryBuffer.getInstance();
    /**
     * The constructor of the Phase class
     *
     * @param p_ge The game engine
     */
    public Phase(GameEngine p_ge) {
        d_ge = p_ge;
    }

    abstract public void displayOptions();

    abstract public void loadMap() throws IOException;

    abstract public void showMap();

    abstract public void validateMap();

    abstract public void showAllMaps();

    abstract public void editCountry();

    abstract public void editContinent();

    abstract public void editNeighbours();

    abstract public void saveMap() throws IOException;

    abstract public void setPlayers();

    abstract public void assignCountries();

    abstract public void deploy();

    abstract public void issueOrder();

    abstract public void endGame();

    abstract public void saveGame();

    abstract public void loadGame();

    abstract public void runTournament() throws IOException;



    abstract public void next() throws IOException;

    /**
     * Prints invalid state message
     */
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName());
    }
}
