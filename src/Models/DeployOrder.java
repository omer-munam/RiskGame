package Models;

import java.util.Collection;

public class DeployOrder implements Order {
    /**
     * The Number of Armies that this order has to apply.
     */
    private int d_numOfArmies;
    /**
     * The destination countryID for this instance of order.
     */
    private int d_destCountryID;

    /**
     * This is a fully parametrized constructor for the Models.Orders class.
     *
     * @param p_numOfArmies Number of Armies to deploy in this order.
     * @param p_countryID   ID of the country on which to deploy the specified number of armies.
     */
    public DeployOrder(int p_numOfArmies, int p_countryID) {
        this.d_destCountryID = p_countryID;
        this.d_numOfArmies = p_numOfArmies;
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
    public int getCountryID() {
        return d_destCountryID;
    }

    /**
     * @param p_newCountry the country ID to be used in the order.
     */
    public void setCountryID(int p_newCountry) {
        this.d_destCountryID = p_newCountry;
    }

    /**
     * Execution of the logic of deploying the armies to the specified Models.Country.
     *
     * @param p_warmap Details of values inside List Country
     */
    @Override
    public void execute(WarMap p_warmap) {
        System.out.println("\n_________________________________________");
        Collection<Country> l_countryInfo = p_warmap.get_countries().values();
        for (Country country : l_countryInfo) {
            if (country.get_countryID() == d_destCountryID) {
                country.set_numOfArmies(country.get_numOfArmies() + d_numOfArmies);
                System.out.println(d_numOfArmies + " armies are deployed to country " + country.get_countryName());
            }
        }
        System.out.println("\n_________________________________________");
        System.out.println("Execution Done Successfully");
    }
}
