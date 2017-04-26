package com.chess.game;

import com.chess.pieces.Piece;

import java.util.HashMap;
import java.util.Map;


public class Square {

    private final char file;
    private final int rank;
    private boolean occupied;
    private Piece pieceOnTile;
    private static final Map<String, Square> SQUARES = new HashMap<String, Square>();

    Square(char file, int rank) {
        this.file = file;
        this.rank = rank;
        occupied = true;
        pieceOnTile = null;
        SQUARES.put(this.toString(), this);
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
    }

    public Piece getPiece() {
        return pieceOnTile;
    }

    public String squareToString() {
        return String.valueOf(file + rank);
    }

    public static Square getSquare(char file, int rank) {
        return SQUARES.get(String.valueOf(file + rank));
    }


}
