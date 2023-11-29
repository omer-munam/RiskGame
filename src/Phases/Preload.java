package Phases;

import Adapter.MapEditorAdapter;
import Adapter.MapEditorConquest;
import Controller.GameEngine;
import Controller.MapEditor;
import Models.WarMap;

import java.io.IOException;

/**
 * Map editing phase for after a map has been loaded
 */
public class Preload extends Edit {
    /**
     * The Constructor of the preload phase
     *
     * @param p_ge The Game Engine of the phase
     */
    public Preload(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("PRELOAD PHASE");
    }

    /**
     * The display options of the preload phase
     */
    @Override
    public void displayOptions() {
        System.out.println("Please choose a map to edit using the command 'editmap filename' command. Alternatively enter the command 'quit' to return to the main menu");
    }

    /**
     * The load map function of the preload phase
     *
     * @throws IOException Exception if IO error occurs
     */
    @Override
    public void loadMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        if (l_input_string_array.length > 1 && l_input_string_array[1] != null) {
            d_ge.set_currentMap(new WarMap());
            if (l_input_string_array[1].matches("(?i).+\\.map")) {
                d_ge.set_currentMap(new MapEditor().editMap(l_input_string_array[1]));
            }
            if (l_input_string_array[1].matches("(?i).+\\.conquest")) {
                MapEditor l_mapEditor = new MapEditorAdapter(new MapEditorConquest());
                d_ge.set_currentMap(l_mapEditor.editMap(l_input_string_array[1]));
            }

            if (!d_ge.get_currentMap().get_mapName().equals("Default Name")) {
                d_logentrybuffer.writeLog("editmap " + l_input_string_array[1] + " runned successfully");
                next();

            }
        }
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editCountry() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editContinent() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    /**
     * Prints invalid state message
     */
    @Override
    public void saveMap() {
        printInvalidCommandMessage();
    }

    /**
     * The next function of the preload phase
     */
    @Override
    public void next() {
        if (d_ge.getCurrentInput().equalsIgnoreCase("quit")) {
            d_ge.setPhase(new MainMenu(d_ge));
        } else {
            d_ge.setPhase(new Postload(d_ge));
        }
    }
}
