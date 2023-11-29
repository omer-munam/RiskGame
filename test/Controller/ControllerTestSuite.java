package Controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for the Controller module.
 * Includes tests for GameEngine and MainGameLoop.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({GameEngineTest.class,
        MainGameLoopTest.class,
})
public class ControllerTestSuite {
}
