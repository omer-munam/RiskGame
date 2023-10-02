package Controller;

import Models.Country;
import Models.Player;
import Models.WarMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This class is the MainGameLoop class. Once the game is set up with the map and all the users, the control is handed over to this class for actual gameplay.
 *
 * @author Omer Munam
 */
public class MainGameLoop {

    /**
     * The current map of the game.
     */
    private WarMap d_map;
    /**
     * The list of players of the game.
     */
    private List<Player> d_playerList;

    /**
     * A fully parametrized constructor built to initialize the class with all the essential values.
     *
     * @param l_map
     * @param l_playerList
     */
    public MainGameLoop(WarMap l_map, List<Player> l_playerList){
        this.d_map = l_map;
        this.d_playerList = l_playerList;
    }

    /**
     * The method which receives the control over from the GameEngine class and is responsible to handle the whole gameplay.
     */
    public void run_game_loop(){
        System.out.println("Begin Main Game Loop...");
    }

    void assign_reinforcements() {

    }

    int getNumOfReinforcements(Player p_player) {
        int l_baseReinforcements = 5;
        d_map.get_countries();
        p_player.get_playerCountries();
        HashMap<Integer, ArrayList<Integer>> l_continent_countries = new HashMap<>();
        for (Country l_c : d_map.get_countries().values()) {
            l_continent_countries.putIfAbsent(l_c.getContinentID(), new ArrayList<Integer>());
            l_continent_countries.get(l_c.getContinentID()).add(l_c.get_countryID());
        }


        HashSet<Integer> l_full_continents = new HashSet<>();
        ArrayList<Integer> l_player_country_ids = new ArrayList<>();
        for (Country l_country : p_player.get_playerCountries()) {
            l_player_country_ids.add(l_country.get_countryID());
        }

        for (ArrayList<Integer> l_c : l_continent_countries.values()) {
            for (int l_i : l_c) {
                if (l_player_country_ids.contains(l_i)) {
                    l_full_continents.add(d_map.get_countries().get(l_i).getContinentID());
                } else {
                    l_full_continents.remove(d_map.get_countries().get(l_i).getContinentID());
                    break;
                }
            }
        }
        for (int l_i : l_full_continents) {
            l_baseReinforcements += d_map.get_continents().get(l_i).get_armyBonus();
        }
        return l_baseReinforcements;
    }

}
