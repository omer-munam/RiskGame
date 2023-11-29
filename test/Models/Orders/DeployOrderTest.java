package Models.Orders;

import Controller.GameEngine;
import Models.BehaviourStrategies.HumanStrategy;
import Models.Continent;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.AssignReinforcements;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test for functions concerning Deploy orders
 */
public class DeployOrderTest {
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
        player.setD_behaviourStrategy(new HumanStrategy(player));
        warMap = new WarMap();
    }


    /**
     * Test if the country deployed to is invalid.
     */
    @Test
    public void testInvalidCountry() {
        player.set_numOfReinforcements(5);
        Country count1 = new Country(1, "Country1", 1);
        Country count2 = new Country(2, "Country2", 1);
        Country count3 = new Country(3, "Country3", 2);
        List<Country> playerCountries = new ArrayList<>();
        playerCountries.add(count1);
        playerCountries.add(count2);
        playerCountries.add(count3);
        player.set_playerCountries(playerCountries);
        WarMap warMap = new WarMap();
        Continent cont1 = new Continent(1, "Cont1", 0);
        Continent cont2 = new Continent(2, "Cont2", 0);
        warMap.addContinent(cont1);
        warMap.addContinent(cont2);
        warMap.addCountry(count1);
        warMap.addCountry(count2);
        warMap.addCountry(count3);
        gameEngine.set_currentMap(warMap);
        gameEngine.setCurrentPlayer(player);
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player);
        gameEngine.set_PlayersList(listOfPlayers);
        gameEngine.setPhase(new AssignReinforcements(gameEngine));


        gameEngine.setCurrentInput("deploy 1 3");
        gameEngine.getPhase().deploy();
        gameEngine.setCurrentInput("deploy 4 2");
        gameEngine.getPhase().deploy();
        gameEngine.setCurrentInput("deploy 3 2");
        gameEngine.getPhase().deploy();

        List<Order> playerOrders = player.get_playerOrder();
        assertEquals(2, playerOrders.size());

        DeployOrder order1 = (DeployOrder) playerOrders.get(0);
        assertEquals(3, order1.getNumOfArmies());
        assertEquals(1, order1.getDestCountryID());

        DeployOrder order2 = (DeployOrder) playerOrders.get(1);
        assertEquals(2, order2.getNumOfArmies());
        assertEquals(3, order2.getDestCountryID());
    }


    /**
     * Deploying more number of armies then there are reinforcements available.
     */
    @Test
    public void testCannotDeployMoreArmiesThanReinforcements() {
        // Set the player's initial reinforcement pool to 5
        player.set_numOfReinforcements(5);
        Country count1 = new Country(1, "Country1", 1);
        Country count2 = new Country(2, "Country2", 1);
        Country count3 = new Country(3, "Country3", 2);
        List<Country> playerCountries = new ArrayList<>();
        playerCountries.add(count1);
        playerCountries.add(count2);
        playerCountries.add(count3);
        player.set_playerCountries(playerCountries);
        WarMap warMap = new WarMap();
        Continent cont1 = new Continent(1, "Cont1", 0);
        Continent cont2 = new Continent(2, "Cont2", 0);
        warMap.addContinent(cont1);
        warMap.addContinent(cont2);
        warMap.addCountry(count1);
        warMap.addCountry(count2);
        warMap.addCountry(count3);
        gameEngine.set_currentMap(warMap);
        gameEngine.setCurrentPlayer(player);
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player);
        gameEngine.set_PlayersList(listOfPlayers);
        // Attempt to issue a deploy order with more armies than available reinforcements
        // Deploying 10 armies with only 5 available
        gameEngine.setPhase(new AssignReinforcements(gameEngine));
        gameEngine.setCurrentInput("deploy 1 10");
        gameEngine.getPhase().deploy();
        gameEngine.setCurrentInput("deploy 2 3");
        gameEngine.getPhase().deploy();
        gameEngine.setCurrentInput("deploy 3 2");
        gameEngine.getPhase().deploy();
        // Ensure that only valid orders were added to the player's order list
        assertEquals(2, player.get_playerOrder().size());

        // Ensure that the player's available reinforcements remain unchanged
        assertEquals(Integer.valueOf(0), player.get_numOfReinforcements());
    }

}
