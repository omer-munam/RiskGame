package Models;

import java.util.ArrayList;

public class Country {
    int d_countryID;
    int d_numOfArmies;
    
    public Country() {
        this(0, "Default Name", 0, new ArrayList<Country>());
    }

    public Country(int p_countryID, String p_countryName, int p_continentID) {
        this(p_countryID, p_countryName, p_continentID, new ArrayList<Country>());
    }

    public Country(int p_countryID, String p_countryName, int p_continentID, ArrayList<Country> p_neighbouringCountries) {
        this(p_countryID, p_countryName, p_continentID, p_neighbouringCountries, 0);
    }

    public Country(int p_countryID, String p_countryName, int p_continentID, ArrayList<Country> p_neighbouringCountries, int p_numOfArmies) {
        d_countryID = p_countryID;
        d_countryName = p_countryName;
        d_continentID = p_continentID;
        d_neighbouringCountries = p_neighbouringCountries;
        d_numOfArmies = p_numOfArmies;
    }

    public int get_countryID() {
        return d_countryID;
    }

    public void set_countryID(int p_countryID) {
        d_countryID = p_countryID;
    }

    public int get_numOfArmies() {
        return d_numOfArmies;
    }

    public void set_numOfArmies(int p_numOfArmies) {
        d_numOfArmies = p_numOfArmies;
    }

    public String get_countryName() {
        return d_countryName;
    }

    public void set_countryName(String p_countryName) {
        d_countryName = p_countryName;
    }

    public ArrayList<Country> getneighbouringCountries() {
        return d_neighbouringCountries;
    }

    public void addneighbouringCountries(Country p_neighbouringCountry) {
        d_neighbouringCountries.add(p_neighbouringCountry);
    }

    String d_countryName;
    ArrayList<Country> d_neighbouringCountries;
    int d_continentID;

    public void addNeighbouringCountry(Country p_country) {
        d_neighbouringCountries.add(p_country);
    }

    void removeNeighbouringCountry(Country p_country) {
        d_neighbouringCountries.remove(p_country);
    }

    @Override
    public String toString() {
        return "Models.Country{" +
                "countryID=" + d_countryID +
                ", countryName='" + d_countryName + '\'' +
                '}';
    }
}
