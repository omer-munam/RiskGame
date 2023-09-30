package Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

    // DFS recursive function for map
    void dfsHelper(Integer p_country, HashMap<Integer, Boolean> p_isvisited) {
    	p_isvisited.put(p_country, true);
        for (int l_neighbour : d_adjencyList.get(p_country))
            if (!p_isvisited.get(l_neighbour))
                dfsHelper(l_neighbour, p_isvisited);
    }

    // DFS recursive function for Continents
    void dfsHelperContinents(Integer p_country, HashMap<Integer, Boolean> p_isvisited, HashMap<Integer, ArrayList<Integer>> p_adjencylistcontinent) {
    	p_isvisited.put(p_country, true);
        for (int l_neighbour : p_adjencylistcontinent.get(p_country))
            if (!p_isvisited.get(l_neighbour))
                dfsHelper(l_neighbour, p_isvisited);
    }

	// Function to validate a map
    public boolean validateMap() {

    	// Getting Continents set from country map.
		Set<Integer> l_continent = new HashSet<Integer>();
		for( Entry<Integer, Country> l_country : d_countries .entrySet()) {
			l_continent.add(l_country.getValue().getContinentID());
		}
		
		// Validating count of Continents
		if(l_continent.size()!=d_continents.size()) {
			System.out.println("Number of Continents mismatch");
			return false;
		}
		// Validating countries having border or not
		if(d_countries.size()!=d_adjencyList.size()) {
			System.out.println("Countries with no border or with no existing continent ID Exist");
			return false;
		}
		
		// DFS Traversal on graph to check if it is fully connected.
        int l_component = 0;
        HashMap<Integer, Boolean> l_isvisited = new HashMap<Integer, Boolean>();

        for (Integer l_country : d_adjencyList.keySet()) {
        	l_isvisited.put(l_country, false);
        }
        for (Integer l_country : d_adjencyList.keySet()) {
            if (!l_isvisited.get(l_country)) {
                dfsHelper(l_country, l_isvisited);
                l_component++;
            }
        }
        if (l_component != 1) {
            System.out.println("Not a Connected map");
            return false;
        }

		// DFS Traversal on Continents for checking if they are fully connected.
        for (Entry<Integer, Continent> l_continentset : d_continents.entrySet()) {
        	
        	// Making adjacency List for selected continent Id.
            int l_continent_Id = l_continentset.getKey();
            HashMap<Integer, ArrayList<Integer>> l_adjencylistcontinent = new HashMap<Integer, ArrayList<Integer>>();

            for (Entry<Integer, Country> l_countryset : d_countries.entrySet()) {
                int l_countryid = l_countryset.getKey();

                if (d_countries.get(l_countryid).getContinentID() == l_continent_Id) {
                	l_adjencylistcontinent.put(l_countryid, new ArrayList<Integer>());

                    for (Country l_neighborcountry : l_countryset.getValue().getneighbouringCountries()) {
                        if (l_neighborcountry.getContinentID() == l_continent_Id)
                        	l_adjencylistcontinent.get(l_countryid).add(l_neighborcountry.get_countryID());
                    }
                }
            }

			//DFS traversal on selected Continent
            int l_subcomponent = 0;
            HashMap<Integer, Boolean> l_subisvisited = new HashMap<Integer, Boolean>();

            for (Integer l_country : l_adjencylistcontinent.keySet()) {
            	l_subisvisited.put(l_country, false);
            }
            for (Integer l_country : l_adjencylistcontinent.keySet()) {
                if (!l_subisvisited.get(l_country)) {
                	dfsHelperContinents(l_country, l_subisvisited, l_adjencylistcontinent);
                    l_subcomponent++;
                }
            }
            if (l_subcomponent != 1) {
                System.out.println("Continent countries not Connected Graph");
                return false;
            }
        }

        return true;
    }

    public void saveMap(String p_map_name) {

		// Checking if a map is valid before saving it.
        if (validateMap()) {
            try {
                FileWriter l_fstream = new FileWriter(d_base_path + "\\" + p_map_name);
                BufferedWriter l_info = new BufferedWriter(l_fstream);

				// creating Continents Section in map File
                l_info.write("[continents]");
                l_info.newLine();
                for (Entry<Integer, Continent> l_continent : d_continents.entrySet()) {
                    l_info.write(l_continent.getValue().get_continentName() + " " + l_continent.getValue().get_armyBonus());
                    l_info.newLine();
                }
                l_info.newLine();

				//creating Country Section in map File
                l_info.write("[countries]");
                l_info.newLine();
                for (Entry<Integer, Country> l_country : d_countries.entrySet()) {
                    l_info.write(l_country.getKey() + " " + l_country.getValue().get_countryName() + " " + l_country.getValue().get_countryID());
                    l_info.newLine();
                }
                l_info.newLine();

				// creating border section in File
                l_info.write("[borders]");
                l_info.newLine();
                for (Entry<Integer, ArrayList<Integer>> l_neighbourlist : d_adjencyList.entrySet()) {
                    StringBuilder l_border = new StringBuilder();
                    l_border.append(l_neighbourlist.getKey());
                    for (Integer nghbr_cuntry : l_neighbourlist.getValue()) l_border.append(" " + nghbr_cuntry);
                    l_info.write(String.valueOf(l_border));
                    l_info.newLine();
                }
                l_info.close();

            } catch (Exception e) {
                System.out.println("Exception occured file saving a map file");
            }
        }
        else System.out.println("Invalid_Map");
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

