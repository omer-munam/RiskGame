package Phases;

import Controller.GameEngine;
import Resources.Cards;

import static java.lang.System.exit;

public class IssueOrders extends OrderPhase {
    public IssueOrders(GameEngine p_ge) {
        super(p_ge);
        p_ge.getCurrentPlayer().get_diplomacy_list().clear();
    }

    @Override
    public void displayOptions() {
        System.out.println("Taking commands for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Please provide a command to execute or type execute to finish giving commands");
        if (d_ge.getCurrentPlayer().get_playerCards().size() > 0) {
            System.out.println("You have the following cards available:");
            for (Cards l_c : d_ge.getCurrentPlayer().get_playerCards()) {
                System.out.println(l_c);
            }
        }
        System.out.println("Possible commands are: \n" +
                "diplomacy targetplayer\n" +
                "blockade targetID\n" +
                "bomb targetID\n" +
                "advance sourceID targetID armies\n" +
                "airlift sourceID targetID armies\n");
    }

    public void deploy() {
        printInvalidCommandMessage();
    }

    @Override
    public void issueOrder() {
        d_ge.getCurrentPlayer().issue_order();
    }

    @Override
    public void endGame() {

    }
    @Override
    public void next() {
        if (d_ge.getCurrentInput().toLowerCase().contains("quit")) {
            System.out.println("Exiting program");
            exit(0);
        }
        if (d_ge.getCurrentInput().toLowerCase().contains("execute")) {
            if (d_ge.getCurrentPlayer().equals(d_ge.get_PlayersList().get(d_ge.get_PlayersList().size() - 1))) {
                d_ge.nextPlayer();
                d_ge.setPhase(new OrderExecution(d_ge));
            } else {
                d_ge.nextPlayer();
                d_ge.setPhase(new IssueOrders(d_ge));
            }
        }
    }
}
