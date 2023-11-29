package Phases;

import Controller.GameEngine;
import Models.Country;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for functions concerning Play phase
 */
public class PlayPhaseTest {
    /**
     * Instance of the game engine used for tests
     */
    private GameEngine d_gameEngine;

    /**
     * setup to occur for all playphase tests
     */
    @Before
    public void setUp() {
        d_gameEngine = GameEngine.getInstance();
        d_gameEngine.setPhase(new MainMenu(d_gameEngine));
        d_gameEngine.get_PlayersList().clear();
    }

    /**
     * Test to ensure proper phase transitions in play phase
     *
     * @throws IOException when an IO exception occurs
     */
    @Test
    public void testPlayPhaseTransition() throws IOException {
        d_gameEngine.setCurrentInput("loadmap europe.map");
        d_gameEngine.getPhase().next();
        assertEquals("Startup", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.setCurrentInput("goback");
        d_gameEngine.getPhase().next();
        assertEquals("MainMenu", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.setCurrentInput("loadmap europe.map");
        d_gameEngine.getPhase().next();
        assertEquals("Startup", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.addPlayer("player1", "human");
        d_gameEngine.addPlayer("player2", "human");
        d_gameEngine.setCurrentInput("assigncountries");
        d_gameEngine.getPhase().assignCountries();
        assertEquals("AssignReinforcements", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.getPhase().next(); //moves to second player
        assertEquals("AssignReinforcements", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.getPhase().next(); //moves to issue order phase
        assertEquals("IssueOrders", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.setCurrentInput("execute");
        d_gameEngine.getPhase().next();
        assertEquals("IssueOrders", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.getPhase().next();
        assertEquals("OrderExecution", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.getPhase().next();
        assertEquals("AssignReinforcements", d_gameEngine.getPhase().getClass().getSimpleName());
    }

    /**
     * Test to ensure that there is a valid map and adequate number of players when the game is started
     * @throws IOException If an IO exception occurs
     */
    @Test
    public void testStartUpValidations() throws IOException {
        d_gameEngine.setCurrentInput("loadmap");
        d_gameEngine.getPhase().loadMap();
        assertEquals("MainMenu", d_gameEngine.getPhase().getClass().getSimpleName()); //Stays main menu without a proper map loaded
        d_gameEngine.setCurrentInput("loadmap europe.map");
        d_gameEngine.getPhase().loadMap();
        assertEquals("Startup", d_gameEngine.getPhase().getClass().getSimpleName()); //Goes to startup with proper map
        d_gameEngine.setCurrentInput("assigncountries");
        d_gameEngine.getPhase().assignCountries();
        assertEquals("Startup", d_gameEngine.getPhase().getClass().getSimpleName()); //Stays start up as not enough players added
        d_gameEngine.addPlayer("player1", "human");
        d_gameEngine.addPlayer("player2", "human");
        d_gameEngine.setCurrentInput("assigncountries");
        d_gameEngine.getPhase().assignCountries();
        assertEquals("AssignReinforcements", d_gameEngine.getPhase().getClass().getSimpleName()); //Goes to assignreinforcements after adding two players
    }

    /**
     * Test for checking if game ends when there is only one player left. (They will therefore own all countries)
     */
    @Test
    public void testGameEnds() {
        d_gameEngine.setPhase(new OrderExecution(d_gameEngine));
        d_gameEngine.addPlayer("Ryan", "Human");
        d_gameEngine.get_PlayersList().get(0).get_playerCountries().add(new Country()); //Give the player a country so he does not get removed from the game
        d_gameEngine.getPhase().displayOptions(); //runs execute phase functions - Since there is only one player he will be the winner and game will go to main menu
        assertEquals("MainMenu", d_gameEngine.getPhase().getClass().getSimpleName());
    }
}
