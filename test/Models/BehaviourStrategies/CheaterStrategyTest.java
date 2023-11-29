package Models.BehaviourStrategies;
import Models.Player;
import Models.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheaterStrategyTest {

    private Player player;
    private CheaterStrategy cheaterStrategy;

    @Before
    public void setup() {
        player = new Player("TestPlayer");
        cheaterStrategy = new CheaterStrategy(player);
    }

}