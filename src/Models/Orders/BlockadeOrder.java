package Models.Orders;
import Models.Country;
import Models.Player;
import Models.WarMap;

import java.util.Collection;

public class BlockadeOrder implements Order{
    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;
    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     *
     * @param p_destcountryID ID of the country on which to deploy the specified number of armies.
     * @param p_player
     */
    public BlockadeOrder(int p_destcountryID, Player p_player) {
        this.d_destCountryID = p_destcountryID;
        this.d_player = p_player;
    }

    /**
     * The player of the order
     */
    private Player d_player;

    /**
     * This method is does execution for blockade orders.
     * Checks if the destination country ID exists, if true triple the numofArmies
     * Set owner of that country to null
     * Remove that country from the player owned country list.
     * Finally, removes the blockade card from player owned card list
     */
    @Override
    public void execute(WarMap warMap) {
        Collection<Country> l_countryInfo = warMap.get_countries().values();
        for (Country l_country : l_countryInfo) {
            if (l_country.get_countryID() == d_destCountryID) {
                l_country.set_numOfArmies(l_country.get_numOfArmies() * 3);
                l_country.setD_ownerPlayer(null);
                break;
            }
        }
        for (Country l_country : d_player.get_playerCountries()) {
            if (l_country.get_countryID() == d_destCountryID) {
                d_player.get_playerCountries().remove(l_country);
                break;
            }
        }

    }
}
