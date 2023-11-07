package Models.Orders;

import Models.Country;
import Models.Player;
import Models.WarMap;
import Resources.Cards;

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
     * If the target territory belongs to another player, an attack happens between the two territories.
     */
    @Override
    public void execute(WarMap warMap) {
        // Check if the player owns the source country, if there are enough armies to move,
        // and if the countries are adjacent.
        if (d_player.get_playerCountries().contains(d_sourceCountry) &&
                d_sourceCountry.get_numOfArmies() >= d_numArmies &&
                d_sourceCountry.getNeighbouringCountries().containsKey(d_targetCountry.get_countryID())) {

            if (d_targetCountry.getD_ownerPlayer() == d_player) {
                // The target territory belongs to the current player; move the armies to the target territory.
                d_sourceCountry.set_numOfArmies(d_sourceCountry.get_numOfArmies() - d_numArmies);
                d_targetCountry.set_numOfArmies(d_targetCountry.get_numOfArmies() + d_numArmies);
            } else {
                if(d_targetCountry.getD_ownerPlayer().get_diplomacy_list().contains(d_player.get_playerName())){
                    System.out.println("\n_________________________________________");
                    System.out.println("Can't attack, since Negotiate found");
                    return;
                }
                // The target territory belongs to another player; simulate an attack.
                int defenderArmies = d_targetCountry.get_numOfArmies();
                // Implement your attack logic here. You can use dice rolls, a calculation based on armies, etc.
                // For this example, we'll assume the attacker always wins and takes over the target territory.
                int attackerArmies = d_numArmies;
                if (attackerArmies > defenderArmies) {
                    d_sourceCountry.set_numOfArmies(d_sourceCountry.get_numOfArmies() - d_numArmies);
                    d_targetCountry.set_numOfArmies(attackerArmies - defenderArmies);
                    d_targetCountry.getD_ownerPlayer().get_playerCountries().remove(d_targetCountry);
                    d_targetCountry.setD_ownerPlayer(d_player);
                    Cards.playerAcquiredTerritory(d_player);
                } else {
                    // The attacker loses, and the target territory remains with its owner.
                    d_sourceCountry.set_numOfArmies(d_sourceCountry.get_numOfArmies() - d_numArmies);
                    d_targetCountry.set_numOfArmies(d_targetCountry.get_numOfArmies() - d_numArmies);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Advance order: " + d_player.get_playerName() + " is advancing " +
                d_numArmies + " armies from " + d_sourceCountry.get_countryName() +
                " to " + d_targetCountry.get_countryName();
    }
}
