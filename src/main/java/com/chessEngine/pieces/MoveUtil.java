package com.chessengine.pieces;

import com.chessengine.game.Square;

import java.util.Set;


final class MoveUtil {
    /*/
    / /   This method is used to help calculate possible moves for Bishop, Rook and Queen
    /*/

    static Set<Square> getPossibleMoves(Set<Square> moves, Piece piece, int fileDirection, int rankDirection) {
        Square checkSquare;
        for (int i = 0; i < 8; i++) {
            checkSquare = Square.getSquare((char) (piece.getSquare().getFile() + (i + 1) * fileDirection), piece.getSquare().getRank() + (i + 1) * rankDirection);
            if (checkSquare != null) {
                if (!checkSquare.isOccupied()) {
                    moves.add(checkSquare);
                } else if (checkSquare.getPiece().getColor() != piece.getColor()) {
                    moves.add(checkSquare);
                    break;
                } else break;
            } else {
                break;
            }
        }
        return moves;
    }
}