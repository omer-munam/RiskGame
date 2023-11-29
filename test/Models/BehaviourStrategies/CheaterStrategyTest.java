package Models.BehaviourStrategies;
import Models.Player;
import Models.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the CheaterStrategy class.
 * It tests the behavior of the CheaterStrategy's issue_order() method.
 *
 * @author [Leila Mousavi]
 */
public class CheaterStrategyTest {

    private Player player;
    private CheaterStrategy cheaterStrategy;

    /**
     * Set up the test environment before each test case.
     */
    @Before
    public void setup() {
        player = new Player("TestPlayer");
        cheaterStrategy = new CheaterStrategy(player);
    }

    @Test
    public void testIssueOrder_ConquerEnemyAndDoubleArmies() {
        // Create a country with an enemy neighbor
        Country country = new Country(1, "CountryA", 1);
        Country enemyNeighbor = new Country(2, "CountryB", 1);
        enemyNeighbor.setD_ownerPlayer(new Player("EnemyPlayer"));
        country.addNeighbouringCountry(enemyNeighbor);
        player.get_playerCountries().add(country);

        // Set up initial armies
        country.set_numOfArmies(6);
        enemyNeighbor.set_numOfArmies(2);

        // Issue order for the cheater strategy
        cheaterStrategy.issue_order();

        // Check if the enemy neighbor is conquered and armies are doubled
        assertEquals(player, enemyNeighbor.getD_ownerPlayer());
        assertEquals(4, enemyNeighbor.get_numOfArmies());

        // Check if the armies on the original country are doubled
        assertEquals(6, country.get_numOfArmies());
    }

    @Test
    public void testIssueOrder_NoEnemyNeighbors() {
        // Create a country with no enemy neighbors
        Country country = new Country(1, "CountryA", 1);
        player.get_playerCountries().add(country);

        // Set up initial armies
        country.set_numOfArmies(3);

        // Issue order for the cheater strategy
        cheaterStrategy.issue_order();

        // Check that nothing changes (no enemy neighbors to conquer)
        assertEquals(3, country.get_numOfArmies());
    }

    @Test
    public void testIssueOrder_MultipleEnemyNeighbors() {
        // Create a country with multiple enemy neighbors
        Country country = new Country(1, "CountryA", 1);
        Country enemyNeighbor1 = new Country(2, "CountryB", 1);
        Country enemyNeighbor2 = new Country(3, "CountryC", 1);
        enemyNeighbor1.setD_ownerPlayer(new Player("EnemyPlayer"));
        enemyNeighbor2.setD_ownerPlayer(new Player("EnemyPlayer"));
        country.addNeighbouringCountry(enemyNeighbor1);
        country.addNeighbouringCountry(enemyNeighbor2);
        player.get_playerCountries().add(country);

        // Set up initial armies
        country.set_numOfArmies(6);
        enemyNeighbor1.set_numOfArmies(2);
        enemyNeighbor2.set_numOfArmies(4);

        // Issue order for the cheater strategy
        cheaterStrategy.issue_order();

        // Check if all enemy neighbors are conquered and armies are doubled
        assertEquals(player, enemyNeighbor1.getD_ownerPlayer());
        assertEquals(player, enemyNeighbor2.getD_ownerPlayer());
        assertEquals(4, enemyNeighbor1.get_numOfArmies());
        assertEquals(8, enemyNeighbor2.get_numOfArmies());

        // Check if the armies on the original country are doubled
        assertEquals(6, country.get_numOfArmies());
    }

}