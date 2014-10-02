package michelle.calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *This class creates a button panel,when the panel is stretched, the 
 * components expand. The input is accepted from the mouse or the keyboard.
 * @author Yu Xiao
 */
public class ButtonPanel extends JPanel
{
    private static final String[] keys = {"CE", "C", "7", "8","9", "/", "=", "4", "5", "6",
                                          "*", "1", "2", "3", "-", "0", "+/-", ".", "+", "(", ")"};                                           
    private GridBagLayout gridBagLayout;
    private GridBagConstraints[] constraints;
    private JButton[] theButtons;
    private Font font;      
    private DisplayModel displayText;
   
    /**
     * Default Constructor sets up the panel
     * @param displayText 
     */
    public ButtonPanel(DisplayModel displayText)
    {
        super();
        this.displayText = displayText;
        font = new Font(Font.SANS_SERIF, Font.BOLD,18);
        initialize();
    }
 
    public void initialize()
    {
        //button panel uses GridBagLayout
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        initializeConstraints();
        createAndPlaceButtons();
        setButtonsWidthAndHeight();    
    }
    
    /**
     * Initialize the constraints
     */
    public void initializeConstraints()
    {
        constraints = new GridBagConstraints[keys.length];
        for(int x = 0; x < keys.length; ++x)
        {
            constraints[x] = new GridBagConstraints();
            //specify the minimum amount of space between the component and
            //  and the corresponding edge
            constraints[x].insets = new Insets(5, 5, 5, 5);
            //each button gets a full share of the extra space when the panel 
            //is stretched
            constraints[x].fill = GridBagConstraints.BOTH;
            constraints[x].weightx = 0.8;
            constraints[x].weighty = 0.8;
        }
                
        //set the constraints for the 1st element constraints[0]
        constraints[0].gridx = 2;
        constraints[0].gridy = 0;
        constraints[0].gridwidth = 2;
        //set the constraints for the 2nd element constraints[1]
        constraints[1].gridx = 4;
        constraints[1].gridy = 0;
        constraints[1].gridwidth = 1;
        //set the constraints for the 3rd element constraints[2]
        constraints[2].gridx = 0;
        constraints[2].gridy = 1;
        //set the constraints for the 4th element constraints[3]
        constraints[3].gridx = 1;
        constraints[3].gridy = 1;
        //set the constraints for the 5th element constraints[4]
        constraints[4].gridx = 2;
        constraints[4].gridy = 1;
        //set the constraints for the 6th element constraints[5]
        constraints[5].gridx = 3;
        constraints[5].gridy = 1;
        //set the constraints for the 7th element constraints[6]
        constraints[6].gridx = 4;
        constraints[6].gridy = 1;
        constraints[6].gridheight = 4;
        //set the constraints for the 8th element constraints[7]
        constraints[7].gridx = 0;
        constraints[7].gridy = 2;
        //set the constraints for the 9th element constraints[8]
        constraints[8].gridx = 1;
        constraints[8].gridy = 2;
        //set the constraints for the 10th element constraints[9]
        constraints[9].gridx = 2;
        constraints[9].gridy = 2;
        //set the constraints for the 11th element constraints[10]
        constraints[10].gridx = 3;
        constraints[10].gridy = 2;
        //set the constraints for the 12th element constraints[11]
        constraints[11].gridx = 0;
        constraints[11].gridy = 3;
        //set the constraints for the 13th element constraints[12]
        constraints[12].gridx = 1;
        constraints[12].gridy = 3;
        //set the constraints for the 14th element constraints[13]
        constraints[13].gridx = 2;
        constraints[13].gridy = 3;
        //set the constraints for the 15th element constraints[14]
        constraints[14].gridx = 3;
        constraints[14].gridy = 3;
        //set the constraints for the 16th element constraints[15]
        constraints[15].gridx = 0;
        constraints[15].gridy = 4;
        //set the constraints for the 17th element constraints[16]
        constraints[16].gridx = 1;
        constraints[16].gridy = 4;
        //set the constraints for the 18th element constraints[17]
        constraints[17].gridx = 2;
        constraints[17].gridy = 4;
        //set the constraints for the 19th element constraints[18]
        constraints[18].gridx = 3;
        constraints[18].gridy = 4;
        //set the constraints for the 20th element constraints[18]
        constraints[19].gridx = 0;
        constraints[19].gridy = 0;
        //set the constraints for the 21st element constraints[18]
        constraints[20].gridx = 1;
        constraints[20].gridy = 0;
    }
    
