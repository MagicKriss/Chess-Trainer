package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.List;

public class Pawn extends Piece {

    Pawn(char file, int rank, PieceColor pieceColor) throws Exception {
        super(file, rank, pieceColor);
    }

    public List<Square> legalMoves() {
        return null;
    }

}
