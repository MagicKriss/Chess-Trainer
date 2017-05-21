package com.chessEngine.pieces;

import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Rook extends Piece {
    public Rook(char file, int rank, Color color) throws OccupiedSquareException {
        super(file, rank, color);
    }

    public Rook(char file, int rank, Color color, boolean hasMoved) throws OccupiedSquareException {
        super(file, rank, color);
        if (hasMoved) {
            this.setHasMoved();
        }
    }

    @Override
    public Set<Square> getMoves() {
        Set<Square> moves = new HashSet();
        // directions in witch to check for legal moves
        int rankDirection = 0;
        int fileDirection = 0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    rankDirection = -1;
                    fileDirection = 0;
                    break;
                case 1:
                    rankDirection = 0;
                    fileDirection = -1;
                    break;
                case 2:
                    rankDirection = 1;
                    fileDirection = 0;
                    break;
                case 3:
                    rankDirection = 0;
                    fileDirection = 1;
                    break;
            }
            moves = MoveUtil.getPossibleMoves(moves, this, fileDirection, rankDirection);
        }
        return moves;
    }

    public String getPieceName() {
        return "Rook";
    }
}
