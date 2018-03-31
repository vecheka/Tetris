/*
 * TCSS 305- WINTER 2018
 * Assignment 5- Tetris Game. 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;



/**
 * @author Vecheka Chhourn
 * @version 02/28/2018
 * A class that draws the settings board of the game to customize keys.
 */
public class TetrisSettings extends JFrame {

    
    /** Custom keys string representation .*/
    protected static final String[] CUSTOM_KEY_NAME = {"left1", "left2", "right1", "right2"
                    , "down1", "down2", "rotate1", "rotate2", "drop1"};
    
    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = -7003917618827150001L;
    
    // Source: EasyStreetGUI by Charles Bryan
    /** A ToolKit to capture screen size. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit(); 
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** Title.*/
    private static final String TITLE = "Settings";
    /** Window width.*/
    private static final int WINDOW_WIDTH = 800;
    /** Window height.*/
    private static final int WINDOW_HEIGHT = 600;
    /** Window icon size.*/
    private static final int ICON_SIZE = 24;
    /** Text field width.*/
    private static final int TEXTFIELD_WIDTH = 10;
    /** Distance between two different moves keys text field.*/
    private static final int DISTANCE_BETWEEN_TEXTFIELD = 30;
    /** Distance between labels.*/
    private static final int DISTANCE_BETWEEN_LABEL = 50;
    /** Space between the two the same move keys text field.*/
    private static final int SPACE = 5;
    /** Or label.*/
    private static final String OR_LABEL = "              OR";
    
    /** String representation of game moves.*/
    private static final String[] GAME_MOVES = {"LEFT", "RIGHT", "DOWN", "ROTATE", "DROP"};
    
    
    /** Tetris  Moves.*/
    private final TetrisMoves myTetrisMoves;
   
    
    /** Default Left move text field.*/
    private List<JTextField> myDefaultLeftMove;
    /** Default Right text field.*/
    private List<JTextField> myDefaultRightMove;
    /** Default Down text field.*/
    private List<JTextField> myDefaultDownMove;
    /** Default Drop text field.*/
    private List<JTextField> myDefaultDropMove;
    /** Default Rotate text field.*/
    private List<JTextField> myDefaultRotateMove;
    /** map that points text field to the default key moves.*/
    private Map<List<JTextField>, List<Integer>> myDefaultKeyMoves;
    
    /** Custom Left move text field.*/
    private List<JTextField> myCustomLeftMove;
    /** Custom Right move text field.*/
    private List<JTextField> myCustomRightMove;
    /** Custom Down move text field.*/
    private List<JTextField> myCustomDownMove;
    /** Custom Drop move text field.*/
    private List<JTextField> myCustomDropMove;
    /** Custom Rotate move text field.*/
    private List<JTextField> myCustomRotateMove;

    
    
    /** Keep track of window state.*/
    private boolean myIsOpen;
    
    
    /**
     * Constructor that has a reference to key listener class.
     * @param theTetrisMoves key listener
     */
    public TetrisSettings(final TetrisMoves theTetrisMoves) {
        super(TITLE);
        myTetrisMoves = theTetrisMoves;
        final ImageIcon image = new ImageIcon("icons/gameIcon.png");
        setIconImage(image.getImage().getScaledInstance(ICON_SIZE * 2, ICON_SIZE * 2, 
                                                                 java.awt.Image.SCALE_SMOOTH));
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    }
    
    /**
     * Set up Setting Window and its components.
     */
    public void openSettings() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        setupWindowListener();
        setupDefaultTextFieldMoves();
        setupCustomTextFieldMoves();
        
        
        final JPanel controlKeyPanel = new JPanel(new BorderLayout());
        controlKeyPanel.add(createActionsLabel(), BorderLayout.WEST);
        controlKeyPanel.add(createDefaultKeys(), BorderLayout.CENTER);
        controlKeyPanel.add(createCustomKeys(), BorderLayout.EAST);
        
        final JTabbedPane tab = new JTabbedPane();
       
        tab.add("Control Keys", controlKeyPanel);
        
        add(tab);
        
        final int r = 53; 
        final int b = 81;
        final int g = 124;
        getContentPane().setBackground(new Color(r, b, g));
        
