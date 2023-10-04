package Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 *This class describes information stores a WarMap to be used in the game, it's main use is to store the countries, continents and adjency list of neighbouring countries
 *It also contains the functions for validating, saving and showing the game map.
 *
 * @author Ryan Feher
 * @author Mohammad Uvas
 */
public class WarMap {
    /**
     * The name of the WarMap, in general this is the same as the filename.
     */
    private String d_mapName;
    /**
     * A hashmap of the countries in the WarMap, this will take the form of HashMap(CountryID, Country)
     */
    private HashMap<Integer, Country> d_countries;
    /**
     * A hashmap of the continents in the WarMap, this will take the form of HashMap(ContinentID, Continent)
     */
    private HashMap<Integer, Continent> d_continents;
    /**
     * A hashmap of the adjency list of the WarMap, this will take the form of HashMap(CountryID, List of Neighboring Country IDs)
     */
    private HashMap<Integer, HashMap<Integer, Integer>> d_adjencyList;
    /**
     * Stores the location of saved WarMaps in the file directory.
     */
    private String d_base_path = String.valueOf(System.getProperty("user.dir")) + "\\Src\\Resources\\Maps";
    /**
     * This is a default constructor method of the Models.WarMap class
     */
    public WarMap() {
        this("Default Name", new HashMap<Integer, Country>(), new HashMap<Integer, Continent>(), new HashMap<Integer, HashMap<Integer, Integer>>());

    }
    /**
     * This is a parameterized constructor method of the Models.WarMap class
     *
     * @param p_mapName is the map's name (generally the file name).
     */
    public WarMap(String p_mapName) {
        this(p_mapName, new HashMap<Integer, Country>(), new HashMap<Integer, Continent>(), new HashMap<Integer, HashMap<Integer, Integer>>());

    }
    /**
     * This is a parameterized constructor method of the Models.WarMap class
     *
     * @param p_mapName is the map's name (generally the file name).
     * @param p_countries is a hashmap of the countries in the map where the hashmap contains keys with the countries ID and the values are the corresponding countries.
     * @param p_continents is a hashmap of the continents in the map where the hashmap contains keys with the continent Id's and the values are the corresponding continents.
     * @param p_adjencyList is a hashmap of the neighbouring countries in the map where the hashmap contains keys with the country IDs and the values are a list of the country Id's of neighbouring countries.
     */
    public WarMap(String p_mapName, HashMap<Integer, Country> p_countries, HashMap<Integer, Continent> p_continents, HashMap<Integer, HashMap<Integer, Integer>> p_adjencyList) {
        d_mapName = p_mapName;
        d_countries = p_countries;
        d_continents = p_continents;
        d_adjencyList = p_adjencyList;

    }
    /**
     * @return the name of the WarMap.
     */
    public String get_mapName() {
        return d_mapName;
    }
    /**
     * @param p_mapName the name to give the WarMap.
     */
    public void set_mapName(String p_mapName) {
        d_mapName = p_mapName;
    }
    /**
     * @return The hashmap of countries in the WarMap.
     */
    public HashMap<Integer, Country> get_countries() {
        return d_countries;
    }
    /**
     * @param p_countries the hashmap of countries to give the WarMap.
     */
    public void set_countries(HashMap<Integer, Country> p_countries) {
        d_countries = p_countries;
    }
    /**
     * @return the hashmap of continents in the WarMap.
     */
    public HashMap<Integer, Continent> get_continents() {
        return d_continents;
    }
    /**
     * @param p_continents the hashmap of continents to give the WarMap.
     */
    public void set_continents(HashMap<Integer, Continent> p_continents) {
        d_continents = p_continents;
    }
    /**
     * @return the adjency list of the WarMap.
     */
    public HashMap<Integer, HashMap<Integer, Integer>> get_adjencyList() {
        return d_adjencyList;
    }
    /**
     * @param p_adjencyList the adjency list to give the WarMap.
     */
    public void setD_adjencyList(HashMap<Integer, HashMap<Integer, Integer>> p_adjencyList) {
        d_adjencyList = p_adjencyList;
    }




