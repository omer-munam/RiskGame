package Models.BehaviourStrategies;

import Controller.GameEngine;
import Models.Player;
import Models.WarMap;
import Resources.Commands;

public class HumanStrategy extends BehaviourStrategyBase {
    public HumanStrategy(Player p_player){
        super(p_player);
    }
    @Override
    public void issue_order() {
        GameEngine l_ge = GameEngine.getInstance();
        WarMap l_map = l_ge.get_currentMap();
        String l_command = l_ge.getCurrentInput();
        String[] commandTokens = l_command.split(" ");
        switch (commandTokens[0]) {
            case Commands.DEPLOY_COMMAND:
                deploy_issue_order(commandTokens);
                break;
            case Commands.ADVANCE_ORDER:
                advance_issue_order(commandTokens, l_map);
                break;
            case Commands.BOMB_ORDER:
                bomb_issue_order(commandTokens, l_map);
                break;
            case Commands.BLOCKADE_ORDER:
                blockade_issue_order(commandTokens, l_map);
                break;
            case Commands.AIRLIFT_ORDER:
                airlift_issue_order(commandTokens);
                break;
            case Commands.DIPLOMACY_ORDER:
                diplomacy_issue_order(commandTokens, l_map, l_ge.get_PlayersList());
                break;
            default:
                System.out.println("Invalid command given... Please try again...");
        }
    }
}
