package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Game;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
    }

    @Override
    public List<Square> getLegalMoves() {
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
                case 1:
                    rankDirection = -2;
                    fileDirection = 1;
                case 2:
                    rankDirection = -1;
                    fileDirection = -2;
                case 3:
                    rankDirection = -1;
                    fileDirection = 2;
                case 4:
                    rankDirection = 1;
                    fileDirection = -2;
                case 5:
                    rankDirection = 1;
                    fileDirection = 2;
                case 6:
                    rankDirection = 2;
                    fileDirection = -1;
                case 7:
                    rankDirection = 2;
                    fileDirection = 1;
            }
            if (this.getFile() + fileDirection > 104 || this.getFile() + fileDirection < 97) {
                continue;
            } else if (this.getRank() + rankDirection > 8 || this.getRank() + rankDirection < 1) {
                continue;
            }
            checkSquare = Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + (rankDirection));
            if (!((King) Game.getKingSquare(this.getColor()).getPiece()).checkForCheck(this.getSquare(), checkSquare)) { // check if after this move same color king will be under check
                if (!checkSquare.isOccupied()) {
                    legalMoves.add(checkSquare);
                } else if (checkSquare.getPiece().getColor() != this.getColor()) {
                    legalMoves.add(checkSquare);
                }
            }
        }
        return null;
    }
}
