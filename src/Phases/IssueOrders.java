package Phases;

import Controller.GameEngine;

public class IssueOrders extends OrderPhase {
    public IssueOrders(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {

    }

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
        d_ge.setPhase(new OrderExecution(d_ge));
    }
}
