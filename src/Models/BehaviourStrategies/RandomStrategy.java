package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.AssignReinforcements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This class describes information about random strategy and the order that were issued based on logic.
 * Random strategy represents logic that deploys on a random country,
 * attacks random neighboring countries,
 * and moves armies randomly between its countries.
 *
 * @author Omer Munam
 */
public class RandomStrategy extends BehaviourStrategyBase {

    /**
     * Constructor for the RandomStrategy class.
     *
     * @param p_player  The player associated with this strategy.
     */
    public RandomStrategy(Player p_player){
        super(p_player);
    }

    /**
     * Issues a random order based on the current game phase.
     * If the current phase is AssignReinforcements, a deploy order is created.
     * Otherwise, both attack and advance orders are created randomly.
     */
    @Override
    public void issue_order() {
        if (d_player.get_playerCountries().isEmpty())
            return;
        if (GameEngine.getInstance().getPhase() instanceof AssignReinforcements)
            createDeployOrderCommand();
        else{
            createAttackCommand();
            createAdvanceOrderCommand();
        }
    }

    /**
     * Creates an attack order by randomly selecting a source country and a neighboring target country.
     * The number of armies to move is also determined randomly.
     */
    private void createAttackCommand() {
        Random l_random = new Random();
        Country l_sourceCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));
        HashMap<Integer, Country> l_neighbouringCountries = l_sourceCountry.getNeighbouringCountries();
        Country l_targetCountry = null;
        for (Country l_neighbouringCountry : l_neighbouringCountries.values()){
            if (!d_player.get_playerCountries().contains((l_neighbouringCountry))){
                l_targetCountry = l_neighbouringCountry;
            }
        }
        int l_armiesToMove;
        if (l_targetCountry == null){
            System.out.println("No enemy neighbouring countries of random country found.");
            return;
        }
        if (l_sourceCountry.get_numOfArmies() > 1) {
            l_armiesToMove = l_random.nextInt(l_sourceCountry.get_numOfArmies() - 1) + 1;
        } else {
            l_armiesToMove = 1;
        }

        String l_command = String.format("advance %d %d %d", l_sourceCountry.get_countryID(), l_targetCountry.get_countryID(), l_armiesToMove);
        String[] commandTokens = l_command.split(" ");
        advance_issue_order(commandTokens);
        System.out.println(Arrays.toString(commandTokens));
    }

    /**
     * Creates an advance order by randomly selecting a source country and a neighboring target country.
     * The number of armies to move is also determined randomly.
     */
    private void createAdvanceOrderCommand() {
        if (d_player.get_playerCountries().size() < 2){
            System.out.println("Cannot issue Advance order. Less than 2 territories owned.");
            return;
        }
        Random l_random = new Random();
        Country l_sourceCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));

        HashMap<Integer, Country> l_neighbouringCountries = l_sourceCountry.getNeighbouringCountries();
        Country l_targetCountry = null;
        for (Country l_neighbouringCountry : l_neighbouringCountries.values()){
            if (d_player.get_playerCountries().contains((l_neighbouringCountry))){
                l_targetCountry = l_neighbouringCountry;
            }
        }
        int l_armiesToMove = 0;
        if (l_targetCountry == null){
            System.out.println("No neighbouring countries of random country found.");
            return;
        }
        if (l_sourceCountry.get_numOfArmies() > 1) {
            l_armiesToMove = l_random.nextInt(l_sourceCountry.get_numOfArmies() - 1) + 1;
        } else {
            l_armiesToMove = 1;
        }

        String l_command = String.format("advance %d %d %d", l_sourceCountry.get_countryID(), l_targetCountry.get_countryID(), l_armiesToMove);
        String[] commandTokens = l_command.split(" ");
        advance_issue_order(commandTokens);
        System.out.println(Arrays.toString(commandTokens));
    }

    /**
     * Creates a deploy order by randomly selecting a target country and deploying reinforcements.
     */
    private void createDeployOrderCommand() {
        Random l_random = new Random();
        Country l_targetCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));
        String l_command = String.format("deploy %d %d", l_targetCountry.get_countryID(), d_player.get_numOfReinforcements());
        String[] commandTokens = l_command.split(" ");
        deploy_issue_order(commandTokens);
        System.out.println(Arrays.toString(commandTokens));
    }
}
