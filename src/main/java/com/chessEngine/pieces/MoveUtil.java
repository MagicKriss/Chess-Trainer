package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.util.List;
import java.util.Set;


public final class MoveUtil {
    /*/
    / /   This method is used to help calculate possible moves for Bishop, Rook and Queen
    /*/

    public static Set<Square> getPossibleMoves(Set<Square> moves, Piece piece, int fileDirection, int rankDirection) {
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
            }else{
                break;
            }
        }
        return moves;
    }
}