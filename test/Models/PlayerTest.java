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


}