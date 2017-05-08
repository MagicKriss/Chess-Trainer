package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Queen extends Piece {
    public Queen(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }

    @Override
    protected List<Square> getMoves() {
        List<Square> moves = new ArrayList<Square>();
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    rankDirection = -1;
                    fileDirection = -1;
                    break;
                case 1:
                    rankDirection = -1;
                    fileDirection = 0;
                    break;
                case 2:
                    rankDirection = -1;
                    fileDirection = 1;
                    break;
                case 3:
                    rankDirection = 0;
                    fileDirection = -1;
                    break;
                case 4:
                    rankDirection = 0;
                    fileDirection = 1;
                    break;
                case 5:
                    rankDirection = 1;
                    fileDirection = -1;
                    break;
                case 6:
                    rankDirection = 1;
                    fileDirection = 0;
                    break;
                case 7:
                    rankDirection = 1;
                    fileDirection = 1;
                    break;
            }
                    moves.addAll(MoveUtil.getPossibleMoves(moves, this, fileDirection, rankDirection));
        }
        return moves;
    }

    public String getPieceName() {
        return "Queen";
    }
}
