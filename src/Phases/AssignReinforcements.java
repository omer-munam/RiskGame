package Phases;

import Controller.GameEngine;
import Models.Country;
import Models.Orders.DeployOrder;
import Models.Orders.Order;
import Models.Player;

public class AssignReinforcements extends OrderPhase {
    public AssignReinforcements(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("ASSIGNREINFORCEMENTS PHASE");
    }

    @Override
    public void displayOptions() {

        System.out.println("Please issue deploy orders for Player " + d_ge.getCurrentPlayer().get_playerName());
        System.out.println("Remaining reinforcements: " + d_ge.getCurrentPlayer().get_numOfReinforcements());


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
        d_logentrybuffer.writeLog("Deployed country with ID "+countryID+" with "+numOfArmies+" armies");
        if (d_ge.getCurrentPlayer().get_numOfReinforcements() == 0) {
            System.out.println("_____________________________________________");
            next();
        }
    }

    @Override
    public void issueOrder() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        if (d_ge.getCurrentPlayer().equals(d_ge.get_PlayersList().get(d_ge.get_PlayersList().size() - 1))) {
            d_ge.nextPlayer();
            d_ge.setPhase(new IssueOrders(d_ge));
        } else {
            d_ge.nextPlayer();

        }
    }
}
