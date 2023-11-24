package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Country;
import Models.Player;
import Models.WarMap;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy extends BehaviourStrategyBase {
    public BenevolentStrategy(Player p_player){
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
        GameEngine l_ge = GameEngine.getInstance();
        WarMap l_map = l_ge.get_currentMap();
        int l_armiesToMove;
        int l_randomSourceCountryID;
        Country l_weakestTargetCountry = null;
        int l_weakestTargetCountryID = 0;
        int l_minValue = Integer.MAX_VALUE;

        Random l_random = new Random();

        Country l_randomSourceCountry = d_player.get_playerCountries().get(l_random.nextInt(d_player.get_playerCountries().size()));
        l_randomSourceCountryID = l_randomSourceCountry.get_countryID();

        HashMap<Integer, Country> l_neighbouringCountries = l_randomSourceCountry.getNeighbouringCountries();

        for (Country l_neighbouringCountry : l_neighbouringCountries.values()){
            if (d_player.get_playerCountries().contains((l_neighbouringCountry))){
                if (l_neighbouringCountry.get_numOfArmies() < l_minValue){
                    l_minValue = l_neighbouringCountry.get_numOfArmies();
                    l_weakestTargetCountry = l_neighbouringCountry;
                }
            }
        }
        if (l_weakestTargetCountry != null){
            l_weakestTargetCountryID = l_weakestTargetCountry.get_countryID();
        }
        else {
            System.out.println("No Neighbouring country owned by Player");
        }

        if (l_randomSourceCountry.get_numOfArmies() > 1) {
            l_armiesToMove = l_random.nextInt(l_randomSourceCountry.get_numOfArmies() - 1) + 1;
        } else {
            l_armiesToMove = 1;
        }

        String l_command = String.format("advance %d %d %d", l_randomSourceCountryID, l_weakestTargetCountryID, l_armiesToMove);
        String[] commandTokens = l_command.split(" ");
        advance_issue_order(commandTokens, l_map);


    }

}
