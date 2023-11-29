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
 * Unit tests for the {@link AggressiveStrategy} class, which represents
 * the aggressive behavior strategy for a player in the game. The tests
 * cover the creation of deploy, advance, and attack orders based on the
 * specific strategy rules.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AggressiveStrategyTest {

    private AggressiveStrategy aggressiveStrategy;
    private Player player;
    private WarMap warMap;

    private Map<Integer, Country> countries;
    /**
     * Initializing the player instance before each test.
     */
    private GameEngine gameEngine;
    /**
     * Sets up the initial state for each test, including creating a player,
     * an aggressive strategy instance, and initializing countries and the game engine.
     */
    @Before
    public void setUp() {
        player = new Player("John Doe");
        aggressiveStrategy = new AggressiveStrategy(player);
        countries = new HashMap<>();
        gameEngine = GameEngine.getInstance();
        warMap = new WarMap();
    }
    /**
     * Tests the creation of a deploy order command for the aggressive strategy.
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
        player.setD_behaviourStrategy(aggressiveStrategy);

        GameEngine.getInstance().getCurrentPlayer().set_numOfReinforcements(5);
        player.set_numOfReinforcements(5);
        AssignReinforcements assignReinforcementsPhase = new AssignReinforcements(gameEngine);
        GameEngine.getInstance().setPhase(assignReinforcementsPhase);
        player.issue_order();


        assertEquals(1, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an advance order command for the aggressive strategy
     * when there are neighboring countries. Ensures that the order is not created
     * under certain conditions.
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
        player.setD_behaviourStrategy(aggressiveStrategy);
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an advance order command for the aggressive strategy
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
        player.setD_behaviourStrategy(aggressiveStrategy);
        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an attack order command for the aggressive strategy
     * when there are neighboring countries. Ensures that the order is not created
     * under certain conditions.
     */
    @Test
    public void testCreateAttackOrderCommandWithNeighbouringCountries() {

        WarMap map = new WarMap();

        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        map.addCountry(destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        map.addCountry(destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 1);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        map.addCountry(destCountry3);

        Country destCountry4 = new Country(4, "DestCountry3", 1);
        destCountry4.set_numOfArmies(8);
        destCountry4.setD_ownerPlayer(player);
        map.addCountry(destCountry4);

        destCountry1.addNeighbouringCountry(destCountry2);
        destCountry1.addNeighbouringCountry((destCountry3));
        destCountry1.addNeighbouringCountry((destCountry4));
        destCountry2.addNeighbouringCountry((destCountry3));
        destCountry2.addNeighbouringCountry((destCountry1));
        destCountry2.addNeighbouringCountry((destCountry4));
        destCountry3.addNeighbouringCountry((destCountry1));
        destCountry3.addNeighbouringCountry((destCountry2));
        destCountry3.addNeighbouringCountry((destCountry4));
        destCountry4.addNeighbouringCountry(destCountry1);
        destCountry4.addNeighbouringCountry((destCountry2));
        destCountry4.addNeighbouringCountry((destCountry3));


        player.set_playerCountries(List.of(destCountry1, destCountry3));
        player.setD_behaviourStrategy(aggressiveStrategy);
        GameEngine.getInstance().setPhase(new IssueOrders(GameEngine.getInstance()));
        GameEngine.getInstance().set_currentMap(map);

        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
    }
    /**
     * Tests the creation of an attack order command for the aggressive strategy
     * when there are no neighboring countries. Ensures that the order is not created
     * under certain conditions.
     */
    @Test
    public void testCreateAttackOrderCommandWithoutNeighbouringCountries() {

        WarMap map = new WarMap();

        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        map.addCountry(destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        map.addCountry(destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 1);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        map.addCountry(destCountry3);

        Country destCountry4 = new Country(4, "DestCountry3", 1);
        destCountry4.set_numOfArmies(8);
        destCountry4.setD_ownerPlayer(player);
        map.addCountry(destCountry4);


        player.set_playerCountries(List.of(destCountry1, destCountry3));
        player.setD_behaviourStrategy(aggressiveStrategy);
        GameEngine.getInstance().setPhase(new IssueOrders(GameEngine.getInstance()));
        GameEngine.getInstance().set_currentMap(map);

        player.issue_order();

        assertEquals(0, player.get_playerOrder().size());
    }
}