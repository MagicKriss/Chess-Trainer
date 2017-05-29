package com.chessengine.pieces;

import com.chessengine.game.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(char file, int rank, Color color) throws OccupiedSquareException {
        super(file, rank, color);
    }

    public King(char file, int rank, Color color, boolean hasMoved) throws OccupiedSquareException {
        super(file, rank, color);
        if (hasMoved) {
            this.setHasMoved();
        }
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

            checkSquare = Square.getSquare((char) (this.getSquare().getFile() + fileDirection), this.getSquare().getRank() + rankDirection);
            if (checkSquare != null) {
                if (!checkSquare.isOccupied() || checkSquare.getPiece().getColor() != this.getColor()) {
                    moves.add(checkSquare);
                }
            }
        }

        return moves;
    }

    public String getPieceName() {
        return "King";
    }
}
