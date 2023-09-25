import java.util.List;

/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
 * 
 * @version 1.0
 * @author omer-munam
 * @author shezin-saleem
 */

public class Orders {

    private int d_numOfArmies;

    public int getNumOfArmies() {
        return d_numOfArmies;
    }

    public void setNumOfArmies(int newNum) {
        this.d_numOfArmies = newNum;
    }

    private int d_countryID;

    public int getCountryID() {
        return d_numOfArmies;
    }

    public void setCountryID(int newCountry) {
        this.d_countryID = newCountry;
    }
    /**
     * This is a fully parametrized constructor for the Orders class.
     * 
     * @param numOfArmies Number of Armies to deploy in this order.
     * @param countryID ID of the country on which to deploy the specified number of armies.
     */
    public Orders(int numOfArmies, int countryID){
        this.d_countryID = countryID;
        this.d_numOfArmies = numOfArmies;
    }

    /** 
     * Execution of the logic of deploying the armies to the specified Country.
     */
    public void execute(Player p_player, WarMap p_WarMap){
        // Assigning dummy values for testing the login
        p_player.set_playerName("Shezin");
        p_player.set_playerCountries("India", "Iran", "China");
        p_player.set_armiesNumber(4,7,3);

        //playerOrder is a list with (SourceCountry, DestinationCountry, NoOfArmies)
        List<String> Player_playerOrder = p_player.set_playerOrder();

    }
}