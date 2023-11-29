package Models.Orders;

import Controller.GameEngine;
import Models.BehaviourStrategies.HumanStrategy;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for functions concerning Airlift orders
 */
public class AirliftOrderTest {
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
    public void testAirliftCommandExecution() {

        player.setD_behaviourStrategy(new HumanStrategy(player));


        // Create a test scenario where the player has the Airlift card and valid input.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Airlift);
        player.set_playerCards(cards);
        WarMap map = new WarMap();

        // Create source and target countries.
        Country sourceCountry = new Country(1, "SourceCountry", 1);
        Country targetCountry = new Country(2, "TargetCountry", 1);
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(player));

        // Simulate a valid airlift command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("airlift 1 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert that an AirliftOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) instanceof AirliftOrder);

        // Assert that the Airlift card is removed from the player's cards.
        assertFalse(player.get_playerCards().contains(Cards.Airlift));
    }

    @Test
    public void testAirliftCommandExecutionWithInvalidSourceCountry() {

        player.setD_behaviourStrategy(new HumanStrategy(player));


        // Create a test scenario where the player has two countries, but the source country is invalid.
        WarMap map = new WarMap();
        Country sourceCountry = new Country(1, "SourceCountry", 1);
        Country targetCountry = new Country(2, "TargetCountry", 1);
        map.addCountry(sourceCountry);
        map.addCountry(targetCountry);
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

        // Simulate an invalid airlift command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("airlift 3 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no AirliftOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }

}
