package com.chess.pieces;


import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    Bishop(char file, int rank, PieceColor pieceColor) throws Exception {
        super(file, rank, pieceColor);
    }

    @Override
    public List<Square> legalMoves() {
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
                case 1:
                    rankDirection = 1;
                    fileDirection = -1;
                case 2:
                    rankDirection = -1;
                    fileDirection = 1;
                case 3:
                    rankDirection = -1;
                    fileDirection = -1;
            }

            legalMoves = LegalMoveUtil.getLegalMoves(legalMoves,this,fileDirection,rankDirection);
        }

        return legalMoves;
    }
}
