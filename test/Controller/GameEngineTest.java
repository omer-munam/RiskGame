package Controller;


import Models.BehaviourStrategies.HumanStrategy;
import Models.Country;
import Models.Player;
import Models.WarMap;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the GameEngine class.
 * This class contains test methods for various functionalities of the GameEngine class,
 * such as assigning countries to players, adding players, and removing players.
 */
public class GameEngineTest {

    private GameEngine gameEngine;

    /**
     * Sets up a new GameEngine instance before each test.
     */
    @Before
    public void setUp() {
        gameEngine = GameEngine.getInstance();
        gameEngine.get_PlayersList().clear();
    }

    /**
     * Test the functionality to assign countries to players.
     * It creates a test WarMap with countries and players and checks if countries
     * are correctly assigned to the players.
     */
    @Test
    public void testAssignCountries() {
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

    /**
     * Test the functionality to add a player to the GameEngine.
     * It checks if a player is correctly added to the player's list.
     */
    @Test
    public void testAddPlayer() {

        Player player1 = new Player("Player1");
        player1.setD_behaviourStrategy(new HumanStrategy(player1));

        Player player2 = new Player("Player2");
        player2.setD_behaviourStrategy(new HumanStrategy(player2));


        // Test adding a new player
        gameEngine.get_PlayersList().add(player1);
        assertEquals(1, gameEngine.get_PlayersList().size());

        // Test adding a new player
        gameEngine.get_PlayersList().add(player2);
        assertEquals(2, gameEngine.get_PlayersList().size());
    }

    /**
     * Test the functionality to remove a player from the GameEngine.
     * It checks if a player is correctly removed from the player's list.
     */
    @Test
    public void testRemovePlayer() {

        Player player1 = new Player("Player1");
        player1.setD_behaviourStrategy(new HumanStrategy(player1));
        gameEngine.get_PlayersList().add(player1);

        Player player2 = new Player("Player2");
        player2.setD_behaviourStrategy(new HumanStrategy(player2));
        gameEngine.get_PlayersList().add(player2);

        gameEngine.removePlayer("Player2");

        List<Player> players = gameEngine.get_PlayersList();

        assertEquals(1, players.size());
        assertEquals("Player1", players.get(0).get_playerName());
    }
}