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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for functions concerning Bomb orders
 */
public class BombOrderTest {
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
    public void testBombCommandExecution() {
        // Create a test scenario where the player has the Bomb card, and the input is valid.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Bomb);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        player.set_playerCountries(List.of(countryA));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that a BombOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) instanceof BombOrder);

        // Ensure that the Bomb card is removed from the player's cards.
        assertFalse(player.get_playerCards().contains(Cards.Bomb));
    }

    @Test
    public void testBombCommandExecutionWithInvalidCountry() {
        // Create a test scenario where the player has the Bomb card, but the target country is invalid.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Bomb);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        GameEngine.getInstance().set_currentMap(map);
        // Simulate an invalid target country by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no BombOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }

    @Test
    public void testBombCommandExecutionWithoutBombCard() {
        // Create a test scenario where the player does not have the Bomb card.
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        map.addCountry(countryA);

        player.set_playerCountries(List.of(countryA));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 1");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no BombOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }

}
