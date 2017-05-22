package com.chessengine.pieces;

import com.chessengine.game.Game;
import com.chessengine.game.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PawnTest {
    private Set<Square> legalMoveList;

    @BeforeEach
    void setUp() throws Piece.OccupiedSquareException {
        Game.newGame();
        Game.newLevel("");
    }

    @Test
    void testGetAllLegalFirstPawnMoves() {
        Piece pawn;
        for (int i = 0; i < 8; i++) {
            legalMoveList = new HashSet<>();
            pawn = Square.getSquare((char) ('a' + i), 2).getPiece();
            legalMoveList.add(Square.getSquare((char) ('a' + i), 3));
            legalMoveList.add(Square.getSquare((char) ('a' + i), 4));
            assertEquals(legalMoveList, pawn.getMoves());
        }
    }


    @Test
    void testGetCaptureMove() throws Piece.OccupiedSquareException {
        Piece pawn = Square.getSquare('c', 2).getPiece();
        legalMoveList = new HashSet<>();
        legalMoveList.add(Square.getSquare('c', 3));
        legalMoveList.add(Square.getSquare('c', 4));
        assertEquals(legalMoveList, pawn.getMoves());
        Game.Board.getBoardMap().get("b3").setPieceOnTile(new Pawn('b', 3, Color.BLACK));
        legalMoveList.add(Square.getSquare('b', 3));
        assertEquals(legalMoveList, pawn.getMoves());
        Game.Board.getBoardMap().get("d3").setPieceOnTile(new Pawn('d', 3, Color.BLACK));
        legalMoveList.add(Square.getSquare('d', 3));
        assertEquals(legalMoveList, pawn.getMoves());
        Game.Board.getBoardMap().get("c4").setPieceOnTile(new Pawn('c', 4, Color.BLACK));
        legalMoveList.remove(Square.getSquare('c', 4));
        assertEquals(legalMoveList, pawn.getMoves());
        Game.Board.getBoardMap().get("c3").setPieceOnTile(new Pawn('c', 3, Color.BLACK));
        legalMoveList.remove(Square.getSquare('c', 3));
        assertEquals(legalMoveList, pawn.getMoves());
    }


}