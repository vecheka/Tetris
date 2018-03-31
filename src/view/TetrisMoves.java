/* 
 * TCSS 305- WINTER 2018
 * Assingment 5- Tetris Game.
 * 
 */
package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.TetrisControlls;

/**
 * @author Vecheka Chhourn
 * @version 02/28/2018
 * A class that handles all the key events on the window.
 */
public class TetrisMoves extends KeyAdapter {
    
    /** For determining custom key #1 move.*/
    private static final String MOVE_ONE = "1";
    /** For determining custom key #2 move.*/
    private static final String MOVE_TWO = "2";
    /** Alert message when an existing key is being picked.*/
    private static final String MESSAGE = "Key already existed! "
                    + "Please choose a different key.";
    
    
    /** Game moves.*/
    private Map<List<Integer>, Runnable> myGameMoves;
    /** Game functionality.*/
    private Map<Integer, Runnable> myGameFunctions;
    /** Custom Key Moves.*/
    private Map<List<Integer>, Runnable> myCustomMoves;
    /** Tetris Controlls.*/
    private final TetrisControlls myBoard;
    /** Tetris Frame.*/
    private final TetrisFrame myFrame;
    /** Left move default key.*/
    private List<Integer> myLeftKey;
    /** Right move default key.*/
    private List<Integer> myRightKey;
    /** Down move default key.*/
    private List<Integer> myDownKey;
    /** Drop move default key.*/
    private List<Integer> myDropKey;
    /** Rotate move default key.*/
    private List<Integer> myRotateKey;
    /** Custom Key String and List Map.*/
    private Map<String, List<Integer>> myCustomStringKeys;
    
    
    
    /**
     * Constructor that has a main frame reference, and Tetris controllers reference
     * to call their methods.
     * @param theFrame frame window
     * @param theBoard TetrisControlls
     */
    public TetrisMoves(final TetrisFrame theFrame, final TetrisControlls theBoard) {
        super();
        myBoard = theBoard;
        myFrame = theFrame;
        

        setupMoveList();
        setupGameFunctions();
        setupCustomKeyList();
    }
    
    
    
    /**
     * Create custom keys list.
     */
    private void setupCustomKeyList() {
        // initialize custom keys list
        myCustomStringKeys = new HashMap<String, List<Integer>>();
        final List<Integer> customLeftKey = new ArrayList<Integer>();
        final List<Integer> customRightKey = new ArrayList<Integer>();
        final List<Integer> customDownKey = new ArrayList<Integer>();
        final List<Integer> customRotateKey = new ArrayList<Integer>();
        final List<Integer> customDropKey = new ArrayList<Integer>();
        
        // String representations of each moves.
        myCustomStringKeys.put("left1", customLeftKey);
        myCustomStringKeys.put("left2", customLeftKey);
        myCustomStringKeys.put("right1", customRightKey);
        myCustomStringKeys.put("right2", customRightKey);
        myCustomStringKeys.put("down1", customDownKey);
        myCustomStringKeys.put("down2", customDownKey);
        myCustomStringKeys.put("rotate1", customRotateKey);
        myCustomStringKeys.put("rotate2", customRotateKey);
        myCustomStringKeys.put("drop1", customDropKey);
        

        // add moves to their related methods
        myCustomMoves = new HashMap<List<Integer>, Runnable>();
        
       
        
    }



    /**
     * Set up list of game functions, example: Quit...
     */
    private void setupGameFunctions() {
        myGameFunctions = new HashMap<Integer, Runnable>();
        
        // instruction
        myGameFunctions.put(KeyEvent.VK_I, myFrame::printGameInstruction);
        // new game
        myGameFunctions.put(KeyEvent.VK_N, myFrame::startNewGame);
        // quit game
        myGameFunctions.put(KeyEvent.VK_Q, myFrame::closeTheGame);
        // pause/resume game
        myGameFunctions.put(KeyEvent.VK_P, myFrame::pauseTheGame);
        // end game 
        myGameFunctions.put(KeyEvent.VK_E, myFrame::endCurrentGame);
        
    }


