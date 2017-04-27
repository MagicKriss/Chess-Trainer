package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean hasMoved;

    public Pawn(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
        hasMoved = false;
    }

    @Override
    public List<Square> legalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        int direction = getColor() == Color.WHITE ? 1 : -1; // White pawns move upward, black pawns move downward
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
            if (checkSquare.isOccupied() && checkSquare.getPiece().getColor() != this.getColor()) {
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
