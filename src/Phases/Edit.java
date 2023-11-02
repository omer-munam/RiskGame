package Phases;

import Controller.GameEngine;

public abstract class Edit extends Phase {
    public Edit(GameEngine p_ge) {
        super(p_ge);
    }

    public void setPlayers() {
        printInvalidCommandMessage();
    }

    public void assignCountries() {
        printInvalidCommandMessage();
    }

    public void attack() {
        printInvalidCommandMessage();

    }

    public void reinforce() {
        printInvalidCommandMessage();
    }

    public void fortify() {
        printInvalidCommandMessage();
    }

    public void endGame() {
        printInvalidCommandMessage();
    }
}
