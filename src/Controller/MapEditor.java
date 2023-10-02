package Controller;

import Models.Continent;
import Models.Country;
import Models.WarMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapEditor {
    static String d_base_path = String.valueOf(System.getProperty("user.dir")) + "\\Src\\Resources\\Maps";

    public void editMapEntry() throws IOException {
        WarMap l_current_map = new WarMap();
        String l_input_string;
        String[] l_input_string_array;
        System.out.println("Please choose a map to edit using the command 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");

        while (true) {
            if (!l_current_map.get_mapName().equals("Default Name")) {
                System.out.println("You are currently editing " + l_current_map.get_mapName() + " the available commands are: ");
                System.out.println("editcontinent -add continentID continentvalue -remove continentID");
                System.out.println("editcountry -add countryID continentID -remove countryID");
                System.out.println("editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                System.out.println("savemap");
                System.out.println("showmap");
                System.out.println("validatemap");
                System.out.println("editmap filename");
                System.out.println("exit");
            }
            l_input_string = GameEngine.SCANNER.nextLine();
            l_input_string_array = l_input_string.split(" ");

            if (l_input_string_array[0].equals("editmap") && l_input_string_array.length > 1 && l_input_string_array[1] != null) {
                l_current_map = new WarMap();
                this.editMap(l_input_string_array[1], l_current_map);
                if (l_current_map.get_mapName().equals("Default Name")) {
                    System.out.println("You must specify a map to edit using the 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");
                    continue;
                }
            }
            if (l_input_string_array[0].equals("exit")) {
                return;
            }
            if (l_input_string_array[0].equals("editcontinent")) {
                for (int i = 1; i < l_input_string_array.length; i++) {
                    if (l_input_string_array[i].equals("-add")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            int l_input_continent_ID = Integer.parseInt(l_input_string_array[i]);
                            i++;
                            if (i < l_input_string_array.length) {
                                int l_continent_army_bonus = Integer.parseInt(l_input_string_array[i]);
                                System.out.println("Please enter a continent name for continent with ID " + l_input_continent_ID);
                                String l_continent_name = GameEngine.SCANNER.nextLine();
                                Continent l_newcontinent = new Continent(l_input_continent_ID, l_continent_name, l_continent_army_bonus);
                                l_current_map.addContinent(l_newcontinent);
                                continue;
                            } else {
                                System.out.println("Reached end of command while parsing please ensure correct command is used.");
                                continue;
                            }
                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                            continue;
                        }
                    }
                    if (l_input_string_array[i].equals("-remove")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            if (l_current_map.get_continents().containsKey(Integer.parseInt(l_input_string_array[i]))) {
                                System.out.println("Removing Continent: " + l_current_map.get_continents().get(Integer.parseInt(l_input_string_array[i])));
                                l_current_map.get_continents().remove(Integer.parseInt(l_input_string_array[i]));
                            } else {
                                System.out.println("Could not remove continent " + l_input_string_array[i] + " as it is not a valid continentID");
                            }

                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                        }

                    }
                }
            }
            if (l_input_string_array[0].equals("editcountry")) {
                for (int i = 1; i < l_input_string_array.length; i++) {
                    if (l_input_string_array[i].equals("-add")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            int l_input_country_ID = Integer.parseInt(l_input_string_array[i]);
                            i++;
                            if (i < l_input_string_array.length) {
                                int l_input_country_continent_ID = Integer.parseInt(l_input_string_array[i]);
                                System.out.println("Please enter a country name for country with ID " + l_input_country_ID);
                                String l_country_name = GameEngine.SCANNER.nextLine();
                                Country l_new_country = new Country(l_input_country_ID, l_country_name, l_input_country_continent_ID);
                                l_current_map.addCountry(l_new_country);
                                continue;
                            } else {
                                System.out.println("Reached end of command while parsing please ensure correct command is used.");
                                continue;
                            }
                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                            continue;
                        }
                    }
                    if (l_input_string_array[i].equals("-remove")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            if (l_current_map.get_countries().containsKey(Integer.parseInt(l_input_string_array[i]))) {
                                for (Integer l_i : l_current_map.get_adjencyList().get(Integer.parseInt(l_input_string_array[i]))) {
                                    l_current_map.removeNeighbour(l_i, Integer.parseInt(l_input_string_array[i]));
                                }
                                l_current_map.get_adjencyList().remove(l_input_string_array[i]);
                                System.out.println("Removing country with ID: " + l_current_map.get_countries().get(Integer.parseInt(l_input_string_array[i])));
                                l_current_map.get_countries().remove(Integer.parseInt(l_input_string_array[i]));
                            } else {
                                System.out.println("Could not remove country with ID: " + l_input_string_array[i] + " as it is not a valid countryID");
                            }

                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                        }

                    }
                }
            }
            if (l_input_string_array[0].equals("editneighbor")) {
                for (int i = 1; i < l_input_string_array.length; i++) {
                    if (l_input_string_array[i].equals("-add")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            int l_input_country_ID = Integer.parseInt(l_input_string_array[i]);
                            i++;
                            if (i < l_input_string_array.length) {
                                int l_input_country_neighbor_ID = Integer.parseInt(l_input_string_array[i]);
                                if (l_current_map.get_countries().containsKey(l_input_country_ID) && l_current_map.get_countries().containsKey(l_input_country_neighbor_ID)) {
                                    l_current_map.addNeighbour(l_input_country_ID, l_input_country_neighbor_ID);
                                    l_current_map.addNeighbour(l_input_country_neighbor_ID, l_input_country_ID);
                                } else {
                                    System.out.println("Unable to add neighbors for countryIDs: " + l_input_country_ID + " and " + l_input_country_neighbor_ID + " as at least one of these country IDs do not exist");
                                }


                                continue;
                            } else {
                                System.out.println("Reached end of command while parsing please ensure correct command is used.");
                                continue;
                            }
                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                            continue;
                        }
                    }
                    if (l_input_string_array[i].equals("-remove")) {
                        i++;
                        if (i < l_input_string_array.length) {
                            int l_input_country_ID = Integer.parseInt(l_input_string_array[i]);
                            i++;
                            if (i < l_input_string_array.length) {
                                int l_input_country_neighborID = Integer.parseInt(l_input_string_array[i]);
                                l_current_map.removeNeighbour(l_input_country_ID, l_input_country_neighborID);
                                l_current_map.removeNeighbour(l_input_country_neighborID, l_input_country_ID);
                                continue;
                            } else {
                                System.out.println("Reached end of command while parsing please ensure correct command is used.");
                                continue;
                            }
                        } else {
                            System.out.println("Reached end of command while parsing please ensure correct command is used.");
                            continue;
                        }
                    }
                }
            }
            if (l_current_map.get_mapName().equals("Default Name")) {
                System.out.println("You must specify a map to edit using the 'editmap filename' command. Alternatively enter the command 'exit' to return to the main menu");
                continue;
            }
            if (l_input_string_array[0].equals("savemap")) {
                l_current_map.saveMap(l_current_map.get_mapName());
            }
            if (l_input_string_array[0].equals("showmap")) {
                l_current_map.showMap();
            }
            if (l_input_string_array[0].equals("validatemap")) {
                if(l_current_map.validateMap()==true) System.out.println("Valid Map");
                else System.out.println("Invalid Map");
            }


            //NEED TO IMPLEMENT Edit Commands for user input, they are all already done in WarMap or MapEditor Classes, just parse input and call them.
        }
    }

    public static boolean readmap(String p_filename, WarMap p_map) throws IOException {
        BufferedReader l_bufferReader = new BufferedReader(new FileReader(d_base_path + "\\" + p_filename));
        String l_line = l_bufferReader.readLine();
        String l_readState = "";
        int l_continentCount = 0;
        p_map.set_mapName(p_filename);
        while (l_line != null) {

            if (l_line.equals("[continents]")) {
                l_readState = "continents";
                l_line = l_bufferReader.readLine();
            }
            if (l_line.equals("[countries]")) {
                l_readState = "countries";
                l_line = l_bufferReader.readLine();
            }
            if (l_line.equals("[borders]")) {
                l_readState = "borders";
                l_line = l_bufferReader.readLine();
            }
            if (l_readState.equals("continents") && l_line.length() > 0) {
                l_continentCount++;
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));

                Continent l_continent = new Continent(l_continentCount, l_splitLine.get(0), Integer.parseInt(l_splitLine.get(1)));
                p_map.addContinent(l_continent);
            }

            if (l_readState.equals("countries") && l_line.length() > 0) {
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                Country l_country = new Country(Integer.parseInt(l_splitLine.get(0)), l_splitLine.get(1), Integer.parseInt(l_splitLine.get(2)));
                p_map.addCountry(l_country);
            }
            if (l_readState.equals("borders") && l_line.length() > 0) {
                List<String> l_splitLine = Arrays.asList(l_line.split(" "));
                for (int l_i = 1; l_i < l_splitLine.size(); l_i++) {
                    p_map.addNeighbour(Integer.parseInt(l_splitLine.get(0)), Integer.parseInt(l_splitLine.get(l_i)));
                }
            }

            l_line = l_bufferReader.readLine();
        }
        return false;
    }

    boolean editMap(String p_filename, WarMap p_map) throws IOException {
        File l_f = new File(d_base_path, p_filename);
        if (l_f.exists()) {
            readmap(p_filename, p_map);
            return true;
        } else {
            l_f.createNewFile(); //fix this to to match where savemap links
            p_map.set_mapName(p_filename);
        }
        return false;
    }
}
