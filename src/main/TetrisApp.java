/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.TetrisFrame;

/**
 * @author Vecheka Chhourn
 * @version 02/23/2018
 */
public final class TetrisApp {


    /** 
     * empty constructor.
     */
    private TetrisApp() {
    }
    
    /**
     * Constructs the Controller GUI.
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        // Source: Class's lecture codes by Charles Bryan (TCSS 305 Winter 2018).
        try {
            
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TetrisFrame().setupComponents();
            }
        });

    }

}
