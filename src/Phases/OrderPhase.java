package Phases;

import Controller.GameEngine;

public abstract class OrderPhase extends Play {
    /**
     * The constructor for the OrderPhase
     *
     * @param p_ge The Game Engine
     */
    public OrderPhase(GameEngine p_ge) {
        super(p_ge);
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void loadMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void showMap() {
        d_ge.get_currentMap().showMap(d_ge.get_PlayersList());
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }


}
