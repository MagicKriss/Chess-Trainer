package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Game;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;


public class Queen extends Piece {
    public Queen(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }

    @Override
    public List<Square> getMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    rankDirection = -1;
                    fileDirection = -1;
                    break;
                case 1:
                    rankDirection = -1;
                    fileDirection = 0;
                    break;
                case 2:
                    rankDirection = -1;
                    fileDirection = 1;
                    break;
                case 3:
                    rankDirection = 0;
                    fileDirection = -1;
                    break;
                case 4:
                    rankDirection = 0;
                    fileDirection = 1;
                    break;
                case 5:
                    rankDirection = 1;
                    fileDirection = -1;
                    break;
                case 6:
                    rankDirection = 1;
                    fileDirection = 0;
                    break;
                case 7:
                    rankDirection = 1;
                    fileDirection = 1;
                    break;
            }
            for (Square move : MoveUtil.getLegalMoves(legalMoves, this, fileDirection, rankDirection)) {
                if (!((King) Game.getKingSquare(this.getColor()).getPiece()).checkForCheck(this.getSquare(), move)) { // check if after this move same color king will be under check
                    legalMoves.add(move); // if not, add this move to legal moves
                }
            }
            // legalMoves.addAll(MoveUtil.getMoves(legalMoves,this,fileDirection,rankDirection));

        }

        return legalMoves;
    }
}
