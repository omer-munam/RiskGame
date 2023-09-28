import Models.Continent;
import Models.Country;
import Models.WarMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Scanner SCANNER;
    public static void main(String[] args) throws IOException {
        SCANNER = new Scanner(System.in);
        String l_state = "Map Editing";
        WarMap l_current_map = null;
        MapEditor l_map_editor = new MapEditor();
        while (l_state.equals("Map Editing")) {
            if (l_current_map.equals(null)) {
                System.out.println("Please choose a map to edit using the command 'editmap filename'");
                String l_input_string = SCANNER.nextLine();
                String[] l_input_string_array = l_input_string.split(" ");
                if (l_input_string_array[0].equals("editmap") && !l_input_string_array[1].equals(null)) {
                    l_map_editor.editMap(l_input_string_array[1], l_current_map);
                }
            } else {
                String l_input_string = SCANNER.nextLine();
                String[] l_input_string_array = l_input_string.split(" ");
                //NEED TO IMPLEMENT Edit Commands for user input, they are all already done in WarMap or MapEditor Classes, just parse input and call them.
            }
        }
    }
}