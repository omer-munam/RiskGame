package Resources;

import Models.Player;

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
     * Assign a random card to player. Call this method whenever player acquires new territory.
     *
     * @param p_player
     */
    public static void assignRandomCardToPlayer(Player p_player) {
        Random random = new Random();
        p_player.get_playerCards().add(values()[random.nextInt(values().length)]);
    }
}


