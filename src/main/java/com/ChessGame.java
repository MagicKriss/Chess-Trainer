package com;

import com.GUI.Table;
import com.chessEngine.game.Game;

/**
 * Created by Krisjanis on 07.05.2017.
 */
public class ChessGame {
    private static Table table;
    private static Game game;

    public static void main(String[] args) throws Exception {
        game = Game.newGame();
        table = Table.newTable();
    }

}
