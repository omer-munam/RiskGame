import Controller.GameEngineTest;
import Controller.MainGameLoopTest;
import Models.Orders.BlockadeOrderTest;
import Models.OrdersTest;
import Models.PlayerTest;
import Models.WarMapTest;
import Phases.EditPhaseTest;
import Phases.PlayPhaseTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({GameEngineTest.class,
        MainGameLoopTest.class,
        OrdersTest.class,
        BlockadeOrderTest.class,
        PlayerTest.class,
        WarMapTest.class,
        EditPhaseTest.class,
        PlayPhaseTest.class})
public class FullTestSuite {
}
