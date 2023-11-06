package Models.Orders;

import Models.WarMap;

/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
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
    @Override
    public void execute(WarMap warMap) {

    }
}
