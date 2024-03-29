/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Block;
import model.Board;
import model.TetrisControlls;
import model.TetrisPiece;

/**
 * @author Vecheka Chhourn
 * @version 02/28/2018
 * A class that handles all the JMenuItem actions, and main container for the game.
 */
public class TetrisFrame extends JFrame implements ActionListener, Observer {
    
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = -2116814665964170048L;
    
    // Source: EasyStreetGUI by Charles Bryan
    /** A ToolKit to capture screen size. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit(); 
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** Title.*/
    private static final String TITLE = "Tetris Game";
    /** Window Default Width.*/
    private static final int WINDOW_WIDTH = 800;
    /** Window Default Height.*/
    private static final int WINDOW_HEIGHT = 660;
    /** Icon size.*/
    private static final int ICON_SIZE = 12;
    /** Initial Delay for Timer.*/
    private static final int[] DIFFICULTY_DELAY = {2000, 1000, 500};
    /** Level up delay for Timer.*/
    private static final int LEVEL_UP_DELAY = 100;
    
    /** Start Game command.*/
    private static final String START_GAME = "New Game";
    /** End Game command.*/
    private static final String END_GAME = "End Game";
    /** Reset high score Game command.*/
    private static final String RESET = "Reset Highscore";
    /** Pause Game command.*/
    private static final String PAUSE_GAME = "Pause";
    /** Resume Game command.*/
    private static final String RESUME = "Resume";
    /** Setting Game command.*/
    private static final String SETTING = "Settings";
    /** Load Game command.*/
    private static final String LOAD_GAME = "Load Game...";
    /** Save Game command.*/
    private static final String SAVE_GAME = "Save Game";
    /**Game Instruction command.*/
    private static final String INSTRUCTION = "Instruction...";
    /** About Game command.*/
    private static final String ABOUT = "About...";
    /** Exit Game command.*/
    private static final String EXIT_GAME = "Exit";
    
    /** Reference to Board class.*/
    private final TetrisControlls myBoard;
    /** Tetris Graphics.*/
    private final TetrisGraphics myTetrisGraphics;
    /** Tetris Moves.*/
    private final TetrisMoves myTetrisMoves;
    /** Tetris Setting.*/
    private final TetrisSettings myTetrisSetting;
    
    /** Resume JMenuItem.*/
    private JMenuItem myResume;
    /** Pause JMenuItem.*/
    private JMenuItem myPause;
    /** Action map of menu item.*/
    private Map<String, Runnable> myActionList;
    
    
    /** Whether the game is paused or played.*/
    private boolean myGameIsPause;
    /** Status to tell whether a new game has started or not.*/
    private boolean myGameIsStarted;
    /** Whether game is over.*/
    private boolean myGameIsOver;
    /** Timer to advance the game.*/
    private Timer myTimer;
    /** Time to level up to.*/
    private int myLevelUpTime;
    /** Current game level.*/
    private int myCurrentLevel;
    /** Board data.*/
    private Block[][] myBoardData;
    /** Tetris Next Piece.*/
    private TetrisPiece myNextPiece;
    /** Tetris save game.*/
    private TetrisSaveGame myTetrisSaveGame;
    /** Game Difficulty.*/
    private int myGameDifficulty;
    /** Select level menu item list.*/
    private List<JMenuItem> mySelectLevel;
    /** Game time.*/
    private int myTime;
    /** File chooser.*/
    private JFileChooser myFileChooser;
    
    
    
    /**
     * Constructor that sets up the frame's title, and initializes all 
     * its children components.
     */
    public TetrisFrame() {
        super(TITLE);
       
        final Board board = new Board();
        myTetrisGraphics = new TetrisGraphics(WINDOW_WIDTH);
        
        board.addObserver(myTetrisGraphics);
        board.addObserver(this);
        myBoard = board;
        myTetrisMoves = new TetrisMoves(this, myBoard);
        myTetrisSetting = new TetrisSettings(myTetrisMoves);
        
        
    }
    
    
    /** 
     * Set up frame and game board.
     */
    public void setupComponents() {
        
        final ImageIcon image = new ImageIcon("icons/gameIcon.png");
        setIconImage(image.getImage().getScaledInstance(ICON_SIZE * 2, ICON_SIZE * 2, 
                                                                 java.awt.Image.SCALE_SMOOTH));
        
        myFileChooser = new JFileChooser("savedGame.txt");
        myFileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        // always true at the start
        myGameIsOver = true;
        myCurrentLevel = 1;
        myTetrisSaveGame = new TetrisSaveGame();
        myGameDifficulty = 1;
        myTimer = new Timer(DIFFICULTY_DELAY[myGameDifficulty], this);
        
        // create Controls menu bar
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createControlMenu());
        menuBar.add(createHelpMenu());
        this.setJMenuBar(menuBar);
        
