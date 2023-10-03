package Controller;

import Models.Country;
import Models.Player;
import Models.WarMap;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MainGameLoopTest {

    @BeforeEach
    void setUp() {
        Player player1 = new Player("Adeel"); // Create an instance of the Player class


        WarMap game_map = new WarMap()

        HashMap<Integer, ArrayList<Integer>> countries = new HashMap<>();
        HashSet<Integer> continents = new HashSet<>();

        // Populate countries and continents as needed for testing
        // You can create dummy data or use a mocking library like Mockito
    }
    @org.junit.jupiter.api.Test
    void testGetNumOfReinforcements() {
        // Add countries to the player's list of owned countries
        ArrayList<Country> playerCountries = new ArrayList<>();
        // Add countries to playerCountries as needed for testing
        MainGameLoo player1;
        player1.setPlayerCountries(playerCountries);

        // Create and add continents to the map
        Continent continent1 = new Continent(1, "Continent1", 3); // Replace with actual data
        Continent continent2 = new Continent(2, "Continent2", 5); // Replace with actual data
        continents.put(continent1.getContinentID(), continent1);
        continents.put(continent2.getContinentID(), continent2);

        // Assign countries to continents
        // Populate the 'countries' map with Country instances and assign them to continents

        // Set the 'd_map' member variable of the player to the map containing continents and countries
        player1.setD_map(new Map(continents, countries)); // Replace with actual Map class and data

        int numOfReinforcements = player1.getNumOfReinforcements(player);

        // Define the expected number of reinforcements based on your test scenario
        int expectedReinforcements = 8; // Adjust this value based on your specific test case

        assertEquals(expectedReinforcements, numOfReinforcements);
    }
}