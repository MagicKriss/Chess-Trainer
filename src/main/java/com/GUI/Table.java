package com.GUI;

import com.chessengine.game.Game;
import com.chessengine.game.Square;
import com.chessengine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Table {

    private final static String ABOUT_PATH = "resources/about.txt";
    private final static String PIECE_ICON_PATH = "resources/images/other/";
    private final static Dimension FRAME_DIMENSIONS = new Dimension(800, 600);
    private final static Dimension BOARD_PANEL_DIMENSIONS = new Dimension(400, 350);
    private final static Dimension SIDE_PANEL_DIMENSIONS = new Dimension(200, 350);
    private static final Logger LOGGER = Logger.getLogger(Table.class.getName());
    private static Table table;
    private static JFrame gameFrame;
    private static Square fromSquare;
    private static Square toSquare;
    private int currentLevel;
    private BoardPanel boardPanel;
    private JPanel sidePanel;
    private List<Game.NextMove> levelMoveList;
    private int iter;

    private Table() {
        gameFrame = new JFrame("Chess");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(FRAME_DIMENSIONS);
        render();
        currentLevel = 0;
    }

    public static void newTable() throws Piece.OccupiedSquareException {
        Game.newGame();
        table = new Table();
    }

    public static Table getTable() {
        return table;
    }

    /*-----------------------------------------------------------------
    * This method is used for creating all new the components in
    * gameFrame and then redrawing them back in frame
    -----------------------------------------------------------------*/
    private void render() {
        if (this.boardPanel != null) {
            gameFrame.remove(this.sidePanel);
            gameFrame.remove(this.boardPanel);
        }

        this.boardPanel = new BoardPanel(); // panel for displaying board
        this.sidePanel = new JPanel(); // panel on right side for displaying information
        this.sidePanel.setBackground(new Color(206, 165, 165));
        this.sidePanel.setPreferredSize(SIDE_PANEL_DIMENSIONS);
        JLabel sideLabel = new JLabel();
        sideLabel.setFont(new Font("Verdana", Font.BOLD, 15));
        JLabel colorLabel = new JLabel();

        if (Game.getHeroe() != null) {
            String color = Game.getHeroe().getColor() == Color.WHITE ? "WHITE" : "BLACK";
            sideLabel.setText("You are playing as ");
            colorLabel.setText(color);
            colorLabel.setForeground(Game.getHeroe().getColor());
            colorLabel.setFont(new Font("Verdana", Font.BOLD, 20));
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

    private void makeComputersMove() {
        if (!levelMoveList.isEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                Thread.currentThread().interrupt();
            }
            Game.NextMove move = Game.getLevelMoveList().get(iter); // getting next right move
            Game.move(Game.getComputer(), move.getFromSquare(), move.getToSquare(), iter); // making that move
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
        // additional menu bar items can be added later here
        return tableMenuBar;
    }

    private JMenu createFileMenu() {

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem newGame = new JMenuItem("Start Game");
        final JMenuItem hint = new JMenuItem("Show Hint");
        final JMenuItem about = new JMenuItem("About");
        final JMenuItem exit = new JMenuItem("Exit");
        // setting action listeners for each button for specifying
        // what action will be made after that button is pressed
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Game.newLevel("level1")) {
                        currentLevel = 1;
                        render();
                        hint.setEnabled(true);
                    } else {
                        showMessage("Could not load level \"level1\"", "No level found !");
                    }
                } catch (Piece.OccupiedSquareException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        });

        hint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHint(Game.getHint());
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showMessage(getAbout(), "About");
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                gameFrame.dispose(); // getting rid of game frame
            }
        });
        hint.setEnabled(false); // disabling hit button, before any level is loaded
        fileMenu.add(newGame);
        fileMenu.add(hint);
        fileMenu.add(about);
        fileMenu.add(exit);
        return fileMenu;
    }

    private String getAbout() throws IOException {
        String about;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ABOUT_PATH))) {

            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append('\n');
                line = br.readLine();
            }
            about = sb.toString();
        }
        return about;
    }

    private void showHint(String message) {
        if (message.isEmpty()) {
            message = "Sorry, no hint was specified for this level";
        }
        showMessage(message, "Hint");
    }

    public void showMessage(String message, String title) {
        //custom title, no icon
        JOptionPane.showMessageDialog(gameFrame,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }


    private class BoardPanel extends JPanel {
        private List<SquarePanel> boardSquares;

        private BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardSquares = new ArrayList<>();
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

    private class SquarePanel extends JPanel {
        private final Square square;

        SquarePanel(final BoardPanel boardPanel, final Square square) {
            super(new GridBagLayout());
            this.square = square;
            setPreferredSize(new Dimension(boardPanel.getWidth() / 8, boardPanel.getHeight() / 8));
            setBackground(square.getColor());
            assignPiece();
            addMouseListener(new MouseAdapter() {
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
                                @Override
                                public void run() {
                                    boardPanel.drawBoard();

                                    if (iter == levelMoveList.size()) {
                                        if (currentLevel == Game.getLevelCount()) {
                                            showMessage("You complected all levels!", "CONGRATULATION!");
                                            try {
                                                // disable "Hint" item in menu bar
                                                gameFrame.getJMenuBar().getMenu(0).getItem(1).setEnabled(false);
                                                Game.newLevel("");
                                                render();
                                            } catch (Piece.OccupiedSquareException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);

                                            }

                                        } else {
                                            int response = showNextMessage();
                                            //Next = 0, Close = 1
                                            if (response == 0) {
                                                try {
                                                    currentLevel++;
                                                    Game.newLevel("level" + currentLevel);
                                                    render();
                                                } catch (Piece.OccupiedSquareException ex) {
                                                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                                }
                                            }

                                        }
                                    } else {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            @Override
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
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        }

        private int showNextMessage() {
            Object[] options = {"Next Level", "Close"};
            return JOptionPane.showOptionDialog(gameFrame,
                    "You complected this level!",
                    "CONGRATULATION!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title
        }

        private BufferedImage resize(BufferedImage image, int width, int height) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            return bi;
        }

        private void drawSquare() {
            removeAll();
            setBackground(square.getColor());
            assignPiece();
            validate();
            repaint();
        }
    }

}
