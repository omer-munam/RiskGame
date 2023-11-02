package Phases;

import Controller.GameEngine;

public class Preload extends Edit {
    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {

    }

    @Override
    public void loadMap() {

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
    public void next() {
        d_ge.setPhase(new Postload(d_ge));
    }
}
