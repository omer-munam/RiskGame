package Controller;

import Models.Player;
import Models.WarMap;

import java.util.List;

/**
 * This class is the MainGameLoop class. Once the game is set up with the map and all the users, the control is handed over to this class for actual gameplay.
 *
 * @author Omer Munam
 */
public class MainGameLoop {

    /**
     * The current map of the game.
     */
    private WarMap d_map;
    /**
     * The list of players of the game.
     */
    private List<Player> d_playerList;

    /**
     * A fully parametrized constructor built to initialize the class with all the essential values.
     *
     * @param l_map
     * @param l_playerList
     */
    public MainGameLoop(WarMap l_map, List<Player> l_playerList){
        this.d_map = l_map;
        this.d_playerList = l_playerList;
    }

    /**
     * The method which receives the control over from the GameEngine class and is responsible to handle the whole gameplay.
     */
    public void run_game_loop(){
        System.out.println("Begin Main Game Loop...");
    }
}
