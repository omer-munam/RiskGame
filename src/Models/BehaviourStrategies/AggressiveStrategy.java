package Models.BehaviourStrategies;

import Models.Player;

public class AggressiveStrategy implements BehaviourStrategy {
    Player d_player;
    AggressiveStrategy(Player p_player){
        d_player = p_player;
    }
    @Override
    public void issue_order() {

    }
}
