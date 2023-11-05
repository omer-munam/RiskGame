package Phases;

import Controller.GameEngine;
import Controller.MapEditor;
import Models.WarMap;

import java.io.IOException;

public class Preload extends Edit {
    public Preload(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {
        System.out.println("Please choose a map to edit using the command 'editmap filename' command. Alternatively enter the command 'quit' to return to the main menu");
    }

    @Override
    public void loadMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        if (l_input_string_array.length > 1 && l_input_string_array[1] != null) {
            d_ge.set_currentMap(new WarMap());
            MapEditor.editMap(l_input_string_array[1], d_ge.get_currentMap());
            if (!d_ge.get_currentMap().get_mapName().equals("Default Name")) {
                next();
            }
        }
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountry() {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinent() {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighbours() {
        printInvalidCommandMessage();
    }

    @Override
    public void saveMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        if (d_ge.getCurrentInput().toLowerCase().equals("quit")) {
            d_ge.setPhase(new MainMenu(d_ge));
        } else {
            d_ge.setPhase(new Postload(d_ge));
        }
    }
}
