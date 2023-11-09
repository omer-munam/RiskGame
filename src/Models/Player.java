package Models;

import Controller.GameEngine;
import Models.Orders.*;
import Resources.Cards;
import Resources.Commands;
import logging.LogEntryBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class describes information about each player and the order that were issued using the logic
 * present in the same class.
 *
 * @author Omer Munam
 * @author Leila
 */
public class Player {

    /**
     * List of Countries that the player controls.
     */
    List<Country> d_playerCountries;
    /**
     * List of Continents that the player controls.
     */
    List<Continent> d_playerContinents;
    /**
     * number of reinforcements given to the player at the start of every round.
     */
    Integer d_numOfReinforcements;
    /**
     * List of player's orders for execution.
     */
    List<Order> d_playerOrders;
    /**
     * List of Cards the player holds.
     */
    List<Cards> d_playerCards;
    LogEntryBuffer d_logentrybuffer = LogEntryBuffer.getInstance();
    /**
     * The name of the player taken by the user.
     */
    private String d_playerName;
    /**
     * List of players to be negotiated with.
     */
    private List<String> d_diplomacy_list;

    /**
     * This is the constructor method of the Models.Player class
     *
     * @param p_playerName is player's name.
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_numOfReinforcements = Integer.valueOf(0);
        this.d_playerOrders = new ArrayList<Order>();
        this.d_playerCountries = new ArrayList<Country>();
        this.d_playerContinents = new ArrayList<Continent>();
        this.d_diplomacy_list = new ArrayList<>();
        this.d_playerCards = new ArrayList<>();
    }

    /**
     * @return the player name
     */
    public String get_playerName() {
        return d_playerName;
    }

    /**
     * @param p_name the player name
     */
    public void set_playerName(String p_name) {
        this.d_playerName = p_name;
    }

    /**
     * @return a list of the player's countries
     */
    public List<Country> get_playerCountries() {
        return d_playerCountries;
    }

    /**
     * @param p_playerCountries a list of the player's countries
     */
    public void set_playerCountries(List<Country> p_playerCountries) {
        this.d_playerCountries = p_playerCountries;
    }

    /**
     * @return a list of the player's continents
     */
    public List<Continent> get_playerContinents() {
        return d_playerContinents;
    }

    /**
     * @param p_playerContinents a list of the player's continents
     */
    public void set_playerContinents(List<Continent> p_playerContinents) {
        this.d_playerContinents = p_playerContinents;
    }

    /**
     * @return a list of the players to be negotiated with.
     */
    public List<String> get_diplomacy_list() {
        return d_diplomacy_list;
    }

    /**
     * @param d_diplomacy_list a list of the players to be negotiated with.
     */
    public void set_diplomacy_list(List<String> d_diplomacy_list) {
        this.d_diplomacy_list = d_diplomacy_list;
    }

    /**
     * @return a list of the player's cards
     */
    public List<Cards> get_playerCards() {
        return d_playerCards;
    }

    /**
     * @param p_playerCards a list of the player's cards
     */
    public void set_playerCards(List<Cards> p_playerCards) {
        this.d_playerCards = p_playerCards;
    }

    /**
     * @return a list of the player's orders
     */
    public List<Order> get_playerOrder() {
        return d_playerOrders;
    }

    /**
     * @param p_playerOrder a list of the player's orders
     */
    public void set_playerOrder(List<Order> p_playerOrder) {
        this.d_playerOrders = p_playerOrder;
    }

    /**
     * @return the number of reinforcements the player should get
     */
    public Integer get_numOfReinforcements() {
        return d_numOfReinforcements;
    }

    /**
     * @param p_armiesNumber the number of reinforcements the player should get
     */
    public void set_numOfReinforcements(Integer p_armiesNumber) {
        this.d_numOfReinforcements = p_armiesNumber;
    }

    /**
     * “issue_order()” (no parameters, no return value) whose function is
     * to add an order to the list of orders held by the
     * player when the game engine calls it during the issue orders phase.
     */
    public void issue_order() {
        GameEngine l_ge = GameEngine.getInstance();
        WarMap l_map = l_ge.get_currentMap();
        String l_command = l_ge.getCurrentInput();
        String[] commandTokens = l_command.split(" ");
        switch (commandTokens[0]) {
            case Commands.DEPLOY_COMMAND:
                deploy_issue_order(commandTokens);
                break;
            case Commands.ADVANCE_ORDER:
                advance_issue_order(commandTokens, l_map);
                break;
            case Commands.BOMB_ORDER:
                bomb_issue_order(commandTokens, l_map);
                break;
            case Commands.BLOCKADE_ORDER:
                blockade_issue_order(commandTokens, l_map);
                break;
            case Commands.AIRLIFT_ORDER:
                airlift_issue_order(commandTokens);
                break;
            case Commands.DIPLOMACY_ORDER:
                diplomacy_issue_order(commandTokens, l_map, l_ge.get_PlayersList());
                break;
            default:
                System.out.println("Invalid command given... Please try again...");
        }
    }

