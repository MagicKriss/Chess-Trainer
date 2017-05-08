package com.chessEngine.pieces;

import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Rook extends Piece {
    public Rook(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }


    @Override
    public List<Square> getMoves() {
        List<Square> moves = new ArrayList<Square>();
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    rankDirection = 0;
                    fileDirection = 1;
                    break;
                case 1:
                    rankDirection = 0;
                    fileDirection = -1;
                    break;
                case 2:
                    rankDirection = -1;
                    fileDirection = 0;
                    break;
                case 3:
                    rankDirection = -1;
                    fileDirection = -0;
                    break;
            }
            moves.addAll(MoveUtil.getPossibleMoves(moves, this, fileDirection, rankDirection));
        }
        return moves;
    }

    public String getPieceName() {
        return "Rook";
    }
}
