package Phases;

import Controller.GameEngine;

public class OrderExecution extends Play {
    public OrderExecution(GameEngine p_ge) {
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

    }

    @Override
    public void reinforce() {

    }

    @Override
    public void fortify() {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void next() {
        d_ge.setPhase(new AssignReinforcements(d_ge));
    }
}
