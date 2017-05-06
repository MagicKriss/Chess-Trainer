package com.chess.game;

import com.chess.Color;
import com.chess.pieces.*;


public class Square {

    private final char file;
    private final int rank;
    private boolean occupied;
    private Piece pieceOnTile;
    private final Color color;

    Square(char file, int rank) {
        this.file = file;
        this.rank = rank;
        occupied = false;
        pieceOnTile = null;
        color = (file - 97 + rank) % 2 == 0 ? Color.WHITE : Color.BLACK;
        Game.Board.getBOARD().put(this.toString(), this);
    }

    public int getRank() {
        return rank;
    }

    public char getFile() {
        return file;
    }

    public void toggleOccupied() {
        occupied = !occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setPieceOnTile(Piece pieceOnTile) {
        this.pieceOnTile = pieceOnTile;
        if (!this.isOccupied()) {
            this.toggleOccupied();
        }
    }

    public Piece getPiece() {
        return pieceOnTile;
    }

    public Color getColor() {
        return color;
    }

    public String squareToString() {
        return String.valueOf(file) + rank;
    }

    public static Square getSquare(char file, int rank) {
        return Game.Board.getBOARD().get(String.valueOf(file) + rank);
    }

}