    /**
     * Function used for adding a continent to the HashMap containing the continents of the map.
     *
     * @param p_continent the continent to be added to the WarMap.
     */
    public void addContinent(Continent p_continent) {
        d_continents.put(p_continent.get_continentID(), p_continent);
    }
    /**
     * Function used for adding a Country to the HashMap containing the Countries of the map.
     *
     * @param p_country the country to be added to the WarMap.
     */
    public void addCountry(Country p_country) {
        d_countries.put(p_country.get_countryID(), p_country);
    }
    /**
     * Function used for adding a neighbour country, this will add a neighbour to the country, as well as update the adjency list.
     *
     * @param p_countryID the country who will have a neighbour added.
     * @param p_neighbourID the neighbouring country to be added.
     */
    public void addNeighbour(int p_countryID, int p_neighbourID) {
        d_adjencyList.putIfAbsent(p_countryID, new HashMap<Integer, Integer>());
        if (!d_adjencyList.get(p_countryID).containsKey(p_neighbourID)) {
            d_adjencyList.get(p_countryID).put(p_neighbourID, p_neighbourID);
            d_countries.get(p_countryID).addNeighbouringCountry(d_countries.get(p_neighbourID));
        }
    }
    /**
     * Function used for removing a neighbour country, this will remove a neighbour from the country, as well as update the adjency list.
     *
     * @param p_countryID the country who will have a neighbour added.
     * @param p_neighbourID the neighbouring country to be added.
     */
    public void removeNeighbour(int p_countryID, int p_neighbourID) {
        if (d_adjencyList.get(p_countryID).containsKey(p_neighbourID)) {
            d_adjencyList.get(p_countryID).remove(p_neighbourID);
        }
            d_countries.get(p_countryID).removeNeighbouringCountry(d_countries.get(p_neighbourID));
        }

    /**
     * DFS recursive function for WarMap
     *
     * @param p_country the country ID.
     * @param p_isvisited a HashMap of containing booleans for if the country has been visited.
     */
    // DFS recursive function for map
    private void dfsHelper(Integer p_country, HashMap<Integer, Boolean> p_isvisited) {
    	p_isvisited.put(p_country, true);
        for (int l_neighbour : d_adjencyList.get(p_country).keySet())
            if (!p_isvisited.get(l_neighbour))
                dfsHelper(l_neighbour, p_isvisited);
    }
    /**
     * DFS recursive function for Continents
     *
     * @param p_isvisited a HashMap containing visited countries
     * @param p_adjencylistcontinent a HashMap containing the adjency list of continents.
     */
    // DFS recursive function for Continents
    private void dfsHelperContinents(Integer p_country, HashMap<Integer, Boolean> p_isvisited, HashMap<Integer, ArrayList<Integer>> p_adjencylistcontinent) {
    	p_isvisited.put(p_country, true);
        for (int l_neighbour : p_adjencylistcontinent.get(p_country))
            if (!p_isvisited.get(l_neighbour))
            	dfsHelperContinents(l_neighbour, p_isvisited, p_adjencylistcontinent);
    }
    /**
     * Function that should be called to validate a WarMap.
     *
     * @return Returns a boolean which is true if the map is valid, and false if the map is invalid.
     */
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

