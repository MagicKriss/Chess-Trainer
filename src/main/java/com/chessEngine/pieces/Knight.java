package com.chessengine.pieces;

import com.chessengine.game.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    public Knight(char file, int rank, Color color) throws OccupiedSquareException {
        super(file, rank, color);
    }

    @Override
    public Set<Square> getMoves() {
        HashSet<Square> moves = new HashSet<>();
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
            if (!checkSquare.isOccupied()) {
                moves.add(checkSquare);
            } else if (checkSquare.getPiece().getColor() != this.getColor()) {
                moves.add(checkSquare);
            }
        }
        return moves;
    }

    public String getPieceName() {
        return "Knight";
    }
}
