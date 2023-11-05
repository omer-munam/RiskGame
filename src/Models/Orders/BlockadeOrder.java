package Models.Orders;

import Models.WarMap;

public class BlockadeOrder implements Order{
    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;
    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     *
     * @param p_destcountryID   ID of the country on which to deploy the specified number of armies.
     */
    public BlockadeOrder(int p_destcountryID) {
        this.d_destCountryID = p_destcountryID;
    }

    @Override
    public void execute(WarMap warMap) {

    }
}
