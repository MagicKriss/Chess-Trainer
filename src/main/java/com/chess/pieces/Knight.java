package com.chess.pieces;

import com.chess.PieceColor;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    Knight(char file, int rank, PieceColor pieceColor) throws Exception {
        super(file, rank, pieceColor);
    }

    @Override
    public List<Square> legalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
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

            if (!Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + rankDirection).isOccupied()) {
                legalMoves.add(Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + (rankDirection)));
            } else if (Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + rankDirection).getPiece().getPieceColor() != this.getPieceColor()) {
                legalMoves.add(Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + rankDirection));
            }
        }
        return null;
    }
}
