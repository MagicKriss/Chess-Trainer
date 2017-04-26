package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;


public class Rook extends Piece {
    Rook(char file, int rank, PieceColor pieceColor) throws Exception {
        super(file, rank, pieceColor);
    }


    @Override
    public List<Square> legalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    rankDirection = 0;
                    fileDirection = 1;
                case 1:
                    rankDirection = 0;
                    fileDirection = -1;
                case 2:
                    rankDirection = -1;
                    fileDirection = 0;
                case 3:
                    rankDirection = -1;
                    fileDirection = -0;
            }
            legalMoves = LegalMoveUtil.getLegalMoves(legalMoves,this,fileDirection,rankDirection);
        }
        return legalMoves;
    }
}
