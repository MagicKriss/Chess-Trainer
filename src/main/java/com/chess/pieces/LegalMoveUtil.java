package com.chess.pieces;

import com.chess.game.Square;

import java.util.List;


public final class LegalMoveUtil {
    /*/
    / /   This method is used to help calculate legal moves for Bishop, Rook and Queen
    /*/
    public static List<Square> getLegalMoves(List<Square> legalMoves, Piece piece, int fileDirection, int rankDirection) {
        Square checkSquare;
        for (int i = 0; i < 8; i++) {
            checkSquare = Square.getSquare((char) (piece.getFile() + (i + 1) * fileDirection), piece.getRank() + (i + 1) * rankDirection);
            if (checkSquare != null) {
                if (!checkSquare.isOccupied()) {
                    legalMoves.add(checkSquare);
                } else if (checkSquare.getPiece().getColor() != piece.getColor()) {
                    legalMoves.add(checkSquare);
                    break;
                } else break;
            }
        }
        return legalMoves;
    }
}
