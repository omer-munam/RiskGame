package Models;

import Controller.MapEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        l_warmap = new WarMap();
    }

    /**
     * Test for if the continents in the war map are connected
     */
    @Test
    void testIsContinentConnected() {
        try {
            MapEditor.readMap("europe.map", l_warmap);
            assertTrue(l_warmap.validateMap());
            l_warmap.removeCountry(9);
            l_warmap.removeCountry(10);
            assertFalse(l_warmap.validateMap());
        } catch (IOException e) {
            System.err.println("Some Error occured while validating map");
            e.printStackTrace();
        }
    }

    /**
     * Test for if the entire war map is connected
     */
    @Test
    void testIsConnectedGraph() {
        try {
            MapEditor.readMap("europe.map", l_warmap);
            assertTrue(l_warmap.validateMap());
            l_warmap.removeNeighbourCountry(3, 1);
            l_warmap.removeNeighbourCountry(3, 2);
            assertFalse(l_warmap.validateMap());
        } catch (IOException e) {
            System.err.println("Some Error occured while validating map");
            e.printStackTrace();
        }
    }

    /**
     * Test for if there is a continent with no countries in the war map
     */
    @Test
    void testisEmptyContinent() {
        try {
            MapEditor.readMap("europe.map", l_warmap);
            assertTrue(l_warmap.validateMap());
            l_warmap.addContinent(5, "demo_Continent", 7);
            assertFalse(l_warmap.validateMap());
        } catch (IOException e) {
            System.err.println("Some Error occured while validating map");
            e.printStackTrace();
        }
    }

    /**
     * Checking if all countries have at least one neighbor
     */
    @Test
    void testingisCountryNoNeighbor() {
        try {
            MapEditor.readMap("europe.map", l_warmap);
            assertTrue(l_warmap.validateMap());
            l_warmap.addCountry(25, "demo_country", 4);
            assertFalse(l_warmap.validateMap());
        } catch (IOException e) {
            System.err.println("Some Error occured while validating map");
            e.printStackTrace();
        }
    }
}
