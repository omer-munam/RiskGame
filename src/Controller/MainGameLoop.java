package Controller;

import Models.Player;
import Models.WarMap;

import java.util.List;

public class MainGameLoop {

    private WarMap d_map;
    private List<Player> d_playerList;
    public MainGameLoop(WarMap l_map, List<Player> l_playerList){
        this.d_map = l_map;
        this.d_playerList = l_playerList;
    }

    public void begin_game(){
        System.out.println("Begin Main Game Loop...");
    }
}
