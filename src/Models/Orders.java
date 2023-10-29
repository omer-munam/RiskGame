package Models;

import Resources.Cards;

import java.util.Collection;

/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
 *
 * @author omer-munam
 * @author shezin-saleem
 * @version 1.0
 */

public class Orders {

    /**
     * The card that this order uses.
     */
    private Cards d_orderCard;
    /**
     * The Number of Armies that this order has to apply.
     */
    private int d_numOfArmies;
    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;
    /**
     * The source countryID for this instance of order.
     */
    private int d_sourceCountryID;

    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     *
     * @param p_numOfArmies Number of Armies to deploy in this order.
     * @param p_destcountryID   ID of the country on which to deploy the specified number of armies.
     * @param p_sourcecountryID   ID of the country on which to deploy the specified number of armies.
     * @param p_card   Card that this order will use.
     */
    public Orders(int p_numOfArmies, int p_destcountryID, int p_sourcecountryID, Cards p_card) {
        this.d_destCountryID = p_destcountryID;
        this.d_sourceCountryID = p_sourcecountryID;
        this.d_numOfArmies = p_numOfArmies;
        this.d_orderCard = p_card;
    }

    public Orders(Cards p_card, int p_destcountryID) {
        this.d_orderCard = p_card;
        this.d_destCountryID = p_destcountryID;
    }

    /**
     * @return the card being used in this order
     */
    public Cards getCard() {
        return d_orderCard;
    }

    /**
     * @param p_newCard the Card to be used in this order.
     */
    public void setCard(Cards p_newCard) {
        this.d_orderCard = p_newCard;
    }

    /**
     * @return the number of armies to be used in the order
     */
    public int getNumOfArmies() {
        return d_numOfArmies;
    }

    /**
     * @param p_newNum the number of armies to be used in the order.
     */
    public void setNumOfArmies(int p_newNum) {
        this.d_numOfArmies = p_newNum;
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
     * @return the country ID to be used in the order
     */
    public int getSourceCountryID() {
        return d_sourceCountryID;
    }

    /**
     * @param p_newCountry the country ID to be used in the order.
     */
    public void setSourceCountryID(int p_newCountry) {
        this.d_sourceCountryID = p_newCountry;
    }

    /**
     * Execution of the logic of deploying the armies to the specified Models.Country.
     *
     * @param p_warmap Details of values inside List Country
     */
    public void execute(WarMap p_warmap) {
        System.out.println("\n_________________________________________");
        switch (d_orderCard){
            case Bomb:
                //TODO:  Bomb order execution function;
                break;
            case Airlift:
                //TODO:  Airlift order execution function;
                break;
            case Blockade:
                //TODO:  Blockade order execution function;
                Collection<Country> l_countryInfo = p_warmap.get_countries().values();
                for (Country country : l_countryInfo) {
                    if (country.get_countryID() == d_destCountryID) {
                        country.set_numOfArmies(country.get_numOfArmies() * 3);
                    }

                    // Now make the territory Neutral,
                    // How long it should be neutral??
                    // After that remove BLOCKADE card from player-card list
                }
                //break;
            case Diplomacy:
                //TODO:  Diplomacy order execution function;
                break;
            default:
                deployOrderExecution(p_warmap);     // TODO: Advance order if source and destination both country given;
        }
        System.out.println("\n_________________________________________");
        System.out.println("Execution Done Successfully");
    }

    public void deployOrderExecution(WarMap p_warmap){
        Collection<Country> l_countryInfo = p_warmap.get_countries().values();
        for (Country country : l_countryInfo) {
            if (country.get_countryID() == d_destCountryID) {
                country.set_numOfArmies(country.get_numOfArmies() + d_numOfArmies);
                System.out.println(d_numOfArmies + " armies are deployed to country " + country.get_countryName());
            }
        }
    }
}