package Models;

import Controller.MapEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for validation of the WarMap
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WarMapTest {
    /**
     * WarMap used in the test
     */
    WarMap l_warmap;

    /**
     * Makes the WarMap object a new WarMap before every test
     *
     * @throws Exception Exception if IO error occurs
     */
    @Before
    public void setUp() throws Exception {
        l_warmap = new MapEditor().readMap("europe.map");
    }

    /**
     * Test for if the continents in the war map are connected
     */
    @Test
    public void testIsContinentConnected() {

            assertTrue(l_warmap.validateMap());
            l_warmap.removeCountry(9);
            l_warmap.removeCountry(10);
            assertFalse(l_warmap.validateMap());

    }

    /**
     * Test for if the entire war map is connected
     */
    @Test
    public void testIsConnectedGraph() {

        assertTrue(l_warmap.validateMap());
            l_warmap.removeNeighbourCountry(3, 1);
            l_warmap.removeNeighbourCountry(3, 2);
            assertFalse(l_warmap.validateMap());


    }

    /**
     * Test for if there is a continent with no countries in the war map
     */
    @Test
    public void testisEmptyContinent() {

        assertTrue(l_warmap.validateMap());
            l_warmap.addContinent(5, "demo_Continent", 7);
            assertFalse(l_warmap.validateMap());

    }


    /**
     * Checking if all countries have at least one neighbor
     */
    @Test
    public void testingisCountryNoNeighbor() {

        assertTrue(l_warmap.validateMap());
            l_warmap.addCountry(25, "demo_country", 4);
            assertFalse(l_warmap.validateMap());

    }
}
