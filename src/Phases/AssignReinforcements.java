package Phases;

import Controller.GameEngine;
import Models.Orders.Order;
import Models.Player;

public class AssignReinforcements extends OrderPhase {
    public AssignReinforcements(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {
        System.out.println("Assigning Reinforcements....");
        System.out.println("_________________________________________");
        for (Player player : d_ge.get_PlayersList()) {
            player.set_numOfReinforcements(d_ge.getNumOfReinforcements(player));
            System.out.println("Assigned `" + player.get_numOfReinforcements() + "` reinforcements to player: " + player.get_playerName());
        }
        System.out.println("\n_________________________________________");
        System.out.println("Taking orders from each player....");
        System.out.println("_________________________________________");
        for (Player player : d_ge.get_PlayersList()) {
            player.issue_order(null, d_ge.get_currentMap());
            System.out.println("_________________________________________");
        }
        for (Player player : d_ge.get_PlayersList()) {
            while (true) {
                Order order = player.next_order();
                if (order == null)
                    break;
                order.execute(d_ge.get_currentMap());
            }
        }
        System.out.println("\n_________________________________________");
        System.out.println("All commands executed successfully..... ");
        System.out.println("_________________________________________");
    }


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
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        d_ge.setPhase(new IssueOrders(d_ge));
    }
}
