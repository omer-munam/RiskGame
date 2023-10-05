package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrdersTest {
    private WarMap warmap;
    private Country country;

    @BeforeEach
    void setUp() {
        warmap = new WarMap();
        country = new Country();
        warmap.get_countries().put(country.get_countryID(), country);

    }

    @Test
    void testExecute() {
        Orders order = new Orders(5, country.get_countryID());
        order.execute(warmap);

        assertEquals(5, warmap.get_countries().get(country.get_countryID()).get_numOfArmies());

    }
}