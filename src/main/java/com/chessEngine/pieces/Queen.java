package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.awt.*;
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
                if (!((King) Game.getKing(this.getColor())).checkForCheck(this.getSquare(), move)) { // check if after this setHasMoved same color king will be under check
                    legalMoves.add(move); // if not, add this setHasMoved to legal moves
                }
            }
            // legalMoves.addAll(MoveUtil.getMoves(legalMoves,this,fileDirection,rankDirection));

        }

        return legalMoves;
    }

    public String getPieceName() {
        return "Queen";
    }
}