        // set location of window to the middle of the screen. 
        // (Citation: EasyStreetGUI by Charles Bryan)
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                    SCREEN_SIZE.height / 2 - getHeight() / 2);
        //pack();
        setVisible(true);
        setResizable(false);
    }
    
   

    /**
     * Set up text fields and their related customized keys moves.
     */
    private void setupCustomTextFieldMoves() {
       
        myCustomLeftMove = new ArrayList<JTextField>();
        myCustomRightMove = new ArrayList<JTextField>();
        myCustomDownMove = new ArrayList<JTextField>();
        myCustomRotateMove = new ArrayList<JTextField>();
        myCustomDropMove = new ArrayList<JTextField>();
        
        addJTextField(myCustomLeftMove);
        addJTextField(myCustomRightMove);
        addJTextField(myCustomDownMove);
        addJTextField(myCustomRotateMove);

        final JTextField drop = new JTextField();
        drop.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        drop.setHorizontalAlignment(JTextField.CENTER);
        myCustomDropMove.add(drop);
        
        
    }

    /**
     * Add window listener to listens to when the window is closed or opened.
     */
    private void setupWindowListener() {
        // inner class
        addWindowListener(new WindowAdapter() {

            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
             */
            @Override
            public void windowClosed(final WindowEvent theEvent) {
                myIsOpen = false;
            }

            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowActivated(java.awt.event.WindowEvent)
             */
            @Override
            public void windowActivated(final WindowEvent theEvent) {
                myIsOpen = true;
            }
            
        });
        
    }

    /**
     * Create custom text field keys for users to change the game's moves keys. 
     * @return panel with text fields.
     */
    private JPanel createCustomKeys() {
        final JPanel eastPanel = new JPanel();
        final Box box = new Box(BoxLayout.Y_AXIS);
                        
        final JLabel key = new JLabel("CUSTOM KEYS");
        key.setHorizontalAlignment(JLabel.CENTER);
        box.add(key);
        box.add(Box.createRigidArea(new Dimension(TEXTFIELD_WIDTH, 0)));

        
        final List<List<JTextField>> tempList = new ArrayList<List<JTextField>>();
        tempList.add(myCustomLeftMove);
        tempList.add(myCustomRightMove);
        tempList.add(myCustomDownMove);
        tempList.add(myCustomRotateMove);
        
        
        
        JTextField temp; 
        int index = 0;
        for (final List<JTextField> listField: tempList) {
            box.add(Box.createRigidArea(new Dimension(0, DISTANCE_BETWEEN_TEXTFIELD)));
            temp = listField.get(0);
            temp.setName(CUSTOM_KEY_NAME[index]);
            temp.addKeyListener(myTetrisMoves);
            
            box.add(temp);
            box.add(Box.createRigidArea(new Dimension(0, SPACE)));
            final JLabel or = new JLabel(OR_LABEL);
            box.add(or);
            temp = listField.get(1);
            temp.addKeyListener(myTetrisMoves);
            temp.setName(CUSTOM_KEY_NAME[index + 1]);
            box.add(Box.createRigidArea(new Dimension(0, SPACE)));
            box.add(temp);
            index += 2;
        }
       
        
        box.add(Box.createRigidArea(new Dimension(0, DISTANCE_BETWEEN_LABEL 
                                                  + (SPACE * 2) + SPACE)));
        temp = myCustomDropMove.get(0);
        temp.setName(CUSTOM_KEY_NAME[index]);
        temp.addKeyListener(myTetrisMoves);
        box.add(temp);
        eastPanel.add(box);
        
        return eastPanel;
    }

    /**
     * Set up the text field and its related default move keys.
     */
    private void setupDefaultTextFieldMoves() {
        myDefaultKeyMoves = new HashMap<List<JTextField>, List<Integer>>();
        myDefaultLeftMove = new ArrayList<JTextField>();
        myDefaultRightMove = new ArrayList<JTextField>();
        myDefaultDownMove = new ArrayList<JTextField>();
        myDefaultRotateMove = new ArrayList<JTextField>();
        myDefaultDropMove = new ArrayList<JTextField>();
        
        //  add JTextField
        addJTextField(myDefaultLeftMove);
        addJTextField(myDefaultRightMove);
        addJTextField(myDefaultDownMove);
        addJTextField(myDefaultRotateMove);
       
        
        final JTextField dropField = new JTextField();
        dropField.setHorizontalAlignment(JLabel.CENTER);
        dropField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        dropField.setEditable(false);
        myDefaultDropMove.add(dropField);
        
        
        
        
        // add keys to map
        myDefaultKeyMoves.put(myDefaultLeftMove, myTetrisMoves.getMyLeftKey());
        myDefaultKeyMoves.put(myDefaultRightMove, myTetrisMoves.getMyRightKey());
        myDefaultKeyMoves.put(myDefaultDownMove, myTetrisMoves.getMyDownKey());
        myDefaultKeyMoves.put(myDefaultDropMove, myTetrisMoves.getMyDropKey());
        myDefaultKeyMoves.put(myDefaultRotateMove, myTetrisMoves.getMyRotateKey());
        
    }

    
    /** 
     * Add JTextFields to a list.
     * @param theJTextFieldMove list to store JTextField
     */
    private void addJTextField(final List<JTextField> theJTextFieldMove) {
        
        final JTextField field1 = new JTextField();
        field1.setHorizontalAlignment(JLabel.CENTER);
        field1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        final JTextField field2 = new JTextField(); 
        field2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        field2.setHorizontalAlignment(JLabel.CENTER);
        theJTextFieldMove.add(field1);
        theJTextFieldMove.add(field2);
        
    }

    /**
     * Create default key bindings instruction of the game.
     * @return east panel
     */
    private JPanel createDefaultKeys() {

        final JPanel centerPanel = new JPanel();
        
        final Box box = new Box(BoxLayout.Y_AXIS);

        final JLabel key = new JLabel("DEFAULT KEYS");
        key.setHorizontalAlignment(JLabel.CENTER);
        box.add(key);
        box.add(Box.createRigidArea(new Dimension(TEXTFIELD_WIDTH, 0)));
        
        final List<List<JTextField>> tempList = new ArrayList<List<JTextField>>();
        tempList.add(myDefaultLeftMove);
        tempList.add(myDefaultRightMove);
        tempList.add(myDefaultDownMove);
        tempList.add(myDefaultRotateMove);
        
        
        List<Integer> keys;
        JTextField tempField;
        for (final List<JTextField> listField: tempList) {
            keys = myDefaultKeyMoves.get(listField);
            box.add(Box.createRigidArea(new Dimension(0, DISTANCE_BETWEEN_TEXTFIELD)));
            tempField = listField.get(0);
            tempField.setEditable(false);
            tempField.setText(KeyEvent.getKeyText(keys.get(0)).toUpperCase(Locale.US));
            box.add(listField.get(0));
            box.add(Box.createRigidArea(new Dimension(0, SPACE)));
            final JLabel or = new JLabel(OR_LABEL);
            box.add(or);
            box.add(Box.createRigidArea(new Dimension(0, SPACE)));
            tempField = listField.get(1);
            tempField.setText(KeyEvent.getKeyText(keys.get(1)).toUpperCase(Locale.US));
            tempField.setEditable(false);
            box.add(listField.get(1));
            
        }
        box.add(Box.createRigidArea(new Dimension(0, DISTANCE_BETWEEN_LABEL 
                                                  + (SPACE * 2) + SPACE)));
        keys = myDefaultKeyMoves.get(myDefaultDropMove);
        myDefaultDropMove.get(0).setText(KeyEvent.
                                         getKeyText(keys.get(0)).toUpperCase(Locale.US));
        box.add(myDefaultDropMove.get(0));
        
        
        centerPanel.add(box);
        
        return centerPanel;
    }

    /**
     * Create labels of the game moves.
     * @return centerPanel panel to add labels to
     */
    private JPanel createActionsLabel() {
        final JPanel westPanel = new JPanel();
        
        final Box box = new Box(BoxLayout.Y_AXIS);
        final JLabel action = new JLabel("ACTIONS");
        action.setHorizontalAlignment(JLabel.CENTER);
        box.add(action);
        box.add(Box.createVerticalStrut(DISTANCE_BETWEEN_LABEL));
        JLabel gameMove;
        for (final String move: GAME_MOVES) {
            gameMove = new JLabel(move);
            gameMove.setHorizontalAlignment(JLabel.CENTER);
            gameMove.setVerticalAlignment(JLabel.CENTER);
            box.add(gameMove);
            box.add(Box.createVerticalStrut(DISTANCE_BETWEEN_LABEL 
                                            + DISTANCE_BETWEEN_TEXTFIELD));
        }
        westPanel.add(box);
        return westPanel;
    }


    /**
     * Getters for the state of the window.
     * @return true if window is opened.
     */
    public boolean isWindowOpen() {
        return myIsOpen;
    }

   
   



   

    
    
   
}
