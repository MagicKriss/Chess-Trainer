package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }

    @Override
    public List<Square> getMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        Square checkSquare;
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    rankDirection = -2;
                    fileDirection = -1;
                    break;
                case 1:
                    rankDirection = -2;
                    fileDirection = 1;
                    break;
                case 2:
                    rankDirection = -1;
                    fileDirection = -2;
                    break;
                case 3:
                    rankDirection = -1;
                    fileDirection = 2;
                    break;
                case 4:
                    rankDirection = 1;
                    fileDirection = -2;
                    break;
                case 5:
                    rankDirection = 1;
                    fileDirection = 2;
                    break;
                case 6:
                    rankDirection = 2;
                    fileDirection = -1;
                    break;
                case 7:
                    rankDirection = 2;
                    fileDirection = 1;
                    break;
            }
            if (this.getSquare().getFile() + fileDirection > 104 || this.getSquare().getFile() + fileDirection < 97) {
                continue;
            } else if (this.getSquare().getRank() + rankDirection > 8 || this.getSquare().getRank() + rankDirection < 1) {
                continue;
            }
            checkSquare = Square.getSquare((char) (this.getSquare().getFile() + fileDirection), this.getSquare().getRank() + (rankDirection));
            if (!((King) Game.getKing(this.getColor())).checkForCheck(this.getSquare(), checkSquare)) { // check if after this setHasMoved same color king will be under check
                if (!checkSquare.isOccupied()) {
                    legalMoves.add(checkSquare);
                } else if (checkSquare.getPiece().getColor() != this.getColor()) {
                    legalMoves.add(checkSquare);
                }
            }
        }
        return legalMoves;
    }

    public String getPieceName() {
        return "Knight";
    }
}
