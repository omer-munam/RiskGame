package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Orders.*;
import Models.Player;
import Models.WarMap;
import Resources.Cards;

import java.util.List;
import java.util.Objects;

public abstract class BehaviourStrategyBase implements BehaviourStrategy {
    Player d_player;
    BehaviourStrategyBase(Player p_player){
        d_player = p_player;
    }
    /**
     * This method is called for diplomacy orders, checks if the target player name exists.
     * If true, add the player name to diplomacy list, which will be checked before other order execution.
     *
     * @param p_commandTokens The order command input
     * @param p_map           The WarMap
     * @param p_list          The PlayerList
     */
    protected void diplomacy_issue_order(String[] p_commandTokens, WarMap p_map, List<Player> p_list) {
        boolean l_hasDiplomacyCard = false;
        for (Cards l_card : d_player.get_playerCards()) {
            if (l_card.toString().equals("Diplomacy")) {
                l_hasDiplomacyCard = true;
                break;
            }
        }
        if (l_hasDiplomacyCard) {
            //check if playerID exists in playerList
            //Do diplomacy logic
            String l_targetPlayerName;
            l_targetPlayerName = p_commandTokens[1];

            boolean l_targetPlayerNameExists = false;

            for (Player l_player : p_list)
                if (Objects.equals(l_player.get_playerName(), l_targetPlayerName)) {
                    l_targetPlayerNameExists = true;
                    break;
                }
            if (!l_targetPlayerNameExists) {
                System.out.println("The given Player name doesn't exists.");

            } else {
                d_player.get_diplomacy_list().add(l_targetPlayerName);

                System.out.println("Diplomacy order executed successfully.");
            }
        } else {
            System.out.println("Player do not have Diplomacy card");
        }
    }

    /**
     * This method is called for blockade orders, check if the destination country exists
     * If true create a new order and executses in blockadeOrder and finally removes the blockade card from player cards.
     *
     * @param p_commandTokens The order command input
     * @param p_map           The WarMap
     */
    protected void blockade_issue_order(String[] p_commandTokens, WarMap p_map) {
        boolean l_hasBlockadeCard = false;
        for (Cards l_card : d_player.get_playerCards()) {
            if (l_card.toString().equals("Blockade")) {
                l_hasBlockadeCard = true;
                break;
            }
        }
        if (l_hasBlockadeCard) {
            int l_destCountryID;
            l_destCountryID = Integer.parseInt(p_commandTokens[1]);

            boolean l_destCountryIDExists = false;
            for (Country l_country : d_player.get_playerCountries())
                if (l_country.get_countryID() == l_destCountryID) {
                    l_destCountryIDExists = true;
                    break;
                }
            if (!l_destCountryIDExists) {
                System.out.println("The given CountryID is not under your control.");
            } else {
                BlockadeOrder l_order = new BlockadeOrder(l_destCountryID, d_player);
                d_player.get_playerOrder().add(l_order);
                d_player.get_playerCards().remove(Cards.Blockade);
                System.out.println("Blockade order executed successfully.");
            }
        } else {
            System.out.println("Player do not have Blockade card");
        }

    }

    /**
     * The method which creates a bomb order command.
     *
     * @param p_commandTokens input command.
     * @param p_map           current map.
     */
    protected void bomb_issue_order(String[] p_commandTokens, WarMap p_map) {
        boolean l_hasBombCard = d_player.get_playerCards().contains(Cards.Bomb);
        if (!l_hasBombCard) {
            System.out.println("Player does not have Bomb card");
            return;
        }
        int l_destCountryID;
        l_destCountryID = Integer.parseInt(p_commandTokens[1]);


        boolean l_countryValid = false;
        for (Country l_country : p_map.get_countries().values()) {
            if (l_country.get_countryID() == l_destCountryID) {
                l_countryValid = true;
                break;
            }
        }
        if (!l_countryValid) {
            System.out.println("Please provide a valid country.");
            return;
        }
        for (Country l_country : d_player.get_playerCountries())
            if (l_country.get_countryID() == l_destCountryID) {
                System.out.println("You cannot bomb your own country.");
                return;
            }

        BombOrder l_order = new BombOrder(l_destCountryID, d_player);
        d_player.get_playerOrder().add(l_order);
        d_player.get_playerCards().remove(Cards.Bomb);
        System.out.println("Bomb order issued successfully.");
    }

