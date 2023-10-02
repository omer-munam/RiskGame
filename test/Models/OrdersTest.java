package Models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrdersTest {
    private WarMap warmap;
    @BeforeEach
    void setUp() {
        warmap = new WarMap();
        HashMap<Integer, Country> countries = new HashMap<>();
        countries.put(1, new Country(1, "CountryA"));
        countries.put(2, new Country(2, "CountryB"));
        warmap.set_countries(countries);
    }

    @Test
    void execute() {
        Orders order = new Orders(5, 1);
        order.execute(warmap);

        List<Country> countryInfo = new ArrayList<>(warmap.get_countries().values());
        for (Country country : countryInfo) {
            if (country.get_countryID() == 1) {
                assertEquals(5, country.get_numOfArmies(), "CountryA should have 5 armies now.");
            }
            else {
                assertEquals(0, country.get_numOfArmies(), "CountryB should have 0 armies.");
            }
        }
    }
}