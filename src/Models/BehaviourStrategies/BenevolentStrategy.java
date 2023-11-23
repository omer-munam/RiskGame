package Models.BehaviourStrategies;

import Models.Player;

public class BenevolentStrategy implements BehaviourStrategy {
    Player d_player;
    BenevolentStrategy(Player p_player){
        d_player = p_player;
    }
    @Override
    public void issue_order() {

    }
}
