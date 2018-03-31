/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import model.Block;
import model.Point;
import model.TetrisPiece;

/**
 * @author Vecheka Chhourn
 * @version 02/23/2018
 * A class that handles all the painting, and repainting on the main frame of Tetris game.
 */
public class TetrisGraphics extends JComponent implements Observer {

    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = -4763324919600478629L;
    
    /** number to convert sec to min, and min to hour.*/
    private static final int SECOND = 60;
   
    
    /** Default width for Tetris board.*/
    private static final int WIDTH = 10;
    /** Default height for Tetris board.*/
    private static final int HEIGHT = 20;
    /** Default game over font.*/
    private static final int DEFAULT_GAMEOVER_FONT_SIZE = 30;
    /** Default thickness.*/
    private static final int THICKNESS = 3;
    /** Default font type.*/
    private static final String DEFAULT_FONT = "SanSerif";
    /** Default square area.*/
    private static final int SQUARE_AREA = 30;
    /** Starting position of the piece.*/
    private static final int STARTING_POSITION = -120;
    /** Box width.*/
    private static final int BOX_WIDTH =  WIDTH * (SQUARE_AREA / 3) + SQUARE_AREA * 2;
    /** Box height.*/
    private static final int BOX_HEIGHT = (HEIGHT / 5) * SQUARE_AREA;
    /** Scoring System list.*/
    private static final int[] SCORING_SYSTEM = {40, 100, 300, 1200};
    /** Line till next level.*/
    private static final int LINES_TO_LEVELUP = 4;
    
    /** Score string.*/
    private static final String SCORE = "SCORE";
    /** Current game level string.*/
    private static final String LEVEL = "LEVEL";
    /** Current lines string.*/
    private static final String CURRENT_LINES = "CURRENTLINES";
    /** Next level string.*/
    private static final String NEXT_LEVEL = "NEXTLEVEL";
    /** Remaining line string.*/
    private static final String REMAINING_LINES = "REMAININGLINES";
    
    /** Next piece to play.*/
    private TetrisPiece myNextPiece;
    /** Board data.*/
    private Block[][] myBoardData;
    /** Tetris shape reference.*/
    private TetrisShape myTetrisShape;
    /** Tetris High Score.*/
    private final TetrisHighScore myTetrisHighScore;
    
    /** Screen mid position.*/
    private final int myMiddleLocation;
    /** Color of each pieces.*/
    private final Map<Block, Color> myPieceColorList;
    /** List to store Previous-Next Piece on the window.*/
    private List<Point> myNextPiecePoint;
    /** Keep track when the game is over.*/
    private boolean myGameOver;
    
    // Game stats
    /** Current level of the game.*/
    private int myCurrentLevel;
    /** Total cleared lines.*/
    private int myTotalClearLines;
    /** Lines remaining till next level.*/
    private int myRemaingLineToLevelUp;
    /** Lines count to determine when to level up.*/
    private int myLevelUp;
    /** Game score.*/
    private int myScore;
    /** Map to store game stats.*/
    private Map<?, ?> myGameStatsMap;
    /** Game time.*/
    private int myTime;

    
    
    
    /**
     * Constructor that takes the width of the main window, and draw the game
     * board, pieces in the middle of the frame.
     * @param theWidth width of the frame window.
     */
    public TetrisGraphics(final int theWidth) {
        super();
        myTetrisShape = new TetrisShape();
        myTetrisHighScore = new TetrisHighScore();
        myPieceColorList = myTetrisShape.getPieceColorList();
        myMiddleLocation = theWidth / 2 - (WIDTH * SQUARE_AREA /  2);
        myCurrentLevel = 1;
        myRemaingLineToLevelUp = LINES_TO_LEVELUP;
       
    }
    
    


    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        final Graphics2D g2D = (Graphics2D) theGraphics;
        
