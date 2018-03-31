/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Vecheka Chhourn
 * @version 03/06/2018
 * A class that saves high score of the game to a file.
 */
public class TetrisHighScore {
    
    /** Default file's name to store the game's high score.*/
    private static final String FILE_NAME = "highscore.txt";
    
    
    /** To print out to file. */
    private PrintStream myStream;
    /** High score.*/
    private int myHighScore;
    
    /**
     * Read high score file on first initiation.
     */
    public TetrisHighScore() {
        readFile();
    }


    
    
    /**
     * Read file and store high score.
     */
    private void readFile() {
        final Scanner inputFile;
        try {
            inputFile = new Scanner(new File(FILE_NAME));
            myHighScore = inputFile.nextInt();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
       
        
        
    }
    
    /** 
     * Set new high score if there is one.
     * @param theScore new score.
     */
    public void setNewHighScore(final int theScore) {
        if (theScore > myHighScore) {
            myHighScore = theScore;
            try {
                myStream = new PrintStream(new File(FILE_NAME));
                myStream.print(theScore);
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    
    /**
     * Reset high score to zero.
     */
    public void resetHighScore() {
        myHighScore =  0;
        try {
            myStream = new PrintStream(new File(FILE_NAME));
            myStream.print(myHighScore);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /** 
     * Getter for high score.
     * @return high score of the game.
     */
    public int getHighScore() {
        return myHighScore;
    }
    
}