                    for (Integer l_neighborcountryId : l_countryset.getValue().getNeighbouringCountries().keySet()) {
                        if (l_neighborcountryId != null && d_countries.get(l_neighborcountryId).getContinentID() == l_continent_Id)
                        	l_adjencylistcontinent.get(l_countryid).add(l_neighborcountryId);
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
    /**
     * A function which saves the WarMap to a file.
     *
     * @param p_map_name The file name/map name for the WarMap you wish to save
     */
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
                for (Entry<Integer, HashMap<Integer, Integer>> l_neighbourlist : d_adjencyList.entrySet()) {
                    StringBuilder l_border = new StringBuilder();
                    l_border.append(l_neighbourlist.getKey());
                    for (Integer nghbr_cuntry : l_neighbourlist.getValue().keySet())
                        l_border.append(" " + nghbr_cuntry);
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
    /**
     * A function which prints the WarMap to the console in a readable format.
     */
    public void showMap() { //Show map for only map
        for (Map.Entry<Integer, Country> l_entry : d_countries.entrySet()) { //All the countries with their neighbouring countries
            System.out.println("Country with ID: " + l_entry.getValue().get_countryID() + " and name: " + l_entry.getValue().get_countryName());
            System.out.println("The neighboring countries are:");
            for (Integer l_neighborcountryId : l_entry.getValue().getNeighbouringCountries().keySet()) {

                if (d_countries.get(l_neighborcountryId) != null) {
                    System.out.println(d_countries.get(l_neighborcountryId).get_countryName() + " with country ID: " + d_countries.get(l_neighborcountryId).get_countryID());
                }
                if (d_countries.get(l_neighborcountryId) == null) {
                    System.out.println("A null country was found");
                }
            }
            System.out.println("---------------------------------");
        }
        for (Map.Entry<Integer, Continent> l_entry : d_continents.entrySet()) { //All the continents with the country ids belonging to them
            System.out.println("Continent with ID: " + l_entry.getValue().get_continentID() + " and name: " + l_entry.getValue().get_continentName() + " with army bonus: " + l_entry.getValue().get_armyBonus());
            System.out.println("This continent is made up of the following Country IDs:");
            for (Country l_c : this.get_countries().values()) {
                if (l_c.getContinentID() == l_entry.getKey()) {
                    System.out.print(l_c.get_countryID() + " ");
                }
            }
            System.out.println();
            System.out.println("---------------------------------");

        }

    }
    /**
     * A function which prints the WarMap to the console in a readable format which also shows ownership of the countries based on the list of players passed.
     *
     * @param p_players a list of players playing on the current WarMap
     */
    public void showMap(List<Player> p_players) { //Show map for with player ownership
        showMap();
        for (Player l_player : p_players) { //Shows what countries are owned by the players with the corresponding number of armies
            System.out.println(l_player.get_playerName() + " owns the following countries:");
            for (Country l_c : l_player.get_playerCountries()) {
                System.out.println(l_c.get_countryName() + " with countryID " + l_c.get_countryID() + " with " + l_c.get_numOfArmies() + " armies.");

            }
            System.out.println("---------------------------------");
        }
    }
    
	public void addContinent(Integer p_continentId, String p_continentName, Integer p_armybonus) {
        d_continents.put(p_continentId, new Continent(p_continentId, p_continentName, p_armybonus));
        System.out.println("Added a continent with ID " + p_continentId);
	}
	
	public void removeContinent(Integer p_continentId) {
		for (Integer l_countryId : d_countries.keySet()) {
            if (d_countries.containsKey(l_countryId)) {
                if (d_countries.get(l_countryId).getContinentID() == p_continentId) {

                    removeCountry(l_countryId);

                }
            }
        }
		d_continents.remove(p_continentId);
	}
	
	public void addCountry(Integer p_countryId, String p_countryName, Integer p_continentId) {
		d_countries.put(p_countryId, new Country(p_countryId, p_countryName, p_continentId));
		System.out.println("Added country with ID " + p_countryId);
	}
	
	public void removeCountry(Integer p_countryId) {
        for (Integer l_neighbouringcountryId : d_countries.get(p_countryId).getNeighbouringCountries().keySet()) {
			removeNeighbour(l_neighbouringcountryId, p_countryId);
		}
		d_countries.remove(p_countryId);
		d_adjencyList.remove(p_countryId);
        System.out.println("Removed Country with ID " + p_countryId);
	}
	
	public void addNeighbourCountry(Integer l_input_country_ID, Integer l_input_country_neighbor_ID) {
		addNeighbour(l_input_country_ID, l_input_country_neighbor_ID);
        addNeighbour(l_input_country_neighbor_ID, l_input_country_ID);
		
	}
	public void removeNeighbourCountry(Integer l_input_country_ID, Integer l_input_country_neighbor_ID) {
		removeNeighbour(l_input_country_ID, l_input_country_neighbor_ID);
        removeNeighbour(l_input_country_neighbor_ID, l_input_country_ID);
	}

}

