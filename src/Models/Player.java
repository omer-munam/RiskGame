package Models;

import Controller.GameEngine;
import Models.BehaviourStrategies.BehaviourStrategy;
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

    public BehaviourStrategy getD_behaviourStrategy() {
        return d_behaviourStrategy;
    }

    public void setD_behaviourStrategy(BehaviourStrategy d_behaviourStrategy) {
        this.d_behaviourStrategy = d_behaviourStrategy;
    }

    BehaviourStrategy d_behaviourStrategy;
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
        d_behaviourStrategy.issue_order();
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


