package com.GUI;

import com.chessEngine.game.Game;
import com.chessEngine.game.Square;
import com.chessEngine.pieces.Piece;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class Table {

    private int currentLevel;
    private static Table table;
    private static JFrame gameFrame;
    private BoardPanel boardPanel;
    private JPanel sidePanel;
    private JLabel sideLabel;
    private JLabel colorLabel;
    private final static String ABOUT_PATH = "resources/about.txt";
    private final static String PIECE_ICON_PATH = "resources/images/other/";
    private static Square fromSquare;
    private static Square toSquare;
    private final static Dimension FRAME_DIMENSIONS = new Dimension(800, 600);
    private final static Dimension BOARD_PANEL_DIMENSIONS = new Dimension(400, 350);
    private final static Dimension SIDE_PANEL_DIMENSIONS = new Dimension(200, 350);

    private List<Game.NextMove> levelMoveList;
    private int iter;

    public static Table newTable() throws Piece.OccupiedSquareException {
        Game.newGame();
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
            gameFrame.remove(this.sidePanel);
            gameFrame.remove(this.boardPanel);
        }

        this.boardPanel = new BoardPanel();
        this.sidePanel = new JPanel();
        this.sidePanel.setBackground(new Color(206, 165, 165));
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
        gameFrame.add(this.boardPanel, BorderLayout.CENTER);
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
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem newGame = new JMenuItem("Start Game");
        final JMenuItem hint = new JMenuItem("Show Hint");
        final JMenuItem about = new JMenuItem("About");
        final JMenuItem exit = new JMenuItem("Exit");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Game.newLevel("level1");
                    currentLevel = 1;
                    render();
                    hint.setEnabled(true);
                } catch (Piece.OccupiedSquareException e1) {
                    e1.printStackTrace();
                }
            }
        });

        hint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessage(Game.getHint(), "Hint");
            }
        });
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showMessage(getAbout(), "About");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        hint.setEnabled(false);
        fileMenu.add(newGame);
        fileMenu.add(hint);
        fileMenu.add(about);
        fileMenu.add(exit);
        return fileMenu;
    }

    private String getAbout() throws IOException {
        String about;
        // fen[0] = fen notation
        // fen[1] = level move list
        // fen [2] = level hint
        String[] fen = new String[3];
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(ABOUT_PATH));
        try {

            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append('\n');
                line = br.readLine();
            }
            about = sb.toString();
        } finally {
            br.close();
        }
        return about;
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
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    drawBoard();
                }
            });
            setPreferredSize(BOARD_PANEL_DIMENSIONS);
            validate();
        }

        private void drawBoard() {
            ListIterator it;
            removeAll();
            SquarePanel squarePanel;
            if (Game.getHeroe() != null && Game.getHeroe().getColor().equals(Color.BLACK)) {
                it = boardSquares.listIterator(boardSquares.size());
                while (it.hasPrevious()) {
                    squarePanel = (SquarePanel) it.previous();
                    squarePanel.drawSquare();
                    add(squarePanel);
                }
            } else {
                it = boardSquares.listIterator();
                while (it.hasNext()) {
                    squarePanel = (SquarePanel) it.next();
                    squarePanel.drawSquare();
                    add(squarePanel);
                }
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
            setPreferredSize(new Dimension(boardPanel.getWidth() / 8, boardPanel.getHeight() / 8));
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
                                            try {
                                                // disable "Hint" item in menu bar
                                                gameFrame.getJMenuBar().getMenu(0).getItem(1).setEnabled(false);
                                                Game.newLevel("");
                                                render();
                                            } catch (Piece.OccupiedSquareException e1) {
                                                e1.printStackTrace();
                                            }

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
                    BufferedImage image = ImageIO.read(new File(PIECE_ICON_PATH + square.getPiece().getPieceName() + "_" + colorName + ".png"));
                    image = resize(image, boardPanel.getHeight() / 10, boardPanel.getHeight() / 10);
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private BufferedImage resize(BufferedImage image, int width, int height) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            return bi;
        }

        public void drawSquare() {
            removeAll();
            setBackground(square.getColor());
            assignPiece();
            validate();
        }
    }

}
