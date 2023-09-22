import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WarMap {
    String d_mapName;

    public HashMap<Integer, Country> get_countries() {
        return d_countries;
    }

    public void set_countries(HashMap<Integer, Country> p_countries) {
        d_countries = p_countries;
    }

    public HashMap<Integer, Continent> get_continents() {
        return d_continents;
    }

    public void set_continents(HashMap<Integer, Continent> p_continents) {
        d_continents = p_continents;
    }

    public HashMap<Integer, ArrayList<Integer>> get_adjencyList() {
        return d_adjencyList;
    }

    public void setD_adjencyList(HashMap<Integer, ArrayList<Integer>> p_adjencyList) {
        d_adjencyList = p_adjencyList;
    }

    HashMap<Integer, Country> d_countries;
    HashMap<Integer, Continent> d_continents;
    HashMap<Integer, ArrayList<Integer>> d_adjencyList;

    public WarMap() {
        this("Default Name", new HashMap<Integer, Country>(), new HashMap<Integer, Continent>(), new HashMap<Integer, ArrayList<Integer>>());

    }

    public WarMap(String p_mapName) {
        this(p_mapName, new HashMap<Integer, Country>(), new HashMap<Integer, Continent>(), new HashMap<Integer, ArrayList<Integer>>());

    }

    public WarMap(String p_mapName, HashMap<Integer, Country> p_countries, HashMap<Integer, Continent> p_continents, HashMap<Integer, ArrayList<Integer>> p_adjencyList) {
        d_mapName = p_mapName;
        d_countries = p_countries;
        d_continents = p_continents;
        d_adjencyList = p_adjencyList;

    }

    public void addContinent(Continent p_continent) {
        d_continents.put(p_continent.get_continentID(), p_continent);
    }

    public void addCountry(Country p_country) {
        d_countries.put(p_country.get_countryID(), p_country);
    }

    public void addNeighbour(int p_countryID, int p_neighbourID) {
        d_adjencyList.putIfAbsent(p_countryID, new ArrayList<Integer>());
        if (!d_adjencyList.get(p_countryID).contains(p_neighbourID)) {
            d_adjencyList.get(p_countryID).add(p_neighbourID);
        }
    }
}
