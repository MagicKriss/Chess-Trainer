package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.List;

public abstract class Piece {
    private char file;
    private int rank;
    private final PieceColor pieceColor;

    Piece(char file, int rank, PieceColor pieceColor) throws Exception {
        if (Square.getSquare(file, rank).isOccupied()) {
            throw new Exception("This Square is occupied");
        }
        this.file = file;
        this.rank = rank;
        this.pieceColor = pieceColor;
        this.getSquare().toggleOccupied();
    }


    public Square getSquare() {
        return Square.getSquare(file, rank);
    }

    public char getFile(){
        return file;
    }

    public int getRank() {
        return rank;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public abstract List<Square> legalMoves();


}