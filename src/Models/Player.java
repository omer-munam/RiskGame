package Models;

import java.util.ArrayList;
import java.util.List;


/**
 * This class describes information about each player.
 */
public class Player {

    private String playerName;
    List<Country> playerCountries;
    List<Continent> playerContinents;

    /**
     * number of player's armies
     */
    Integer armiesNumber;

    /**
     * List of player's orders for execution.
     */
    List<Orders> playerOrders; //private List<String> playerOrders; //playerOrders should have (SourceCountry, DestinationCountry, NoOfArmies)
                               //Change <Models.Orders> ---> <String>
    /**
     * This is the constructor method of the Models.Player class
     *
     * @param v_playerName is player's name.
     */
    public Player(String v_playerName) {
        this.playerName = v_playerName;
        this.armiesNumber = Integer.valueOf(0);
        this.playerOrders = new ArrayList<Orders>();
    }

    public String get_playerName() {
        return playerName;
    }

    public void set_playerName(String p_name) {
        this.playerName = p_name;
    }

    public List<Country> get_playerCountries() {
        return playerCountries;
    }

    public void set_playerCountries(List<Country> p_playerCountries) {
        this.playerCountries = p_playerCountries;
    }

    public List<Continent> get_playerContinents() {
        return playerContinents;
    }

    public void set_playerContinents(List<Continent> p_playerContinents) {
        this.playerContinents = p_playerContinents;
    }

    public List<Orders> get_playerOrder() {
        return playerOrders;
    }

    public void set_playerOrder(List<Orders> p_playerOrder) {
        this.playerOrders = p_playerOrder;
    }

    public Integer get_armiesNumber() {
        return armiesNumber;
    }

    public void set_armiesNumber(Integer p_armiesNumber) {
        this.armiesNumber = p_armiesNumber;
    }

    /**
     *“issue_order()” (no parameters, no return value) whose function is
     *to add an order to the list of orders held by the
     *player when the game engine calls it during the issue orders phase.
     */
    public void issue_order(){
    }


    /** * The player class must also have a
     * “next_order()” (no parameters) method that is called by the GameEngine
     * during executing order phase and
     * returns the first order in the player’s list of orders, then removes it from the list.
     */
    public Orders next_order() {
        List<Orders> orders=get_playerOrder();
        if (orders.isEmpty()) {
            return null; // or throw an exception if desired
        }

        Orders firstOrder = orders.get(0);
        orders.remove(0);
        return firstOrder;
    }
}

