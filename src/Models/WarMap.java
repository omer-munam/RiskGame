package Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WarMap {
    String d_mapName;
    String d_base_path = String.valueOf(System.getProperty("user.dir")) + "\\maps";

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

    void dfsHelper(Integer p_cntry, HashMap<Integer, Boolean> p_is_visited) {
        p_is_visited.put(p_cntry, true);
        for (int neighbour : d_adjencyList.get(p_cntry))
            if (!p_is_visited.get(neighbour))
                dfsHelper(neighbour, p_is_visited);
    }

    void dfsHelper_Continents(Integer p_cntry, HashMap<Integer, Boolean> p_is_visited, HashMap<Integer, ArrayList<Integer>> p_adjencyList_Continent) {
        p_is_visited.put(p_cntry, true);
        for (int neighbour : p_adjencyList_Continent.get(p_cntry))
            if (!p_is_visited.get(neighbour))
                dfsHelper(neighbour, p_is_visited);
    }

    public boolean validateMap() {

        int l_component = 0;
        HashMap<Integer, Boolean> l_is_visited = new HashMap<Integer, Boolean>();

        for (Integer l_temp_cntry : d_adjencyList.keySet()) {
            l_is_visited.put(l_temp_cntry, false);
        }
        for (Integer l_temp_cntry : d_adjencyList.keySet()) {
            if (!l_is_visited.get(l_temp_cntry)) {
                dfsHelper(l_temp_cntry, l_is_visited);
                l_component++;
            }
        }
        if (l_component != 1) {
            System.out.println("Not a Connected map");
            return false;
        }

        for (Entry<Integer, Continent> l_continent_set : d_continents.entrySet()) {
            int l_continent_Id = l_continent_set.getKey();
            HashMap<Integer, ArrayList<Integer>> l_adjencyList_Continent = new HashMap<Integer, ArrayList<Integer>>();

            for (Entry<Integer, Country> l_country_set : d_countries.entrySet()) {
                int l_country_Id = l_country_set.getKey();

                if (d_countries.get(l_country_Id).getContinentID() == l_continent_Id) {
                    l_adjencyList_Continent.put(l_country_Id, new ArrayList<Integer>());

                    for (Country l_nghbr_Cntry : l_country_set.getValue().getneighbouringCountries()) {
                        if (l_nghbr_Cntry.getContinentID() == l_continent_Id)
                            l_adjencyList_Continent.get(l_country_Id).add(l_nghbr_Cntry.get_countryID());
                    }
                }
            }

            int l_sub_component = 0;
            HashMap<Integer, Boolean> l_sub_is_visited = new HashMap<Integer, Boolean>();

            for (Integer l_temp_cntry : l_adjencyList_Continent.keySet()) {
                l_sub_is_visited.put(l_temp_cntry, false);
            }
            for (Integer l_temp_cntry : l_adjencyList_Continent.keySet()) {
                if (!l_sub_is_visited.get(l_temp_cntry)) {
                    dfsHelper_Continents(l_temp_cntry, l_sub_is_visited, l_adjencyList_Continent);
                    l_sub_component++;
                }
            }
            if (l_sub_component != 1) {
                System.out.println("Continent countries not Connected Graph");
                return false;
            }
        }

        return true;
    }

    public void saveMap(String p_map_name) {
        if (validateMap()) {
            try {

                FileWriter l_fstream = new FileWriter(d_base_path + "\\" + p_map_name);
                BufferedWriter l_info = new BufferedWriter(l_fstream);

                l_info.write("[continents]");
                l_info.newLine();
                for (Entry<Integer, Continent> l_set : d_continents.entrySet()) {
                    l_info.write(l_set.getValue().get_continentName() + " " + l_set.getValue().get_armyBonus());
                    l_info.newLine();
                }
                l_info.newLine();

                l_info.write("[countries]");
                l_info.newLine();
                for (Entry<Integer, Country> l_set : d_countries.entrySet()) {
                    l_info.write(l_set.getKey() + " " + l_set.getValue().get_countryName() + " " + l_set.getValue().get_countryID());
                    l_info.newLine();
                }
                l_info.newLine();

                l_info.write("[borders]");
                l_info.newLine();
                for (Entry<Integer, ArrayList<Integer>> l_set : d_adjencyList.entrySet()) {
                    StringBuilder l_border = new StringBuilder();
                    l_border.append(l_set.getKey());
                    for (Integer nghbr_cuntry : l_set.getValue()) l_border.append(" " + nghbr_cuntry);
                    l_info.write(String.valueOf(l_border));
                    l_info.newLine();
                }
                l_info.close();

            } catch (Exception e) {
                System.out.println("writing error");
            }
        } else System.out.println("Invalid_Map");
    }

    public void showMap() { //Show map for only map (no player ownership or army count. - need to make seperate one that incorporates that for gamestate)
        for (Map.Entry<Integer, Country> entry : d_countries.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<Integer, Continent> entry : d_continents.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<Integer, ArrayList<Integer>> entry : d_adjencyList.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}

