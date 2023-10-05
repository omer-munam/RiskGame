package Models;

import java.util.HashMap;

/**
 * This class describes information about each country, including the contained armies and neighbouring countries.
 *
 * @author Ryan Feher
 * @author Mohammad Uvas
 */
public class Country {
    /**
     * Stores the neighbouring countries of a country.
     */
    private final HashMap<Integer, Country> d_neighbouringCountries;
    /**
     * Stores the country ID of a country.
     */
    private int d_countryID;
    /**
     * Stores the number of armies placed on a country.
     */
    private int d_numOfArmies;
    /**
     * Stores the country name of a country.
     */
    private String d_countryName;
    /**
     * Stores the continentID of the continent that the country belongs to.
     */
    private int d_continentID;

    /**
     * This is the default constructor method of the Models.Country class
     */
    public Country() {
        this(0, "Default Name", 0, new HashMap<Integer, Country>());
    }

    /**
     * This is a parameterized constructor method of the Models.Country class
     *
     * @param p_countryID   is the country's ID.
     * @param p_countryName is the country's name.
     * @param p_continentID is the country's continent's ID.
     */
    public Country(int p_countryID, String p_countryName, int p_continentID) {
        this(p_countryID, p_countryName, p_continentID, new HashMap<Integer, Country>());
    }

    /**
     * This is a parameterized constructor method of the Models.Country class
     *
     * @param p_countryID             is the country's ID.
     * @param p_countryName           is the country's name.
     * @param p_continentID           is the country's continent's ID.
     * @param p_neighbouringCountries is the list of the country's neighbouring countries.
     */
    public Country(int p_countryID, String p_countryName, int p_continentID, HashMap<Integer, Country> p_neighbouringCountries) {
        this(p_countryID, p_countryName, p_continentID, p_neighbouringCountries, 0);
    }

    /**
     * This is a parameterized constructor method of the Models.Country class
     *
     * @param p_countryID             is the country's ID.
     * @param p_countryName           is the country's name.
     * @param p_continentID           is the country's continent's ID.
     * @param p_neighbouringCountries is the list of the country's neighbouring countries.
     * @param p_numOfArmies           is the number of armies placed on the country.
     */
    public Country(int p_countryID, String p_countryName, int p_continentID, HashMap<Integer, Country> p_neighbouringCountries, int p_numOfArmies) {
        d_countryID = p_countryID;
        d_countryName = p_countryName;
        d_continentID = p_continentID;
        d_neighbouringCountries = p_neighbouringCountries;
        d_numOfArmies = p_numOfArmies;
    }

    /**
     * @return The country's ID
     */
    public int get_countryID() {
        return d_countryID;
    }

    /**
     * @param p_countryID The country's ID
     */
    public void set_countryID(int p_countryID) {
        d_countryID = p_countryID;
    }

    /**
     * @return the number of armies placed on the country
     */
    public int get_numOfArmies() {
        return d_numOfArmies;
    }

    /**
     * @param p_numOfArmies the number of armies placed on the country
     */
    public void set_numOfArmies(int p_numOfArmies) {
        d_numOfArmies = p_numOfArmies;
    }

    /**
     * @return the country's name
     */
    public String get_countryName() {
        return d_countryName;
    }

    /**
     * @param p_countryName the country's name
     */
    public void set_countryName(String p_countryName) {
        d_countryName = p_countryName;
    }

    /**
     * @return A list of the neighbouring countries.
     */
    public HashMap<Integer, Country> getNeighbouringCountries() {
        return d_neighbouringCountries;
    }

    /**
     * @return the continent ID of the country
     */
    public int getContinentID() {
        return d_continentID;
    }

    /**
     * @param p_continentID the continent ID of the country
     */
    public void setContinentID(int p_continentID) {
        d_continentID = p_continentID;
    }

    /**
     * @param p_country the neighbouring country to be added.
     */
    public void addNeighbouringCountry(Country p_country) {
        d_neighbouringCountries.put(p_country.get_countryID(), p_country);
    }

    /**
     * @param p_country the neighbouring country to be removed.
     */
    void removeNeighbouringCountry(Country p_country) {
        d_neighbouringCountries.remove(p_country.get_countryID());
    }

    @Override
    public String toString() {
        return "Models.Country{" +
                "countryID=" + d_countryID +
                ", countryName='" + d_countryName + '\'' +
                '}';
    }


}
