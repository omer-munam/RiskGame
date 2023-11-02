package Controller;

import Models.Continent;
import Models.Country;
import Models.WarMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains utility functions for creating and editing maps to be used in the game. It is also the entry point to be used from main to access the map editor.
 *
 * @author Ryan Feher
 * @author Mohammad Uvas
 */
public class MapEditor {
    /**
     * Stores the location of saved maps in the file directory.
     */
    static String d_base_path = System.getProperty("user.dir") + "\\Src\\Resources\\Maps";

    /**
     * Stores the command for editing maps.
     */
    static HashMap<String, Integer> command_Code_Hashmap = new HashMap<String, Integer>();

    /**
     * A function used for reading a WarMap from a file into a WarMap object.
     *
     * @param p_filename The file name of the map
     * @param p_map      The map class in which you wish to store the map.
     * @throws IOException
     */
    public static boolean readMap(String p_filename, WarMap p_map) throws IOException {

        BufferedReader l_bufferReader = new BufferedReader(new FileReader(d_base_path + "\\" + p_filename));
        String l_line = l_bufferReader.readLine();
        String l_readState = "";
        int l_continentCount = 0;
        p_map.set_mapName(p_filename);
        while (l_line != null) {

            if (l_line.equals("[continents]")) { //Sets readstate to continents
                l_readState = "continents";
                l_line = l_bufferReader.readLine();
            }
            if (l_line.equals("[countries]")) { //Sets readstate to countries
                l_readState = "countries";
                l_line = l_bufferReader.readLine();
            }
            if (l_line.equals("[borders]")) { //Sets readstate to borders
                l_readState = "borders";
                l_line = l_bufferReader.readLine();
            }
            if (l_readState.equals("continents") && l_line.length() > 0) { //Logic for adding a continent when readstate is continents
                l_continentCount++;
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));

                Continent l_continent = new Continent(l_continentCount, l_splitLine.get(0), Integer.parseInt(l_splitLine.get(1)));
                p_map.addContinent(l_continent);
            }

            if (l_readState.equals("countries") && l_line.length() > 0) { //Logic for adding a country when readstate is countries
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                Country l_country = new Country(Integer.parseInt(l_splitLine.get(0)), l_splitLine.get(1), Integer.parseInt(l_splitLine.get(2)));
                p_map.addCountry(l_country);
            }
            if (l_readState.equals("borders") && l_line.length() > 0) { //Logic for adding neighbours when the readstate is borders
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                for (int l_i = 1; l_i < l_splitLine.size(); l_i++) {
                    p_map.addNeighbour(Integer.parseInt(l_splitLine.get(0)), Integer.parseInt(l_splitLine.get(l_i)));
                }
            }

            l_line = l_bufferReader.readLine();
        }
        return true;
    }

    /**
     * A function used for either reading a WarMap from a file, or creating a new WarMap if the file does not exist.
     *
     * @param p_filename The filename of the WarMap
     * @param p_map      The WarMap object that is to be edited.
     * @return Returns true if the WarMap file already exists, and false if it is a new WarMap
     * @throws IOException
     */
    public static boolean editMap(String p_filename, WarMap p_map) throws IOException {
        File l_f = new File(d_base_path, p_filename);
        if (l_f.exists()) {
            readMap(p_filename, p_map);
            return true;
        } else {
            l_f.createNewFile(); //fix this to to match where savemap links
            p_map.set_mapName(p_filename);
        }
        return false;
    }

    /**
     * A function that print's the list of available maps to the console.
     */
    public static void showAllMaps() {
        File l_maps_directory = new File(d_base_path);
        String[] l_filenameslist = l_maps_directory.list();
        boolean l_anymapfile = false;
        for (String l_filename : l_filenameslist) {
            if (l_filename.contains(".map")) {
                System.out.println(l_filename);
                l_anymapfile = true;
            }
        }
        if (!l_anymapfile) System.out.println("There are no map files in \\Maps folder \n");
    }

}
