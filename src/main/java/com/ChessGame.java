package com;

import com.GUI.Table;
import com.chessEngine.game.Game;

/**
 * Created by Krisjanis on 07.05.2017.
 */
public class ChessGame {

    public static void main(String[] args) throws Exception {
        Game game = Game.newGame();
        Table table = Table.newTable();
    }

}
