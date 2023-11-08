package Models;

import java.util.Arrays;
import Controller.GameEngine;
import Models.Orders.BombOrder;
import Models.Orders.DeployOrder;
import Models.Orders.AirliftOrder;
import Models.Orders.Order;
import Phases.AssignReinforcements;
import Resources.Cards;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for functions concerning Player orders
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerTest {

    /**
     * The player instance required to run the test case.
     */
    private Player player;
    /**
     * Initializing the player instance before each test.
     */
    private GameEngine gameEngine;
    /**
     * The Instance of warmap.
     */
    private WarMap warMap;

    @Before
    public void setUp() {
        gameEngine = GameEngine.getInstance();
        player = new Player("John Doe");
        warMap = new WarMap();
    }

    /**
     * Testing if the next order returns null when no orders are issued.
     */
    @Test
    public void testNextOrderEmptyList() {
        DeployOrder nextOrder = (DeployOrder) player.next_order();

        assertNull(nextOrder);
    }

    /**
     * Testing the next orders and the size of the order list upon adding.
     */
    @Test
    public void testNextOrder() {
        DeployOrder order1 = new DeployOrder(3, 1);
        DeployOrder order2 = new DeployOrder(2, 2);
        DeployOrder order3 = new DeployOrder(4, 3);

        player.get_playerOrder().add(order1);
        player.get_playerOrder().add(order2);
        player.get_playerOrder().add(order3);

        DeployOrder nextOrder = (DeployOrder) player.next_order();

        assertEquals(order1, nextOrder);
        assertEquals(2, player.get_playerOrder().size());
        assertFalse(player.get_playerOrder().contains(order1));
    }













}

