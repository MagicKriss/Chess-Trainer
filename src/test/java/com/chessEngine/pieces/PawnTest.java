package com.chessEngine.pieces;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PawnTest {
    List<Square> legalMoveList;
    Game game;

    @BeforeEach
    public void setUp() throws Exception {
        legalMoveList = new ArrayList<Square>();
        game = Game.newGame();

    }

    @Test
    void testGetAllLegalFirstPawnMoves() {
        Piece pawn;
        for (int i = 0; i < 8; i++) {
            legalMoveList = new ArrayList<Square>();
            pawn = Square.getSquare((char) ('a' + i), 2).getPiece();
            legalMoveList.add(Square.getSquare((char) ('a' + i), 3));
            legalMoveList.add(Square.getSquare((char) ('a' + i), 4));
            assertEquals(legalMoveList, pawn.getMoves());
        }
    }

    @Test
    void testGetAllLegalMovesAfterMove() {
        Square a2 = Square.getSquare('a', 2);
        Square a3 = Square.getSquare('a', 3);
        Square a4 = Square.getSquare('a', 4);

        legalMoveList.add(a3);
        legalMoveList.add(a4);
        Piece whitePawn = a2.getPiece();
        assertEquals(legalMoveList, whitePawn.getLegalMoves());
        Game.move(Game.getPlayerToMove(), a2, a3);
        legalMoveList.remove(a3);
        assertEquals(legalMoveList, whitePawn.getLegalMoves());

    }

    @Test
    void testGetCaptureMove() throws Exception {
        Piece pawn = Square.getSquare('c', 2).getPiece();

        legalMoveList.add(Square.getSquare('c', 3));
        legalMoveList.add(Square.getSquare('c', 4));
        assertEquals(legalMoveList, pawn.getLegalMoves());
        Game.Board.getBOARD().get("b3").setPieceOnTile(new Pawn('b', 3, Color.BLACK));
        legalMoveList.add(Square.getSquare('b', 3));
        assertEquals(legalMoveList, pawn.getLegalMoves());
        Game.Board.getBOARD().get("d3").setPieceOnTile(new Pawn('d', 3, Color.BLACK));
        legalMoveList.add(Square.getSquare('d', 3));
        assertEquals(legalMoveList, pawn.getLegalMoves());
        Game.Board.getBOARD().get("c4").setPieceOnTile(new Pawn('c', 4, Color.BLACK));
        legalMoveList.remove(Square.getSquare('c', 4));
        assertEquals(legalMoveList, pawn.getLegalMoves());
        Game.Board.getBOARD().get("c3").setPieceOnTile(new Pawn('c', 3, Color.BLACK));
        legalMoveList.remove(Square.getSquare('c', 3));
        assertEquals(legalMoveList, pawn.getLegalMoves());
    }


}