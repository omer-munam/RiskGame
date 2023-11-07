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
    Advance,
    Diplomacy;

    /**
     * List containing players who have acquired territories.
     */
    private static List<Player> playersAcquiringTerritories = new ArrayList<>();
    /**
     * Call this method whenever player acquires new territory.
     *
     * @param p_player
     */
    public static void playerAcquiredTerritory(Player p_player) {
        if (!playersAcquiringTerritories.contains(p_player))
            playersAcquiringTerritories.add(p_player);
    }

    /**
     * Assign a random card to all players in list.
     */
    public static void assignRandomCardsToPlayers() {
        for (Player player : playersAcquiringTerritories){
            Random random = new Random();
            player.get_playerCards().add(values()[random.nextInt(values().length)]);
        }
    }
}


