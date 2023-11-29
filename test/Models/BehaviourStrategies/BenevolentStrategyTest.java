package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.AssignReinforcements;

import Phases.IssueOrders;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BenevolentStrategy} class, which represents
 * the benevolent behavior strategy for a player in the game. The tests
 * cover the creation of deploy and advance orders based on the specific
 * strategy rules.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BenevolentStrategyTest {
    /**
     * The benevolent strategy instance under test.
     */
    private BenevolentStrategy benevolentStrategy;
    /**
     * The player instance used in the tests.
     */
    private Player player;
    /**
     * The WarMap instance used in the tests.
     */
    private WarMap warMap;
    /**
     * A map representing countries used in the tests.
     */

    private Map<Integer, Country> countries;
    /**
     * The GameEngine instance used in the tests.
     */
    private GameEngine gameEngine;
    /**
     * Sets up the initial state for each test, including creating a player,
     * a benevolent strategy instance, and initializing countries and the game engine.
     */
    @Before
    public void setUp() {
        player = new Player("John Doe");
        benevolentStrategy = new BenevolentStrategy(player);
        countries = new HashMap<>();
        gameEngine = GameEngine.getInstance();
        warMap = new WarMap();
    }
    /**
     * Tests the creation of a deploy order command for the benevolent strategy.
     * Verifies that the order is created correctly based on certain conditions.
     */
    @Test
   public void testCreateDeployOrderCommand() {

        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(1);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        warMap.set_countries((HashMap<Integer, Country>) countries);
        player.set_playerCountries(new ArrayList<>(countries.values()));
        GameEngine.getInstance().getCurrentPlayer().set_playerCountries(Arrays.asList(destCountry1, destCountry2));
        player.setD_behaviourStrategy(benevolentStrategy);

        GameEngine.getInstance().getCurrentPlayer().set_numOfReinforcements(5);
        player.set_numOfReinforcements(5);
        AssignReinforcements assignReinforcementsPhase = new AssignReinforcements(gameEngine);
        GameEngine.getInstance().setPhase(assignReinforcementsPhase);
        player.issue_order();


        assertEquals(1, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an advance order command for the benevolent strategy
     * when there are neighboring countries. Ensures that the order is created
     * correctly based on certain conditions.
     */
    @Test
    public void testCreateAdvanceOrderCommandWithNeighbouringCountries() {

        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 1);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        countries.put(3, destCountry3);

        destCountry1.addNeighbouringCountry(destCountry2);
        destCountry1.addNeighbouringCountry((destCountry3));
        destCountry2.addNeighbouringCountry((destCountry3));
        destCountry2.addNeighbouringCountry((destCountry1));
        destCountry3.addNeighbouringCountry((destCountry1));
        destCountry3.addNeighbouringCountry((destCountry2));

        player.set_playerCountries(Arrays.asList(destCountry1, destCountry2, destCountry3));
        GameEngine.getInstance().setPhase(new IssueOrders(GameEngine.getInstance()));
        GameEngine.getInstance().getCurrentPlayer().set_playerCountries(Arrays.asList(destCountry1, destCountry2, destCountry3));
        player.setD_behaviourStrategy(benevolentStrategy);

        player.issue_order();

        assertEquals(1, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an advance order command for the benevolent strategy
     * when there are no neighboring countries. Ensures that the order is not created
     * under certain conditions.
     */
    @Test
    public void testCreateAdvanceOrderCommandWithoutNeighbouringCountries() {

        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 1);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        countries.put(3, destCountry3);

        player.set_playerCountries(Arrays.asList(destCountry1, destCountry2, destCountry3));
        GameEngine.getInstance().setPhase(new IssueOrders(GameEngine.getInstance()));
        GameEngine.getInstance().getCurrentPlayer().set_playerCountries(Arrays.asList(destCountry1, destCountry2, destCountry3));
        player.setD_behaviourStrategy(benevolentStrategy);
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
    }
}