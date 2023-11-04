package Phases;

import Controller.GameEngine;

public abstract class OrderPhase extends Play {
    public OrderPhase(GameEngine p_ge) {
        super(p_ge);
    }


    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {
        d_ge.get_currentMap().showMap(d_ge.get_PlayersList());
    }

    @Override
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }


}
