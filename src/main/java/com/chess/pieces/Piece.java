package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Square;

import java.util.List;

public abstract class Piece {
    private char file;
    private int rank;
    private final Color color;

    Piece(char file, int rank, Color color) throws Exception {
        if (Square.getSquare(file, rank).isOccupied()) {
            throw new Exception("This Square is occupied");
        }
        this.file = file;
        this.rank = rank;
        this.color = color;
        this.getSquare().toggleOccupied();
    }


    public Square getSquare() {
        return Square.getSquare(file, rank);
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public Color getColor() {
        return color;
    }

    public abstract List<Square> getLegalMoves();


}