    public void createAndPlaceButtons()
    {       
        Border b = BorderFactory.createLineBorder(Color.black);
        //the length of array theButtons is the same with the length of keys[]
        theButtons = new JButton[keys.length];                        
        MyKeyListener myKeyListener = new MyKeyListener();
        MyActionListener myActionListener = new MyActionListener();
        
        for(int x = 0; x < keys.length; ++x)
        {
            theButtons[x] = new JButton(keys[x]);
            //set the font of each button
            theButtons[x].setFont(font);  
            //set the border of each button
            theButtons[x].setBorder(b);           
            //add keyListener to each button
            theButtons[x].addKeyListener(myKeyListener);
            //add actionListener to each button
            theButtons[x].addActionListener(myActionListener);
            //add corresponding constraints to each button
            add(theButtons[x],constraints[x]);
        } 
        
        //add tool tips for "CE", "C", and "+/-" keys
        //when put mouse on these three keys, it will tell the user what to input on keyboard
        theButtons[0].setToolTipText("press E / e key");
        theButtons[1].setToolTipText("press C / c key");
        theButtons[16].setToolTipText("press Z / z key");
        //theButtons[0].requestFocusInWindow();    
        //theButtons[0].requestFocus();
        //theButtons[x].setRequestFocusEnabled(true);
    }
    
     /**
     * Determines which button is widest and use that to make all buttons the
     * same width and height
     */
    private void setButtonsWidthAndHeight() {
        Dimension newSize = new Dimension(0, 0);
        Dimension currentSize = new Dimension(0, 0);

        // Get the size of each button and preserve the widest
        for (int x = 0; x < keys.length; x++) {
            currentSize = theButtons[x].getPreferredSize();
            if (currentSize.width > newSize.width) {
                newSize.width = currentSize.width;
            }
        }
        // Set height to width for square buttons
        newSize.height = newSize.width;

        // Set the new preferred size for each button
        for (int x = 0; x < keys.length; x++) {
            theButtons[x].setPreferredSize(newSize);
        }
    }
        
    class MyKeyListener extends KeyAdapter  {

        /*
         * Receive key press events and click the appropriate button
         */
        @Override
        public void keyTyped(KeyEvent evt) {

            int keyChar = evt.getKeyChar();

            try {
                switch (keyChar)
                {
                    case KeyEvent.VK_0:
                        theButtons[indexOf(keys, "0")].doClick();
                        break;
                    case KeyEvent.VK_1:
                        theButtons[indexOf(keys, "1")].doClick();
                        break;
                    case KeyEvent.VK_2:
                        theButtons[indexOf(keys, "2")].doClick();
                        break;
                    case KeyEvent.VK_3:
                        theButtons[indexOf(keys, "3")].doClick();
                        break;
                    case KeyEvent.VK_4:
                        theButtons[indexOf(keys, "4")].doClick();
                        break;
                    case KeyEvent.VK_5:
                        theButtons[indexOf(keys, "5")].doClick();
                        break;
                    case KeyEvent.VK_6:
                        theButtons[indexOf(keys, "6")].doClick();
                        break;
                    case KeyEvent.VK_7:
                        theButtons[indexOf(keys, "7")].doClick();
                        break;
                    case KeyEvent.VK_8:
                        theButtons[indexOf(keys, "8")].doClick();
                        break;
                    case KeyEvent.VK_9:
                        theButtons[indexOf(keys, "9")].doClick();
                        break; 
                    case '+':
                        theButtons[indexOf(keys, "+")].doClick();                        
                        break;
                    case KeyEvent.VK_MINUS:
                        theButtons[indexOf(keys, "-")].doClick();
                        break;
                    case '*':
                        theButtons[indexOf(keys, "*")].doClick();
                        break;
                    case KeyEvent.VK_SLASH:
                        theButtons[indexOf(keys, "/")].doClick();
                        break;
                    case KeyEvent.VK_PERIOD:
                        theButtons[indexOf(keys, ".")].doClick();
                        break;
                    case KeyEvent.VK_EQUALS:
                        theButtons[indexOf(keys, "=")].doClick();
                        break;
                    case '(':
                        theButtons[indexOf(keys, "(")].doClick();
                        break;
                    case ')':
                        theButtons[indexOf(keys, ")")].doClick();
                        break;
                    case 'Z':
                    case 'z':
                        theButtons[indexOf(keys, "+/-")].doClick();                       
                        break;
                    case 'C':
                    case 'c':
                        theButtons[indexOf(keys, "C")].doClick();
                        break;
                    case 'E':
                    case 'e':
                        theButtons[indexOf(keys, "CE")].doClick();
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Potential problem if indexOf returns an index out of range
                // likely due to not finding the element in the array and
                // therefore returning a -1
                // Just ignore it.
            }
        }

        /**
         * Search "array" for the specified "key" value
         *
         * @param array to search through
         * @param key to search for
         * @return index in the array
         */
        private int indexOf(String array[], String key) {
            for (int x = 0; x < array.length; x++) {
                if (array[x].equalsIgnoreCase(key)) {
                    return x;
                }
            }
            return -1;
        }
    }

    /**
     * Inner class action listener for buttons
     */
    class MyActionListener implements ActionListener {

        /**
         * Receive button presses
         * @param e the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();          
                displayText.setDisplayText(buttonText);                       
        }
    }
}
