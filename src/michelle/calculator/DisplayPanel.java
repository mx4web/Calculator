package michelle.calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *This class creates a display panel, it displays the value that 
 * displayModel notified
 * @author Yu Xiao
 */
public class DisplayPanel extends JPanel implements Observer
{
    private GridBagLayout gridBagLayout;
    private GridBagConstraints cons;
    private JTextField textField;
    private Font font;
    
    /**
     * Default Constructor
     */
    public DisplayPanel()
    {
        super();
        //display panel uses GridBagLayout
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);       
        font = new Font(Font.SANS_SERIF, Font.BOLD,18);
        createAndPlaceTextField();
    }
    
    public void createAndPlaceTextField()
    {
        Border b = BorderFactory.createLineBorder(Color.black);
        textField = new JTextField();  
        //set the font of text field
        textField.setFont(font); 
        //set the border of the text field
        textField.setBorder(b);
        //set the background of the text field
        textField.setBackground(Color.white);
        textField.setPreferredSize(new Dimension(300,30));
        //this text field is not editable, is only for display
        textField.setEditable(false);    
        //set the font alignment
        textField.setHorizontalAlignment(JTextField.RIGHT);
        
        cons = new GridBagConstraints();
        //the text field gets a full share of the extra space when the component 
        //was stretched
        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 0.2;
        cons.weighty = 0.2;   
        //add text field to the display panel
        add(textField,cons);        
    }
    
    /**
     * Callback method for the observable event     
     */
     @Override
    public void update(Observable observable, Object object) {

        // Determine the source of the observer event
        if (observable instanceof DisplayModel) {
            DisplayModel displayText = (DisplayModel) observable;
           //System.out.println("print out from DisplayPanel  " + displayText.getDisplayText());           
            textField.setText(displayText.getDisplayText());           
        }
    }    
}