        setupActionList();
        
        add(myTetrisGraphics);
        addKeyListener(myTetrisMoves);
        final int r = 53; 
        final int b = 81;
        final int g = 124;
        getContentPane().setBackground(new Color(r, b, g));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        this.pack();
        
        // set location of window to the middle of the screen. 
        // (Citation: EasyStreetGUI by Charles Bryan)
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                    SCREEN_SIZE.height / 2 - getHeight() / 2);
    }
    
    
    
    

    
    /** 
     * Create an action list of menu items.
     */
    private void setupActionList() {
        myActionList = new HashMap<String, Runnable>();
        
        myActionList.put(START_GAME, this::startNewGame);
        myActionList.put(END_GAME, this::endCurrentGame);
        myActionList.put(EXIT_GAME, this::closeTheGame);
        myActionList.put(SETTING, this::openSettings);
        myActionList.put(ABOUT, this::printAboutInformation);
        myActionList.put(INSTRUCTION, this::printGameInstruction);
        myActionList.put(RESUME, this::pauseTheGame);
        myActionList.put(PAUSE_GAME, this::pauseTheGame);
        myActionList.put(SAVE_GAME, this::saveGame);
        myActionList.put(LOAD_GAME, this::loadGame);
        myActionList.put(RESET, this::resetHighScore);
    }


    /**
     * Create file menu.
     * @return menu
     */
    private JMenu createFileMenu() {
        final JMenu menu = new JMenu("File");
        final JMenuItem loadGame = new JMenuItem(LOAD_GAME);
        loadGame.addActionListener(this);
        final JMenuItem saveGame = new JMenuItem(SAVE_GAME);
        saveGame.addActionListener(this);
        
        final JMenuItem exit = new JMenuItem(EXIT_GAME);
        exit.addActionListener(this);
        menu.add(loadGame);
        menu.add(saveGame);
        menu.addSeparator();
        menu.add(exit);
        return menu;
    }


    /**
     * Create help menu.
     * @return menu.
     */
    private JMenu createHelpMenu() {
        final JMenu menu = new JMenu("Help");
        final JMenuItem instruction =  new JMenuItem(INSTRUCTION);
        instruction.addActionListener(this);
        final JMenuItem about = new JMenuItem(ABOUT);
        about.addActionListener(this);
        menu.add(instruction);
        menu.add(about);
        return menu;
    }


    /**
     * Create control menu.
     * @return menu 
     */
    private JMenu createControlMenu() {
        final JMenu menu = new JMenu("Controls");
        final JMenuItem startGame = new JMenuItem(START_GAME);
        startGame.addActionListener(this);
        myPause = new JMenuItem(PAUSE_GAME);
        myPause.addActionListener(this);
        myResume = new JMenuItem(RESUME);
        myResume.addActionListener(this);
        myResume.setVisible(false);
        final JMenuItem endGame = new JMenuItem(END_GAME);
        endGame.addActionListener(this);
        final JMenuItem setting = new JMenuItem(SETTING);
        setting.addActionListener(this);
        final JMenuItem reset = new JMenuItem(RESET);
        reset.addActionListener(this);
        
        final String[] name = {"Beginner", "Intermediate", "Advance"};
        final JMenu select = new JMenu("Select Level");

        mySelectLevel = new ArrayList<JMenuItem>();
        JMenuItem level;
        for (int i = 0; i < name.length; i++) {
            level = new JMenuItem(name[i]);
            level.setActionCommand(String.valueOf(i));
            level.addActionListener(this);
            select.add(level);
            mySelectLevel.add(level);
        }
        // intermediate level is a default level
        setCheckIcon(String.valueOf(myGameDifficulty));
        
        menu.add(startGame);
        menu.add(endGame);
        menu.add(select);
        menu.add(myPause);
        menu.add(myResume);
        menu.add(reset);
        menu.add(setting);

        
        return menu;
    }
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final Object source = theEvent.getSource();
        if (source.equals(myTimer)) {
            advanceTheGame();  
        } else if (source instanceof JMenuItem) {
            doActions(theEvent); 
        } 
             
    }

    

    /**
     * Handle all menu items action events.
     * @param theEvent action events.
     */
    private void doActions(final ActionEvent theEvent) {
        final JMenuItem temp = (JMenuItem) theEvent.getSource();
        final String command = temp.getActionCommand();
       
        
        if (temp.getActionCommand().equals("0")) {
            setCheckIcon(command);
            myGameDifficulty = Integer.valueOf(command);
            myTimer.setDelay(DIFFICULTY_DELAY[myGameDifficulty]);

        } else  if (temp.getActionCommand().equals("1")) {
            setCheckIcon(command);
            myGameDifficulty = Integer.valueOf(command);
            myTimer.setDelay(DIFFICULTY_DELAY[myGameDifficulty]);

        } else if (temp.getActionCommand().equals("2")) {
            setCheckIcon(command);
            myGameDifficulty = Integer.valueOf(command);
            myTimer.setDelay(DIFFICULTY_DELAY[myGameDifficulty]);

        } else {
            myActionList.get(command).run(); 
        }
        
        


       
        
    }

    /**
     * Set check icon if the level was selected.
     * @param theCommand action command.
     */
    private void setCheckIcon(final String theCommand) {
        
        ImageIcon image = new ImageIcon("icons/checkIcon.png");
        image = new ImageIcon(image.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, 
                                                                 java.awt.Image.SCALE_SMOOTH));
        for (final JMenuItem menuItem: mySelectLevel) {
            final String temp = menuItem.getActionCommand();
            if (temp.equals(theCommand)) {
                menuItem.setIcon(image);
            } else {
                menuItem.setIcon(null);
            }
        }
        
    }


    /**
     * Reset high score to 0.
     */
    private void resetHighScore() {
        myTetrisGraphics.resetHighScore(true);
    }
    
    
    /**
     * Load previous saved game.
     */
    private void loadGame() {
        if (!myGameIsPause) {
            pauseTheGame();
        }
       
        
        myFileChooser.showOpenDialog(this);
        
        if (myFileChooser.getSelectedFile() == null) {
            pauseTheGame();
        } else {
            final File savedFile = myFileChooser.getSelectedFile();
           
            try {
                myBoard.loadGame(savedFile);
                myGameIsStarted = true;
                myGameIsPause = false;
//                myGameLoaded = true;
                myTetrisGraphics.resetGame(false);
                myTimer.start();
                TetrisMusic.music.loop();
            } catch (final IOException io) {
                JOptionPane.showMessageDialog(this, 
                                              "Could not read the race file " + savedFile
                                              + ":\n\n" + io.getMessage(), "I/O Error",
                                              JOptionPane.ERROR_MESSAGE);
                loadGame();
            }
            
           
        }
    }
    
    
    /**
     * Save game to file.
     */
    private void saveGame() {
        if (!myGameIsPause) {
            pauseTheGame();
        }
        final Object[] options = {"Save", "Save As...", "Cancel"};
        final int confirm = JOptionPane.showOptionDialog(this,
                                                          "Do you want to save the game?", 
                                                          "Select an Option",
                                                          JOptionPane.YES_NO_CANCEL_OPTION,
                                                          JOptionPane.QUESTION_MESSAGE,
                                                          null,
                                                          options,
                                                          options[0]);
       
        
        if (confirm == JOptionPane.YES_OPTION) {
            myTetrisSaveGame.saveGameToDefaultFile(myBoardData, myNextPiece
                                            , myTetrisGraphics.getCurrentGameStat());
        } else if (confirm == JOptionPane.NO_OPTION) {
            saveAsOption();
        } else {
            pauseTheGame();
        }
    }
    
    
    /**
     * Save the game to a new file.
     */
    private void saveAsOption() {
        myFileChooser.showSaveDialog(this);
        if (myFileChooser.getSelectedFile() != null) {
            final String fileName = myFileChooser.getSelectedFile().getName();
            myFileChooser.setCurrentDirectory(new File(fileName));
            myTetrisSaveGame.saveGameToNewFile(myBoardData, myNextPiece
                                            , myTetrisGraphics.getCurrentGameStat(), fileName);
        } 
        
    }


    /** 
     * Open settings.
     */
    private void openSettings() {
        myTetrisSetting.openSettings();
        if (myGameIsStarted) {
            if (!myGameIsPause) {
                pauseTheGame();
            }
           
            JOptionPane.showMessageDialog(this, "Game is paused!", 
                                          "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    /**
     * End the current running game.
     */
    protected void endCurrentGame() {
        if (myGameIsStarted) { 
            myTimer.stop();
            myGameIsStarted = false;
            myTetrisGraphics.setGameEnded(true);
            TetrisMusic.music.stop();
            final int confirm = JOptionPane.showConfirmDialog(this
                                                              , "Current game has ended!\n"
                            + "Do you wish to play again?", 
                            "Message", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                startNewGame();
                
            } else {
                JOptionPane.showMessageDialog(this, "Thank you for playing!", 
                                              "message".toUpperCase(Locale.US)
                                              , JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
    }



    /**
     * Advance the game forward.
     */
    private void advanceTheGame() {
        myBoard.step();
        final int minTime = 50;
        if (myTetrisGraphics.getMyCurrentLevel() > myCurrentLevel) {
            myCurrentLevel++;
            if (DIFFICULTY_DELAY[myGameDifficulty] - myLevelUpTime <= 0 
                            && LEVEL_UP_DELAY / 2 >= minTime) {
                myTimer.setDelay(LEVEL_UP_DELAY / 2);
            } else {
                myTimer.setDelay(DIFFICULTY_DELAY[myGameDifficulty] - myLevelUpTime);
                myLevelUpTime += LEVEL_UP_DELAY;
            }
        }
        myTime++;
        myTetrisGraphics.setGameTime(myTime);
    }


    /**
     * Start a new game.
     */
    protected void startNewGame() {
        if (!myGameIsStarted) {
            myBoard.newGame();
            myTimer.setDelay(DIFFICULTY_DELAY[myGameDifficulty]);
            myLevelUpTime = LEVEL_UP_DELAY;
            myTimer.start();
            myGameIsStarted = true;
            myGameIsPause = false;
            TetrisMusic.music.loop();
            
        } 
        if (!myGameIsOver) {
            myTimer.start();
        }
        
        
        myTetrisGraphics.resetGame(false);
        
    }


    /**
     * Quit the game, and close window.
     */
    protected void closeTheGame() {
        this.dispose();
        myTimer.stop();
        
    }


    /** 
     * Print informations about the author, and give any citation sources.
     */
    protected void printAboutInformation() {
        
        JOptionPane.showMessageDialog(this, "Vecheka Chhourn\n"
                        + "Winter 2018\nTCSS 305 Assignment 5");
    }


    /**
     * Print Game Instruction for the users.
     */
    protected void printGameInstruction() {
        JOptionPane.showMessageDialog(this, "Move Left:  Left Arrow / A\n"
                                      + "Move Right:  Right Arrow / D\n"
                        + "Rotate:  Up Arrow / W\n"
                                      + "Move Down:  Down Arrow / S\n"
                        + "Drop:  SpaceBar", "Instruction", JOptionPane.INFORMATION_MESSAGE);
        
    }


    
    /**
     * Pause the game.
     */
    protected void pauseTheGame() {
        if (myGameIsStarted) {
            
            if (myGameIsPause && !isGameSettingOpen()) {
                myGameIsPause = false;
                myTimer.start();
                TetrisMusic.music.play();
            } else {
                myGameIsPause = true;
                myTimer.stop();
                TetrisMusic.music.pause();
            }

            // switch between resume and pause JMenuItem
            if (myResume.isVisible()) {
                myPause.setVisible(true);
                myResume.setVisible(false);
            } else if (myPause.isVisible()) {
                myPause.setVisible(false);
                myResume.setVisible(true);
            }
        }
    }


    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theData instanceof Boolean) {
            myGameIsOver = (Boolean) theData;
            gameOver();
           
        } else if (theData instanceof Block[][]) {
            myBoardData = (Block[][]) theData;
        } else if (theData instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theData;
        }
        
    }
    
    /** 
     * Handle action when game is over.
     */
    private void gameOver() {
       
        myGameIsPause = false;
        myGameIsStarted = false;

        myTimer.stop();
        TetrisMusic.music.stop();
        
       
    }


    /** 
     * Get game pause/resume status.
     * @return true if game is paused.
     */
    protected boolean isGamePause() {
        return myGameIsPause;
    }
    
    
    /** 
     * Get game start status.
     * @return true if game is paused.
     */
    protected boolean isGameStarted() {
        return myGameIsStarted;
    }
    
    /**
     * Getter for when text field is used.
     * @return true if text field is used.
     */
    protected boolean isGameSettingOpen() {
        return myTetrisSetting.isWindowOpen();
    }
}
