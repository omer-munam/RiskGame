package Phases;

import Controller.GameEngine;

public class Postload extends Edit {
    public Postload(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {

    }

    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {

    }

    @Override
    public void editCountry() {

    }

    @Override
    public void editContinent() {

    }

    @Override
    public void editNeighbours() {

    }

    @Override
    public void saveMap() {

    }

    @Override
    public void next() {
        d_ge.setPhase(new MainMenu(d_ge));
    }
}
