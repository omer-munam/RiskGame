package Phases;

import Controller.GameEngine;
import Models.Orders.Order;
import Models.Player;
import Resources.Cards;

import java.util.ArrayList;

/**
 * Phase for executing orders
 */
public class OrderExecution extends Play {
    /**
     * The constructor for the Order Execution phase
     *
     * @param p_ge The Game Engine
     */
    public OrderExecution(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("Order Execution Phase");

    }

    /**
     * The option display for the order execution phase
     */
    @Override
    public void displayOptions() {
        //Deploy Orders
        for (Player player : d_ge.get_PlayersList()) {
            d_logentrybuffer.writeLog(player.get_playerName() + " deploy orders executing");
            ArrayList<Order> l_ordersToReadd = new ArrayList<>();
            while (true) {
                Order order = player.next_order();
                if (order == null)
                    break;
                if (order.getClass().getSimpleName().equals("DeployOrder")) {
                    order.execute(d_ge.get_currentMap());
                } else {
                    l_ordersToReadd.add(order);
                }
            }
            for (Order l_o : l_ordersToReadd) {
                player.get_playerOrder().add(l_o);
            }
        }
        //Advance orders
        for (Player player : d_ge.get_PlayersList()) {
            d_logentrybuffer.writeLog(player.get_playerName() + " advance order executing");
            ArrayList<Order> l_ordersToReadd = new ArrayList<>();
            while (true) {
                Order order = player.next_order();
                if (order == null)
                    break;
                if (order.getClass().getSimpleName().equals("AdvanceOrder")) {
                    order.execute(d_ge.get_currentMap());
                } else {
                    l_ordersToReadd.add(order);
                }
            }
            for (Order l_o : l_ordersToReadd) {
                player.get_playerOrder().add(l_o);
            }
        }
        //Other orders
        for (Player player : d_ge.get_PlayersList()) {
            d_logentrybuffer.writeLog(player.get_playerName() + " misc order executing");
            ArrayList<Order> l_ordersToReadd = new ArrayList<>();
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

        d_logentrybuffer.writeLog("All orders executed successfully");
        ArrayList<Player> l_playersToRemove = new ArrayList<>();
        for (Player player : d_ge.get_PlayersList()) {
            if (player.get_playerCountries().size() == 0) {
                l_playersToRemove.add(player);
            }
        }
        for (Player player : l_playersToRemove) {
            System.out.println(player.get_playerName() + " is out of countries and has been removed from the game");
            d_logentrybuffer.writeLog(player.get_playerName() + " has been removed from the game");
            d_ge.get_PlayersList().remove(player);
        }
        if (d_ge.get_PlayersList().size() == 1) {
            System.out.println(d_ge.get_PlayersList().get(0).get_playerName() + " is the only player left and has won the game.");
            d_logentrybuffer.writeLog(d_ge.get_PlayersList().get(0).get_playerName() + " is the only player left and has won the game");
            d_ge.setPhase(new MainMenu(d_ge));
            return;
        }

        this.next();
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
        printInvalidCommandMessage();
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

    /**
     * Prints invalid state message
     */
    @Override
    public void deploy() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void endGame() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void saveGame() {
        printInvalidCommandMessage();
    }

    /**
     * The next function for the order execution phase
     */
    @Override
    public void next() {
        Cards.assignRandomCardsToPlayers();
        d_logentrybuffer.writeLog("ASSIGNREINFORCEMENTS PHASE");
        System.out.println("Assigning Reinforcements....");
        System.out.println("_________________________________________");
        for (Player player : d_ge.get_PlayersList()) {
            player.set_numOfReinforcements(d_ge.getNumOfReinforcements(player));
            d_logentrybuffer.writeLog("assigned " + player.get_playerName() + " " + player.get_numOfReinforcements() + " no of reinforcement armies.");
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
        d_logentrybuffer.writeLog("ISSUE ORDERS PHASE");
        Cards.clearPlayerAcquiredTerritory();


    }
}
