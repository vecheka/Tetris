/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Block;
import model.Point;
import model.TetrisPiece;

/**
 * @author Vecheka Chhourn
 * @version 02/24/2018
 * A class to draw the different Tetris piece types.
 */
public class TetrisShape {
    
   
    /** List to store the shapes of Piece type.*/
    private final List<Shape> myShapeList;
    /** List to store shape's points.*/
    private final List<Point> myPointsList; // not needed but will leave here for now
    /** x-coordinate the piece will be located at.*/
    private int myX;
    /** Size of the shape.*/
    private int mySize;
    /** Piece type.*/
    private TetrisPiece myPiece;
    /** Color of each Piece types.*/
    private final Map<Block, Color> myPieceColorList;
    
    
    
    /**
     * Constructor to initialize collections.
     * and create the shape.
     */
    public TetrisShape() {
        
        myShapeList = new ArrayList<Shape>();
        myPointsList = new ArrayList<Point>();
        myPieceColorList = new HashMap<Block, Color>();
    }
    
    
    
    
    /** 
     * Create a map of piece and its color representation.
     * @return map of piece and its color.
     */
    public Map<Block, Color> getPieceColorList() {
        // default colors
        final Color[] colorList = {Color.BLUE, Color.GREEN, Color.RED, Color.PINK
                        , Color.YELLOW, Color.CYAN, Color.ORANGE};
        int i = 0;
        for (final Block piece: Block.values()) {
            if (i < colorList.length && piece != Block.EMPTY) {
                myPieceColorList.put(piece, colorList[i]);
                i++;
            }
           
        }
        
        
        return myPieceColorList;
    }
    
    
    
    /**
     * Add shapes at a location on the frame to the shape list.
     * @param theX x coordinate
     * @param theArea size of the piece
     * @param thePiece piece type
     */
    public void addShapesAt(final int theX, final int theArea, 
                          final TetrisPiece thePiece) {
        myX = theX;
        mySize = theArea;
        myPiece = thePiece;
        switch (myPiece.getBlock()) {
            case I: 
                drawI();
               
                break;
            case J:
                drawJ();
               
                break;
            case L:
                drawL();
                
                break;
            case O:
                drawO();
               
                break;
            case S:
                drawS();
               
                break;
            case Z:
                drawZ();
               
                break;
            case T:
                drawT();
                
                break;
            default:
                break;
        }
        
    }
    
    /**
     * Draw T piece on the Next Piece box.
     */
    private void drawT() {
        int x = myX;
        int y;
        Shape block;  
        y = mySize * (myPiece.getWidth() + 1);
        for (int i = 0; i < myPiece.getWidth(); i++) {
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            x += mySize;
            myShapeList.add(block);
           
           
        }
        y -= mySize;
        x -= mySize;
        block = new Rectangle(x, y, mySize, mySize);
        myPointsList.add(new Point(x, y));
        myShapeList.add(block);
       
    }

    /**
     * Draw Z piece on the Next Piece box.
     */
    private void drawZ() {
        int x = myX;
        int y;
        Shape block;
        y = mySize * (myPiece.getWidth());
        
        for (int i = 0; i < myPiece.getHeight(); i++) {
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            x += mySize;
            myShapeList.add(block);
           
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            myShapeList.add(block);
           
            y += mySize;
        }
    }

    /**
     * Draw O piece on the Next Piece box.
     */
    private void drawO() {
        int x = myX;
        int y;
        final int evenType = 20;
        Shape block;
        y = mySize * (myPiece.getWidth());
        for (int i = 0; i < myPiece.getHeight(); i++) {
            block = new Rectangle(x + mySize + evenType, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize + evenType, y));
            myShapeList.add(block);
            
            x += mySize;
            block = new Rectangle(x + mySize + evenType, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize + evenType, y));
            myShapeList.add(block);
           
            x = myX;
            y += mySize;
        }
        
    }

    /**
     * Draw S piece on the Next Piece box.
     */
    private void drawS() {
        int x = myX + mySize;
        int y;
        Shape block;
        y = mySize * (myPiece.getWidth());
        for (int i = 0; i < myPiece.getHeight(); i++) {
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            x += mySize;
            myShapeList.add(block);
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            myShapeList.add(block);
            x = myX;
            y += mySize;
        }
        
    }

    /**
     * Draw L piece on the Next Piece box.
     */
    private void drawL() {
        int x = myX;
        final int y;
        Shape block;
        y = mySize * (myPiece.getWidth() + 1);
        for (int i = 0; i < myPiece.getWidth(); i++) {
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            myShapeList.add(block);
            x += mySize;
        }
        block = new Rectangle(x, y - mySize, mySize, mySize);
        myPointsList.add(new Point(x, y - mySize));
        myShapeList.add(block);
        
    }

    /**
     * Draw J piece on the Next Piece box.
     */
    private void drawJ() {
        int x = myX;
        int y;
        Shape block;
        y = mySize * myPiece.getWidth();
        block = new Rectangle(x + mySize , y, mySize, mySize);
        myPointsList.add(new Point(x + mySize, y));
        myShapeList.add(block);
        y += mySize;
        for (int i = 0; i < myPiece.getWidth(); i++) {
            block = new Rectangle(x + mySize, y, mySize, mySize);
            myPointsList.add(new Point(x + mySize, y));
            myShapeList.add(block);
            x += mySize;
        }
        
    }

    /**
     * Draw I piece on the Next Piece box.
     */
    private void drawI() {
        int x = myX;
        final int y;
        final int evenType = 20;
        Shape block;
        y = mySize * myPiece.getWidth() - mySize / 2;
        for (int i = 0; i < myPiece.getWidth(); i++) {
            block = new Rectangle(x + evenType, 
                                  y, 
                                  mySize, mySize);
            myPointsList.add(new Point(x + evenType, y));
            myShapeList.add(block);
            x += mySize;
        }
        
    }


   


    /**
     * Getter for the shape list.
     * @return shape list
     */
    public List<Shape> getMyShapeList() {
        return myShapeList;
    }
    
    /** 
     * Getter for point list.
     * @return point list.
     */
    public List<Point> getMyPointsList() {
        return myPointsList;
    }
}
