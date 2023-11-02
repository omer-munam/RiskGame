package Phases;

import Controller.GameEngine;
import Controller.MapEditor;

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

    public void showAllMaps() {
        printInvalidCommandMessage();
    }

    public void validateMap() {
        printInvalidCommandMessage();
    }
    public void showMap() {
        d_ge.get_currentMap().showMap();
    }
}

