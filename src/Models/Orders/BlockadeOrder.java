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
    private Player d_player;
    @Override
    public void execute(WarMap warMap) {
        Collection<Country> l_countryInfo = warMap.get_countries().values();
        for (Country country : l_countryInfo) {
            if (country.get_countryID() == d_destCountryID) {
                country.set_numOfArmies(country.get_numOfArmies() * 3);
                country.setD_ownerPlayer(null);
                break;
            }
        }
        for (Country country : d_player.get_playerCountries()) {
            if (country.get_countryID() == d_destCountryID) {
                d_player.get_playerCountries().remove(country);
                break;
            }
        }

    }
}
