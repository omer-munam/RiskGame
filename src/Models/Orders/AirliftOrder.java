package Models.Orders;

import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;

/**
 * This class represents an Airlift Order in the game.
 * An Airlift Order allows a player to move armies from one country to another using the airlift card.
 */
class AirliftOrder implements Order {
    private final Player d_player;
    private final Country d_sourceCountry;
    private final Country d_targetCountry;
    private final int d_numArmies;

    /**
     * Constructor for the AirliftOrder class.
     *
     * @param player         The player issuing the order.
     * @param sourceCountry  The source country from which armies will be moved.
     * @param targetCountry  The target country to which armies will be moved.
     * @param numArmies      The number of armies to move.
     */
    public AirliftOrder(Player player, Country sourceCountry, Country targetCountry, int numArmies) {
        this.d_player = player;
        this.d_sourceCountry = sourceCountry;
        this.d_targetCountry = targetCountry;
        this.d_numArmies = numArmies;
    }

    /**
     * Executes the Airlift Order.
     * Moves the specified number of armies from the source country to the target country.
     */
    @Override
    public void execute(WarMap warmap) {
        // Check if the player owns the source country and has the airlift card.
        if (d_player.get_playerCountries().contains(d_sourceCountry) &&
                d_player.get_playerCards().contains(Cards.Airlift)) {

            // Move armies from the source country to the target country.
            d_sourceCountry.set_numOfArmies(d_sourceCountry.get_numOfArmies() - d_numArmies);
            d_targetCountry.set_numOfArmies(d_targetCountry.get_numOfArmies() + d_numArmies);
        }
    }

    @Override
    public String toString() {
        return "Airlift order: " + d_player.get_playerName() + " is airlifting " +
                d_numArmies + " armies from " + d_sourceCountry.get_countryName() +
                " to " + d_targetCountry.get_countryName();
    }
}
