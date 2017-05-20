package com.chessEngine.game;

import com.GUI.Table;
import com.chessEngine.pieces.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {
    public static final int LEVEL_COUNT = 5;
    private static Square lastFromSquare;
    private static Square lastToSquare;
    private static Game game;
    private static final Player whitePlayer = new Player(Color.WHITE);
    private static final Player blackPlayer = new Player(Color.BLACK);
    private static Player playerToMove;
    private static Player heroe;
    private static Player computer;
    private static List<NextMove> levelMoveList = new ArrayList<NextMove>();

    private Game() throws Exception {
        Board.BoardBuilder.emptyBoardWhiteSide();
    }

    public static void newLevel(String level) throws Exception {
        if (level.equals("level1")) {
            Board.BoardBuilder.level1();
        } else if (level.equals("level2")) {
            Board.BoardBuilder.level2();
        } else if (level.equals("level3")) {
            Board.BoardBuilder.level3();
        } else if (level.equals("level4")) {
            Board.BoardBuilder.level4();
        } else if (level.equals("level5")) {
            Board.BoardBuilder.level5();
        } else {
            Board.BoardBuilder.defaultBoard();
        }
    }

    public static Game getGame() {
        return game;
    }

    public static Game newGame() throws Exception {
        game = new Game();
        return game;
    }

    public static List<NextMove> getLevelMoveList() {
        return levelMoveList;
    }

    private static void setLevelMoveList(List<NextMove> nextMoveList) {
        Game.levelMoveList = nextMoveList;
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

    private static void setLastSquares(Square fromSquare, Square toSquare) {
        Game.lastFromSquare = fromSquare;
        Game.lastToSquare = toSquare;
    }

    public static Player getPlayerToMove() {
        return playerToMove;
    }

    public static Player getHeroe() {
        return heroe;
    }

    private static void setHeroe(Player heroe) {
        Game.heroe = heroe;
    }

    public static Player getComputer() {
        return computer;
    }

    private static void setComputer(Player computer) {
        Game.computer = computer;
    }

    public static void togglePlayerToMove() {
        playerToMove = playerToMove.getColor() == Color.WHITE ? blackPlayer : whitePlayer;
    }

    private static void setPlayerToMove(Player playerToMove) {
        Game.playerToMove = playerToMove;
    }

    public static boolean move(Player player, Square fromSquare, Square toSquare, int iter) {

        // TODO
        // need to handle moves when king is under check
        Piece pieceToMove;
        if (player.getColor() == playerToMove.getColor() && fromSquare.isOccupied()) {
            pieceToMove = fromSquare.getPiece();
            if (pieceToMove.getColor() == player.getColor() && pieceToMove.getMoves().contains(toSquare)) {
                if (fromSquare.equals(levelMoveList.get(iter).getFromSquare()) && toSquare.equals(levelMoveList.get(iter).getToSquare())) {
                    toSquare.setPieceOnTile(pieceToMove);
                    fromSquare.removePiece();
                    fromSquare.toggleOccupied();
                    if (!pieceToMove.getHasMoved()) {
                        pieceToMove.setHasMoved();
                    }
                    pieceToMove.setSquare(toSquare);
                    togglePlayerToMove();
                    if (lastToSquare != null) {
                        lastToSquare.setToNormalColor();
                        lastFromSquare.setToNormalColor();
                    }
                    setLastSquares(fromSquare, toSquare);
                    return true;
                } else {
                    Table.getTable().showMessage("Wrong move, try again!", "Wrong Move!");
                    return false;
                }
            }
        }
        return false;
    }


    public static class Board {
        private static Map<String, Square> BOARD = new LinkedHashMap<String, Square>();

        public static Map<String, Square> getBOARD() {
            return BOARD;
        }

        public static class BoardBuilder {
            public static void emptyBoardWhiteSide() {
                BOARD.clear();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        BOARD.put(String.valueOf((char) (i + 97)) + (j + 1), new Square((char) (i + 97), j + 1));
                    }
                }
            }

            public static void emptyBoardBlackSide() {
                BOARD.clear();
                for (int i = 7; i >= 0; i--) {
                    for (int j = 7; j >= 0; j--) {
                        BOARD.put(String.valueOf((char) (i + 97)) + (j + 1), new Square((char) (i + 97), j + 1));
                    }
                }
            }

            public static void defaultBoard() throws Exception {
                emptyBoardWhiteSide();
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
                Game.setPlayerToMove(whitePlayer);
            }

            public static void level1() throws Exception {
                emptyBoardWhiteSide();
                BOARD.get("a1").setPieceOnTile(new Rook('a', 1, Color.WHITE));
                BOARD.get("b1").setPieceOnTile(new Knight('b', 1, Color.WHITE));
                BOARD.get("e1").setPieceOnTile(new King('e', 1, Color.WHITE));
                BOARD.get("h1").setPieceOnTile(new Bishop('h', 1, Color.BLACK));

                BOARD.get("a2").setPieceOnTile(new Pawn('a', 2, Color.WHITE));
                BOARD.get("b2").setPieceOnTile(new Pawn('b', 2, Color.WHITE));
                BOARD.get("f2").setPieceOnTile(new Pawn('f', 2, Color.WHITE));
                BOARD.get("h2").setPieceOnTile(new Pawn('h', 2, Color.WHITE));

                BOARD.get("c3").setPieceOnTile(new Pawn('c', 3, Color.WHITE));
                BOARD.get("h3").setPieceOnTile(new Knight('h', 3, Color.WHITE));

                BOARD.get("d4").setPieceOnTile(new Pawn('d', 4, Color.WHITE));
                BOARD.get("f4").setPieceOnTile(new Queen('f', 4, Color.WHITE));

                BOARD.get("b6").setPieceOnTile(new Pawn('b', 6, Color.BLACK));
                BOARD.get("e6").setPieceOnTile(new King('e', 6, Color.BLACK));
                BOARD.get("f6").setPieceOnTile(new Knight('f', 6, Color.BLACK));
                BOARD.get("g6").setPieceOnTile(new Bishop('g', 6, Color.WHITE));


                BOARD.get("a7").setPieceOnTile(new Pawn('a', 7, Color.BLACK));
                BOARD.get("c7").setPieceOnTile(new Pawn('c', 7, Color.BLACK));
                BOARD.get("d7").setPieceOnTile(new Pawn('d', 7, Color.BLACK));
                BOARD.get("e7").setPieceOnTile(new Pawn('e', 7, Color.BLACK));
                BOARD.get("h7").setPieceOnTile(new Pawn('h', 7, Color.WHITE));

                BOARD.get("a8").setPieceOnTile(new Rook('a', 8, Color.BLACK));
                BOARD.get("b8").setPieceOnTile(new Knight('b', 8, Color.BLACK));
                BOARD.get("f8").setPieceOnTile(new Queen('f', 8, Color.BLACK));
                BOARD.get("h8").setPieceOnTile(new Rook('h', 8, Color.BLACK));

                Game.setPlayerToMove(blackPlayer);
                Game.setHeroe(whitePlayer);
                Game.setComputer(blackPlayer);
                ArrayList<NextMove> moveList = new ArrayList<NextMove>();
                moveList.add(new NextMove(BOARD.get("d7"), BOARD.get("d6")));
                moveList.add(new NextMove(BOARD.get("f4"), BOARD.get("f5")));
                setLevelMoveList(moveList);
            }

            public static void level2() throws Exception {
                emptyBoardWhiteSide();
                BOARD.get("d1").setPieceOnTile(new Rook('d', 1, Color.WHITE));
                BOARD.get("e1").setPieceOnTile(new King('e', 1, Color.WHITE));
                BOARD.get("h1").setPieceOnTile(new Rook('h', 1, Color.WHITE));

                BOARD.get("b2").setPieceOnTile(new Bishop('b', 2, Color.WHITE));
                BOARD.get("f2").setPieceOnTile(new Pawn('f', 2, Color.WHITE));

                BOARD.get("a3").setPieceOnTile(new Pawn('a', 3, Color.WHITE));
                BOARD.get("e3").setPieceOnTile(new Pawn('e', 3, Color.WHITE));
                BOARD.get("f3").setPieceOnTile(new Knight('f', 3, Color.WHITE));
                BOARD.get("h3").setPieceOnTile(new Pawn('h', 3, Color.WHITE));

                BOARD.get("b4").setPieceOnTile(new Pawn('b', 4, Color.WHITE));

                BOARD.get("c5").setPieceOnTile(new Pawn('c', 5, Color.WHITE));
                BOARD.get("d5").setPieceOnTile(new Pawn('d', 5, Color.BLACK));
                BOARD.get("g5").setPieceOnTile(new Pawn('g', 5, Color.WHITE));

                BOARD.get("c6").setPieceOnTile(new Knight('c', 6, Color.BLACK));
                BOARD.get("h6").setPieceOnTile(new Queen('h', 6, Color.WHITE));


                BOARD.get("a7").setPieceOnTile(new Pawn('a', 7, Color.BLACK));
                BOARD.get("c7").setPieceOnTile(new Pawn('c', 7, Color.BLACK));
                BOARD.get("d7").setPieceOnTile(new Queen('d', 7, Color.BLACK));
                BOARD.get("e7").setPieceOnTile(new Bishop('e', 7, Color.BLACK));
                BOARD.get("f7").setPieceOnTile(new Pawn('f', 7, Color.BLACK));
                BOARD.get("g7").setPieceOnTile(new Knight('g', 7, Color.BLACK));

                BOARD.get("a8").setPieceOnTile(new Rook('a', 8, Color.BLACK));
                BOARD.get("f8").setPieceOnTile(new Rook('f', 8, Color.BLACK));
                BOARD.get("g8").setPieceOnTile(new King('g', 8, Color.BLACK));


                Game.setPlayerToMove(blackPlayer);
                Game.setHeroe(whitePlayer);
                Game.setComputer(blackPlayer);
                ArrayList<NextMove> moveList = new ArrayList<NextMove>();
                moveList.add(new NextMove(BOARD.get("g7"), BOARD.get("f5")));
                moveList.add(new NextMove(BOARD.get("h6"), BOARD.get("h8")));
                setLevelMoveList(moveList);
            }

            public static void level3() throws Exception {
                emptyBoardWhiteSide();
                BOARD.get("a1").setPieceOnTile(new Rook('a', 1, Color.WHITE));
                BOARD.get("c1").setPieceOnTile(new Bishop('c', 1, Color.WHITE));
                BOARD.get("e1").setPieceOnTile(new Queen('e', 1, Color.WHITE));
                BOARD.get("f1").setPieceOnTile(new Rook('f', 1, Color.WHITE));
                BOARD.get("h1").setPieceOnTile(new King('h', 1, Color.WHITE));

                BOARD.get("a2").setPieceOnTile(new Pawn('a', 2, Color.WHITE));
                BOARD.get("b2").setPieceOnTile(new Pawn('b', 2, Color.WHITE));
                BOARD.get("c2").setPieceOnTile(new Pawn('c', 2, Color.WHITE));
                BOARD.get("g2").setPieceOnTile(new Pawn('g', 2, Color.WHITE));
                BOARD.get("h2").setPieceOnTile(new Pawn('h', 2, Color.WHITE));

                BOARD.get("c3").setPieceOnTile(new Knight('c', 3, Color.WHITE));

                BOARD.get("e4").setPieceOnTile(new Knight('e', 4, Color.WHITE));

                BOARD.get("a5").setPieceOnTile(new Knight('a', 5, Color.BLACK));

                BOARD.get("c6").setPieceOnTile(new Bishop('c', 6, Color.BLACK));
                BOARD.get("d6").setPieceOnTile(new Pawn('d', 6, Color.BLACK));
                BOARD.get("g6").setPieceOnTile(new Pawn('g', 6, Color.WHITE));
                BOARD.get("h6").setPieceOnTile(new Pawn('h', 6, Color.BLACK));

                BOARD.get("a7").setPieceOnTile(new Pawn('a', 7, Color.BLACK));
                BOARD.get("b7").setPieceOnTile(new Pawn('b', 7, Color.BLACK));
                BOARD.get("e7").setPieceOnTile(new Pawn('e', 7, Color.BLACK));

                BOARD.get("c8").setPieceOnTile(new Rook('c', 8, Color.BLACK));
                BOARD.get("d8").setPieceOnTile(new Queen('d', 8, Color.BLACK));
                BOARD.get("e8").setPieceOnTile(new King('e', 8, Color.BLACK));
                BOARD.get("f8").setPieceOnTile(new Bishop('f', 8, Color.BLACK));
                BOARD.get("h8").setPieceOnTile(new Rook('h', 8, Color.BLACK));


                Game.setPlayerToMove(blackPlayer);
                Game.setHeroe(whitePlayer);
                Game.setComputer(blackPlayer);
                ArrayList<NextMove> moveList = new ArrayList<NextMove>();
                moveList.add(new NextMove(BOARD.get("h8"), BOARD.get("g8")));
                moveList.add(new NextMove(BOARD.get("e4"), BOARD.get("f6")));
                setLevelMoveList(moveList);
            }

            public static void level4() throws Exception {
                emptyBoardWhiteSide();
                BOARD.get("f2").setPieceOnTile(new Pawn('f', 2, Color.WHITE));

                BOARD.get("c4").setPieceOnTile(new Rook('c', 4, Color.BLACK));
                BOARD.get("b4").setPieceOnTile(new Pawn('b', 4, Color.WHITE));

                BOARD.get("b5").setPieceOnTile(new Pawn('b', 5, Color.BLACK));
                BOARD.get("f5").setPieceOnTile(new Pawn('f', 5, Color.BLACK));

                BOARD.get("e6").setPieceOnTile(new King('e', 6, Color.WHITE));

                BOARD.get("b7").setPieceOnTile(new Rook('b', 7, Color.WHITE));

                BOARD.get("e8").setPieceOnTile(new King('e', 8, Color.BLACK));


                Game.setPlayerToMove(blackPlayer);
                Game.setHeroe(whitePlayer);
                Game.setComputer(blackPlayer);
                ArrayList<NextMove> moveList = new ArrayList<NextMove>();
                moveList.add(new NextMove(BOARD.get("c4"), BOARD.get("c3")));
                moveList.add(new NextMove(BOARD.get("b7"), BOARD.get("b8")));
                moveList.add(new NextMove(BOARD.get("c3"), BOARD.get("c8")));
                moveList.add(new NextMove(BOARD.get("b8"), BOARD.get("c8")));
                setLevelMoveList(moveList);
            }

            public static void level5() throws Exception {
                emptyBoardBlackSide();
                BOARD.get("c1").setPieceOnTile(new Rook('c', 1, Color.WHITE));
                BOARD.get("d1").setPieceOnTile(new Queen('d', 1, Color.WHITE));
                BOARD.get("f1").setPieceOnTile(new Rook('f', 1, Color.WHITE));
                BOARD.get("g1").setPieceOnTile(new King('g', 1, Color.WHITE));

                BOARD.get("g2").setPieceOnTile(new Pawn('g', 2, Color.WHITE));

                BOARD.get("a3").setPieceOnTile(new Pawn('a', 3, Color.WHITE));
                BOARD.get("b3").setPieceOnTile(new Pawn('b', 3, Color.WHITE));
                BOARD.get("c3").setPieceOnTile(new Knight('c', 3, Color.WHITE));
                BOARD.get("f3").setPieceOnTile(new King('f', 3, Color.WHITE));
                BOARD.get("h3").setPieceOnTile(new Pawn('h', 3, Color.WHITE));

                BOARD.get("e4").setPieceOnTile(new Pawn('e', 4, Color.WHITE));

                BOARD.get("d5").setPieceOnTile(new Pawn('d', 5, Color.WHITE));
                BOARD.get("h5").setPieceOnTile(new Bishop('h', 5, Color.BLACK));

                BOARD.get("c6").setPieceOnTile(new Knight('c', 6, Color.BLACK));
                BOARD.get("d6").setPieceOnTile(new Queen('d', 6, Color.BLACK));

                BOARD.get("a7").setPieceOnTile(new Pawn('a', 7, Color.BLACK));
                BOARD.get("b7").setPieceOnTile(new Pawn('b', 7, Color.BLACK));
                BOARD.get("f7").setPieceOnTile(new Pawn('f', 7, Color.BLACK));
                BOARD.get("g7").setPieceOnTile(new Pawn('g', 7, Color.BLACK));
                BOARD.get("h7").setPieceOnTile(new Pawn('h', 7, Color.BLACK));

                BOARD.get("c8").setPieceOnTile(new Rook('c', 8, Color.BLACK));
                BOARD.get("d8").setPieceOnTile(new Rook('d', 8, Color.BLACK));
                BOARD.get("g8").setPieceOnTile(new King('g', 8, Color.BLACK));


                Game.setPlayerToMove(whitePlayer);
                Game.setHeroe(blackPlayer);
                Game.setComputer(whitePlayer);
                ArrayList<NextMove> moveList = new ArrayList<NextMove>();
                moveList.add(new NextMove(BOARD.get("d5"), BOARD.get("c6")));
                moveList.add(new NextMove(BOARD.get("d6"), BOARD.get("c5")));
                moveList.add(new NextMove(BOARD.get("g1"), BOARD.get("h1")));
                moveList.add(new NextMove(BOARD.get("d8"), BOARD.get("d1")));
                setLevelMoveList(moveList);
            }
        }
    }

    public static class NextMove {
        private final Square fromSquare;
        private final Square toSquare;

        private NextMove(Square fromSquare, Square toSquare) {
            this.fromSquare = fromSquare;
            this.toSquare = toSquare;
        }

        public Square getFromSquare() {
            return fromSquare;
        }

        public Square getToSquare() {
            return toSquare;
        }

    }

}
