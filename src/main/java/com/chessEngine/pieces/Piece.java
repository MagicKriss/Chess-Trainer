package com.chessengine.pieces;


import com.chessengine.game.Square;

import java.awt.*;
import java.util.Set;

public abstract class Piece {
    private Square square;
    private final Color color;
    private boolean hasMoved;

    Piece(char file, int rank, Color color) throws OccupiedSquareException {
        if (Square.getSquare(file, rank).isOccupied()) {
            throw new OccupiedSquareException("This Square is occupied");
        }

        this.square = Square.getSquare(file, rank);
        this.color = color;
        this.getSquare().toggleOccupied();
        hasMoved = false;
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Color getColor() {
        return color;
    }

    public abstract Set<Square> getMoves();

    public abstract String getPieceName();

    public class OccupiedSquareException extends Exception {
        OccupiedSquareException(String message) {
            super(message);
        }
    }


}