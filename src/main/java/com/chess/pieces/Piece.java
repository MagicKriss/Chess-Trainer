package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Game;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private Square square;
    private final Color color;

    Piece(char file, int rank, Color color) throws Exception {
        if (Square.getSquare(file, rank).isOccupied()) {
            throw new Exception("This Square is occupied");
        }

        this.square = Square.getSquare(file, rank);
        this.color = color;
        this.getSquare().toggleOccupied();
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

    public abstract List<Square> getMoves();

    public List<Square> getLegalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        for (Square move : this.getMoves()) {
            if (!((King) Game.getKingSquare(this.getColor()).getPiece()).checkForCheck(this.getSquare(), move)) {
                legalMoves.add(move);
            }
        }
        //TODO
        // en passant

        return legalMoves;
    }


}