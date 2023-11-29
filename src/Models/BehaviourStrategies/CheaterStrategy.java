package Models.BehaviourStrategies;

import Models.Player;
import Models.Country;


/**
 * Represents the cheater behavior strategy for a player.
 * The cheater conquers all immediate neighboring enemy countries and doubles the number of armies on its countries
 * that have enemy neighbors.
 *
 * @author Leila Mousavi
 */
public class CheaterStrategy extends BehaviourStrategyBase {
    public CheaterStrategy(Player p_player){
        super(p_player);
    }
    @Override
    public void issue_order() {
        for (Country country : d_player.get_playerCountries()) {
            // Conquer all immediate neighboring enemy countries
            for (Country neighbor : country.getNeighbouringCountries().values()) {
                if (neighbor.getD_ownerPlayer() != d_player) {
                    // Conquer the enemy country
                    neighbor.setD_ownerPlayer(d_player);
                    // Double the armies on the conquered country
                    int currentArmies = neighbor.get_numOfArmies();
                    neighbor.set_numOfArmies(currentArmies * 2);
                }
            }

            // Double the armies on countries with enemy neighbors
            if (hasEnemyNeighbors(country)) {
                int currentArmies = country.get_numOfArmies();
                country.set_numOfArmies(currentArmies * 2);
            }
        }
    }

    /**
     * Check if a country has enemy neighbors
     *
     * @param country The country to check
     * @return True if the country has enemy neighbors, false otherwise
     */
    private boolean hasEnemyNeighbors(Country country) {
        for (Country neighbor : country.getNeighbouringCountries().values()) {
            if (neighbor.getD_ownerPlayer() != d_player) {
                return true;
            }
        }
        return false;
    }
}