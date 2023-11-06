package Phases;

import Controller.GameEngine;
import Models.Country;
import Models.Orders.DeployOrder;
import Models.Orders.Order;

public class IssueOrders extends OrderPhase {
    public IssueOrders(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {
        System.out.println("Taking commands for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Remaining reinforcements: " + d_ge.getCurrentPlayer().get_numOfReinforcements());
        System.out.println("Please provide a command to execute or type execute to finish giving commands");
    }

    public void deploy() {
        String[] l_commandTokens = d_ge.getCurrentInput().split(" ");
        if (l_commandTokens.length != 3) {
            System.out.println("Invalid deploy order format. Syntax: deploy <countryID> <num>");
            return;
        }
        int numOfArmies;
        int countryID;
        try {
            countryID = Integer.parseInt(l_commandTokens[1]);
            numOfArmies = Integer.parseInt(l_commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid CountryID or Number of Reinforcements");
            return;
        }

        if (numOfArmies > d_ge.getCurrentPlayer().get_numOfReinforcements()) {
            System.out.println("Specified number of reinforcements exceed the available.");
            return;
        }

        boolean countryExists = false;
        for (Country country : d_ge.getCurrentPlayer().get_playerCountries()) {
            if (country.get_countryID() == countryID) {
                countryExists = true;
                break;
            }
        }

        if (!countryExists) {
            System.out.println("The given CountryID is not under your control.");
            return;
        }

        Order deployOrder = new DeployOrder(numOfArmies, countryID);
        d_ge.getCurrentPlayer().get_playerOrder().add(deployOrder);
        d_ge.getCurrentPlayer().set_numOfReinforcements(d_ge.getCurrentPlayer().get_numOfReinforcements() - numOfArmies);
        System.out.println("Deploy order issued successfully.");
        d_ge.nextPlayer();
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
        if (d_ge.getCurrentInput().toLowerCase().contains("execute")) {
            if (d_ge.getCurrentPlayer().get_numOfReinforcements() == 0) {
                d_ge.get_FinishedPlayers().add(d_ge.getCurrentPlayer());
                if (d_ge.get_FinishedPlayers().size() == d_ge.get_PlayersList().size()) {
                    d_ge.setPhase(new OrderExecution(d_ge));
                } else {
                d_ge.nextPlayer();
                }
            } else {
                System.out.println("You cannot finish giving orders until all armies have been deployed");
            }
        }

    }
}
