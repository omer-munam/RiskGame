package Models.Orders;

import Models.WarMap;

/**
 * This class is used to implement the data and logic of how to execute orders given by a player.
 *
 * @author omer-munam
 * @author shezin-saleem
 * @version 1.0
 */

public interface Order {
    /**
     * The execute function which executes the orders
     *
     * @param warMap The warmap of the gameengine that the order takes place
     */
    void execute(WarMap warMap);
}