    /**
     * This method is called for diplomacy orders, checks if the target player name exists.
     * If true, add the player name to diplomacy list, which will be checked before other order execution.
     *
     * @param p_commandTokens The order command input
     * @param p_map           The WarMap
     * @param p_list          The PlayerList
     */
    private void diplomacy_issue_order(String[] p_commandTokens, WarMap p_map, List<Player> p_list) {
        boolean l_hasDiplomacyCard = false;
        for (Cards l_card : d_playerCards) {
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
                d_diplomacy_list.add(l_targetPlayerName);

                System.out.println("Diplomacy order executed successfully.");
                d_logentrybuffer.writeLog(d_playerName + " issued negotiate order on " + l_targetPlayerName);
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
    void blockade_issue_order(String[] p_commandTokens, WarMap p_map) {
        boolean l_hasBlockadeCard = false;
        for (Cards l_card : d_playerCards) {
            if (l_card.toString().equals("Blockade")) {
                l_hasBlockadeCard = true;
                break;
            }
        }
        if (l_hasBlockadeCard) {
            int l_destCountryID;
            l_destCountryID = Integer.parseInt(p_commandTokens[1]);

            boolean l_destCountryIDExists = false;
            for (Country l_country : d_playerCountries)
                if (l_country.get_countryID() == l_destCountryID) {
                    l_destCountryIDExists = true;
                    break;
                }
            if (!l_destCountryIDExists) {
                System.out.println("The given CountryID is not under your control.");
            } else {
                BlockadeOrder l_order = new BlockadeOrder(l_destCountryID, this);
                d_playerOrders.add(l_order);
                d_playerCards.remove(Cards.Blockade);
                System.out.println("Blockade order executed successfully.");
                d_logentrybuffer.writeLog(d_playerName + " issued blockade order with countryId " + l_destCountryID);
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
    private void bomb_issue_order(String[] p_commandTokens, WarMap p_map) {
        boolean l_hasBombCard = d_playerCards.contains(Cards.Bomb);
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
        for (Country l_country : d_playerCountries)
            if (l_country.get_countryID() == l_destCountryID) {
                System.out.println("You cannot bomb your own country.");
                return;
            }

        BombOrder l_order = new BombOrder(l_destCountryID, this);
        d_playerOrders.add(l_order);
        d_playerCards.remove(Cards.Bomb);
        System.out.println("Bomb order issued successfully.");
        d_logentrybuffer.writeLog(d_playerName + " issued bomb order on countryId " + l_destCountryID);
    }

    /**
     * This method is called for advance orders, check if advance order is possible
     * If possible it adds the advance order to the player's order list.
     *
     * @param p_commandTokens The order command input
     * @param p_map           The WarMap
     */
    private void advance_issue_order(String[] p_commandTokens, WarMap p_map) {

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
        for (Country l_country : d_playerCountries) {
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
        AdvanceOrder l_advanceOrder = new AdvanceOrder(this, l_sourceCountry, l_targetCountry, l_numArmies);
        d_playerOrders.add(l_advanceOrder);
        d_logentrybuffer.writeLog(d_playerName + " issued advance country order from " + l_sourceCountry + " to " + l_targetCountry
                + " with " + l_numArmies + " armies");
    }

    /**
     * This method is called for airlift orders, check if the airlift order is possible
     * If possible it adds the airlift order to the player's order list.
     *
     * @param p_commandTokens The order command input
     */
    private void airlift_issue_order(String[] p_commandTokens) {
        // Check if the player has the Airlift card.
        boolean l_hasAirliftCard = d_playerCards.contains(Cards.Airlift);
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
        for (Country country : d_playerCountries) {
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
        AirliftOrder l_airliftOrder = new AirliftOrder(this, l_sourceCountry, l_targetCountry, l_numArmies);
        d_playerOrders.add(l_airliftOrder);
        d_playerCards.remove(Cards.Airlift);
        d_logentrybuffer.writeLog(d_playerName + " issued Airlift order with sourcecountryID " + l_sourceCountryID + ", targetcountryID, " +
                l_targetCountry + " with " + l_numArmies + " armies");
    }

    /**
     * This method is called for deploy orders, check if deploy order is possible
     * If possible it adds the order to the players order list.
     *
     * @param p_commandTokens The order command input
     */
    private void deploy_issue_order(String[] p_commandTokens) {

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
        d_playerOrders.add(l_deployOrder);
        this.set_numOfReinforcements(GameEngine.getInstance().getCurrentPlayer().get_numOfReinforcements() - l_numOfArmies);
        System.out.println("Deploy order issued successfully.");
        d_logentrybuffer.writeLog(d_playerName + " issued Deploy order country with ID " + l_countryID + " with " + l_numOfArmies + " armies");
    }


    /**
     * This method is called by the GameEngine during executing order phase and
     * @return the first order in the player’s list of orders, then removes it from the list.
     */
    public Order next_order() {
        if (d_playerOrders.isEmpty()) {
            return null; // or throw an exception if desired
        }

        Order l_firstOrder = d_playerOrders.get(0);
        d_playerOrders.remove(0);
        return l_firstOrder;
    }
}


