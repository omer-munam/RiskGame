package Phases;

import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for functions concerning Edit Phase
 */
public class EditPhaseTest {
    /**
     * Instance of the game engine to use for tests
     */
    private GameEngine d_gameEngine;

    @Before
    public void setUp() {
        d_gameEngine = GameEngine.getInstance();
        d_gameEngine.setPhase(new MainMenu(d_gameEngine));
        d_gameEngine.get_PlayersList().clear();

    }

    /**
     * Test for ensuring proper phase transitions during the edit phase
     *
     * @throws IOException when an IO exception occurs
     */
    @Test
    public void testEditPhaseTransition() throws IOException {
        d_gameEngine.setCurrentInput("editmap");
        d_gameEngine.getPhase().next();
        assertEquals("Preload", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.setCurrentInput("quit");
        d_gameEngine.getPhase().next();
        assertEquals("MainMenu", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.setCurrentInput("editmap");
        d_gameEngine.getPhase().next();
        d_gameEngine.getPhase().next();
        assertEquals("Postload", d_gameEngine.getPhase().getClass().getSimpleName());
        d_gameEngine.getPhase().next();
        assertEquals("MainMenu", d_gameEngine.getPhase().getClass().getSimpleName());


    }
}
