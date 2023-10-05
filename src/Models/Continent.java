package Models;

/**
 * This is the continent class, it contains the structure of a Continent to be used in the WarMap, it also contains the army bonus to be used during the game.
 *
 * @author Ryan Feher
 * @author Mohammad Uvas
 */
public class Continent {

    /**
     * The continent ID of the continent.
     */
    private int d_continentID;
    /**
     * The name of the continent.
     */
    private String d_continentName;
    /**
     * The army bonus obtained from owning the entirety of the continent.
     */
    private int d_armyBonus;

    /**
     * This is a default constructor method of the Models.Continent class
     */
    public Continent() {
        d_continentID = 0;
        d_continentName = "Default name";
        d_armyBonus = 0;
    }

    /**
     * This is a parameterized constructor method of the Models.Continent class
     *
     * @param p_continentID   is the continent's ID.
     * @param p_continentName is the continent's name.
     * @param p_armyBonus     is the army bonus of obtaining the entire continent.
     */
    public Continent(int p_continentID, String p_continentName, int p_armyBonus) {
        d_continentID = p_continentID;
        d_continentName = p_continentName;
        d_armyBonus = p_armyBonus;
    }

    /**
     * @return the continent ID of the continent.
     */
    public int get_continentID() {
        return d_continentID;
    }

    /**
     * @return the name of the continent.
     */
    public String get_continentName() {
        return d_continentName;
    }

    /**
     * @return the army bonus of the continent.
     */
    public int get_armyBonus() {
        return d_armyBonus;
    }

    /**
     * @param p_continentName the continent name to give the continent.
     */
    public void set_continentName(String p_continentName) {
        d_continentName = p_continentName;
    }

    /**
     * @param p_continentID the continent ID to give the continent.
     */
    public void set_continentID(int p_continentID) {
        d_continentID = p_continentID;
    }

    /**
     * @param p_armyBonus the army bonus to give the continent.
     */
    public void set_armyBonus(int p_armyBonus) {
        d_armyBonus = p_armyBonus;
    }

    @Override
    public String toString() {
        return "Models.Continent{" +
                "continentID=" + d_continentID +
                ", continentName='" + d_continentName + '\'' +
                '}';
    }
}