        // for better graphics display
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        drawBoard(g2D);
        drawShorcutInfo(g2D);
        drawPiece(g2D);
        removePreviousNextPiece(g2D);
        drawNextPiece(g2D);
        drawHighScoreBoard(g2D);
        drawScoreLineLevelBoard(g2D);
        if (myGameOver) {
           
            drawGameOver(g2D);
        }
        
    }
    
    
    /**
     * Draw score, current lines, and level board.
     * @param theG graphics to draw.
     */
    private void drawScoreLineLevelBoard(final Graphics2D theG) {
        final int x = myMiddleLocation + (WIDTH * SQUARE_AREA + SQUARE_AREA);
        final int stretchY = 9;
        int y = SQUARE_AREA * stretchY;
        final int magicNumber = 4;
        
        final int r = 71;
        final int b = 162;
        final int g = 232;
        final int r1 = 237;
        final int b1 = 239;
        final int g1 = 88;
        
        // draw score board
        theG.setStroke(new BasicStroke(THICKNESS));
        theG.setPaint(new Color(r, b, g));
        
        theG.drawRoundRect(x, y, BOX_WIDTH, 
                           BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(new Color(r1, b1, g1));
        theG.drawString(SCORE, (x + SQUARE_AREA * 2) - (SQUARE_AREA / magicNumber)
                        , y + SQUARE_AREA / 2);
        theG.drawString(String.valueOf(myScore), (x + SQUARE_AREA * 2) + SQUARE_AREA
                        , y + SQUARE_AREA + SQUARE_AREA / 2);
        
        
        // draw level board
        theG.setPaint(new Color(r, b, g));
        y += SQUARE_AREA * 2;
        theG.drawRoundRect(x, y + SQUARE_AREA, BOX_WIDTH, 
                           BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(new Color(r1, b1, g1));
        theG.drawString(LEVEL, (x + SQUARE_AREA * 2) - (SQUARE_AREA / magicNumber)
                        , y + SQUARE_AREA + SQUARE_AREA / 2);
        theG.drawString(String.valueOf(myCurrentLevel), (x + SQUARE_AREA * 2) + SQUARE_AREA
                        , y + SQUARE_AREA * 2 + SQUARE_AREA / 2);
        
        // draw current line board
        theG.setPaint(new Color(r, b, g));
        y += SQUARE_AREA * 2;
        theG.drawRoundRect(x, y + SQUARE_AREA * 2, BOX_WIDTH, 
                           BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(new Color(r1, b1, g1));
        theG.drawString("CURRENT LINES", x + SQUARE_AREA / 2
                        , y + SQUARE_AREA * 2 + SQUARE_AREA / 2);
        theG.drawString(String.valueOf(myTotalClearLines), (x + SQUARE_AREA * 2) + SQUARE_AREA
                        , y + SQUARE_AREA * (magicNumber - 1) + SQUARE_AREA / 2);
        
        // draw lines to reach next level board
        theG.setPaint(new Color(r, b, g));
        y += SQUARE_AREA * 2;
        theG.drawRoundRect(x, y + SQUARE_AREA * 2 + SQUARE_AREA, BOX_WIDTH, 
                           BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(new Color(r1, b1, g1));
        theG.drawString("NEXT LEVEL", x + SQUARE_AREA
                        , y + SQUARE_AREA * 2 + SQUARE_AREA + SQUARE_AREA / 2);
        theG.drawString(String.valueOf(myRemaingLineToLevelUp), 
                        (x + SQUARE_AREA * 2) + SQUARE_AREA
                        , y + SQUARE_AREA * (magicNumber - 1) + SQUARE_AREA + SQUARE_AREA / 2);
    }




    /**
     * Draw high score board.
     * @param theG graphics to draw.
     */
    private void drawHighScoreBoard(final Graphics2D theG) {
        final int x = myMiddleLocation - SQUARE_AREA * 7;
        final int y = SQUARE_AREA * 2;
        final int magicNumber = 4;
        
        final int r = 9;
        final int b = 141;
        final int g = 249;
        
        theG.drawRoundRect(x, y, BOX_WIDTH, 
                                         BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(new Color(r, b, g));
        theG.drawString("HIGH SCORE", (x + SQUARE_AREA) + (SQUARE_AREA / magicNumber)
                        , y - SQUARE_AREA / 2);
        theG.drawString(String.valueOf(myTetrisHighScore.getHighScore())
                        , x + SQUARE_AREA * 2 + SQUARE_AREA / magicNumber
                        , y + SQUARE_AREA + SQUARE_AREA / magicNumber);
        
        theG.setPaint(Color.GRAY.brighter());
        theG.drawRoundRect(x, y + SQUARE_AREA * (magicNumber + 1), BOX_WIDTH, 
                           BOX_HEIGHT / 2, SQUARE_AREA, SQUARE_AREA);
        theG.setPaint(new Color(r, b, g));
        theG.drawString("TIME", x + SQUARE_AREA * 2 
                        , y + SQUARE_AREA * (magicNumber + 1) 
                        - SQUARE_AREA / (magicNumber - 1));
        final int mid = 28;
        theG.drawString(formatTime(myTime), x + SQUARE_AREA / 2 + mid
                        , y + SQUARE_AREA * (magicNumber + 2) + SQUARE_AREA / magicNumber);
    }




    /**
     * Draw Game Over on the board.
     * @param theG graphics to draw.
     */
    private void drawGameOver(final Graphics2D theG) {
        
        theG.setPaint(Color.RED);
        theG.setFont(new Font("Monospaced", Font.BOLD, DEFAULT_GAMEOVER_FONT_SIZE));
        theG.drawString("Game Over!", getWidth() / 2 - (SQUARE_AREA * 2 + SQUARE_AREA / 2)
                        , getHeight() /  2);
    }




    /** 
     * Draw shortcut keys informations to the players.
     * @param theG graphics to draw.
     */
    private void drawShorcutInfo(final Graphics2D theG) {
        final int stretchY = 13;
        final int stretchX = 5;
        int x = WIDTH;
        int y = SQUARE_AREA * stretchY;
        theG.setFont(new Font(DEFAULT_FONT, Font.CENTER_BASELINE, SQUARE_AREA / 2));
        theG.setPaint(Color.CYAN);
        
        theG.drawString("Shortcut Key(s): ", x, y);
        y += SQUARE_AREA;
        theG.drawString("New Game", x + WIDTH, y);
        y += SQUARE_AREA;
        theG.drawString("End Game", x + WIDTH, y);
        y += SQUARE_AREA;
        theG.drawString("Pause/Resume", x + WIDTH, y);
        y += SQUARE_AREA;
        theG.drawString("Quit", x + WIDTH, y);
        y += SQUARE_AREA;
        theG.drawString("Instructions", x + WIDTH, y);
        
        x = SQUARE_AREA * stretchX;
        y = SQUARE_AREA * stretchY + SQUARE_AREA;
        theG.drawString("[ N ]", x, y);
        y += SQUARE_AREA;
        theG.drawString("[ E ]", x, y);
        y += SQUARE_AREA;
        theG.drawString("[ P ]", x, y);
        y += SQUARE_AREA;
        theG.drawString("[ Q ]", x, y);
        y += SQUARE_AREA;
        theG.drawString("[ I  ]", x, y);
        
    }




    /** 
     * Remove the previous-Next Piece in the box.
     * @param theG graphics to draw.
     */
    private void removePreviousNextPiece(final Graphics2D theG) {
        if (myNextPiecePoint != null) {
            for (final Point point: myNextPiecePoint) {
                theG.setPaint(getBackground());
                theG.drawRect(point.x(), point.y(), SQUARE_AREA, SQUARE_AREA);
            }
        }
        
    }


    /**
     * Draw a box to show the next piece to drop.
     * @param theG graphics to draw.
     */
    private void drawNextPiece(final Graphics2D theG) {
        
        final int magicNumber = 4;
        final int x = myMiddleLocation + (WIDTH * SQUARE_AREA + SQUARE_AREA);
        final int y = SQUARE_AREA * 2;
        
        theG.setFont(new Font(DEFAULT_FONT, Font.BOLD, SQUARE_AREA / 2));
        theG.setPaint(Color.ORANGE);
        theG.drawString("NEXT PIECE", (x + SQUARE_AREA) + (SQUARE_AREA / magicNumber)
                        , y - SQUARE_AREA / 2);
        
        theG.setStroke(new BasicStroke(THICKNESS));
        theG.setPaint(Color.GRAY.brighter());
        
        theG.drawRoundRect(x, y, BOX_WIDTH, BOX_HEIGHT, SQUARE_AREA, SQUARE_AREA);

        myTetrisShape = new TetrisShape(); // re-initiate new shape each time
        if (myNextPiece != null) {
           
            myTetrisShape.addShapesAt(x, SQUARE_AREA, myNextPiece);
            myNextPiecePoint = myTetrisShape.getMyPointsList();
           
            for (final Point p: myNextPiecePoint) {
                theG.setPaint(myPieceColorList.get(myNextPiece.getBlock()));
                theG.fill3DRect(p.x(), p.y(), SQUARE_AREA, SQUARE_AREA, false);
            }
            
        }
        
    }



    /**
     * Draw the piece on the board.
     * @param theG graphics to draw the board.
     */
    private void drawPiece(final Graphics2D theG) {

       
        int x = myMiddleLocation;
        int y = STARTING_POSITION;
        if (myBoardData != null) {
            for (int i = myBoardData.length - 1; i >= 0; i--) {
                final Block[] temp = myBoardData[i];
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j] != null) {
//                        final Shape block = new Rectangle(x, y,
//                                                          SQUARE_AREA, SQUARE_AREA);
                        
                        
                        theG.setPaint(myPieceColorList.get(temp[j]));
                        //theG.fill(block);
                        theG.fill3DRect(x, y, SQUARE_AREA, SQUARE_AREA, false);
                    }
                    x += SQUARE_AREA;
                }
                x = myMiddleLocation;
                y += SQUARE_AREA;
            }
        }
        
    }


    /**
     * Draw Tetris board.
     * @param theG graphics to draw the board.
     */
    private void drawBoard(final Graphics2D theG) {
       
        final Shape board = new Rectangle(myMiddleLocation, 0, WIDTH * SQUARE_AREA, 
                                          HEIGHT * SQUARE_AREA);
        
        final int r = 206;
        final int b = 204;
        final int g = 202;
        theG.setStroke(new BasicStroke(THICKNESS));
        theG.setPaint(new Color(r, b, g));
        theG.draw(board);
        
        // draw square boxes in the board.
        int x = myMiddleLocation;
        int y = 0;
        
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
               
                theG.setPaint(Color.BLACK);
                theG.draw3DRect(x, y, SQUARE_AREA, SQUARE_AREA, false);
                x += SQUARE_AREA;
            }
            x = myMiddleLocation;
            y += SQUARE_AREA;
        }
        
        
    }


    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theData instanceof Block[][]) {
            
            myBoardData = (Block[][]) theData;
            repaint();
        } else if (theData instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theData;
            repaint();
        } else if (theData instanceof Boolean) {
            myGameOver = (Boolean) theData;
            myTetrisHighScore.setNewHighScore(myScore);
            resetGameStats();
            repaint();
        } else if (theData instanceof Integer[]) {
            final Integer[] temp = (Integer[]) theData;
            updateScore(temp);
        } else if (theData instanceof Map) {
            myGameStatsMap = (Map<?, ?>) theData;
            setSavedGameStats();
        }
    }




    /**
     * Set the game stats back to when it was saved.
     */
    private void setSavedGameStats() {
        myScore = (int) myGameStatsMap.get(SCORE);
        myLevelUp = (int) myGameStatsMap.get(NEXT_LEVEL);
        myTotalClearLines = (int) myGameStatsMap.get(CURRENT_LINES);
        myCurrentLevel = (int) myGameStatsMap.get(LEVEL);
        myRemaingLineToLevelUp = (int) myGameStatsMap.get(REMAINING_LINES);
        repaint();
        
    }




    /** 
     * Reset game scores, level, and others.
     */
    private void resetGameStats() {
        myScore = 0;
        myRemaingLineToLevelUp = LINES_TO_LEVELUP;
        myCurrentLevel = 1;
        myTotalClearLines = 0;
        myLevelUp = 0;
    }




    /**
     * Update score on the board game.
     * @param theClearLineList list of cleared lines.
     */
    private void updateScore(final Integer[] theClearLineList) {
        final int size = theClearLineList.length;
        myLevelUp += size;
        if (myLevelUp >= LINES_TO_LEVELUP) {
            myCurrentLevel += 1;
            myLevelUp -= LINES_TO_LEVELUP;
          
        } 
        myScore += SCORING_SYSTEM[size - 1] * myCurrentLevel;
        myTotalClearLines += size;
        
        
        // calculate lines to next level
        myRemaingLineToLevelUp -= size;
        if (myRemaingLineToLevelUp == 0) {
            myRemaingLineToLevelUp = LINES_TO_LEVELUP;
        } else if (myRemaingLineToLevelUp < 0) {
            myRemaingLineToLevelUp = LINES_TO_LEVELUP + myRemaingLineToLevelUp;
        }
        
        
        repaint();
    }


    /** 
     * Reset high score to 0.
     * @param theBoolean true to reset high score.
     */
    public void resetHighScore(final boolean theBoolean) {
        if (theBoolean) {
            myTetrisHighScore.resetHighScore();
        }
        
    }
    

    /**
     * Getter for game level.
     * @return current game level.
     */
    public int getMyCurrentLevel() {
        return myCurrentLevel;
    }

    /**
     * Set game over to false value when new game is called.
     * @param theBoolean true if game is over; false otherwise.
     */
    public void resetGame(final boolean theBoolean) {
        myGameOver = theBoolean;
    }
    
    /** 
     * Sets current game status to false when the game has ended by the users.
     * @param theBoolean false when game has ended.
     */
    public void setGameEnded(final boolean theBoolean) {
       
        if (theBoolean) {
            myTetrisHighScore.setNewHighScore(myScore);
            resetGameStats();
        }
        repaint(); 
    }
    
    
    /**
     * Get the current game's stats.
     * @return map of game stats
     */
    public Map<String, Integer> getCurrentGameStat() {
        final Map<String, Integer> gameStatsMap = new HashMap<String, Integer>();
        gameStatsMap.put(SCORE, myScore);
        gameStatsMap.put(LEVEL, myCurrentLevel);
        gameStatsMap.put(CURRENT_LINES, myTotalClearLines);
        gameStatsMap.put(REMAINING_LINES, myRemaingLineToLevelUp);
        gameStatsMap.put(NEXT_LEVEL, myLevelUp);
        
        return gameStatsMap;
    }
    
    /**
     * Set game time.
     * @param theTime game time.
     */
    public void setGameTime(final int theTime) {
        if (!myGameOver) {
            myTime = theTime;
            repaint();
        }
    }
    
    
    /** 
     * Format time in H:M:S.
     * @param theTime time to format
     * @return time in corrected format.
     */
    private String formatTime(final int theTime) {
        final int min = theTime / (SECOND * SECOND);
        final int sec = theTime / SECOND % SECOND;
        final int millis = theTime % SECOND;
        return String.format("%02d : %02d : %02d", min, sec, millis);
    }
    
    
    
    
}
