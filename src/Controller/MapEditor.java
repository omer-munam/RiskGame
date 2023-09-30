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

public class MapEditor {
    String d_base_path = String.valueOf(System.getProperty("user.dir")) + "\\Src\\Resources\\Maps";

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
                l_current_map.validateMap();
            }


            //NEED TO IMPLEMENT Edit Commands for user input, they are all already done in WarMap or MapEditor Classes, just parse input and call them.
        }
    }

    boolean readmap(String p_filename, WarMap p_map) throws IOException {
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
                    p_map.get_countries().get(Integer.parseInt(l_splitLine.get(0))).addNeighbouringCountry(p_map.get_countries().get(l_splitLine.get(l_i)));
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
