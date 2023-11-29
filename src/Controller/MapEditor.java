package Controller;

import Models.Continent;
import Models.Country;
import Models.WarMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected static String d_base_path = System.getProperty("user.dir") + "\\Src\\Resources\\Maps";

    /**
     * Stores the command for editing maps.
     */
    protected static HashMap<String, Integer> command_Code_Hashmap = new HashMap<String, Integer>();

    /**
     * A function used for reading a WarMap from a file into a WarMap object.
     *
     * @param p_filename The file name of the map
     * @throws IOException Exception if error with IO
     * @return True if a map could be read
     */
    public WarMap readMap(String p_filename) throws IOException {
        WarMap l_map = new WarMap();
        BufferedReader l_bufferReader = new BufferedReader(new FileReader(d_base_path + "\\" + p_filename));
        String l_line = l_bufferReader.readLine();
        String l_readState = "";
        int l_continentCount = 0;
        l_map.set_mapName(p_filename);
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
                l_map.addContinent(l_continent);
            }

            if (l_readState.equals("countries") && l_line.length() > 0) { //Logic for adding a country when readstate is countries
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                Country l_country = new Country(Integer.parseInt(l_splitLine.get(0)), l_splitLine.get(1), Integer.parseInt(l_splitLine.get(2)));
                l_map.addCountry(l_country);
            }
            if (l_readState.equals("borders") && l_line.length() > 0) { //Logic for adding neighbours when the readstate is borders
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                for (int l_i = 1; l_i < l_splitLine.size(); l_i++) {
                    l_map.addNeighbour(Integer.parseInt(l_splitLine.get(0)), Integer.parseInt(l_splitLine.get(l_i)));
                }
            }

            l_line = l_bufferReader.readLine();
        }
        return l_map;
    }

    /**
     * A function used for either reading a WarMap from a file, or creating a new WarMap if the file does not exist.
     *
     * @param p_filename The filename of the WarMap
     * @return Returns true if the WarMap file already exists, and false if it is a new WarMap
     * @throws IOException Exception if IO error occurs
     */
    public WarMap editMap(String p_filename) throws IOException {
        File l_f = new File(d_base_path, p_filename);
        if (l_f.exists()) {
            return readMap(p_filename);

        } else {
            l_f.createNewFile(); //fix this to to match where savemap links
            WarMap l_map = new WarMap();
            l_map.set_mapName(p_filename);
            return l_map;
        }

    }

    /**
     * A function that print's the list of available maps to the console.
     */
    public static void showAllMaps() {
        File l_maps_directory = new File(d_base_path);
        String[] l_filenameslist = l_maps_directory.list();
        boolean l_anymapfile = false;
        for (String l_filename : l_filenameslist) {
            if (l_filename.contains(".map") || l_filename.contains(".conquest")) {
                System.out.println(l_filename);
                l_anymapfile = true;
            }
        }
        if (!l_anymapfile) System.out.println("There are no map files in \\Maps folder \n");
    }

    public void saveMap(String p_map_name, WarMap p_warMap) {

        // Checking if a map is valid before saving it.
        if (p_warMap.validateMap()) {
            try {
                FileWriter l_fstream = new FileWriter(d_base_path + "\\" + p_map_name);
                BufferedWriter l_info = new BufferedWriter(l_fstream);

                // creating Continents Section in map File
                l_info.write("[continents]");
                l_info.newLine();
                for (Map.Entry<Integer, Continent> l_continent : p_warMap.get_continents().entrySet()) {
                    l_info.write(l_continent.getValue().get_continentName() + " " + l_continent.getValue().get_armyBonus());
                    l_info.newLine();
                }
                l_info.newLine();

                //creating Country Section in map File
                l_info.write("[countries]");
                l_info.newLine();
                for (Map.Entry<Integer, Country> l_country : p_warMap.get_countries().entrySet()) {
                    l_info.write(l_country.getKey() + " " + l_country.getValue().get_countryName() + " " + l_country.getValue().getContinentID());
                    l_info.newLine();
                }
                l_info.newLine();

                // creating border section in File
                l_info.write("[borders]");
                l_info.newLine();
                for (Map.Entry<Integer, HashMap<Integer, Integer>> l_neighbourlist : p_warMap.get_adjencyList().entrySet()) {
                    StringBuilder l_border = new StringBuilder();
                    l_border.append(l_neighbourlist.getKey());
                    for (Integer nghbr_cuntry : l_neighbourlist.getValue().keySet())
                        l_border.append(" " + nghbr_cuntry);
                    l_info.write(String.valueOf(l_border));
                    l_info.newLine();
                }
                l_info.close();

            } catch (Exception e) {
                System.out.println("Exception occured file saving a map file");
            }
        } else System.out.println("Invalid_Map");
    }

}
