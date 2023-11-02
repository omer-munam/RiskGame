package Phases;

import Controller.GameEngine;

public class Startup extends Play {
    public Startup(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void loadMap() {

    }

    @Override
    public void showMap() {

    }

    @Override
    public void setPlayers() {

    }

    @Override
    public void assignCountries() {

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

    }

    @Override
    public void next() {
        d_ge.setPhase(new IssueOrders(d_ge));
    }
}
