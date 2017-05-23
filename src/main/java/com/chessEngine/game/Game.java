package com.chessengine.game;

import com.GUI.Table;
import com.chessengine.pieces.*;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private static final String LEVEL_PATH = "resources/levels/";
    private static int LEVEL_COUNT;
    private static Square lastFromSquare;
    private static Square lastToSquare;
    private static Game game;
    private static final Player whitePlayer = new Player(Color.WHITE);
    private static final Player blackPlayer = new Player(Color.BLACK);
    private static Player playerToMove;
    private static Player heroe;
    private static Player computer;
    private static List<NextMove> levelMoveList = new ArrayList<>();
    private static String hint;

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    public static int getLevelCount() {
        return LEVEL_COUNT;
    }

    private Game() throws Piece.OccupiedSquareException {
        Board.emptyBoard();
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
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static boolean newLevel(String level) throws Piece.OccupiedSquareException {
        try {
            if (level== "") {
                Board.defaultBoard();
                Board.setHeroe(null);
                hint = "";
                return true;
            } else if (Board.buildBoardFromFen(LEVEL_PATH + level + ".fen")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Board.defaultBoard();
            Board.setHeroe(null);
            hint = "";
            return false;
        }
    }


    public static void newGame() throws Piece.OccupiedSquareException {
        game = new Game();
    }

    public static List<NextMove> getLevelMoveList() {
        return levelMoveList;
    }

    public static void setLevelMoveList(List<NextMove> nextMoveList) {
        Game.levelMoveList = nextMoveList;
    }

    private static void setLastSquares(Square fromSquare, Square toSquare) {
        Game.lastFromSquare = fromSquare;
        Game.lastToSquare = toSquare;
    }

    public static Player getHeroe() {
        return heroe;
    }

    public static Player getComputer() {
        return computer;
    }


    private static void togglePlayerToMove() {
        playerToMove = playerToMove.getColor() == Color.WHITE ? blackPlayer : whitePlayer;
    }


    public static boolean move(Player player, Square fromSquare, Square toSquare, int iter) {

        // TODO
        // need to handle moves when king is under check
        Piece pieceToMove;
        if (player != null && player.getColor() == playerToMove.getColor() && fromSquare.isOccupied()) {
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
        private static Map<String, Square> BOARD_MAP = new HashMap<>();

        public static Map<String, Square> getBoardMap() {
            return BOARD_MAP;
        }

        private static void setPlayerToMove(Player playerToMove) {
            Game.playerToMove = playerToMove;
        }

        private static void setHeroe(Player heroe) {
            Game.heroe = heroe;
        }

        private static void setComputer(Player computer) {
            Game.computer = computer;
        }

            private static void emptyBoard() {
                BOARD_MAP.clear();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        BOARD_MAP.put(String.valueOf((char) (i + 97)) + (j + 1), new Square((char) (i + 97), j + 1));
                    }
                }
            }

            private static boolean buildBoardFromFen(String path) throws Piece.OccupiedSquareException {
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
                                    if (j == 0 && (i == 0 || i == 7)) {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.BLACK));

                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.BLACK, true));
                                    }
                                    break;
                                case 'n':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Knight((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'b':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Bishop((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'q':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Queen((char) (i + 97), 8 - j, Color.BLACK));
                                    break;
                                case 'k':
                                    if (j == 7 && (i == 4 || i == 7)) {

                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.BLACK));
                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.BLACK, true));
                                    }
                                    break;
                                case 'p':
                                    if (j == 1) {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.BLACK));
                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.BLACK, true));
                                    }
                                    break;
                                case 'R':
                                    if (j == 7 && (i == 0 || i == 7)) {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.WHITE));

                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Rook((char) (i + 97), 8 - j, Color.WHITE, true));
                                    }
                                    break;
                                case 'N':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Knight((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'B':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Bishop((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'Q':
                                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Queen((char) (i + 97), 8 - j, Color.WHITE));
                                    break;
                                case 'K':
                                    if (j == 7 && (i == 4 || i == 7)) {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.WHITE));
                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new King((char) (i + 97), 8 - j, Color.WHITE, true));
                                    }
                                    break;
                                case 'P':
                                    if (j == 6) {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.WHITE));
                                    } else {
                                        BOARD_MAP.get(String.valueOf((char) (i + 97)) + (8 - j)).setPieceOnTile(new Pawn((char) (i + 97), 8 - j, Color.WHITE, true));
                                    }
                                    break;
                                case '-':
                                    break;
                                default:
                                    throw new FENException("Invalid FEN String " + fen[0]);
                            }
                        }
                    }
                    if (fenString[1].equals("b")) {
                        setPlayerToMove(blackPlayer);
                        setHeroe(whitePlayer);
                        setComputer(blackPlayer);
                    } else {
                        setPlayerToMove(whitePlayer);
                        setHeroe(blackPlayer);
                        setComputer(whitePlayer);
                    }
                    levelMoveList = parseMoveList(fen[1]);
                    hint = fen[2];
                    return true;
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                    return false;
                }
            }

            private static List<NextMove> parseMoveList(String moveString) {
                List<NextMove> moveList = new ArrayList<>();
                String[] moveArray = moveString.split("/");
                String[] moveSquares;
                for (String move : moveArray) {
                    moveSquares = move.split("-");
                    moveList.add(new NextMove(BOARD_MAP.get(moveSquares[0]), BOARD_MAP.get(moveSquares[1])));
                }
                return moveList;
            }

            private static void defaultBoard() throws Piece.OccupiedSquareException {
                emptyBoard();
                BOARD_MAP.get("a1").setPieceOnTile(new Rook('a', 1, Color.WHITE));
                BOARD_MAP.get("b1").setPieceOnTile(new Knight('b', 1, Color.WHITE));
                BOARD_MAP.get("c1").setPieceOnTile(new Bishop('c', 1, Color.WHITE));
                BOARD_MAP.get("d1").setPieceOnTile(new Queen('d', 1, Color.WHITE));
                BOARD_MAP.get("e1").setPieceOnTile(new King('e', 1, Color.WHITE));
                BOARD_MAP.get("f1").setPieceOnTile(new Bishop('f', 1, Color.WHITE));
                BOARD_MAP.get("g1").setPieceOnTile(new Knight('g', 1, Color.WHITE));
                BOARD_MAP.get("h1").setPieceOnTile(new Rook('h', 1, Color.WHITE));
                for (int i = 0; i < 8; i++) {
                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + 2).setPieceOnTile(new Pawn((char) (i + 97), 2, Color.WHITE));
                }
                for (int i = 0; i < 8; i++) {
                    BOARD_MAP.get(String.valueOf((char) (i + 97)) + 7).setPieceOnTile(new Pawn((char) (i + 97), 7, Color.BLACK));
                }
                BOARD_MAP.get("a8").setPieceOnTile(new Rook('a', 8, Color.BLACK));
                BOARD_MAP.get("b8").setPieceOnTile(new Knight('b', 8, Color.BLACK));
                BOARD_MAP.get("c8").setPieceOnTile(new Bishop('c', 8, Color.BLACK));
                BOARD_MAP.get("d8").setPieceOnTile(new Queen('d', 8, Color.BLACK));
                BOARD_MAP.get("e8").setPieceOnTile(new King('e', 8, Color.BLACK));
                BOARD_MAP.get("f8").setPieceOnTile(new Bishop('f', 8, Color.BLACK));
                BOARD_MAP.get("g8").setPieceOnTile(new Knight('g', 8, Color.BLACK));
                BOARD_MAP.get("h8").setPieceOnTile(new Rook('h', 8, Color.BLACK));
                setPlayerToMove(whitePlayer);
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

    public static class FENException extends RuntimeException {
        FENException(String message) {
            super(message);
        }

    }

}
