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
     * This function contains the console commands and feedback loop for the Map Editor, it allows loading of maps, editing of maps, showing the map, validating maps, and saving maps.
     * You are able to construct a map from an empty map by editing a filename which is not a current WarMap, you can then save this WarMap if you create a valid map by adding countrys, continents and neighbours.
     *
     * @throws IOException
     */
    public void editMapEntry() throws IOException {

        command_Code_Hashmap.put("editcontinent", 0);
        command_Code_Hashmap.put("editcountry", 1);
        command_Code_Hashmap.put("editneighbor", 2);
        command_Code_Hashmap.put("showmap", 3);
        command_Code_Hashmap.put("savemap", 4);
        command_Code_Hashmap.put("editmap", 5);
        command_Code_Hashmap.put("validatemap", 6);
        command_Code_Hashmap.put("exit", 7);

        WarMap l_current_map = new WarMap();
        String l_input_string;
        String[] l_input_string_array;
        System.out.println("Please choose a map to edit using the command 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");

        while (true) { //Loops until user types the exit command.
            if (!l_current_map.get_mapName().equals("Default Name")) {
                System.out.println("You are currently editing " + l_current_map.get_mapName() + " the available commands are: ");
                System.out.println("editcontinent -add continentID continentName continentarmybonus -remove continentID");
                System.out.println("editcountry -add countryID countryName continentID -remove countryID");
                System.out.println("editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                System.out.println("savemap filename");
                System.out.println("showmap");
                System.out.println("validatemap");
                System.out.println("editmap filename");
                System.out.println("exit");
            }
            l_input_string = GameEngine.SCANNER.nextLine();
            l_input_string_array = l_input_string.split(" ");
            try {
                switch (command_Code_Hashmap.getOrDefault(l_input_string_array[0], 8)) {

                    case 0:
                        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
                            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {


                                if (l_i + 3 < l_input_string_array.length) {
                                    if (l_current_map.get_continents().containsKey(Integer.valueOf(l_input_string_array[l_i + 1]))) {
                                        System.out.println("Cannot add duplicate continent ID of " + Integer.valueOf(l_input_string_array[l_i + 1]));
                                        l_i += 3;
                                    } else {
                                        l_current_map.addContinent(Integer.valueOf(l_input_string_array[l_i + 1]), l_input_string_array[l_i + 2], Integer.valueOf(l_input_string_array[l_i + 3]));
                                        l_i += 3;
                                    }
                                } else {
                                    System.out.println("Reached end of file while parsing not all commands completed");
                                }
                            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                                if (l_i + 1 < l_input_string_array.length) {
                                    l_current_map.removeContinent(Integer.valueOf(l_input_string_array[l_i + 1]));
                                    l_i++;
                                } else {
                                    System.out.println("Reached end of file while parsing not all commands completed");
                                }
                            }
                        }
                        break;

                    case 1:
                        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
                            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {
                                if (l_i + 3 < l_input_string_array.length) {
                                    if (l_current_map.get_countries().containsKey(Integer.valueOf(l_input_string_array[l_i + 1]))) {
                                        System.out.println("Cannot add duplicate country ID of " + Integer.valueOf(l_input_string_array[l_i + 1]));
                                        l_i += 3;
                                    } else {
                                        l_current_map.addCountry(Integer.valueOf(l_input_string_array[l_i + 1]), l_input_string_array[l_i + 2], Integer.valueOf(l_input_string_array[l_i + 3]));
                                        l_i += 3;
                                    }
                                } else {
                                    System.out.println("Reached end of file while parsing not all commands completed");
                                }
                            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                                if (l_i + 1 < l_input_string_array.length) {
                                    l_current_map.removeCountry(Integer.valueOf(l_input_string_array[l_i + 1]));
                                    l_i++;
                                } else {
                                    System.out.println("Reached end of file while parsing not all commands completed");
                                }
                            }
                        }
                        break;

                    case 2:
                        if (l_input_string_array[1].equalsIgnoreCase("-add"))
                            l_current_map.addNeighbourCountry(Integer.valueOf(l_input_string_array[2]), Integer.valueOf(l_input_string_array[3]));
                        else if (l_input_string_array[1].equalsIgnoreCase("-remove"))
                            l_current_map.removeNeighbourCountry(Integer.valueOf(l_input_string_array[2]), Integer.valueOf(l_input_string_array[3]));
                        break;

                    case 3:
                        l_current_map.showMap();
                        break;

                    case 4:
                        if (l_input_string_array.length > 1) {
                            if (l_current_map.validateMap()) {

                                l_current_map.saveMap(l_input_string_array[1]);
                                System.out.println("Map saved");
                                l_current_map = new WarMap();
                                editMap(l_input_string_array[1], l_current_map);
                            } else {
                                System.out.println("Map not saved due to being invalid");
                            }
                        } else {
                            System.out.println("Reached end of file while parsing map not saved");
                        }
                        break;

                    case 5:
                        if (l_input_string_array.length > 1 && l_input_string_array[1] != null) {
                            l_current_map = new WarMap();
                            editMap(l_input_string_array[1], l_current_map);
                            if (l_current_map.get_mapName().equals("Default Name")) {
                                System.out.println("You must specify a map to edit using the 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");
                                continue;
                            }
                        }
                        break;

                    case 6:
                        if (l_current_map.validateMap()) System.out.println("Valid Map");
                        else System.out.println("InValid Map");
                        break;

                    case 7:
                        return;
                    case 8:
                        System.out.println("You have entered an invalid command");
                        break;
                }

                if (l_current_map.get_mapName().equals("Default Name")) {
                    System.out.println("You must specify a map to edit using the 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");
                    continue;
                }

                //NEED TO IMPLEMENT Edit Commands for user input, they are all already done in WarMap or MapEditor Classes, just parse input and call them.
            } catch (NumberFormatException e) {
                System.out.println("You have entered text where a number was required, please ensure you enter the commands in the correct format");
            }

        }
    }

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
