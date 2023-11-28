package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Phases.AssignReinforcements;

import java.util.ArrayList;
import java.util.List;

/**
 * This class describes information about aggressive strategy and the order that were issued based on logic.
 * Aggressive strategy represents logic that that focuses on centralization of forces and then attack
 *
 * @author Omer Munam
 */
public class AggressiveStrategy extends BehaviourStrategyBase {

    /**
     * Constructor for the AggressiveStrategy class.
     *
     * @param p_player  The player associated with this strategy.
     */
    public AggressiveStrategy(Player p_player){
        super(p_player);
    }

    private Country d_strongestCountry;

    /**
     * Deploy on strongest.
     * Attack from strongest.
     * Advance to strongest.
     */
    @Override
    public void issue_order() {
        if (GameEngine.getInstance().getPhase() instanceof AssignReinforcements)
            d_strongestCountry = createDeployOrderCommand();
        else{
            if (d_strongestCountry == null)
            {
                System.out.println("No countries owned..");
                return;
            }
            createAttackCommand(d_strongestCountry);
            createAdvanceCommand(d_strongestCountry);
        }
    }

    /**
     * Advance armies to the strongest country from neighbouring.
     * @param l_strongestCountry strongest country
     */
    private void createAdvanceCommand(Country l_strongestCountry) {
        List<Country> l_ownCountries = new ArrayList<>();
        for (Country l_neighbouringCountry : l_strongestCountry.getNeighbouringCountries().values()){
            if (d_player.get_playerCountries().contains((l_neighbouringCountry))){
                l_ownCountries.add(l_neighbouringCountry);
            }
        }

        if (l_ownCountries.isEmpty()) {
            System.out.println("No countries owned around the strongest one...");
            return;
        }

        for (Country l_ownCountry : l_ownCountries){
            if (l_ownCountry.get_numOfArmies() < 1)
                continue;
            String l_command = String.format("advance %d %d %d", l_ownCountry.get_countryID(), l_strongestCountry.get_countryID(), l_ownCountry.get_numOfArmies());
            String[] commandTokens = l_command.split(" ");
            advance_issue_order(commandTokens);
        }
    }

    /**
     * Attack from the strongest country to any neighbouring...
     * @param l_strongestCountry strongest country
     */
    private void createAttackCommand(Country l_strongestCountry) {

        Country l_enemyCountry = null;
        for (Country l_neighbouringCountry : l_strongestCountry.getNeighbouringCountries().values()){
            if (!d_player.get_playerCountries().contains((l_neighbouringCountry))){
                l_enemyCountry = l_neighbouringCountry;
                break;
            }
        }
        if (l_enemyCountry == null){
            System.out.println("Strongest Country does not have any neighbouring enemies... Cannot issue attack...");
            return;
        }
        String l_command = String.format("advance %d %d %d", l_strongestCountry.get_countryID(), l_enemyCountry.get_countryID(), l_strongestCountry.get_numOfArmies() - 1);
        String[] commandTokens = l_command.split(" ");
        advance_issue_order(commandTokens);
    }

    /**
     * Deploy all reinforcements to the strongest country owned.
     * @return strongest countryID
     */
    public Country createDeployOrderCommand(){
        Country l_strongestCountry = null;
        int l_maxValue = -1;

        for (Country l_country : d_player.get_playerCountries()) {
            if (l_country.get_numOfArmies() > l_maxValue) {
                l_maxValue = l_country.get_numOfArmies();
                l_strongestCountry = l_country;
            }
        }

        if (l_strongestCountry == null){
            System.out.println("No countries owned..");
            return null;
        }

        String l_command = String.format("deploy %d %d", l_strongestCountry.get_countryID(), d_player.get_numOfReinforcements());
        String[] commandTokens = l_command.split(" ");
        deploy_issue_order(commandTokens);
        return l_strongestCountry;
    }
}
