package com.chessEngine.game;

import com.chessEngine.pieces.*;

import java.awt.*;


public class Square {
    public static final Color DARK = new Color(102, 85, 66);
    public static final Color LIGHT = new Color(237, 224, 208);
    public static final Color HIGHLIGHT = new Color(234, 119, 119);
    private final char file;
    private final int rank;
    private boolean occupied;
    private Piece pieceOnTile;
    private Color color;

    Square(char file, int rank) {
        this.file = file;
        this.rank = rank;
        occupied = false;
        pieceOnTile = null;
        color = (file - 97 + rank) % 2 == 0 ? LIGHT : DARK;
        Game.Board.getBOARD().put(this.toString(), this);
    }

    public void setToNormalColor(){
        this.color = (this.file - 97 + this.rank) % 2 == 0 ? LIGHT : DARK;

    }
    public void setToHighlight(){
        color = HIGHLIGHT ;
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
    public void removePiece(){
        this.pieceOnTile = null;
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
