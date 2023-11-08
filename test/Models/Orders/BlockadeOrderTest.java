package Models.Orders;
import Models.Country;
import Models.Player;
import Models.WarMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
     * Sets up the test environment before each test case.
     * It initializes the WarMap, player with playername and hashmap of countries
     */
    @BeforeEach
    public void setUp() {
        warMap = new WarMap();
        player = new Player("Player1");
        countries = new HashMap<>();// Assuming you have a no-argument constructor in your Player class
    }

    /**
     * Tests the execution of orders by blockade
     * It calls the blockade order execute method, and checks the num of Armies,
     * after execution the country is made null
     * checks if the country is removed from player country list
     */
    @Test
    public void testExecuteBlockadeOrder() {
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
        assertNull(destCountry1.getD_ownerPlayer());
        assertEquals(1, player.get_playerCountries().size());

        BlockadeOrder blockadeOrder2 = new BlockadeOrder(2, player);
        blockadeOrder2.execute(warMap);

        assertEquals(12, destCountry2.get_numOfArmies());
        assertNull(destCountry2.getD_ownerPlayer());
        assertEquals(0, player.get_playerCountries().size());
    }
}
