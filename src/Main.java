import View.GameEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Scanner SCANNER;
    public static void main(String[] args) throws IOException {
        GameEngine new_game = new GameEngine();
        new_game.start_game();
    }
}