package com.chessEngine.pieces;


import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }

    @Override
    public List<Square> getMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        Square checkSquare;
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    rankDirection = 1;
                    fileDirection = 1;
                    break;
                case 1:
                    rankDirection = 1;
                    fileDirection = -1;
                    break;
                case 2:
                    rankDirection = -1;
                    fileDirection = 1;
                    break;
                case 3:
                    rankDirection = -1;
                    fileDirection = -1;
                    break;
            }

            legalMoves.addAll(MoveUtil.getLegalMoves(legalMoves, this,fileDirection,rankDirection));
        }

        return legalMoves;
    }

    public String getPieceName() {
        return "Bishop";
    }
}
