package Models;

import Models.Continent;
import Models.Country;
import Models.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;



import java.util.ArrayList;
import java.util.List;



public class PlayerTest {

    private Player player;
    private List<Country> countries;
    private List<Continent> continents;

    @Before
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