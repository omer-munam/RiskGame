package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;
import Resources.Commands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HumanStrategyTest {

    private HumanStrategy humanStrategy;
    private Player player;
    private GameEngine gameEngine;
    private WarMap warMap;

    private Map<Integer, Country> countries;
    @BeforeEach
    public void setUp() {
        player = new Player("John Doe");
        humanStrategy = new HumanStrategy(player);
        gameEngine = GameEngine.getInstance();
        countries = new HashMap<>();
        warMap = new WarMap();
    }

    @Test
    public void testDeployOrderCommandExecution() {
        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(1);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        warMap.set_countries((HashMap<Integer, Country>) countries);
        player.set_playerCountries(new ArrayList<>(countries.values()));
        GameEngine.getInstance().getCurrentPlayer().set_playerCountries(Arrays.asList(destCountry1, destCountry2));

        GameEngine.getInstance().getCurrentPlayer().set_numOfReinforcements(5);
        GameEngine.getInstance().setCurrentInput("deploy 1 3");
        player.setD_behaviourStrategy(humanStrategy);
        player.issue_order();
        assertEquals(1, player.get_playerOrder().size());
    }

    @Test
    public void testAdvanceOrderCommandExecution() {
        Country destCountry1 = new Country(1, "DestCountry1", 1);
        destCountry1.set_numOfArmies(10);
        destCountry1.setD_ownerPlayer(player);
        countries.put(1, destCountry1);

        Country destCountry2 = new Country(2, "DestCountry2", 2);
        destCountry2.set_numOfArmies(4);
        destCountry2.setD_ownerPlayer(player);
        countries.put(2, destCountry2);

        Country destCountry3 = new Country(3, "DestCountry3", 1);
        destCountry3.set_numOfArmies(2);
        destCountry3.setD_ownerPlayer(player);
        countries.put(3, destCountry3);

        destCountry1.addNeighbouringCountry(destCountry2);
        destCountry1.addNeighbouringCountry((destCountry3));
        destCountry2.addNeighbouringCountry((destCountry3));
        destCountry2.addNeighbouringCountry((destCountry1));
        destCountry3.addNeighbouringCountry((destCountry1));
        destCountry3.addNeighbouringCountry((destCountry2));

        player.set_playerCountries(Arrays.asList(destCountry1, destCountry2, destCountry3));
        player.setD_behaviourStrategy(humanStrategy);

        GameEngine.getInstance().setCurrentInput("advance 1 2 3");
        player.issue_order();
        assertEquals(1, player.get_playerOrder().size());
    }

    @Test
    public void testBombOrderCommandExecution() {
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Bomb);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        player.set_playerCountries(List.of(countryA));
        player.setD_behaviourStrategy(humanStrategy);
        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that a BombOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());

    }

    /*
    @Test
    public void testBlockadeOrderCommandExecution() {
        WarMap warMap = new WarMap();
        GameEngine.getInstance().set_currentMap(warMap);
        GameEngine.getInstance().setCurrentInput("blockade 1");
        humanStrategy.issue_order();
        assertEquals(1, player.get_playerOrder().size());
        assertEquals(BlockadeOrder.class, player.get_playerOrder().get(0).getClass());
        assertEquals(1, player.get_playerOrder().get(0).getTargetCountryID());
    }

    @Test
    public void testAirliftOrderCommandExecution() {
        GameEngine.getInstance().setCurrentInput("airlift 1 2 3");
        humanStrategy.issue_order();
        assertEquals(1, player.get_playerOrder().size());
        assertEquals(AirliftOrder.class, player.get_playerOrder().get(0).getClass());
        assertEquals(1, player.get_playerOrder().get(0).getSourceCountryID());
        assertEquals(2, player.get_playerOrder().get(0).getTargetCountryID());
        assertEquals(3, player.get_playerOrder().get(0).getNumOfArmies());
    }

    @Test
    public void testDiplomacyOrderCommandExecution() {
        WarMap warMap = new WarMap();
        GameEngine.getInstance().set_currentMap(warMap);
        GameEngine.getInstance().setCurrentInput("negotiate John");
        humanStrategy.issue_order();
        assertEquals(1, player.get_playerOrder().size());
        assertEquals(BehaviourStrategyBase.class, player.get_playerOrder().get(0).getClass());
        assertEquals("John", player.get_playerOrder().get(0).getTargetPlayer());
    }*/

    @Test
    public void testInvalidCommand() {
        GameEngine.getInstance().setCurrentInput("invalidCommand");
        humanStrategy.issue_order();
        assertEquals(0, player.get_playerOrder().size());
    }
}