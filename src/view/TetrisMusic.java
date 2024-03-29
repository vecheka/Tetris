/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Vecheka Chhourn
 * @version 03/03/2018
 * A class that read a music file, and play as a background music in Tetris game.
 *                              ********Note******** 
 *  This codes were not implemented by me, I found it online. I did make a few changes
 *  , and implemented the pause method by myself.
 *  Other than that, the codes belong to someone else. 
 *  I do not mean to plagiarize, and full credit goes to its original owner.
 *  Link to the codes: (http://www.dreamincode.net/forums/topic/343804
 *                              -how-to-add-background-music-to-my-2d-platformer-game/)
 *  
 */
public class TetrisMusic {
    
    /** Music file .*/
    protected static TetrisMusic music = new TetrisMusic("pianoMan.wav");
    
    
    /**Clip to play music.*/
    private Clip myClip;
    /** Position.*/
    private long myPosition;
    
    
    /**
     * Constructor that takes the music file's name.
     * @param theFileName sound file.
     */
    public TetrisMusic(final String theFileName) {
        readMusic(theFileName);  
    }
    
    
    /**
     * Read the music file.
     * @param theFileName music file.
     */
    private void readMusic(final String theFileName) {
        final AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(new File(theFileName));
            try {
                myClip = AudioSystem.getClip();
            } catch (final LineUnavailableException e) {
                e.printStackTrace();
            }
            
            try {
                myClip.open(stream);
            } catch (final LineUnavailableException e) {
                e.printStackTrace();
            }
            
        } catch (final UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (final IOException e) {
           
            e.printStackTrace();
        }
       
        
        
    }


    /**
     * Play.
     */
    public void play() {
    
        if (myClip != null) {
            new Thread() {
                public void run() {
                    myClip.stop();
                    myClip.setMicrosecondPosition(myPosition);
                    myClip.start();
                }
            }.start();
        }
         
    }
    
    /** 
     * Pause.
     */
    public void pause() {
        myPosition = myClip.getMicrosecondPosition();
        myClip.stop();
        myClip.setMicrosecondPosition(myPosition);
    }
    
    
    
    /**
     * Stop.
     */
    public void stop() {
        if (myClip == null) {
            return;
        }
        myClip.stop();
    }
    
    
    /**
     * Loop.
     */
    public void loop() {
    
        if (myClip != null) {
            new Thread() {
                public void run() {
                    synchronized (myClip) {
                        myClip.stop();
                        myClip.setFramePosition(0);
                        myClip.loop(Clip.LOOP_CONTINUOUSLY);
                        
                    }
                }
            }.start();
        }
    }
    
//    /**
//     * True if active.
//     * @return true
//     */
//    public boolean isActive() {
//        return myClip.isActive();
//    }
//    
    
}
