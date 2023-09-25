import Models.Continent;
import Models.Country;
import Models.WarMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        WarMap test = new WarMap();
        MapEditor MapEditorTester = new MapEditor();
        MapEditorTester.readmap("europe.map", test);
        for (Map.Entry<Integer, Country> entry : test.d_countries.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<Integer, Continent> entry : test.d_continents.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<Integer, ArrayList<Integer>> entry : test.d_adjencyList.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Hello world!");
    }
}