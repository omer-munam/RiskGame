package Models;

import Controller.GameEngine;
import Models.Orders.*;
import Resources.Cards;
import Resources.Commands;
import logging.LogEntryBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Controller.GameEngine.SCANNER;

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
     * @param p_playerCards a list of the player's cards
     */
    public void set_playerCards(List<Cards> p_playerCards) {
        this.d_playerCards = p_playerCards;
    }

    /**
     * @return a list of the player's cards
     */
    public List<Cards> get_playerCards() {
        return d_playerCards;
    }

    /**
     * @param p_playerContinents a list of the player's continents
     */
    public void set_playerContinents(List<Continent> p_playerContinents) {
        this.d_playerContinents = p_playerContinents;
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
        GameEngine p_ge = GameEngine.getInstance();
        WarMap d_map = p_ge.get_currentMap();
        String command = p_ge.getCurrentInput();
        String[] commandTokens = command.split(" ");
        switch (commandTokens[0]) {
            case Commands.DEPLOY_COMMAND:
                deploy_issue_order(commandTokens);
                break;
            case Commands.ADVANCE_ORDER:
                advance_issue_order(commandTokens, d_map);
                break;
            case Commands.BOMB_ORDER:
                bomb_issue_order(commandTokens, d_map);
                break;
            case Commands.BLOCKADE_ORDER:
                blockade_issue_order(commandTokens, d_map);
                break;
            case Commands.AIRLIFT_ORDER:
                airlift_issue_order(commandTokens);
                break;
            case Commands.DIPLOMACY_ORDER:
                diplomacy_issue_order(commandTokens, d_map, p_ge.get_PlayersList());
                break;
            default:
                System.out.println("Invalid command given... Please try again...");
        }
    }

    /**
     * This method is called for diplomacy orders, checks if the target player name exists.
     * If true, add the player name to diplomacy list, which will be checked before other order execution.
     */
    private void diplomacy_issue_order(String[] commandTokens, WarMap d_map, List<Player> p_list){
        boolean hasDiplomacyCard = false;
        for (Cards card : d_playerCards){
            if (card.toString().equals("Diplomacy")){
                hasDiplomacyCard = true;
                break;
            }
        }
        if(hasDiplomacyCard){
            //check if playerID exists in playerList
            //Do diplomacy logic
            String targetPlayerName;
            targetPlayerName = commandTokens[1];

            boolean targetPlayerNameExists = false;

            for (Player player : p_list)
                if (Objects.equals(player.get_playerName(), targetPlayerName)) {
                    targetPlayerNameExists = true;
                    break;
                }
            if (!targetPlayerNameExists) {
                System.out.println("The given Player name doesn't exists.");
            }
            else {
                d_diplomacy_list.add(targetPlayerName);

                System.out.println("Diplomacy order executed successfully.");
            }
        } else {
            System.out.println("Player do not have Diplomacy card");

        }
    }

    /**
     * This method is called for blockade orders, check if the destination country exists
     * If true create a new order and executses in blockadeOrder and finally removes the blockade card from player cards.
     */
    private void blockade_issue_order(String[] commandTokens, WarMap d_map){
        boolean hasBlockadeCard = false;
        for (Cards card : d_playerCards){
            if (card.toString().equals("Blockade")){
                hasBlockadeCard = true;
                break;
            }
        }
        if (hasBlockadeCard) {
            int destCountryID;
            destCountryID = Integer.parseInt(commandTokens[1]);

            boolean destCountryIDExists = false;
            for (Country country : d_playerCountries)
                if (country.get_countryID() == destCountryID) {
                    destCountryIDExists = true;
                    break;
                }
            if (!destCountryIDExists) {
                System.out.println("The given CountryID is not under your control.");
            }
            else {
                BlockadeOrder order = new BlockadeOrder(destCountryID, this);
                d_playerOrders.add(order);
                d_playerCards.remove(Cards.Blockade);
                System.out.println("Blockade order executed successfully.");
            }
        } else {
            System.out.println("Player do not have Blockade card");
        }

    }

    /**
     * The method which creates a bomb order command.
     *
     * @param commandTokens input command.
     * @param d_map current map.
     */
    private void bomb_issue_order(String[] commandTokens, WarMap d_map) {
        boolean hasBombCard = d_playerCards.remove(Cards.Bomb);
        if (!hasBombCard){
            System.out.println("Player does not have Bomb card");
            return;
        }
        int destCountryID;
        destCountryID = Integer.parseInt(commandTokens[1]);


        boolean countryValid = false;
        for (Country country : d_map.get_countries().values()){
            if (country.get_countryID() == destCountryID) {
                countryValid = true;
                break;
            }
        }
        if (!countryValid) {
            System.out.println("Please provide a valid country.");
            return;
        }
        for (Country country : d_playerCountries)
            if (country.get_countryID() == destCountryID) {
                System.out.println("You cannot bomb your own country.");
                return;
            }

        BombOrder order = new BombOrder(destCountryID, this);
        d_playerOrders.add(order);
        d_playerCards.remove(Cards.Bomb);
        System.out.println("Bomb order issued successfully.");
    }
    private void advance_issue_order(String[] commandTokens, WarMap d_map) {

        // Check if the command contains the correct number of tokens.
        if (commandTokens.length != 4) {
            System.out.println("Invalid advance order format. Syntax: advance countryIDfrom countyIDto numarmies");
            return;
        }

        // Parse the source country name and target country name.
        int sourceCountryID;
        int targetCountryID;
        try {
            sourceCountryID = Integer.parseInt(commandTokens[1]);
            targetCountryID = Integer.parseInt(commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid source or target country IDs specified.");
            return;
        }
        // Parse the number of armies to advance.
        int numArmies;
        try {
            numArmies = Integer.parseInt(commandTokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies specified.");
            return;
        }

        // Find the source and target countries.
        Country sourceCountry = null;
        Country targetCountry = null;
        for (Country country : d_playerCountries) {
            if (country.get_countryID() == sourceCountryID) {
                sourceCountry = country;
            }
        }
        if (sourceCountry == null) {
            System.out.println("Source country not found");
        }
        for (Country country : sourceCountry.getNeighbouringCountries().values()) {
            if (country.get_countryID() == targetCountryID) {
                targetCountry = country;
            }
        }
        if (targetCountry == null) {
            System.out.println("Listed target was not a neighbouring country to the source");
        }


        // Create an AdvanceOrder and add it to the player's list of orders.
        AdvanceOrder advanceOrder = new AdvanceOrder(this, sourceCountry, targetCountry, numArmies);
        d_playerOrders.add(advanceOrder);
    }


    private void airlift_issue_order(String[] commandTokens) {
        // Check if the player has the Airlift card.
        boolean hasAirliftCard = d_playerCards.contains(Cards.Airlift);
        if (!hasAirliftCard) {
            System.out.println("You don't have the Airlift card to issue an Airlift order.");
            return;
        }

        // Check if the command contains the correct number of tokens.
        if (commandTokens.length != 4) {
            System.out.println("Invalid airlift order format. Syntax: airlift sourcecountryID targetcountryID numarmies");
            return;
        }

        // Parse the source country ID and target country ID.
        int sourceCountryID;
        int targetCountryID;
        try {
            sourceCountryID = Integer.parseInt(commandTokens[1]);
            targetCountryID = Integer.parseInt(commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid source or target country IDs specified.");
            return;
        }

        // Parse the number of armies to airlift.
        int numArmies;
        try {
            numArmies = Integer.parseInt(commandTokens[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies specified.");
            return;
        }

        // Find the source and target countries.
        Country sourceCountry = null;
        Country targetCountry = null;
        for (Country country : d_playerCountries) {
            if (country.get_countryID() == sourceCountryID) {
                sourceCountry = country;
            }
            if (country.get_countryID() == targetCountryID) {
                targetCountry = country;
            }
        }

        // Check if the source and target countries are valid and under the player's control.
        if (sourceCountry == null || targetCountry == null) {
            System.out.println("Source or target country not found or not under your control.");
            return;
        }

        // Create an AirliftOrder and add it to the player's list of orders.
        AirliftOrder airliftOrder = new AirliftOrder(this, sourceCountry, targetCountry, numArmies);
        d_playerOrders.add(airliftOrder);
        d_playerCards.remove(Cards.Airlift);
    }

    private void deploy_issue_order(String[] p_commandTokens) {

        if (p_commandTokens.length != 3) {
            System.out.println("Invalid deploy order format. Syntax: deploy <countryID> <num>");
            return;
        }
        int numOfArmies;
        int countryID;
        try {
            countryID = Integer.parseInt(p_commandTokens[1]);
            numOfArmies = Integer.parseInt(p_commandTokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid CountryID or Number of Reinforcements");
            return;
        }

        if (numOfArmies > GameEngine.getInstance().getCurrentPlayer().get_numOfReinforcements()) {
            System.out.println("Specified number of reinforcements exceed the available.");
            return;
        }

        boolean countryExists = false;
        for (Country country : GameEngine.getInstance().getCurrentPlayer().get_playerCountries()) {
            if (country.get_countryID() == countryID) {
                countryExists = true;
                break;
            }
        }

        if (!countryExists) {
            System.out.println("The given CountryID is not under your control.");
            return;
        }


        DeployOrder deployOrder = new DeployOrder(numOfArmies, countryID);
        d_playerOrders.add(deployOrder);
        this.set_numOfReinforcements(GameEngine.getInstance().getCurrentPlayer().get_numOfReinforcements() - numOfArmies);
        System.out.println("Deploy order issued successfully.");
        LogEntryBuffer.getInstance().writeLog("Deployed country with ID " + countryID + " with " + numOfArmies + " armies");
    }


    /**
     * This method is called by the GameEngine during executing order phase and
     * returns the first order in the player’s list of orders, then removes it from the list.
     */
    public Order next_order() {
        if (d_playerOrders.isEmpty()) {
            return null; // or throw an exception if desired
        }

        Order firstOrder = d_playerOrders.get(0);
        d_playerOrders.remove(0);
        return firstOrder;
    }
}


