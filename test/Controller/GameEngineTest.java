package Controller;


import Models.Country;
import Models.Player;
import Models.WarMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameEngineTest {

    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        gameEngine = new GameEngine();
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
        gameEngine.set_currentMap(warMap);
        gameEngine.set_PlayersList(List.of(new Player("Player1"), new Player("Player2")));
        gameEngine.assignCountries(true);

        List<Player> playersList = gameEngine.get_PlayersList();
        assertEquals(2, playersList.size());
        assertFalse(playersList.get(0).get_playerCountries().isEmpty());
        assertFalse(playersList.get(1).get_playerCountries().isEmpty());

    }

    @Test
    void addPlayer() {

        gameEngine.addPlayer("Player1");

        List<Player> players = gameEngine.get_PlayersList();

        assertEquals(1, players.size());

        assertEquals("Player1", players.get(0).get_playerName());
    }

    @Test
    void removePlayer() {
        gameEngine.addPlayer("Player1");
        gameEngine.addPlayer("Player2");
        gameEngine.addPlayer("Player3");

        gameEngine.removePlayer("Player2");

        List<Player> players = gameEngine.get_PlayersList();

        assertEquals(2, players.size());
        assertEquals("Player1", players.get(0).get_playerName());
        assertEquals("Player3", players.get(1).get_playerName());
    }
}