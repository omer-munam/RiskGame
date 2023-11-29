package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Orders.AirliftOrder;
import Models.Player;
import Models.WarMap;
import Resources.Cards;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HumanStrategyTest {

    private HumanStrategy humanStrategy;
    private Player player;
    private GameEngine gameEngine;
    private WarMap warMap;

    private Map<Integer, Country> countries;
    @Before
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
        GameEngine.getInstance().set_currentMap(map);
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that a BombOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());

    }

    @Test
    public void testBlockadeOrderCommandExecution() {
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Blockade);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        player.set_playerCountries(List.of(countryA, countryB));
        player.setD_behaviourStrategy(humanStrategy);
        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().set_currentMap(map);
        GameEngine.getInstance().setCurrentInput("blockade 2");

        // Call the method you want to test.
        player.issue_order();

        assertEquals(1, player.get_playerOrder().size());
    }

    @Test
    public void testAirliftOrderCommandExecution() {
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Airlift);
        player.set_playerCards(cards);
        WarMap map = new WarMap();

        // Create source and target countries.
        Country sourceCountry = new Country(1, "SourceCountry", 1);
        Country targetCountry = new Country(2, "TargetCountry", 1);
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

        player.setD_behaviourStrategy(humanStrategy);
        GameEngine.getInstance().set_currentMap(map);

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(player));

        // Simulate a valid airlift command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("airlift 1 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert that an AirliftOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) instanceof AirliftOrder);

        // Assert that the Airlift card is removed from the player's cards.
        assertFalse(player.get_playerCards().contains(Cards.Airlift));
    }

    @Test
    public void testDiplomacyOrderCommandExecution() {
        player.set_playerCards(List.of(Cards.Diplomacy));
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA", 1));
        map.addCountry(new Country(2, "CountryB", 1));
        player.set_playerCountries(Arrays.asList(new Country(1, "CountryA", 1)
                , new Country(2, "CountryB", 1)));

        // Create another player in the game.
        Player otherPlayer = new Player("Player2");
        player.setD_behaviourStrategy(humanStrategy);
        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(otherPlayer));
        GameEngine.getInstance().set_currentMap(map);
        // Simulate a valid diplomacy command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that the target player name is added to the diplomacy list.
        assertTrue(player.get_diplomacy_list().contains("Player2"));


    }

    @Test
    public void testInvalidCommand() {
        GameEngine.getInstance().setCurrentInput("invalidCommand");
        humanStrategy.issue_order();
        assertEquals(0, player.get_playerOrder().size());
    }
}