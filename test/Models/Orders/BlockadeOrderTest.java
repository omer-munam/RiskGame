package Models.Orders;

import Controller.GameEngine;
import Models.BehaviourStrategies.HumanStrategy;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests cases for blockade orders, it ensures
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlockadeOrderTest {
    /**
     * The Instance of warmap.
     */
    private WarMap warMap;
    /**
     * The Instance of player.
     */
    private Player player;
    /**
     * List of countries in the map
     */
    private Map<Integer, Country> countries;
    /**
     * Initializing the player instance before each test.
     */
    private GameEngine gameEngine;
    /**
     * Sets up the test environment before each test case.
     * It initializes the WarMap, player with playername and hashmap of countries
     */
    @Before
    public void setUp() {
        warMap = new WarMap();
        player = new Player("Player1");
        player.setD_behaviourStrategy(new HumanStrategy(player));
        countries = new HashMap<>();// Assuming you have a no-argument constructor in your Player class
        gameEngine = GameEngine.getInstance();

    }


    /**
     * Tests number of armies the execution of orders by blockade
     * It calls the blockade order execute method, and checks if the num of Armies are tripled
     */
    @Test
    public void testExecuteBlockadeOrderNumOfArmies() {
        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(5);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        warMap.set_countries((HashMap<Integer, Country>) countries);
        player.set_playerCountries(new ArrayList<>(countries.values()));

        BlockadeOrder blockadeOrder1 = new BlockadeOrder(1, player);
        blockadeOrder1.execute(warMap);

        assertEquals(15, destCountry1.get_numOfArmies());

        BlockadeOrder blockadeOrder2 = new BlockadeOrder(2, player);
        blockadeOrder2.execute(warMap);

        assertEquals(12, destCountry2.get_numOfArmies());
    }

    /**
     * Tests the country owned by players after execution of orders by blockade
     * Checks the country ownership is made null
     * Checks the destination country is removed from the player owned country list
     */
    @Test
    public void testExecuteBlockadeOrderCountryRemoved() {
        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(5);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 2);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        countries.put(3, destCountry3);

        warMap.set_countries((HashMap<Integer, Country>) countries);
        player.set_playerCountries(new ArrayList<>(countries.values()));

        BlockadeOrder blockadeOrder1 = new BlockadeOrder(1, player);
        blockadeOrder1.execute(warMap);

        assertTrue(destCountry1.getD_ownerPlayer().get_playerName().equals("Neutral"));
        assertEquals(2, player.get_playerCountries().size());

        BlockadeOrder blockadeOrder2 = new BlockadeOrder(2, player);
        blockadeOrder2.execute(warMap);

        assertTrue(destCountry2.getD_ownerPlayer().get_playerName().equals("Neutral"));
        assertEquals(1, player.get_playerCountries().size());

        BlockadeOrder blockadeOrder3 = new BlockadeOrder(3, player);
        blockadeOrder3.execute(warMap);

        assertTrue(destCountry3.getD_ownerPlayer().get_playerName().equals("Neutral"));
        assertEquals(0, player.get_playerCountries().size());
    }


    /**
     * Tests the execution of orders by blockade for non-existing players
     * Checks if a non-existing country is called on blockade, it doesnt make any difference
     */
    @Test
    public void testExecuteBlockadeOrderNonExistingCountry() {
        Country existingCountry = new Country(1, "ExistingCountry", 1);
        countries.put(1, existingCountry);
        warMap.set_countries((HashMap<Integer, Country>) countries);

        BlockadeOrder blockadeOrder = new BlockadeOrder(2, player);
        blockadeOrder.execute(warMap);

        assertEquals(existingCountry, warMap.get_countries().get(1));

        assertEquals(0, player.get_playerCountries().size());

        assertNull(warMap.get_countries().get(2));
    }

    @Test
    public void testBlockadeCommandExecution() {
        // Create a test scenario where the player has a Blockade card and valid input.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Blockade);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA", 1));
        map.addCountry(new Country(2, "CountryB", 1));
        map.addCountry(new Country(3, "CountryC", 1));

        List<Country> playerCountries = new ArrayList<>();
        playerCountries.add(new Country(1, "CountryA", 1));
        player.set_playerCountries(playerCountries);


        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("blockade 1");

        // Call the method you want to test.
        player.issue_order();

        // Assert the expected outcome.
        assertEquals(0, player.get_playerCards().size());
        assertEquals(1, player.get_playerOrder().size()); // The order should be issued successfully.
    }

    /**
     * Checks if the orders are taken correctly with valid Input
     */
    @Test
    public void testBlockadeIssueOrderValidInput() {
        Country country1 = new Country(1, "Country1", 1);
        player.get_playerCountries().add(country1);
        player.get_playerCards().add(Cards.Blockade);

        Country country2 = new Country(2, "Country2", 1);
        player.get_playerCountries().add(country2);
        player.get_playerCards().add(Cards.Blockade);
        gameEngine.setCurrentInput("blockade 1");
        player.issue_order();

        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) != null);
        assertEquals(1, player.get_playerCards().size());
        gameEngine.setCurrentInput("blockade 2");
        player.issue_order();

        assertEquals(2, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(1) != null);
        assertFalse(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(0, player.get_playerCards().size());
    }

    /**
     * Checks if no orders are added to orderlist and
     * Card is not removed with Invalid input
     */
    @Test
    public void testBlockadeIssueOrderInvalidCountry() {
        // Prepare player's countries and cards
        player.get_playerCards().add(Cards.Blockade);

        gameEngine.setCurrentInput("blockade 2");
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
        assertTrue(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(1, player.get_playerCards().size());

        player.get_playerCards().add(Cards.Blockade);
        gameEngine.setCurrentInput("blockade 3");
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
        assertTrue(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(2, player.get_playerCards().size());
    }

    /**
     * Checks if no orders are added to list
     * if there is no blockade card in the player card list
     */
    @Test
    public void testBlockadeIssueOrderNoBlockadeCard() {
        Country country1 = new Country(1, "Country1", 1);
        player.get_playerCountries().add(country1);

        Country country2 = new Country(2, "Country2", 1);
        player.get_playerCountries().add(country2);

        gameEngine.setCurrentInput("blockade 1");
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
        assertFalse(player.get_playerCards().contains(Cards.Blockade));

        gameEngine.setCurrentInput("blockade 2");
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
        assertFalse(player.get_playerCards().contains(Cards.Blockade));
    }

}
