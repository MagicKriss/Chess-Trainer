package com.GUI;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;
import com.chessEngine.pieces.Piece;

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
import java.util.concurrent.TimeUnit;

public class Table {

    private int currentLevel;
    private static Table table;
    private static JFrame gameFrame;
    private BoardPanel boardPanel;
    private JPanel sidePanel;
    private JLabel sideLabel;
    private JLabel colorLabel;
    private final static String PIECE_ICON_PATH = "images/simple/";
    private static Square fromSquare;
    private static Square toSquare;
    private final static Dimension FRAME_DIMENSIONS = new Dimension(800, 600);
    private final static Dimension BOARD_PANEL_DIMENSIONS = new Dimension(400, 350);
    private final static Dimension SIDE_PANEL_DIMENSIONS = new Dimension(200, 200);

    private final static Dimension SQUARE_PANEL_DIMENSIONS = new Dimension(10, 10);
    private List<Game.NextMove> levelMoveList;
    private int iter;

    public static Table newTable() {
        table = new Table();
        return table;
    }

    private Table() {
        gameFrame = new JFrame("Chess");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(FRAME_DIMENSIONS);
        render();
        currentLevel = 0;
    }

    private void render() {
        if (this.boardPanel != null) {
            gameFrame.remove(this.boardPanel);
        }

        this.boardPanel = new BoardPanel();
        gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.sidePanel = new JPanel();
        this.sidePanel.setPreferredSize(SIDE_PANEL_DIMENSIONS);
        this.sideLabel = new JLabel();
        this.sideLabel.setFont(new Font("Verdana", 1, 15));
        this.colorLabel = new JLabel();

        if (Game.getHeroe() != null) {
            String color = Game.getHeroe().getColor() == Color.WHITE ? "WHITE" : "BLACK";
            this.sideLabel.setText("Your are playing as ");
            this.colorLabel.setText(color);
            this.colorLabel.setForeground(Game.getHeroe().getColor());
            this.colorLabel.setFont(new Font("Verdana", 1, 20));
            this.sidePanel.add(sideLabel);
            this.sidePanel.add(colorLabel);
        }
        gameFrame.add(this.sidePanel, BorderLayout.EAST);
        gameFrame.setVisible(true);
        levelMoveList = Game.getLevelMoveList();
        iter = 0;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sidePanel.repaint();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        boardPanel.drawBoard();
                        makeComputersMove();
                    }
                });
            }
        });

    }

    public static Table getTable() {
        return table;
    }

    private void makeComputersMove() {
        if (!levelMoveList.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Game.NextMove move = Game.getLevelMoveList().get(iter);
            Game.move(Game.getComputer(), move.getFromSquare(), move.getToSquare(), iter);
            move.getFromSquare().setToMovedColor();
            move.getToSquare().setToMovedColor();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    boardPanel.drawBoard();
                }
            });
            iter++;
        }
    }

    private JMenuBar createTableMenuBar() {
        JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu(tableMenuBar));
        return tableMenuBar;
    }

    private JMenu createFileMenu(JMenuBar tableMenuBar) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGame = new JMenuItem("Start Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Game.newLevel("level1");
                    currentLevel = 1;
                    render();
                } catch (Piece.OccupiedSquareException e1) {
                    e1.printStackTrace();
                }
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

        private void drawBoard() {
            removeAll();
            for (SquarePanel squarePanel : boardSquares) {
                squarePanel.drawSquare();
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }

    public void showMessage(String message, String title) {
        //custom title, no icon
        JOptionPane.showMessageDialog(gameFrame,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    private int showNextMessage(String message, String title) {
        Object[] options = {"Next Level", "Close"};
        return JOptionPane.showOptionDialog(gameFrame,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
    }

    private class SquarePanel extends JPanel {
        private final Square square;

        SquarePanel(final BoardPanel boardPanel, final Square square) {
            super(new GridBagLayout());
            this.square = square;
            setPreferredSize(SQUARE_PANEL_DIMENSIONS);
            setBackground(square.getColor());
            assignPiece();
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if (fromSquare == square) {
                        fromSquare.setToNormalColor();
                        fromSquare = null;


                    } else if (fromSquare == null) {
                        fromSquare = square;
                        fromSquare.setToHighlight();

                    } else {
                        // second click
                        toSquare = square;
                        if (Game.move(Game.getHeroe(), fromSquare, toSquare, iter)) {
                            fromSquare.setToMovedColor();
                            toSquare.setToMovedColor();
                            iter++;
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    boardPanel.drawBoard();

                                    if (iter == levelMoveList.size()) {
                                        if (currentLevel == Game.LEVEL_COUNT) {
                                            showMessage("You complected all levels!", "CONGRATULATION!");

                                        } else {
                                            int response = showNextMessage("You complected this level!", "CONGRATULATION!");
                                            //Next = 0, Close = 1
                                            if (response == 0) {
                                                try {
                                                    currentLevel++;
                                                    Game.newLevel("level" + currentLevel);
                                                    render();
                                                } catch (Piece.OccupiedSquareException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }

                                        }
                                    } else {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                makeComputersMove();
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            fromSquare.setToNormalColor();
                        }
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

        private void assignPiece() {
            String colorName;
            if (square.isOccupied()) {
                colorName = square.getPiece().getColor() == Color.WHITE ? "White" : "Black";
                try {
                    BufferedImage image = ImageIO.read(new File(PIECE_ICON_PATH + square.getPiece().getPieceName() + "_" + colorName + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public void drawSquare() {
            removeAll();
            setBackground(square.getColor());
            assignPiece();
        }
    }

}
