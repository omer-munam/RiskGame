    package Models.Orders;
    import Models.Country;
    import Models.Player;
    import Models.WarMap;
    import Models.Orders.BlockadeOrder;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.TestInstance;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNull;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class BlockadeOrderTest {

        private WarMap warMap;
        private Player player;
        private Map<Integer, Country> countries;

        @BeforeEach
        public void setUp() {
            warMap = new WarMap();
            player = new Player("Player1");
            countries = new HashMap<>();// Assuming you have a no-argument constructor in your Player class
        }

        @Test
        public void testExecuteBlockadeOrder() {
            Country destCountry = new Country(1, "DestCountry");
            destCountry.set_numOfArmies(5);
            destCountry.setD_ownerPlayer(player);
            countries.put(1, destCountry);

            player.set_playerCountries(new ArrayList<>(countries.values()));
            BlockadeOrder blockadeOrder = new BlockadeOrder(1, player);
            blockadeOrder.execute(warMap);

            // Verifying that the number of armies in the destination country is tripled
            assertEquals(15, destCountry.get_numOfArmies());
            // Verifying that the owner player of the destination country is set to null
            assertNull(destCountry.getD_ownerPlayer());
            // Verifying that the player's country list does not contain the destination country anymore
            assertEquals(0, player.get_playerCountries().size());
        }
    }
