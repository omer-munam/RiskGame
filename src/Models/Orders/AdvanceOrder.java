package Models.Orders;

import Models.Country;
import Models.Player;
import Models.WarMap;

/**
 * This class represents an Advance Order in the game.
 * An Advance Order allows a player to move armies from one country to another.
 */
public class AdvanceOrder implements Order {
    private final Player d_player;
    private final Country d_sourceCountry;
    private final Country d_targetCountry;
    private final int d_numArmies;

    /**
     * Constructor for the AdvanceOrder class.
     *
     * @param player         The player issuing the order.
     * @param sourceCountry  The source country from which armies will be moved.
     * @param targetCountry  The target country to which armies will be moved.
     * @param numArmies      The number of armies to move.
     */
    public AdvanceOrder(Player player, Country sourceCountry, Country targetCountry, int numArmies) {
        this.d_player = player;
        this.d_sourceCountry = sourceCountry;
        this.d_targetCountry = targetCountry;
        this.d_numArmies = numArmies;
    }

    /**
     * Executes the Advance Order.
     * Moves the specified number of armies from the source country to the target country.
     */
    @Override
    public void execute(WarMap warMap) {
        // Check if the player owns the source country, if there are enough armies to move,
        // and if the countries are adjacent.
        if (d_player.get_playerCountries().contains(d_sourceCountry) &&
                d_sourceCountry.get_numOfArmies() >= d_numArmies &&
                d_sourceCountry.getNeighbouringCountries().containsKey(d_targetCountry.get_countryID())) {

            // Move armies from the source country to the target country.
            d_sourceCountry.set_numOfArmies(d_sourceCountry.get_numOfArmies() - d_numArmies);
            d_targetCountry.set_numOfArmies(d_targetCountry.get_numOfArmies() + d_numArmies);
        }
    }

    @Override
    public String toString() {
        return "Advance order: " + d_player.get_playerName() + " is advancing " +
                d_numArmies + " armies from " + d_sourceCountry.get_countryName() +
                " to " + d_targetCountry.get_countryName();
    }
}
