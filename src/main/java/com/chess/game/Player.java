package com.chess.game;

import com.chess.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Color color;
    //private List<String> controlledSquares; // squares on which player has a piece on

    public Player(Color color) {
        //  controlledSquares = new ArrayList<String>();
        this.color = color;
    }

    // public List<String> getControlledSquares() {
    //     return controlledSquares;
    // }

    // public void addControlledSquare(String square) {
    //     controlledSquares.add(square);
    // }

    // public void removeControlledSquare(String square) {
    //     controlledSquares.remove(square);
    // }

    public Color getColor() {
        return color;
    }
}
