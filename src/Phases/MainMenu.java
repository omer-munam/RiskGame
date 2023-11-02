package Phases;

import Controller.GameEngine;

public class MainMenu extends Phase {
    public MainMenu(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry() {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent() {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
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
        if (d_ge.getCurrentInput().equals("Test")) {
            d_ge.setPhase(new Preload(d_ge));
        }
    }
}
