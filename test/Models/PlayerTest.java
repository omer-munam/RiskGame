package Models;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerTest {

    private Player player;
    private List<Country> countries;
    private List<Continent> continents;

    @BeforeAll
    public void setUp() {
        player = new Player("John Doe");
        countries = new ArrayList<>();
        continents = new ArrayList<>();
    }

    @Test
    public void testGetPlayerName() {
        assertEquals("John Doe", player.get_playerName());
    }

    @Test
    public void testSetPlayerName() {
        player.set_playerName("Jane Smith");
        assertEquals("Jane Smith", player.get_playerName());
    }

    @Test
    public void testGetPlayerCountries() {
        player.set_playerCountries(countries);
        assertEquals(countries, player.get_playerCountries());
    }

    @Test
    public void testSetPlayerCountries() {
        List<Country> newCountries = new ArrayList<>();
        player.set_playerCountries(newCountries);
        assertEquals(newCountries, player.get_playerCountries());
    }

    @Test
    public void testGetPlayerContinents() {
        player.set_playerContinents(continents);
        assertEquals(continents, player.get_playerContinents());
    }

    @Test
    public void testSetPlayerContinents() {
        List<Continent> newContinents = new ArrayList<>();
        player.set_playerContinents(newContinents);
        assertEquals(newContinents, player.get_playerContinents());
    }
//////////////////////


        @Test
        public void testNextOrder() {
            Orders order1 = new Orders(3, 1);
            Orders order2 = new Orders(2, 2);
            Orders order3 = new Orders(4, 3);

            player.get_playerOrder().add(order1);
            player.get_playerOrder().add(order2);
            player.get_playerOrder().add(order3);

            Orders nextOrder = player.next_order();

            assertEquals(order1, nextOrder);
            assertEquals(2, player.get_playerOrder().size());
            assertFalse(player.get_playerOrder().contains(order1));
        }

        @Test
        public void testNextOrderEmptyList() {
            Orders nextOrder = player.next_order();

            assertNull(nextOrder);
        }

    @Test
    public void testIssueOrder() {
        player.set_armiesNumber(5);

        List<Country> playerCountries = new ArrayList<>();
        playerCountries.add(new Country(1, "Country1", 1));
        playerCountries.add(new Country(2, "Country2", 1));
        playerCountries.add(new Country(3, "Country3", 2));
        player.set_playerCountries(playerCountries);

        // Set up the input for issue_order() method
        String[] commands = {
                "deploy 1 3",
                "deploy 2 2",
                "deploy 3 1"
        };
        FakeScanner.setCommands(commands);

        player.issue_order();

        List<Orders> playerOrders = player.get_playerOrder();
        assertEquals(3, playerOrders.size());

        Orders order1 = playerOrders.get(0);
        assertEquals(3, order1.getNumOfArmies());
        assertEquals(1, order1.getCountryID());

        Orders order2 = playerOrders.get(1);
        assertEquals(2, order2.getNumOfArmies());
        assertEquals(2, order2.getCountryID());

        Orders order3 = playerOrders.get(2);
        assertEquals(1, order3.getNumOfArmies());
        assertEquals(3, order3.getCountryID());
    }
}

