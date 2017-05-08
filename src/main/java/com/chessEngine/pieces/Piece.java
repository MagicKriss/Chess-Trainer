package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private Square square;
    private final Color color;
    private boolean hasMoved;

    Piece(char file, int rank, Color color) throws Exception {
        if (Square.getSquare(file, rank).isOccupied()) {
            throw new Exception("This Square is occupied");
        }

        this.square = Square.getSquare(file, rank);
        this.color = color;
        this.getSquare().toggleOccupied();
        hasMoved = false;
    }
    public void setHasMoved(){
        hasMoved = true;
    }
    public boolean getHasMoved () {
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

    protected abstract List<Square> getMoves();

    public List<Square> getLegalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        for (Square move : this.getMoves()) {
            if (!((King) Game.getKing(this.getColor())).checkForCheck(this.getSquare(), move)) {
                legalMoves.add(move);
            }
        }
        //TODO
        // en passant

        return legalMoves;
    }
    public abstract String getPieceName();


}