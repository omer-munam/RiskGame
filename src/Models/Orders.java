package Models;

import java.util.List;

/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
 * 
 * @version 1.0
 * @author omer-munam
 * @author shezin-saleem
 */

public class Orders {

    /**
     * The Number of Armies that this order has to apply.
     */
    private int d_numOfArmies;
    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;

    public int getNumOfArmies() {
        return d_numOfArmies;
    }

    public void setNumOfArmies(int p_newNum) {
        this.d_numOfArmies = p_newNum;
    }

    public int getCountryID() {
        return d_destCountryID;
    }

    public void setCountryID(int p_newCountry) {
        this.d_destCountryID = p_newCountry;
    }
    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     * 
     * @param p_numOfArmies Number of Armies to deploy in this order.
     * @param p_countryID ID of the country on which to deploy the specified number of armies.
     */
    public Orders(int p_numOfArmies, int p_countryID){
        this.d_destCountryID = p_countryID;
        this.d_numOfArmies = p_numOfArmies;
    }

    /** 
     * Execution of the logic of deploying the armies to the specified Models.Country.
     * 
     * @param p_warmap Details of values inside List Country
     */
    public void execute(WarMap p_warmap){
        List<Country> l_countryInfo = (List<Country>) p_warmap.get_countries().values();
        for (Country country : l_countryInfo) {
            if (country.d_countryID == d_destCountryID){
                country.d_numOfArmies += d_numOfArmies;
            }
        }
        System.out.println("Execution Done Successfully");
    }
}