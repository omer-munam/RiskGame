package Models.Orders;

import Controller.GameEngine;
import Models.BehaviourStrategies.HumanStrategy;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for functions concerning Diplomacy orders
 */
public class DiplomacyOrderTest {

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
    public void testDiplomacyCommandExecution() {
        // Create a test scenario where the player has the Diplomacy card, and the input is valid.
        player.set_playerCards(List.of(Cards.Diplomacy));
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA", 1));
        map.addCountry(new Country(2, "CountryB", 1));
        player.set_playerCountries(Arrays.asList(new Country(1, "CountryA", 1)
                , new Country(2, "CountryB", 1)));

        // Create another player in the game.
        Player otherPlayer = new Player("Player2");

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(otherPlayer));

        // Simulate a valid diplomacy command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that the target player name is added to the diplomacy list.
        assertTrue(player.get_diplomacy_list().contains("Player2"));

    }

    @Test
    public void testDiplomacyCommandExecutionWithoutDiplomacyCard() {
        // Create a test scenario where the player does not have the Diplomacy card.
        WarMap map = new WarMap();
        map.addCountry(new Country());
        player.set_playerCountries(List.of(new Country(1, "CountryA", 1)));

        // Create another player in the game.
        Player otherPlayer = new Player("Player2");

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(otherPlayer));

        // Simulate a valid diplomacy command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no DiplomacyOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());

        // Assert that the target player name is not added to the diplomacy list.
        assertFalse(player.get_diplomacy_list().contains("Player2"));
    }

    @Test
    public void testDiplomacyCommandExecutionWithInvalidTargetPlayer() {
        // Create a test scenario where the player has the Diplomacy card, but the target player does not exist.
        Player player = new Player("Player1");
        player.setD_behaviourStrategy(new HumanStrategy(player));
        player.set_playerCards(List.of(Cards.Diplomacy));
        WarMap map = new WarMap();
        map.addCountry(new Country());
        player.set_playerCountries(List.of(new Country(1, "CountryA", 1)));

        // Simulate an invalid target player by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy fakePlayer");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no DiplomacyOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());

        // Assert that the target player name is not added to the diplomacy list.
        assertFalse(player.get_diplomacy_list().contains("fakePlayer"));
    }
}
