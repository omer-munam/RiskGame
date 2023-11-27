package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;
import Phases.AssignReinforcements;

import java.util.HashMap;
import java.util.Random;

public class RandomStrategy extends BehaviourStrategyBase {
    public RandomStrategy(Player p_player){
        super(p_player);
    }
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
    }

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
    }

    private void createDeployOrderCommand() {
        Random l_random = new Random();
        Country l_targetCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));
        String l_command = String.format("deploy %d %d", l_targetCountry.get_countryID(), d_player.get_numOfReinforcements());
        String[] commandTokens = l_command.split(" ");
        deploy_issue_order(commandTokens);
    }
}