    /**
     * This method is called for advance orders, check if advance order is possible
     * If possible it adds the advance order to the player's order list.
     *
     * @param p_commandTokens The order command input
     * @param p_map           The WarMap
     */
    protected void advance_issue_order(String[] p_commandTokens, WarMap p_map) {

        // Check if the command contains the correct number of tokens.
        if (p_commandTokens.length != 4) {
            System.out.println("Invalid advance order format. Syntax: advance countryIDfrom countyIDto numarmies");
            return;
        }

        // Parse the source country name and target country name.
        int l_sourceCountryID;
        int l_targetCountryID;
        try {
            l_sourceCountryID = Integer.parseInt(p_commandTokens[1]);
            l_targetCountryID = Integer.parseInt(p_commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid source or target country IDs specified.");
            return;
        }
        // Parse the number of armies to advance.
        int l_numArmies;
        try {
            l_numArmies = Integer.parseInt(p_commandTokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies specified.");
            return;
        }

        // Find the source and target countries.
        Country l_sourceCountry = null;
        Country l_targetCountry = null;
        for (Country l_country : d_player.get_playerCountries()) {
            if (l_country.get_countryID() == l_sourceCountryID) {
                l_sourceCountry = l_country;
            }
        }
        if (l_sourceCountry == null) {
            System.out.println("Source country not found");
            return;
        }
        for (Country country : l_sourceCountry.getNeighbouringCountries().values()) {
            if (country.get_countryID() == l_targetCountryID) {
                l_targetCountry = country;
            }
        }
        if (l_targetCountry == null) {
            System.out.println("Listed target was not a neighbouring country to the source");
            return;
        }


        // Create an AdvanceOrder and add it to the player's list of orders.
        AdvanceOrder l_advanceOrder = new AdvanceOrder(d_player, l_sourceCountry, l_targetCountry, l_numArmies);
        d_player.get_playerOrder().add(l_advanceOrder);
    }

    /**
     * This method is called for airlift orders, check if the airlift order is possible
     * If possible it adds the airlift order to the player's order list.
     *
     * @param p_commandTokens The order command input
     */
    protected void airlift_issue_order(String[] p_commandTokens) {
        // Check if the player has the Airlift card.
        boolean l_hasAirliftCard = d_player.get_playerCards().contains(Cards.Airlift);
        if (!l_hasAirliftCard) {
            System.out.println("You don't have the Airlift card to issue an Airlift order.");
            return;
        }

        // Check if the command contains the correct number of tokens.
        if (p_commandTokens.length != 4) {
            System.out.println("Invalid airlift order format. Syntax: airlift sourcecountryID targetcountryID numarmies");
            return;
        }

        // Parse the source country ID and target country ID.
        int l_sourceCountryID;
        int l_targetCountryID;
        try {
            l_sourceCountryID = Integer.parseInt(p_commandTokens[1]);
            l_targetCountryID = Integer.parseInt(p_commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid source or target country IDs specified.");
            return;
        }

        // Parse the number of armies to airlift.
        int l_numArmies;
        try {
            l_numArmies = Integer.parseInt(p_commandTokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies specified.");
            return;
        }

        // Find the source and target countries.
        Country l_sourceCountry = null;
        Country l_targetCountry = null;
        for (Country country : d_player.get_playerCountries()) {
            if (country.get_countryID() == l_sourceCountryID) {
                l_sourceCountry = country;
            }
            if (country.get_countryID() == l_targetCountryID) {
                l_targetCountry = country;
            }
        }

        // Check if the source and target countries are valid and under the player's control.
        if (l_sourceCountry == null || l_targetCountry == null) {
            System.out.println("Source or target country not found or not under your control.");
            return;
        }

        // Create an AirliftOrder and add it to the player's list of orders.
        AirliftOrder l_airliftOrder = new AirliftOrder(d_player, l_sourceCountry, l_targetCountry, l_numArmies);
        d_player.get_playerOrder().add(l_airliftOrder);
        d_player.get_playerCards().remove(Cards.Airlift);
    }

    /**
     * This method is called for deploy orders, check if deploy order is possible
     * If possible it adds the order to the players order list.
     *
     * @param p_commandTokens The order command input
     */
    protected void deploy_issue_order(String[] p_commandTokens) {

        if (p_commandTokens.length != 3) {
            System.out.println("Invalid deploy order format. Syntax: deploy <countryID> <num>");
            return;
        }
        int l_numOfArmies;
        int l_countryID;
        try {
            l_countryID = Integer.parseInt(p_commandTokens[1]);
            l_numOfArmies = Integer.parseInt(p_commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid CountryID or Number of Reinforcements");
            return;
        }

        if (l_numOfArmies > GameEngine.getInstance().getCurrentPlayer().get_numOfReinforcements()) {
            System.out.println("Specified number of reinforcements exceed the available.");
            return;
        }

        boolean l_countryExists = false;
        for (Country l_country : GameEngine.getInstance().getCurrentPlayer().get_playerCountries()) {
            if (l_country.get_countryID() == l_countryID) {
                l_countryExists = true;
                break;
            }
        }

        if (!l_countryExists) {
            System.out.println("The given CountryID is not under your control.");
            return;
        }


        DeployOrder l_deployOrder = new DeployOrder(l_numOfArmies, l_countryID);
        d_player.get_playerOrder().add(l_deployOrder);
        d_player.set_numOfReinforcements(GameEngine.getInstance().getCurrentPlayer().get_numOfReinforcements() - l_numOfArmies);
        System.out.println("Deploy order issued successfully.");
    }

    public abstract void issue_order();
}
