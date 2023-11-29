package Adapter;

import Controller.MapEditor;
import Models.WarMap;

import java.io.IOException;

/**
 * Adapter class for reading/saving/editing conquest map types
 */
public class MapEditorAdapter extends MapEditor {
    /**
     * The conquest map type
     */
    private MapEditorConquest otherMapEditor;

    /**
     * Constructor for the map editor adapter that stores an instance of the conquest map editor
     *
     * @param p_conquestMapEditor the conquest map editor
     */
    public MapEditorAdapter(MapEditorConquest p_conquestMapEditor) {
        this.otherMapEditor = p_conquestMapEditor;
    }

    /**
     * Reads a map using the instance of the conquest map editor
     * @param p_filename The file name of the map
     * @return a Warmap that was read from the file
     * @throws IOException A file could not be read
     */
    public WarMap readMap(String p_filename) throws IOException {
        return otherMapEditor.readMap(p_filename);
    }

    /**
     * Saves a map using the instance of the conquest map editor
     * @param p_filename the file name of the map
     * @param p_warMap The warmap to be saved
     */
    public void saveMap(String p_filename, WarMap p_warMap) {
        otherMapEditor.saveMap(p_filename, p_warMap);
    }

    /**
     * Edits a map using the instance of the conquest map editor
     * @param p_filename The filename of the WarMap
     * @return The map that is to be edited
     * @throws IOException if the file cannot be read
     */
    public WarMap editMap(String p_filename) throws IOException {
        return otherMapEditor.editMap(p_filename);
    }
}
