/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
 * 
 * @version 1.0
 * @author omer-munam
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
     * Definition of the logic of deploying the armies to the specified Country.
     */
    public void execute(){

    }
}