package Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import Models.Player;
import Models.WarMap;
import Models.Country;

class GameEngineTest {

    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void assignCountries() {
        WarMap warMap = new WarMap();
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 2);
        Country country3 = new Country(3, "Country3", 2);
        warMap.get_countries().put(1, country1);
        warMap.get_countries().put(2, country2);
        warMap.get_countries().put(3, country3);
        gameEngine.set_PlayersList(List.of(new Player("Player1"), new Player("Player2")));
        gameEngine.assignCountries();

        List<Player> playersList = gameEngine.get_PlayersList();
        assertEquals(2, playersList.size());
        assertEquals(2, playersList.get(0).get_playerCountries().size());
        assertEquals(1, playersList.get(1).get_playerCountries().size());
        assertTrue(playersList.get(0).get_playerCountries().contains(country1));
        assertTrue(playersList.get(0).get_playerCountries().contains(country2));
        assertTrue(playersList.get(1).get_playerCountries().contains(country3));
    }

    @Test
    void addPlayer() {
    }

    @Test
    void removePlayer() {
    }
}