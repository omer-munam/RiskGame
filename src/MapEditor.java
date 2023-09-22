import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapEditor {

    boolean readmap(String p_filename, WarMap p_map) throws IOException {
        BufferedReader l_bufferReader = new BufferedReader(new FileReader(p_filename));
        String l_line = l_bufferReader.readLine();
        String l_readState = "";
        int l_continentCount = 0;
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

    boolean editmap(String p_filename) {
        return false;
    }
}
