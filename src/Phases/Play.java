package Phases;

import Controller.GameEngine;

public abstract class Play extends Phase {
    public Play(GameEngine p_ge) {
        super(p_ge);
    }

    public void editCountry() {
        printInvalidCommandMessage();
    }

    public void editContinent() {
        printInvalidCommandMessage();
    }

    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    public void saveMap() {
        printInvalidCommandMessage();
    }
}
