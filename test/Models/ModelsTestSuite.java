package Models;

import Models.Orders.AdvanceOrderTest;
import Models.Orders.AirliftOrderTest;
import Models.Orders.BlockadeOrderTest;
import Models.Orders.BombOrderTest;
import Models.Orders.DeployOrderTest;
import Models.Orders.DiplomacyOrderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrdersTest.class,
        AdvanceOrderTest.class,
        DeployOrderTest.class,
        AirliftOrderTest.class,
        BombOrderTest.class,
        DiplomacyOrderTest.class,
        BlockadeOrderTest.class,
        PlayerTest.class,
        WarMapTest.class,})
public class ModelsTestSuite {
}
