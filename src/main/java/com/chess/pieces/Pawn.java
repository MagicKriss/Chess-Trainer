package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean hasMoved;

    Pawn(char file, int rank, PieceColor pieceColor) throws Exception {
        super(file, rank, pieceColor);
        hasMoved = false;
    }

    @Override
    public List<Square> legalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        int direction = getPieceColor() == PieceColor.WHITE ? 1 : -1; // White pawns move upward, black pawns move downward
        Square checkSquare;
        checkSquare = Square.getSquare(getFile(), getRank() + direction);
        if (!checkSquare.isOccupied()) {
            legalMoves.add(checkSquare);
            if (!Square.getSquare(this.getFile(), this.getRank() + direction * 2).isOccupied() && !hasMoved) {
                legalMoves.add(Square.getSquare(this.getFile(), this.getRank() + direction * 2));
            }
        }
        for (int i = -1; i < 2; i += 2) {
            checkSquare = Square.getSquare((char) (this.getFile() + i), this.getRank() + direction);
            if (checkSquare.isOccupied() && checkSquare.getPiece().getPieceColor() != this.getPieceColor()) {
                legalMoves.add(checkSquare);
            }
        }
        //TODO
        // en passant

        return legalMoves;
    }

    // TODO
    // pawn promotion

}
