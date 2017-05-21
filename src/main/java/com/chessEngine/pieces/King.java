package com.chessEngine.pieces;

import com.chessEngine.game.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 17.26.4.
 */
public class King extends Piece {
    private boolean check;

    public King(char file, int rank, Color color) throws OccupiedSquareException {
        super(file, rank, color);
        check = false;
    }

    public King(char file, int rank, Color color, boolean hasMoved) throws OccupiedSquareException {
        super(file, rank, color);
        if (hasMoved) {
            this.setHasMoved();
        }    }

    public void toggleCheck() {
        check = !check;
    }

    public boolean getCheck() {
        return check;
    }

   /*  private boolean checkPieceForThreat(Square square) {
        if (square.isOccupied() && square.getPiece().getColor() != this.getColor()) {
            if (square.getPiece().getMoves() != null && square.getPiece().getMoves().contains(this.getSquare())) {
                return true;
            }
        }
        return false;
    }

   public boolean checkForCheck(Square squareBeforeMove, Square squareAfterMove) {
        int foo;
        int kingRank = this.getSquare().getRank();
        int kingFile = this.getSquare().getFile();
        boolean isCheck = false;
        Piece capturedPiece = null;
        if(squareAfterMove.isOccupied()){
            capturedPiece = squareAfterMove.getPiece();
        }else {
            squareAfterMove.toggleOccupied();
        }
        squareAfterMove.setPieceOnTile(squareBeforeMove.getPiece());
        squareBeforeMove.toggleOccupied();
        if (kingFile == squareBeforeMove.getFile()) {
            if (squareAfterMove.getFile() == kingFile) {
            } else if (kingRank - squareBeforeMove.getRank() < 0) {
                for (int i = kingRank + 1; i < 9; i++) {
                    if (checkPieceForThreat(Square.getSquare((char) kingFile, i))) {
                        isCheck = true;
                    }
                }
            } else {
                for (int i = kingRank - 1; i > 0; i--) {
                    if (checkPieceForThreat(Square.getSquare((char) kingFile, i))) {
                        isCheck = true;
                    }
                }
            }

        } else if (kingRank == squareBeforeMove.getRank()) {
            if (squareAfterMove.getRank() == kingRank) {
            } else if (kingFile - squareBeforeMove.getFile() < 0) {
                for (int i = kingFile + 1; i < 105; i++) {
                    if (checkPieceForThreat(Square.getSquare((char) i, kingRank))) {
                        isCheck = true;
                    }
                }
            } else {
                for (int i = kingFile - 1; i > 96; i--) {
                    if (checkPieceForThreat(Square.getSquare((char) i , kingRank))) {
                        isCheck = true;
                    }
                }
            }

        } else if (kingFile + kingRank == squareBeforeMove.getFile() + squareBeforeMove.getRank()) {
            if (squareAfterMove.getRank() + squareAfterMove.getFile() == kingRank + kingFile) {
            } else if (kingRank - squareBeforeMove.getRank() < 0) {
                // moving from right - down to  left - up
                foo = 1;
                while(kingFile - foo > 96 && kingRank + foo < 9){
                    if (checkPieceForThreat(Square.getSquare((char) (kingFile - foo), kingRank + foo))) {
                        isCheck = true;
                    }
                   foo++;
                }
            } else {
                // moving from right - up to  left - down
                foo = kingRank -1;
                while(kingFile + foo > 96 && kingRank + foo > 0){
                    if (checkPieceForThreat(Square.getSquare((char) (kingFile - foo), kingRank - foo))) {
                        isCheck = true;
                    }
                    foo--;
                }
            }
        } else if (kingFile - kingRank == squareBeforeMove.getFile() - squareBeforeMove.getRank()) {
            if (squareAfterMove.getRank() - squareAfterMove.getFile() == kingRank - kingFile) {
            } else if (kingRank - squareBeforeMove.getRank() < 0) {
                // moving from  left - down to  right - up
                foo = 1;
                while(kingFile + foo < 105 && kingRank + foo < 9){
                    if (checkPieceForThreat(Square.getSquare((char) (kingFile + foo), kingRank + foo))) {
                        isCheck = true;
                    }
                    foo ++;
                }
            } else {
                // moving from left - up to right - down
                foo = kingRank - 1;
                while(kingFile + foo < 105 && kingRank + foo > 0){
                    if (checkPieceForThreat(Square.getSquare((char) (kingFile + foo), kingRank - foo))) {
                        isCheck = true;
                    }
                    foo --;
                }
            }
        }
        if(capturedPiece != null){
            squareAfterMove.setPieceOnTile(capturedPiece);
        }else {
            squareAfterMove.toggleOccupied();
        }
        squareBeforeMove.toggleOccupied();
        return isCheck;
    }
*/
    //TODO
    // castling

    @Override
    public Set<Square> getMoves() {
        Set<Square> moves = new HashSet();
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
