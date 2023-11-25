package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;

public class AggressiveStrategy extends BehaviourStrategyBase {
    public AggressiveStrategy(Player p_player){
        super(p_player);
    }
    @Override
    public void issue_order() {
        createDeployOrderCommand();
        //TODO: Advance and attack
    }
    public void createDeployOrderCommand(){
        int l_strongestCountryID = 0;
        int l_maxValue = 0;

        for (Country l_country : d_player.get_playerCountries()){
            if (l_country.get_numOfArmies() > l_maxValue) {
                l_maxValue = l_country.get_numOfArmies();
                l_strongestCountryID = l_country.get_countryID();
            }
        }

        String l_command = String.format("deploy %d %d", l_strongestCountryID, d_player.get_numOfReinforcements());
        String[] commandTokens = l_command.split(" ");
        deploy_issue_order(commandTokens);
    }
}
