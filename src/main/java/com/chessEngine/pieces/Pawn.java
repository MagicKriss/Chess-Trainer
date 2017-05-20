package com.chessEngine.pieces;

import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {


    public Pawn(char file, int rank, Color color) throws OccupiedSquareException {
        super(file, rank, color);
    }

    @Override
    public Set<Square> getMoves() {
        Set<Square> moves = new HashSet();
        int direction = getColor() == Color.WHITE ? 1 : -1; // White pawns setHasMoved upward, black pawns setHasMoved downward
        Square checkSquare;
        checkSquare = Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction);
        if (!checkSquare.isOccupied()) {
            moves.add(checkSquare);
            if (!Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction * 2).isOccupied() && !this.getHasMoved()) {
                moves.add(Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction * 2));
            }
        }
        for (int i = -1; i < 2; i += 2) {
            checkSquare = Square.getSquare((char) (this.getSquare().getFile() + i), this.getSquare().getRank() + direction);
            if (checkSquare != null) {
                if (checkSquare.isOccupied() && checkSquare.getPiece().getColor() != this.getColor()) {
                    moves.add(checkSquare);
                }
            }
        }
        return moves;
    }

    public String getPieceName() {
        return "Pawn";
    }

    // TODO
    // pawn promotion
    //TODO
    // en passant

}