    /**
     * Create the game moves list.
     */
    private void setupMoveList() {
        myGameMoves = new HashMap<List<Integer>, Runnable>();
        // move down
        myDownKey = new ArrayList<Integer>();
        myDownKey.add(KeyEvent.VK_DOWN);
        myDownKey.add(KeyEvent.VK_S);
        // move up
        myRotateKey = new ArrayList<Integer>();
        myRotateKey.add(KeyEvent.VK_UP);
        myRotateKey.add(KeyEvent.VK_W);
        // move left
        myLeftKey = new ArrayList<Integer>();
        myLeftKey.add(KeyEvent.VK_LEFT);
        myLeftKey.add(KeyEvent.VK_A);
        // move right
        myRightKey = new ArrayList<Integer>();
        myRightKey.add(KeyEvent.VK_RIGHT);
        myRightKey.add(KeyEvent.VK_D);
        // drop 
        myDropKey = new ArrayList<Integer>();
        myDropKey.add(KeyEvent.VK_SPACE);
        
       

        
        // put moves in a map
        myGameMoves.put(myRotateKey, myBoard::rotateCW);
        myGameMoves.put(myDownKey, myBoard::down);
        myGameMoves.put(myLeftKey, myBoard::left);
        myGameMoves.put(myRightKey, myBoard::right);
        myGameMoves.put(myDropKey, myBoard::drop);
        
               
       
    }
    
    
    
    @Override
    public void keyPressed(final KeyEvent theKeyEvent) {
       
        if (!myFrame.isGameSettingOpen()) {
            doGameFunctions(theKeyEvent);
            if (!myFrame.isGamePause() && myFrame.isGameStarted()) {
                for (final List<Integer> moveKeys: myGameMoves.keySet()) {
                    if (moveKeys.contains(theKeyEvent.getKeyCode())) {
                        myGameMoves.get(moveKeys).run();
                    }
                }
                
                for (final List<Integer> moveKeys: myCustomMoves.keySet()) {
                    if (moveKeys.contains(theKeyEvent.getKeyCode())) {
                        myCustomMoves.get(moveKeys).run();
                    }
                }
            }
        } 
        
    }

   

    /**
     * Takes care of pause/resume, instructions, new game, and quit events.
     * @param theKeyEvent key event
     */
    private void doGameFunctions(final KeyEvent theKeyEvent) {
        final int key = theKeyEvent.getKeyCode();
        if (key == KeyEvent.VK_I || key == KeyEvent.VK_Q 
                        || key == KeyEvent.VK_P || key == KeyEvent.VK_E) {
            myGameFunctions.get(key).run();
            
           
        } else if (key == KeyEvent.VK_N) {
            myGameFunctions.get(key).run();
        } 
        
        
    }

    


    /* (non-Javadoc)
     * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(final KeyEvent theKeyEvent) {
        final Object source = theKeyEvent.getSource();
        if (myFrame.isGameSettingOpen() && source instanceof JTextField) {
            if (theKeyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                deleteCustomKeys(theKeyEvent);
            } else {
                addCustomKeys(theKeyEvent);
            }
            
        }
    }

    /**
     * Add custom keys to list.
     * @param theKeyEvent key events.
     */
    private void addCustomKeys(final KeyEvent theKeyEvent) { 
        final JTextField textField = (JTextField) theKeyEvent.getSource();
        final String name = textField.getName();
        final int key = theKeyEvent.getKeyCode();
        
        final List<Integer> temp;
        if (name.contains(MOVE_ONE)) {
            temp = myCustomStringKeys.get(name);
            addFirstKey(temp, textField, key);
            addMoveToMap(temp, name);
        } else if (name.contains(MOVE_TWO)) {
            temp = myCustomStringKeys.get(name);
            addSecondKey(myCustomStringKeys.get(name), textField, key);
            addMoveToMap(temp, name);
            
        }

    }

    /**
     * Add the custom keys to map.
     * @param theKeyList list of the key moves.
     * @param theName name of the move.
     */
    private void addMoveToMap(final List<Integer> theKeyList, final String theName) {
        if (theName.contains("left")) {
            myCustomMoves.put(theKeyList, myGameMoves.get(myLeftKey));
        } else if (theName.contains("right")) {
            myCustomMoves.put(theKeyList, myGameMoves.get(myRightKey));
        } else if (theName.contains("down")) {
            myCustomMoves.put(theKeyList, myGameMoves.get(myDownKey));
        } else if (theName.contains("rotate")) {
            myCustomMoves.put(theKeyList, myGameMoves.get(myRotateKey));
        } else if (theName.contains("drop")) {
            myCustomMoves.put(theKeyList, myGameMoves.get(myDropKey));
        }
    }



