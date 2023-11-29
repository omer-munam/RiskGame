package Adapter;

import Controller.MapEditor;
import Models.WarMap;

import java.io.IOException;

public class MapEditorAdapter extends MapEditor {
    private MapEditorConquest otherMapEditor;

    public MapEditorAdapter(MapEditorConquest p_fr) {
        // the roundPeg is plugged into the adapter
        this.otherMapEditor = p_fr;
    }

    public WarMap readMap(String p_filename) throws IOException {
        return otherMapEditor.readMap(p_filename);
    }

    public void saveMap(String p_filename, WarMap p_warMap) {
        otherMapEditor.saveMap(p_filename, p_warMap);
    }

    public WarMap editMap(String p_filename) throws IOException {
        return otherMapEditor.editMap(p_filename);
    }
}
