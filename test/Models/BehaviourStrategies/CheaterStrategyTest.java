package Models.BehaviourStrategies;
import Models.Player;
import Models.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheaterStrategyTest {

    private Player player;
    private CheaterStrategy cheaterStrategy;

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

}