public class Continent {
    @Override
    public String toString() {
        return "Continent{" +
                "continentID=" + d_continentID +
                ", continentName='" + d_continentName + '\'' +
                '}';
    }

    int d_continentID;
    String d_continentName;
    int d_armyBonus;

    public int get_continentID() {
        return d_continentID;
    }

    public String get_continentName() {
        return d_continentName;
    }

    public void set_continentName(String p_continentName) {
        d_continentName = p_continentName;
    }

    public void set_continentID(int p_continentID) {
        d_continentID = p_continentID;
    }

    public Continent(int p_continentID, String p_continentName, int d_armyBonus) {
        d_continentID = p_continentID;
        d_continentName = p_continentName;
    }


}
