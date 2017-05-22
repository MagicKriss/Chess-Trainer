package com.chessengine.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by krisjanis.kallings on 5/21/2017.
 */
public class FENParser {

    public static String[] parseFenFile(String path) throws IOException {
        // fen[0] = fen notation
        // fen[1] = level move list
        // fen [2] = level hint
        String[] fen = new String[3];
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        try {
            fen[0] = br.readLine();
            fen[1] = br.readLine();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append('\n');
                line = br.readLine();
            }
            fen[2] = sb.toString();
        } finally {
            br.close();
        }
        return fen;
    }
}
