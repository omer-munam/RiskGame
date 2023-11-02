package Phases;

import Controller.GameEngine;
import Controller.MapEditor;
import Models.WarMap;

import java.io.IOException;

public class Postload extends Edit {
    public Postload(GameEngine p_ge) {
        super(p_ge);
    }

    @Override
    public void displayOptions() {
        System.out.println("You are currently editing " + d_ge.get_currentMap().get_mapName() + " the available commands are: ");
        System.out.println("editcontinent -add continentID continentName continentarmybonus -remove continentID");
        System.out.println("editcountry -add countryID countryName continentID -remove countryID");
        System.out.println("editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        System.out.println("savemap filename");
        System.out.println("showmap");
        System.out.println("validatemap");
        System.out.println("editmap filename");
        System.out.println("quit");
    }

    @Override
    public void loadMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");

        if (l_input_string_array.length > 1 && l_input_string_array[1] != null) {
            d_ge.set_currentMap(new WarMap());
            MapEditor.editMap(l_input_string_array[1], d_ge.get_currentMap());
        } else {
            System.out.println("Could not load map");
        }
    }

    @Override
    public void showMap() {
        d_ge.get_currentMap().showMap();
    }

    @Override
    public void editCountry() {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {
                if (l_i + 3 < l_input_string_array.length) {
                    if (d_ge.get_currentMap().get_countries().containsKey(Integer.valueOf(l_input_string_array[l_i + 1]))) {
                        System.out.println("Cannot add duplicate country ID of " + Integer.valueOf(l_input_string_array[l_i + 1]));
                        l_i += 3;
                    } else {
                        d_ge.get_currentMap().addCountry(Integer.valueOf(l_input_string_array[l_i + 1]), l_input_string_array[l_i + 2], Integer.valueOf(l_input_string_array[l_i + 3]));
                        l_i += 3;
                    }
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 1 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeCountry(Integer.valueOf(l_input_string_array[l_i + 1]));
                    l_i++;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    @Override
    public void editContinent() {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {


                if (l_i + 3 < l_input_string_array.length) {
                    if (d_ge.get_currentMap().get_continents().containsKey(Integer.valueOf(l_input_string_array[l_i + 1]))) {
                        System.out.println("Cannot add duplicate continent ID of " + Integer.valueOf(l_input_string_array[l_i + 1]));
                        l_i += 3;
                    } else {
                        d_ge.get_currentMap().addContinent(Integer.valueOf(l_input_string_array[l_i + 1]), l_input_string_array[l_i + 2], Integer.valueOf(l_input_string_array[l_i + 3]));
                        l_i += 3;
                    }
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 1 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeContinent(Integer.valueOf(l_input_string_array[l_i + 1]));
                    l_i++;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    @Override
    public void editNeighbours() {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {
                if (l_i + 2 < l_input_string_array.length) {
                    d_ge.get_currentMap().addNeighbourCountry(Integer.valueOf(l_input_string_array[l_i + 1]), Integer.valueOf(l_input_string_array[l_i + 2]));
                    l_i += 2;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 2 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeNeighbourCountry(Integer.valueOf(l_input_string_array[l_i + 1]), Integer.valueOf(l_input_string_array[l_i + 2]));
                    l_i += 2;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    @Override
    public void saveMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        if (l_input_string_array.length > 1) {
            if (d_ge.get_currentMap().validateMap()) {

                d_ge.get_currentMap().saveMap(l_input_string_array[1]);
                System.out.println("Map saved");
                d_ge.set_currentMap(new WarMap());
                MapEditor.editMap(l_input_string_array[1], d_ge.get_currentMap());
            } else {
                System.out.println("Map not saved due to being invalid");
            }
        } else {
            System.out.println("Reached end of file while parsing map not saved");
        }
    }

    @Override
    public void validateMap() {
        if (d_ge.get_currentMap().validateMap()) System.out.println("Valid Map");
        else System.out.println("InValid Map");
    }

    @Override
    public void next() {
        d_ge.setPhase(new MainMenu(d_ge));
    }
}
