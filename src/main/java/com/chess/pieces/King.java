package com.chess.pieces;

import com.chess.Color;
import com.chess.game.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17.26.4.
 */
public class King extends Piece {
    private boolean check;
    public King(char file, int rank, Color color) throws Exception {
        super(file, rank, color);
        check = false;
    }

    public void toggleCheck (){
        check =!check;
    }

    public boolean getCheck(){
        return check;
    }

    @Override
    public List<Square> legalMoves() {
        List<Square> legalMoves = new ArrayList<Square>();
        Square checkSquare;
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    rankDirection = -1;
                    fileDirection = -1;
                case 1:
                    rankDirection = -1;
                    fileDirection = 0;
                case 2:
                    rankDirection = -1;
                    fileDirection = 1;
                case 3:
                    rankDirection = 0;
                    fileDirection = -1;
                case 4:
                    rankDirection = 0;
                    fileDirection = 1;
                case 5:
                    rankDirection = 1;
                    fileDirection = -1;
                case 6:
                    rankDirection = 1;
                    fileDirection = 0;
                case 7:
                    rankDirection = 1;
                    fileDirection = 1;
            }

            checkSquare = Square.getSquare((char) (this.getFile() + fileDirection), this.getRank() + rankDirection);
            if (checkSquare != null) {
                if (!checkSquare.isOccupied()) {
                    legalMoves.add(checkSquare);
                } else if (checkSquare.getPiece().getColor() != this.getColor()) {
                    legalMoves.add(checkSquare);
                }
            }
        }

        return legalMoves;
    }
}
