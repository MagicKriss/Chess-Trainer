package com.chess.pieces;

import com.chess.game.Game;
import com.chess.game.Player;
import com.chess.game.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PawnTest {
    List<Square> legalMoveList;
    Game game;

    @BeforeEach
    public void setUp() throws Exception {
        legalMoveList = new ArrayList<Square>();
        game = Game.getGame();

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


}