package com.chessEngine.game;

import com.GUI.Table;
import com.chessEngine.pieces.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {
    public static final String LEVEL_PATH = "resources/levels/";
    public static int LEVEL_COUNT;
    private static Square lastFromSquare;
    private static Square lastToSquare;
    private static Game game;
    private static final Player whitePlayer = new Player(Color.WHITE);
    private static final Player blackPlayer = new Player(Color.BLACK);
    private static Player playerToMove;
    private static Player heroe;
    private static Player computer;
    private static List<NextMove> levelMoveList = new ArrayList<NextMove>();
    private static String hint;

    private Game() throws Piece.OccupiedSquareException {
        Board.BoardBuilder.emptyBoard();
        countLevels();
    }

    public static Game getGame() {
        return game;
    }

    public static String getHint() {
        return hint;
    }

    private static void countLevels() {
        try {
            LEVEL_COUNT = new File(LEVEL_PATH).list().length;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void newLevel(String level) throws Piece.OccupiedSquareException {
        if (!level.equals("")) {
            Board.BoardBuilder.buildBoardFromFen(LEVEL_PATH + level + ".fen");
        } else {
            Board.BoardBuilder.defaultBoard();
            setHeroe(null);
            hint = "";
        }
    }


    public static Game newGame() throws Piece.OccupiedSquareException {
        game = new Game();
        return game;
    }

    public static List<NextMove> getLevelMoveList() {
        return levelMoveList;
    }

    private static void setLevelMoveList(List<NextMove> nextMoveList) {
        Game.levelMoveList = nextMoveList;
    }

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

    private static void togglePlayerToMove() {
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

        private static class BoardBuilder {
            private static void emptyBoard() {
                BOARD.clear();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        BOARD.put(String.valueOf((char) (i + 97)) + (j + 1), new Square((char) (i + 97), j + 1));
                    }
                }
            }

            private static void buildBoardFromFen(String path) throws Piece.OccupiedSquareException {
                emptyBoard();
                try {
                    String[] fen = FENParser.parseFenFile(path);
                    String[] fenString = fen[0].split(" ");

                    final char[] boardTiles = fenString[0].replaceAll("/", "")
                            .replaceAll("8", "--------")
                            .replaceAll("7", "-------")
                            .replaceAll("6", "------")
                            .replaceAll("5", "-----")
                            .replaceAll("4", "----")
                            .replaceAll("3", "---")
                            .replaceAll("2", "--")
                            .replaceAll("1", "-")
                            .toCharArray();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            switch (boardTiles[8 * j + i]) {
                                case 'r':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'n':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Knight((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'b':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Bishop((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'q':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Queen((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'k':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'p':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'R':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'N':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Knight((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'B':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Bishop((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'Q':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Queen((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'K':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'P':
                                    BOARD.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case '-':
                                    break;
                                default:
                                    throw new RuntimeException("Invalid FEN String " + fen[0]);
                            }
                        }
                    }
                    if (fenString[1].equals("b")) {
                        Game.setPlayerToMove(blackPlayer);
                        Game.setHeroe(whitePlayer);
                        Game.setComputer(blackPlayer);
                    } else {
                        Game.setPlayerToMove(whitePlayer);
                        Game.setHeroe(blackPlayer);
                        Game.setComputer(whitePlayer);
                    }
                    levelMoveList = parseMoveList(fen[1]);
                    hint = fen[2];

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            static private List<NextMove> parseMoveList(String moveString) {
                List<NextMove> moveList = new ArrayList<>();
                String moveArray[] = moveString.split("/");
                String[] moveSquares;
                for (String move : moveArray) {
                    moveSquares = move.split("-");
                    moveList.add(new NextMove(BOARD.get(moveSquares[0]), BOARD.get(moveSquares[1])));
                }
                return moveList;
            }

            private static void defaultBoard() throws Piece.OccupiedSquareException {
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
                Game.setPlayerToMove(whitePlayer);
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
