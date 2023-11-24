package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;

public class BenevolentStrategy extends BehaviourStrategyBase {
    BenevolentStrategy(Player p_player){
        super(p_player);
    }
    @Override
    public void issue_order() {
        if (d_player.get_numOfReinforcements() > 0){
            createDeployOrderCommand(d_player.get_numOfReinforcements());
        }
        else {
            createAdvanceOrderCommand();
        }
        //deploy order command - find weakest country and create deploy command
        //advance order command - move armies to weakest country
    }

    public void createDeployOrderCommand(int numOfReinforcements){
        GameEngine l_ge = GameEngine.getInstance();
        WarMap l_map = l_ge.get_currentMap();
        int l_weakestCountryID = 0;
        int l_minValue = Integer.MAX_VALUE;

        for (Country l_country : d_player.get_playerCountries()){
            if (l_country.get_numOfArmies() == 0){
                l_weakestCountryID = l_country.get_countryID();
            }
            else if (l_country.get_numOfArmies() < l_minValue) {
                l_minValue = l_country.get_numOfArmies();
                l_weakestCountryID = l_country.get_countryID();
            }
        }

        String l_command = String.format("deploy %d %d", l_weakestCountryID, numOfReinforcements);
        String[] commandTokens = l_command.split(" ");
        deploy_issue_order(commandTokens);
    }

    public void  createAdvanceOrderCommand(){

    }
}
