package com.chessengine.game;

import com.chessengine.pieces.*;

import java.awt.*;


public class Square {
    public static final Color DARK = new Color(181, 136, 99);
    public static final Color LIGHT = new Color(240, 217, 181);
    public static final Color HIGHLIGHT = new Color(234, 119, 119);
    public static final Color MOVED_LIGHT = new Color(248, 236, 116);
    public static final Color MOVED_DARK = new Color(218, 195, 75);

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
        Game.Board.getBoardMap().put(this.toString(), this);
    }

    public void setToNormalColor(){
        this.color = (this.file - 97 + this.rank) % 2 == 0 ? LIGHT : DARK;

    }
    public void setToHighlight(){
        color = HIGHLIGHT ;
    }

    public void setToMovedColor(){
        this.color = (this.file - 97 + this.rank) % 2 == 0 ? MOVED_LIGHT : MOVED_DARK;
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
        return Game.Board.getBoardMap().get(String.valueOf(file) + rank);
    }

}