    /**
     * Add second custom key to the specified list.
     * @param theSecondCustomKey first custom key.
     * @param theTextField JTextfield.
     * @param theKey key code.
     */
    private void addSecondKey(final List<Integer> theSecondCustomKey, 
                              final JTextField theTextField, final int theKey) {
        if (checkKeys(theKey)) {
            if (theSecondCustomKey.contains(theKey)) { 
                // need to check if the keys already existed.
                theTextField.setText("");
            } else {
                theSecondCustomKey.add(theKey);
                theTextField.setText(KeyEvent.getKeyText(theKey).toUpperCase(Locale.US));
            }
        } else {
            theTextField.setText("");
            JOptionPane.showMessageDialog(myFrame, MESSAGE);
        }
    }



    /**
     * Add first custom key to the specified list.
     * @param theFirstCustomKey first custom key.
     * @param theTextField JTextfield.
     * @param theKey key code.
     */
    private void addFirstKey(final List<Integer> theFirstCustomKey
                             , final JTextField theTextField
                             , final int theKey) {
        if (checkKeys(theKey)) {
            if (theFirstCustomKey.contains(theKey)) {
                theTextField.setText("");
            } else {
                theFirstCustomKey.add(0, theKey);
                theTextField.setText(KeyEvent.getKeyText(theKey).toUpperCase(Locale.US));
            }
        } else {
            theTextField.setText("");
            JOptionPane.showMessageDialog(myFrame, MESSAGE);
        }
    }


    /**
     * Check if a key already exists in the game.
     * @param theKey key move.
     * @return true if keys don't already exist;
     */
    private boolean checkKeys(final int theKey) { 
        boolean exist = true;
        
        exist = !myGameFunctions.containsKey(theKey);
        
        for (final List<Integer> moves: myGameMoves.keySet()) {
            for (final int keys: moves) {
                if (keys == theKey) {
                    exist = false;
                    break;
                }
            }
        }
        
        for (final List<Integer> moves: myCustomMoves.keySet()) {
            if (!moves.isEmpty()) {
                for (final int keys: moves) {
                    if (keys == theKey) {
                        exist = false;
                        break;
                    }
                }
            }
        }
        
        return exist;
    }



    /** 
     * Delete previous custom keys if exist.
     * @param theKeyEvent key events.
     */
    private void deleteCustomKeys(final KeyEvent theKeyEvent) { 
        final JTextField textField = (JTextField) theKeyEvent.getSource();
        final String name = textField.getName();
        final List<Integer> temp;
        if (name.contains(MOVE_ONE)) {
            temp = myCustomStringKeys.get(name);
            deleteFirstKey(temp);
            addMoveToMap(temp, name);
        } else if (name.contains(MOVE_TWO)) {
            temp = myCustomStringKeys.get(name);
            deleteSecondKey(myCustomStringKeys.get(name));
            myCustomMoves.put(temp, myCustomMoves.get(temp));
            addMoveToMap(temp, name);
        }
        
        
    }


    /**
     * Delete the second custom key in the list.
     * @param theSecondCustomKey first custom key.
     */
    private void deleteSecondKey(final List<Integer> theSecondCustomKey) {
        if (!theSecondCustomKey.isEmpty()) {
            if (theSecondCustomKey.size() >= 2) {
                theSecondCustomKey.remove(1);
            } else {
                theSecondCustomKey.remove(0);
            }
        }
    }



    /**
     * Delete the first custom key in the list.
     * @param theFirstCustomKey first custom key.
     */
    private void deleteFirstKey(final List<Integer> theFirstCustomKey) {
        if (!theFirstCustomKey.isEmpty()) {
            theFirstCustomKey.remove(0);
        }
        
    }



    /**
     * Get default left move keys.
     * @return the myLeftKey
     */
    public List<Integer> getMyLeftKey() {
        return myLeftKey;
    }



    /**
     * Get default right move keys.
     * @return the myRightKey
     */
    public List<Integer> getMyRightKey() {
        return myRightKey;
    }



    /**
     * Get default down move keys.
     * @return the myDownKey
     */
    public List<Integer> getMyDownKey() {
        return myDownKey;
    }



    /**
     * Get default drop move key.
     * @return the myDropKey
     */
    public List<Integer> getMyDropKey() {
        return myDropKey;
    }



    /**
     * Get default rotate move keys.
     * @return the myRotateKey
     */
    public List<Integer> getMyRotateKey() {
        return myRotateKey;
    }


}
