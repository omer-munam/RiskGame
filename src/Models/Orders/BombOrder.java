package Models.Orders;

import Models.Country;
import Models.Player;
import Models.WarMap;
import logging.LogEntryBuffer;

import java.util.Collection;

/**
 * This class is used to implement the data and logic of how to execute bomb order given by a player.
 *
 * @author omer-munam
 * @version 1.0
 */
public class BombOrder implements Order{
    /**
     * The player for the order
     */
    private Player d_player;
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
    public BombOrder(int p_destcountryID, Player p_player) {
        this.d_destCountryID = p_destcountryID;
        this.d_player = p_player;
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
        for (Country l_country : l_countryInfo) {
            if (l_country.get_countryID() == d_destCountryID) {
                if (l_country.getD_ownerPlayer().get_diplomacy_list().contains(d_player.get_playerName())) {
                    System.out.println("\n_________________________________________");
                    System.out.println("Can't Bomb, since Negotiate found");
                    return;
                }
                int l_currentNumOfArmies = l_country.get_numOfArmies();
                int l_newNumOfArmies = l_currentNumOfArmies < 2 ? 0 : Math.floorDiv(l_currentNumOfArmies, 2);
                l_country.set_numOfArmies(l_newNumOfArmies);
                System.out.println(l_newNumOfArmies + " armies are left in country " + l_country.get_countryName());
            }
        }
        System.out.println("\n_________________________________________");
        System.out.println("Country Bombed Successfully");
        LogEntryBuffer.getInstance().writeLog(this.toString() + " executed successfully.");
    }
    
	@Override
	public String toString() {
		return "Bomb Order countryID=" + d_destCountryID ;
	}
}
