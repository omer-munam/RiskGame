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

    public Orders(int numOfArmies, int countryID){
        this.d_countryID = countryID;
        this.d_numOfArmies = numOfArmies;
    }

    public void execute(){
        
    }
}