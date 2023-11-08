package Resources;

import Models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Enum class containing all possible cards and the logic of assigning random cards.
 *
 * @author Omer Munam
 */
public enum Cards {
    Bomb,
    Blockade,
    Airlift,
    Diplomacy;

    /**
     * List containing players who have acquired territories.
     */
    private static final List<Player> playersAcquiringTerritories = new ArrayList<>();

    /**
     * Call this method whenever player acquires new territory.
     *
     * @param p_player the player which acquired a territory
     */
    public static void playerAcquiredTerritory(Player p_player) {
        if (!playersAcquiringTerritories.contains(p_player))
            playersAcquiringTerritories.add(p_player);
    }

    /**
     * A function to reset the list of players who have acquired territories.
     */
    public static void clearPlayerAcquiredTerritory() {
        playersAcquiringTerritories.clear();
    }

    /**
     * Assign a random card to all players in list.
     */
    public static void assignRandomCardsToPlayers() {
        for (Player player : playersAcquiringTerritories) {
            System.out.println("______________________________________________");
            Random random = new Random();
            Cards card = values()[random.nextInt(values().length)];
            player.get_playerCards().add(card);
            System.out.println("Player " + player.get_playerName() + " acquired card: " + card);
            System.out.println("______________________________________________");
        }
    }
}


