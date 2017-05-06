package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Game;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean hasMoved;

    public Pawn(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
        hasMoved = false;
    }

    @Override
    public List<Square> getMoves() {
        List<Square> moves = new ArrayList<Square>();
        int direction = getColor() == Color.WHITE ? 1 : -1; // White pawns move upward, black pawns move downward
        Square checkSquare;
        checkSquare = Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction);
        if (!checkSquare.isOccupied()) {
            moves.add(checkSquare);
            if (!Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction * 2).isOccupied() && !hasMoved) {
                moves.add(Square.getSquare(this.getSquare().getFile(), this.getSquare().getRank() + direction * 2));
            }
        }
        for (int i = -1; i < 2; i += 2) {
            checkSquare = Square.getSquare((char) (this.getSquare().getFile() + i), this.getSquare().getRank() + direction);
            if (checkSquare != null) {
                if (checkSquare.isOccupied() && checkSquare.getPiece().getColor() != this.getColor()) {
                    moves.add(checkSquare);
                }
            }
        }
        return moves;
    }
    //            if (!((King) Game.getKingSquare(this.getColor()).getPiece()).checkForCheck(this.getSquare(), checkSquare)) { // check if after this move same color king will be under check

    public void pawnMove() {
        hasMoved = true;
    }

    // TODO
    // pawn promotion

}
