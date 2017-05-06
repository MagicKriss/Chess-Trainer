package com.chess.game;

import com.chess.Color;
import com.chess.pieces.*;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static Game game;
    private static final Player whitePlayer = new Player(Color.WHITE);
    private static final Player blackPlayer = new Player(Color.BLACK);
    private static Player playerToMove;
    private static Square whiteKingSquare;
    private static Square blackKingSquare;

    private Game() throws Exception {
        Board.BoardBuilder.defaultBoard();
       /*
       for (int i = 0; i < 8; i++) {
            whitePlayer.addControlledSquare(String.valueOf((char) (97 + i)) + 1);
            whitePlayer.addControlledSquare(String.valueOf((char) (97 + i)) + 2);
            blackPlayer.addControlledSquare(String.valueOf((char) (97 + i)) + 7);
            blackPlayer.addControlledSquare(String.valueOf((char) (97 + i)) + 8);
        }
        */
        playerToMove = whitePlayer;
        whiteKingSquare = Board.getBOARD().get("e1");
        blackKingSquare = Board.getBOARD().get("e8");
    }

    public static Game getGame() {
        return game;
    }
    public static Game getNewGame() throws Exception {
        return new Game();
    }

    /*
    static {
        try {
            game = new Game();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public static Square getKingSquare(Color color) {
        return color == Color.WHITE ? whiteKingSquare : blackKingSquare;
    }

    public static Player getPlayerToMove() {
        return playerToMove;
    }

    public static void togglePlayerToMove() {
        playerToMove = playerToMove.getColor() == Color.WHITE ? blackPlayer : whitePlayer;
    }

    public static boolean move(Player player, Square fromSquare, Square toSquare) {
        Piece pieceToMove;
        if (player.getColor() == playerToMove.getColor() && fromSquare.isOccupied()) {
            pieceToMove = fromSquare.getPiece();
            if (pieceToMove.getColor() == player.getColor() && pieceToMove.getMoves().contains(toSquare)) {
                toSquare.setPieceOnTile(pieceToMove);
                fromSquare.setPieceOnTile(null);
                fromSquare.toggleOccupied();
                if (pieceToMove.getClass() == Pawn.class) {
                    ((Pawn) pieceToMove).pawnMove();
                }
                pieceToMove.setSquare(toSquare);
                return true;
            }
        }
        return false;
    }


    public static class Board {
        private static Map<String, Square> BOARD = new HashMap<String, Square>();

        public static Map<String, Square> getBOARD() {
            return BOARD;
        }

        public static class BoardBuilder {
            public static void emptyBoard() {
                BOARD.clear();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        BOARD.put(String.valueOf((char) (i + 97)) + (j + 1), new Square((char) (i + 97), j + 1));
                    }
                }
            }

            public static void defaultBoard() throws Exception {
                emptyBoard();
                BOARD.get("a1").setPieceOnTile(new Rook('a', 1, Color.WHITE));
                BOARD.get("b1").setPieceOnTile(new Knight('b', 1, Color.WHITE));
                BOARD.get("c1").setPieceOnTile(new Bishop('c', 1, Color.WHITE));
                BOARD.get("d1").setPieceOnTile(new Queen('d', 1, Color.WHITE));
                BOARD.get("e1").setPieceOnTile(new King('e', 1, Color.WHITE));
                BOARD.get("f1").setPieceOnTile(new Bishop('f', 1, Color.WHITE));
                BOARD.get("g1").setPieceOnTile(new Knight('g', 1, Color.WHITE));
                BOARD.get("h1").setPieceOnTile(new Rook('h', 1, Color.WHITE));
                for (int i = 0; i < 8; i++) {
                    BOARD.get(String.valueOf((char) (i + 97)) + 2).setPieceOnTile(new Pawn((char) (i + 97), 2, Color.WHITE));
                }
                for (int i = 0; i < 8; i++) {
                    BOARD.get(String.valueOf((char) (i + 97)) + 7).setPieceOnTile(new Pawn((char) (i + 97), 7, Color.BLACK));
                }
                BOARD.get("a8").setPieceOnTile(new Rook('a', 8, Color.BLACK));
                BOARD.get("b8").setPieceOnTile(new Knight('b', 8, Color.BLACK));
                BOARD.get("c8").setPieceOnTile(new Bishop('c', 8, Color.BLACK));
                BOARD.get("d8").setPieceOnTile(new Queen('d', 8, Color.BLACK));
                BOARD.get("e8").setPieceOnTile(new King('e', 8, Color.BLACK));
                BOARD.get("f8").setPieceOnTile(new Bishop('f', 8, Color.BLACK));
                BOARD.get("g8").setPieceOnTile(new Knight('g', 8, Color.BLACK));
                BOARD.get("h8").setPieceOnTile(new Rook('h', 8, Color.BLACK));
            }
        }
    }

}
