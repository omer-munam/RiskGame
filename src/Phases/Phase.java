package Phases;

import Controller.GameEngine;

public abstract class Phase {
    public Phase(GameEngine p_ge) {
        d_ge = p_ge;
    }

    GameEngine d_ge;

    abstract public void loadMap();

    abstract public void showMap();

    abstract public void editCountry();

    abstract public void editContinent();

    abstract public void editNeighbours();

    abstract public void saveMap();

    abstract public void setPlayers();

    abstract public void assignCountries();

    abstract public void attack();

    abstract public void reinforce();

    abstract public void fortify();

    abstract public void endGame();

    abstract public void next();

    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state " + this.getClass().getSimpleName());
    }
}
