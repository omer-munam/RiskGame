package Phases;

import Controller.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditPhaseTest {

    private GameEngine gameEngine;

    @BeforeEach
    public void setUp() {
        gameEngine = GameEngine.getInstance();


    }

    @Test
    public void testEditPhaseTransition() throws IOException {
        gameEngine.setCurrentInput("editmap");
        gameEngine.getPhase().next();
        assertEquals("Preload", gameEngine.getPhase().getClass().getSimpleName());
        gameEngine.setCurrentInput("quit");
        gameEngine.getPhase().next();
        assertEquals("MainMenu", gameEngine.getPhase().getClass().getSimpleName());
        gameEngine.setCurrentInput("editmap");
        gameEngine.getPhase().next();
        gameEngine.getPhase().next();
        assertEquals("Postload", gameEngine.getPhase().getClass().getSimpleName());
        gameEngine.getPhase().next();
        assertEquals("MainMenu", gameEngine.getPhase().getClass().getSimpleName());


    }
}
