package com.chess.pieces;

import com.chess.game.Game;
import com.chess.game.Square;

import java.util.List;


public final class MoveUtil {
    /*/
    / /   This method is used to help calculate legal moves for Bishop, Rook and Queen
    /*/

    public static List<Square> getLegalMoves(List<Square> legalMoves, Piece piece, int fileDirection, int rankDirection) {
        Square checkSquare;
        for (int i = 0; i < 8; i++) {
            checkSquare = Square.getSquare((char) (piece.getSquare().getFile() + (i + 1) * fileDirection), piece.getSquare().getRank() + (i + 1) * rankDirection);
            if (checkSquare != null) {
                if (!((King) Game.getKingSquare(piece.getColor()).getPiece()).checkForCheck(piece.getSquare(), checkSquare)) { // check if after this move same color king will be under check
                    if (!checkSquare.isOccupied()) {
                        legalMoves.add(checkSquare);
                    } else if (checkSquare.getPiece().getColor() != piece.getColor()) {
                        legalMoves.add(checkSquare);
                        break;
                    } else break;
                }
            }
        }
        return legalMoves;
    }
}