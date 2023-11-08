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

    /**
     * Checks if the orders are taken correctly with valid Input
     */
    @Test
    public void testBlockadeIssueOrderValidInput() {
        Country country1 = new Country(1, "Country1", 1);
        player.get_playerCountries().add(country1);
        player.get_playerCards().add(Cards.Blockade);

        Country country2 = new Country(2, "Country2", 1);
        player.get_playerCountries().add(country2);
        player.get_playerCards().add(Cards.Blockade);

        String[] commandTokens1 = {"blockade", "1"};
        player.blockade_issue_order(commandTokens1, warMap);

        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) != null);
        assertEquals(1,player.get_playerCards().size());

        String[] commandTokens2 = {"blockade", "2"};
        player.blockade_issue_order(commandTokens2, warMap);

        assertEquals(2, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(1) != null);
        assertFalse(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(0,player.get_playerCards().size());
    }

    /**
     * Checks if no orders are added to orderlist and
     * Card is not removed with Invalid input
     */
    @Test
    public void testBlockadeIssueOrderInvalidCountry() {
        // Prepare player's countries and cards
        player.get_playerCards().add(Cards.Blockade);

        String[] commandTokens1 = {"blockade", "2"};
        player.blockade_issue_order(commandTokens1, warMap);

        assertEquals(0, player.get_playerOrder().size());
        assertTrue(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(1,player.get_playerCards().size());

        player.get_playerCards().add(Cards.Blockade);
        String[] commandTokens2 = {"blockade", "3"};
        player.blockade_issue_order(commandTokens2, warMap);

        assertEquals(0, player.get_playerOrder().size());
        assertTrue(player.get_playerCards().contains(Cards.Blockade));
        assertEquals(2,player.get_playerCards().size());
    }

    /**
     * Checks if no orders are added to list
     * if there is no blockade card in the player card list
     */
    @Test
    public void testBlockadeIssueOrderNoBlockadeCard() {
        Country country1 = new Country(1, "Country1", 1);
        player.get_playerCountries().add(country1);

        Country country2 = new Country(2, "Country2", 1);
        player.get_playerCountries().add(country2);

        String[] commandTokens1 = {"blockade", "1"};
        player.blockade_issue_order(commandTokens1, warMap);

        assertEquals(0, player.get_playerOrder().size());
        assertFalse(player.get_playerCards().contains(Cards.Blockade));

        String[] commandTokens2 = {"blockade", "2"};
        player.blockade_issue_order(commandTokens2, warMap);

        assertEquals(0, player.get_playerOrder().size());
        assertFalse(player.get_playerCards().contains(Cards.Blockade));
    }

    @Test
    public void testBlockadeCommandExecution() {
        // Create a test scenario where the player has a Blockade card and valid input.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Blockade);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA", 1));
        map.addCountry(new Country(2, "CountryB", 1));
        map.addCountry(new Country(3, "CountryC", 1));

        List<Country> playerCountries = new ArrayList<>();
        playerCountries.add(new Country(1, "CountryA", 1));
        player.set_playerCountries(playerCountries);


        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("blockade 1");

        // Call the method you want to test.
        player.issue_order();

        // Assert the expected outcome.
        assertEquals(0, player.get_playerCards().size());
        assertEquals(1, player.get_playerOrder().size()); // The order should be issued successfully.
    }

    @Test
    public void testAdvanceCommandExecution() {
        // Create a test scenario where the player has valid input and neighboring countries.
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        // Make CountryA and CountryB neighbors.
        countryA.addNeighbouringCountry(countryB);

        player.set_playerCountries(Arrays.asList(countryA, countryB));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("advance 1 2 3");

        // Call the method you want to test.
        player.issue_order();

        // Assert the expected outcome.
        assertEquals(1, player.get_playerOrder().size());
    }


    @Test
    public void testBombCommandExecution() {
        // Create a test scenario where the player has the Bomb card, and the input is valid.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Bomb);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        Country countryB = new Country(2, "CountryB", 1);
        map.addCountry(countryA);
        map.addCountry(countryB);

        player.set_playerCountries(List.of(countryA));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that a BombOrder was created and added to the list of orders.
        assertEquals(1, player.get_playerOrder().size());
        assertTrue(player.get_playerOrder().get(0) instanceof BombOrder);

        // Ensure that the Bomb card is removed from the player's cards.
        assertFalse(player.get_playerCards().contains(Cards.Bomb));
    }

    @Test
    public void testBombCommandExecutionWithInvalidCountry() {
        // Create a test scenario where the player has the Bomb card, but the target country is invalid.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Bomb);
        player.set_playerCards(cards);
        WarMap map = new WarMap();
        GameEngine.getInstance().set_currentMap(map);
        // Simulate an invalid target country by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no BombOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }

    @Test
    public void testBombCommandExecutionWithoutBombCard() {
        // Create a test scenario where the player does not have the Bomb card.
        WarMap map = new WarMap();
        Country countryA = new Country(1, "CountryA", 1);
        map.addCountry(countryA);

        player.set_playerCountries(List.of(countryA));

        // Simulate a valid command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("bomb 1");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no BombOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());
    }

    @Test
    public void testDiplomacyCommandExecution() {
        // Create a test scenario where the player has the Diplomacy card, and the input is valid.
        player.set_playerCards(List.of(Cards.Diplomacy));
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA", 1));
        map.addCountry(new Country(2, "CountryB", 1));
        player.set_playerCountries(Arrays.asList(new Country(1, "CountryA", 1)
                , new Country(2, "CountryB", 1)));

        // Create another player in the game.
        Player otherPlayer = new Player("Player2");

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(otherPlayer));

        // Simulate a valid diplomacy command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that the target player name is added to the diplomacy list.
        assertTrue(player.get_diplomacy_list().contains("Player2"));

    }

    @Test
    public void testDiplomacyCommandExecutionWithoutDiplomacyCard() {
        // Create a test scenario where the player does not have the Diplomacy card.
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA"));
        player.set_playerCountries(List.of(new Country(1, "CountryA", 1)));

        // Create another player in the game.
        Player otherPlayer = new Player("Player2");

        // Attach the players to the GameEngine.
        GameEngine.getInstance().set_PlayersList(List.of(otherPlayer));

        // Simulate a valid diplomacy command by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no DiplomacyOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());

        // Assert that the target player name is not added to the diplomacy list.
        assertFalse(player.get_diplomacy_list().contains("Player2"));
    }

    @Test
    public void testDiplomacyCommandExecutionWithInvalidTargetPlayer() {
        // Create a test scenario where the player has the Diplomacy card, but the target player does not exist.
        Player player = new Player("Player1");
        player.set_playerCards(List.of(Cards.Diplomacy));
        WarMap map = new WarMap();
        map.addCountry(new Country(1, "CountryA"));
        player.set_playerCountries(List.of(new Country(1, "CountryA", 1)));

        // Simulate an invalid target player by setting the current input in GameEngine.
        GameEngine.getInstance().setCurrentInput("diplomacy Player2");

        // Call the method you want to test.
        player.issue_order();

        // Assert that no DiplomacyOrder was created and added to the list of orders.
        assertEquals(0, player.get_playerOrder().size());

        // Assert that the target player name is not added to the diplomacy list.
        assertFalse(player.get_diplomacy_list().contains("Player2"));
    }


    @Test
    public void testAirliftCommandExecution() {
        // Create a test scenario where the player has the Airlift card and valid input.
        List<Cards> cards = new ArrayList<>();
        cards.add(Cards.Airlift);
        player.set_playerCards(cards);
        WarMap map = new WarMap();

        // Create source and target countries.
        Country sourceCountry = new Country(1, "SourceCountry", 1);
        Country targetCountry = new Country(2, "TargetCountry", 1);
        player.set_playerCountries(Arrays.asList(sourceCountry, targetCountry));

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


}

