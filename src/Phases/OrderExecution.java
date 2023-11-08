package Phases;

import Controller.GameEngine;
import Models.Orders.Order;
import Models.Player;
import Resources.Cards;

public class OrderExecution extends Play {
    public OrderExecution(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {
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
        this.next();
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
    public void deploy() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {

    }

    @Override
    public void next() {
        Cards.assignRandomCardsToPlayers();
        System.out.println("Assigning Reinforcements....");
        System.out.println("_________________________________________");
        for (Player player : d_ge.get_PlayersList()) {
            player.set_numOfReinforcements(d_ge.getNumOfReinforcements(player));
            System.out.println("Assigned `" + player.get_numOfReinforcements() + "` reinforcements to player: " + player.get_playerName());
        }
        System.out.println("\n_________________________________________");
        System.out.println("Taking orders from each player....");
        System.out.println("_________________________________________");
        System.out.println("Please issue commands for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Remaining reinforcements: " + d_ge.getCurrentPlayer().get_numOfReinforcements());
        d_ge.get_FinishedPlayers().clear();
        d_ge.setCurrentPlayer(d_ge.get_PlayersList().get(0));
        d_ge.setPhase(new AssignReinforcements(d_ge));
    }
}
