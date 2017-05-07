package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

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
                if (!((King) Game.getKing(piece.getColor())).checkForCheck(piece.getSquare(), checkSquare)) { // check if after this setHasMoved same color king will be under check
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