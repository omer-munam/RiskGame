package Models.BehaviourStrategies;

import Models.Player;

public class RandomStrategy implements BehaviourStrategy {
    Player d_player;
    RandomStrategy(Player p_player){
        d_player = p_player;
    }
    @Override
    public void issue_order() {

    }
}
