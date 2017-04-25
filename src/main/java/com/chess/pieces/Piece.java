package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.List;

public abstract class Piece {
    protected char file;
    protected int rank;
    protected final PieceColor pieceColor;

    Piece(char file, int rank, PieceColor pieceColor) throws Exception {
        if(Square.getSquare(file,rank).isOccupied()){
            throw new Exception("This Square is occupied");
        }
        this.file = file;
        this.rank = rank;
        this.pieceColor = pieceColor;
        this.getSquare().toggleOccupied();
    };

    public Square getSquare (){
        return  Square.getSquare(file,rank);
    };

    public abstract List<Square> legalMoves ();


}