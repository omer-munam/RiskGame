package Models;

import Resources.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * This class describes information about each player.
 */
public class Player {

    private String d_playerName;
    List<Country> d_playerCountries;
    List<Continent> d_playerContinents;

    /**
     * number of player's armies
     */
    Integer d_numOfReinforcements;

    /**
     * List of player's orders for execution.
     */
    List<Orders> d_playerOrders;
    /**
     * This is the constructor method of the Models.Player class
     *
     * @param v_playerName is player's name.
     */
    public Player(String v_playerName) {
        this.d_playerName = v_playerName;
        this.d_numOfReinforcements = Integer.valueOf(0);
        this.d_playerOrders = new ArrayList<Orders>();
        this.d_playerCountries = new ArrayList<Country>();
        this.d_playerContinents = new ArrayList<Continent>();
    }

    public String get_playerName() {
        return d_playerName;
    }

    public void set_playerName(String p_name) {
        this.d_playerName = p_name;
    }

    public List<Country> get_playerCountries() {
        return d_playerCountries;
    }

    public void set_playerCountries(List<Country> p_playerCountries) {
        this.d_playerCountries = p_playerCountries;
    }

    public List<Continent> get_playerContinents() {
        return d_playerContinents;
    }

    public void set_playerContinents(List<Continent> p_playerContinents) {
        this.d_playerContinents = p_playerContinents;
    }

    public List<Orders> get_playerOrder() {
        return d_playerOrders;
    }

    public void set_playerOrder(List<Orders> p_playerOrder) {
        this.d_playerOrders = p_playerOrder;
    }

    public Integer get_armiesNumber() {
        return d_numOfReinforcements;
    }

    public void set_armiesNumber(Integer p_armiesNumber) {
        this.d_numOfReinforcements = p_armiesNumber;
    }

    /**
     *“issue_order()” (no parameters, no return value) whose function is
     *to add an order to the list of orders held by the
     *player when the game engine calls it during the issue orders phase.
     */
    public void issue_order(){
        Scanner scanner = new Scanner(System.in);
        while (d_numOfReinforcements.intValue() != 0){
            int countryID;
            int numOfArmies;
            System.out.println("Please issue deploy order command for Player " + d_playerName);
            System.out.println("Remaining reinforcements: " + d_numOfReinforcements);
            String command = scanner.nextLine();
            String[] commandTokens = command.split(" ");
            if (commandTokens.length != 3 || !commandTokens[0].equals(Commands.DEPLOY_COMMAND)){
                System.out.println("Please give the command in format: " + Commands.DEPLOY_COMMAND_SYNTAX);
                continue;
            }
            try {
                countryID = Integer.parseInt(commandTokens[1]);
                numOfArmies = Integer.parseInt(commandTokens[2]);
            }catch (NumberFormatException e){
                System.out.println("Invalid CountryID or Number of Reinforcements");
                continue;
            }
            if (numOfArmies > d_numOfReinforcements.intValue()){
                System.out.println("Specified number of reinforcements exceed the available.");
                continue;
            }
            boolean countryExists = false;
            for (Country country : d_playerCountries)
                if (country.d_countryID == countryID)
                    countryExists = true;
            if (!countryExists){
                System.out.println("The given CountryID is not under your control.");
                continue;
            }
            Orders order = new Orders(numOfArmies, countryID);
            d_playerOrders.add(order);
            d_numOfReinforcements = d_numOfReinforcements.intValue() - numOfArmies;
            System.out.println("Order Issued Successfully.");
        }
    }


    /** * The player class must also have a
     * “next_order()” (no parameters) method that is called by the GameEngine
     * during executing order phase and
     * returns the first order in the player’s list of orders, then removes it from the list.
     */
    public Orders next_order() {
        if (d_playerOrders.isEmpty()) {
            return null; // or throw an exception if desired
        }

        Orders firstOrder = d_playerOrders.get(0);
        d_playerOrders.remove(0);
        return firstOrder;
    }
}

