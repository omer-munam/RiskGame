package Models.Orders;

import Controller.GameEngine;
import Models.BehaviourStrategies.HumanStrategy;
import Models.Country;
import Models.Player;
import Models.WarMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for functions concerning advance orders
 */
public class AdvanceOrderTest {
    /**
     * The player instance required to run the test case.
     */
    private Player player;
    /**
     * Initializing the player instance before each test.
     */
    private GameEngine gameEngine;
    /**
     * The Instance of warmap.
     */
    private WarMap warMap;

    @Before
    public void setUp() {
        gameEngine = GameEngine.getInstance();
        player = new Player("John Doe");
        player.setD_behaviourStrategy(new HumanStrategy(player));
        warMap = new WarMap();
    }

    @Test
    public void testAdvanceCommandExecution() {

        player.setD_behaviourStrategy(new HumanStrategy(player));

        // Create a test scenario where the player has valid input and neighboring countries.
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        // Make CountryA and CountryB neighbors.
        countryA.addNeighbouringCountry(countryB);

        player.set_playerCountries(Arrays.asList(countryA, countryB));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("advance 1 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert the expected outcome.
        assertEquals(1, player.get_playerOrder().size());
    }

    @Test
    public void testAdvanceCommandExecutionWithInvalidSourceCountry() {

        player.setD_behaviourStrategy(new HumanStrategy(player));

        // Create a test scenario where the player has two countries, but the source country is invalid.
        WarMap map = new WarMap();
        Country sourceCountry = new Country(1, "SourceCountry", 1);
        Country targetCountry = new Country(2, "TargetCountry", 1);
        map.addCountry(sourceCountry);
        map.addCountry(targetCountry);
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

        // Make the source and target countries non-neighbors.
        // In this case, the source country is not a neighbor of the target country.
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

        // Simulate an invalid advance command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("advance 1 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no AdvanceOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }
}
