package com.GUI;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final static String PIECE_ICON_PATH = "images/simple/";
    private static Square fromSquare;
    private static Square toSquare;
    private final static Dimension FRAME_DIMENSIONS = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSIONS = new Dimension(400, 350);
    private final static Dimension SQUARE_PANEL_DIMENSIONS = new Dimension(10, 10);


    public Table() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(FRAME_DIMENSIONS);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu(tableMenuBar));
        return tableMenuBar;
    }

    private JMenu createFileMenu(JMenuBar tableMenuBar) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Game Started");
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(newGame);
        fileMenu.add(exit);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        List<SquarePanel> boardSquares;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardSquares = new ArrayList<SquarePanel>();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    SquarePanel squarePanel = new SquarePanel(this, Square.getSquare((char) ('a' + j), 8 - i));
                    this.boardSquares.add(squarePanel);
                    add(squarePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSIONS);
            validate();
        }

        public void drawBoard() {
            removeAll();
            for(SquarePanel squarePanel : boardSquares){
                squarePanel.drawSquare();
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }

    private class SquarePanel extends JPanel {
        private final Square square;

        SquarePanel(final BoardPanel boardPanel, final Square square) {
            super(new GridBagLayout());
            this.square = square;
            setPreferredSize(SQUARE_PANEL_DIMENSIONS);
            setBackground(square.getColor());
            assignePiece();
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if(fromSquare == square) {
                        fromSquare.setToNormalColor();
                        fromSquare = null;


                    }else if (fromSquare == null){
                        fromSquare = square;
                        fromSquare.setToHighlight();

                    }else{
                        // second click
                        toSquare = square;
                        // TODO add player in move
                        if (Game.move(Game.getPlayerToMove(),fromSquare,toSquare)){

                        }
                        fromSquare.setToNormalColor();
                        fromSquare = null;
                        toSquare = null;

                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            boardPanel.drawBoard();
                        }
                    });

                }

                public void mousePressed(MouseEvent e) {

                }

                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {

                }

                public void mouseExited(MouseEvent e) {

                }
            });
            validate();
        }

        private void assignePiece(){
            String colorName;
            if(square.isOccupied()){
                colorName = square.getPiece().getColor() == Color.WHITE ? "White" : "Black";
                try {
                    BufferedImage image = ImageIO.read(new File(PIECE_ICON_PATH + square.getPiece().getPieceName()+"_"+ colorName + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public void drawSquare() {
            removeAll();
            setBackground(square.getColor());
            assignePiece();
        }
    }

}
