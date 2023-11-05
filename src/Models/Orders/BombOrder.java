package Models.Orders;

import Models.Country;
import Models.WarMap;

import java.util.Collection;

/**
 * This class is used to implement the data and logic of how to execute bomb order given by a player.
 *
 * @author omer-munam
 * @version 1.0
 */
public class BombOrder implements Order{

    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;
    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     *
     * @param p_destcountryID   ID of the country on which to deploy the specified number of armies.
     */
    public BombOrder(int p_destcountryID) {
        this.d_destCountryID = p_destcountryID;
    }
    /**
     * @return the country ID to be used in the order
     */
    public int getDestCountryID() {
        return d_destCountryID;
    }

    /**
     * @param p_newCountry the country ID to be used in the order.
     */
    public void setDestCountryID(int p_newCountry) {
        this.d_destCountryID = p_newCountry;
    }
    /**
     * Execution of the logic of bombing to the specified Models.Country.
     *
     * @param p_warmap Details of values inside List Country
     */
    @Override
    public void execute(WarMap p_warmap) {
        System.out.println("\n_________________________________________");
        Collection<Country> l_countryInfo = p_warmap.get_countries().values();
        for (Country country : l_countryInfo) {
            if (country.get_countryID() == d_destCountryID) {
                int currentNumOfArmies = country.get_numOfArmies();
                int newNumOfArmies = currentNumOfArmies < 2 ? 0 : Math.floorDiv(currentNumOfArmies, 2);
                country.set_numOfArmies(newNumOfArmies);
                System.out.println(newNumOfArmies + " armies are left in country " + country.get_countryName());
            }
        }
        System.out.println("\n_________________________________________");
        System.out.println("Country Bombed Successfully");
    }
}
