package Controller;

import Models.Country;
import Models.Orders;
import Models.Player;
import Models.WarMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static Controller.GameEngine.SCANNER;
import static Resources.Commands.SHOW_MAP_COMMAND;

/**
 * This class is the MainGameLoop class. Once the game is set up with the map and all the users, the control is handed over to this class for actual gameplay.
 *
 * @author Omer Munam
 */
public class MainGameLoop {

    /**
     * The current map of the game.
     */
    private final WarMap d_map;
    /**
     * The list of players of the game.
     */
    private final List<Player> d_playerList;

    /**
     * A fully parametrized constructor built to initialize the class with all the essential values.
     *
     * @param p_map        The current game map of play
     * @param p_playerList The list of players
     */
    public MainGameLoop(WarMap p_map, List<Player> p_playerList) {
        this.d_map = p_map;
        this.d_playerList = p_playerList;
    }

    /**
     * The method which receives the control over from the GameEngine class and is responsible to handle the whole gameplay.
     */
    public void run_game_loop() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      Game Starts... Get Ready...       ║");
        System.out.println("╚════════════════════════════════════════╝");

        while (true) {
            System.out.println("Please enter desired command (Applicable commands: `showmap`, `play` & `exit`):");
            String input = SCANNER.nextLine();
            if (input.equalsIgnoreCase(SHOW_MAP_COMMAND)) {
                d_map.showMap(d_playerList);
            } else if (input.equalsIgnoreCase("play")) {
                play();
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting to Main Menu.....");
                break;
            } else {
                System.out.println("Incorrect command....");
            }
        }
    }

    void play() {
        System.out.println("Assigning Reinforcements....");
        System.out.println("_________________________________________");
        assign_reinforcements();

        System.out.println("\n_________________________________________");
        System.out.println("Taking orders from each player....");
        System.out.println("_________________________________________");
        for (Player player : d_playerList) {
            player.issue_order(null, d_map);
            System.out.println("_________________________________________");
        }

        System.out.println("\nStarting the execution of the issued Commands.... ");
        System.out.println("_________________________________________");

        for (Player player : d_playerList) {
            while (true) {
                Orders order = player.next_order();
                if (order == null)
                    break;
                order.execute(d_map);
            }
        }
        System.out.println("\n_________________________________________");
        System.out.println("All commands executed successfully..... ");
        System.out.println("_________________________________________");
    }

    /**
     * The method which will be called when we need to assign reinforcements to players, so that they can issue orders.
     */
    void assign_reinforcements() {
        for (Player player : d_playerList) {
            player.set_numOfReinforcements(getNumOfReinforcements(player));
            System.out.println("Assigned `" + player.get_numOfReinforcements() + "` reinforcements to player: " + player.get_playerName());
        }
    }

    /**
     * This method is used to calculate how many reinforcements are to be assigned to the given player based on the continents they hold.
     *
     * @param p_player The player for which we need to get number of reinforcements.
     * @return NumberOfReinforcements
     */
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
