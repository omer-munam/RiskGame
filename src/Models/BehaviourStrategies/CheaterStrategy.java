package Models.BehaviourStrategies;

import Models.Player;

public class CheaterStrategy implements BehaviourStrategy {
    Player d_player;
    CheaterStrategy(Player p_player){
        d_player = p_player;
    }
    @Override
    public void issue_order() {

    }
}
