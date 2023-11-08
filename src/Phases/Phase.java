package Phases;

import Controller.GameEngine;
import logging.LogEntryBuffer;

import java.io.IOException;

public abstract class Phase {
    public Phase(GameEngine p_ge) {
        d_ge = p_ge;
    }

    GameEngine d_ge;
    LogEntryBuffer d_logentrybuffer = LogEntryBuffer.getInstance();

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

    abstract public void next() throws IOException;

    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName());
    }
}
