package Phases;

import Adapter.MapEditorAdapter;
import Controller.GameEngine;
import Controller.MapEditor;
import Models.WarMap;
import logging.LogWriter;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * Map editing phase for after a map has been loading
 */
public class Postload extends Edit {
    /**
     * The constructor of the postload phase
     *
     * @param p_ge The Game Engine
     */
    public Postload(GameEngine p_ge) {
        super(p_ge);
        d_logentrybuffer.writeLog("POSTLOAD PHASE");
    }

    /**
     * The display options function of the postload phase
     */
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

    /**
     * The Loadmap function of the postload phase
     *
     * @throws IOException Exception if IO error occurs
     */
    @Override
    public void loadMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");

        if (l_input_string_array.length > 1 && l_input_string_array[1] != null) {
            d_ge.set_currentMap(new WarMap());
            MapEditor.editMap(l_input_string_array[1], d_ge.get_currentMap());
            d_logentrybuffer.writeLog("editmap " + l_input_string_array[1] + " runned successfully");
        } else {
            System.out.println("Could not load map");
        }
    }

    /**
     * The showMap function of the postload phase
     */
    @Override
    public void showMap() {
        d_ge.get_currentMap().showMap();
        d_logentrybuffer.writeLog("showmap runned successfully");
    }

    /**
     * The edit country function of the postload phase
     */
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
                        d_logentrybuffer.writeLog("Country " + l_input_string_array[l_i + 2] + "with ID" + l_input_string_array[l_i + 1] + "added successfully in continentID" + l_input_string_array[l_i + 3]);
                        l_i += 3;
                    }
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 1 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeCountry(Integer.valueOf(l_input_string_array[l_i + 1]));
                    d_logentrybuffer.writeLog("Country with ID " + l_input_string_array[l_i + 1] + "removed successfully.");
                    l_i++;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    /**
     * The edit continent function of the postload phase
     */
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
                        d_logentrybuffer.writeLog("Continent " + l_input_string_array[l_i + 2] + "with ID" + l_input_string_array[l_i + 1] + "added successfully with army bonus of " + l_input_string_array[l_i + 3]);
                        l_i += 3;
                    }
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 1 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeContinent(Integer.valueOf(l_input_string_array[l_i + 1]));
                    d_logentrybuffer.writeLog("Continent with ID " + l_input_string_array[l_i + 1] + "removed successfully.");
                    l_i++;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    /**
     * The edit neighbours function of the postload phase
     */
    @Override
    public void editNeighbours() {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        for (int l_i = 1; l_i < l_input_string_array.length; l_i++) {
            if (l_input_string_array[l_i].equalsIgnoreCase("-add")) {
                if (l_i + 2 < l_input_string_array.length) {
                    d_ge.get_currentMap().addNeighbourCountry(Integer.valueOf(l_input_string_array[l_i + 1]), Integer.valueOf(l_input_string_array[l_i + 2]));
                    d_logentrybuffer.writeLog("Neighbour with ID" + l_input_string_array[l_i + 2] + " added to Country with ID " + l_input_string_array[l_i + 1]);
                    l_i += 2;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            } else if (l_input_string_array[l_i].equalsIgnoreCase("-remove")) {
                if (l_i + 2 < l_input_string_array.length) {
                    d_ge.get_currentMap().removeNeighbourCountry(Integer.valueOf(l_input_string_array[l_i + 1]), Integer.valueOf(l_input_string_array[l_i + 2]));
                    d_logentrybuffer.writeLog("Neighbour with ID" + l_input_string_array[l_i + 2] + " removed from Country with ID " + l_input_string_array[l_i + 1]);
                    l_i += 2;
                } else {
                    System.out.println("Reached end of file while parsing not all commands completed");
                }
            }
        }
    }

    /**
     * The save map function of the postload phase
     *
     * @throws IOException Exception if IO error occurs
     */
    @Override
    public void saveMap() throws IOException {
        String[] l_input_string_array = d_ge.getCurrentInput().split(" ");
        if (l_input_string_array.length > 1) {
            if (d_ge.get_currentMap().validateMap()) {
                if (l_input_string_array[1].matches("(?i).+\\.map"))
                    MapEditor.saveMap(l_input_string_array[1], d_ge.get_currentMap());
                if (l_input_string_array[1].matches("(?i).+\\.conquest"))
                    MapEditorAdapter.saveMap(l_input_string_array[1], d_ge.get_currentMap());

                System.out.println("Map saved");
                d_logentrybuffer.writeLog(l_input_string_array[1] + " Map saved successfully.");
                d_ge.set_currentMap(new WarMap());
                MapEditor.editMap(l_input_string_array[1], d_ge.get_currentMap());
            } else {
                System.out.println("Map not saved due to being invalid");
            }
        } else {
            System.out.println("Reached end of file while parsing map not saved");
        }
    }

    /**
     * The validate map function of the postload phase
     */
    @Override
    public void validateMap() {
        if (d_ge.get_currentMap().validateMap()) System.out.println("Valid Map");
        else System.out.println("InValid Map");
        d_logentrybuffer.writeLog("Validate Command executed successfully.");
    }

    /**
     * The next function of the postload phase
     */
    @Override
    public void next() {
        if (d_ge.getCurrentInput().equalsIgnoreCase("quit")) {
            try {
                LogWriter.getInstance().d_info.close();
                exit(0);
            } catch (IOException e) {
                System.out.println("I/O exception closing BufferedWriter");
            }
        }
        d_ge.setPhase(new MainMenu(d_ge));
    }
}
