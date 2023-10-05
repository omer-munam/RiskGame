package Models;

import Controller.MapEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * Tests for validation of the WarMap
 */
class WarMapTest {

    WarMap l_warmap;

    @BeforeEach
    void setUp() throws Exception {
        l_warmap = new WarMap();
    }

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
