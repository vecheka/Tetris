/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import model.Block;
import model.TetrisPiece;

/**
 * @author Vecheka Chhourn
 * @version 03/06/2018
 * A class that saves the previous game to a file, so that it can be loaded later.
 */
public class TetrisSaveGame {
    
    /** Default file to store the saved game.*/
    private static final String FILE_NAME = "savedGame.txt";
    /** Delimiter.*/
    private static final String[] DELIMETER = {":", ","};
    /** File extension.*/
    private static final String FILE_EXTENSION = ".txt";
    
    
    /**
     * Save game board, and stats to a default file.
     * @param theBoardData board data of the game.
     * @param theNextPiece next piece on the board.
     * @param theCurrentGameStats map of current game stats.
     */
    public void saveGameToDefaultFile(final Block[][] theBoardData
                                      , final TetrisPiece theNextPiece,
                               final Map<String, Integer> theCurrentGameStats) {
        if (theBoardData != null) {
            try {
                final PrintStream stream = new PrintStream(new File(FILE_NAME));
                
                // write game stats, and save to file
                for (final String stat: theCurrentGameStats.keySet()) {
                    stream.println(stat + DELIMETER[0] + theCurrentGameStats.get(stat));
                }
                
                // write the pieces, and board data
                stream.println(theNextPiece);
                for (final Block[] block: theBoardData) {
                    final Block temp = block[0];
                    stream.print(temp);
                    for (int i = 1; i < block.length; i++) {
                        stream.print(DELIMETER[1] + block[i]);
                    }
                    stream.println();
                }
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }
        
        }
    }
    
    
    /**
     * Save game board, and stats to a specified file by the players.
     * @param theBoardData board data of the game.
     * @param theNextPiece next piece on the board.
     * @param theCurrentGameStats map of current game stats.
     * @param theFileName file name.
     */
    public void saveGameToNewFile(final Block[][] theBoardData
                                      , final TetrisPiece theNextPiece,
                               final Map<String, Integer> theCurrentGameStats,
                               final String theFileName) {
        String fileName = "";
        if (theFileName.contains(FILE_EXTENSION)) {
            fileName = theFileName.substring(0, theFileName.indexOf('.'));
        } 
        
        
        if (theBoardData != null) {
            try {
                final PrintStream stream = new PrintStream(new 
                                                           File(fileName + FILE_EXTENSION));

                // write game stats, and save to file
                for (final String stat: theCurrentGameStats.keySet()) {
                    stream.println(stat + DELIMETER[0] + theCurrentGameStats.get(stat));
                }

                // write the pieces, and board data
                stream.println(theNextPiece);
                for (final Block[] block: theBoardData) {
                    final Block temp = block[0];
                    stream.print(temp);
                    for (int i = 1; i < block.length; i++) {
                        stream.print(DELIMETER[1] + block[i]);
                    }
                    stream.println();
                }
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }

        }


    }
}
