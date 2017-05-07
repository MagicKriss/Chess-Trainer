package com.chessEngine.game;


import java.awt.*;

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
