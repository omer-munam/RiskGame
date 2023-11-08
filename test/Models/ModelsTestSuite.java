package Models;

import Models.Orders.BlockadeOrderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrdersTest.class,
        BlockadeOrderTest.class,
        PlayerTest.class,
        WarMapTest.class,})
public class ModelsTestSuite {
}
