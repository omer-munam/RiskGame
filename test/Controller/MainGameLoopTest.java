package Controller;

import Models.Continent;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.MainMenu;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test method to verify the calculation of reinforcements for a player in the MainGameLoop class.
 * This test case creates a scenario where a player controls multiple countries and continents.
 * The expected number of reinforcements is calculated and compared to the actual result.
 *
 * <p>Scenario:
 * - Create a WarMap with two continents.
 * - Create five countries and add them to the map.
 * - Assign these countries to a player, including one country from the second continent.
 * - Calculate the number of reinforcements for the player.
 *
 * <p>Expected Result:
 * The player should receive 6 reinforcements, consisting of 5 (total owned countries) plus the army bonus of 1.
 *
 */

public class MainGameLoopTest {
    private GameEngine d_gameEngine;

    @BeforeEach
    public void setUp() {
        d_gameEngine = GameEngine.getInstance();
        d_gameEngine.setPhase(new MainMenu(d_gameEngine));

    }
    /**
     * Test for getting the number of reinforcements
     */
    @org.junit.jupiter.api.Test
    public void testGetNumOfReinforcements() {
        WarMap test_war_map = new WarMap();
        //CREATE THE CONTINENTS
        test_war_map.addContinent(new Continent(1, "continent 1", 1));
        test_war_map.addContinent(new Continent(2, "continent 2", 2));

        //CREATE THE COUNTRIES
        Country test_country_one = new Country(1, "Country 1", 1);
        Country test_country_two = new Country(2, "Country 2", 1);
        Country test_country_three = new Country(3, "Country 3", 1);
        Country test_country_four = new Country(4, "Country 4", 2);
        Country test_country_five = new Country(5, "Country 5", 2);

        //ADD ALL Countries to map.
        test_war_map.addCountry(test_country_one);
        test_war_map.addCountry(test_country_two);
        test_war_map.addCountry(test_country_three);
        test_war_map.addCountry(test_country_four);
        test_war_map.addCountry(test_country_five);

        //Create player and add desired coutnries to player
        Player test_player = new Player("TestPlayer");
        ArrayList<Country> player_countries = new ArrayList<>();
        player_countries.add(test_country_one);
        player_countries.add(test_country_two);
        player_countries.add(test_country_three);
        player_countries.add(test_country_four);

        //Added full continent 1 (countries 1-3) but only one country from continent 2, should get 6 reinforcements, 5+army bonus of 1
        test_player.set_playerCountries(player_countries);

        ArrayList<Player> test_player_list = new ArrayList<>();
        test_player_list.add(test_player); //NEED TO PUT PLAYER IN LIST TO MAKE MainGameLoop Class
        d_gameEngine.set_currentMap(test_war_map);
        d_gameEngine.set_PlayersList(test_player_list);
        int numOfReinforcements = d_gameEngine.getNumOfReinforcements(test_player_list.get(0));

        // Define the expected number of reinforcements based on your test scenario
        int expectedReinforcements = 6; // Adjust this value based on your specific test case

        assertEquals(expectedReinforcements, numOfReinforcements);
    }
}