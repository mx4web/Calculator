package michelle.calculator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *This application creates a simulate calculator, when the buttons 
 * are pressed the text on the face of the button appears in the display
 * @author Yu Xiao
 */
public class CalculatorFrame extends JFrame
{
    private JPanel workingPanel;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints[] constraints;
    private DisplayPanel displayPanel;
    private ButtonPanel buttonPanel;
    private DisplayModel model;   
        
    /**
     * Default Constructor
     */
    public CalculatorFrame()
    {
        super("Calculator1.1");       
        initComponents();
    }
       
    public void initComponents()
    { 
        //creat a working panel using GridBagLayout 
        workingPanel = new JPanel();
        gridBagLayout = new GridBagLayout(); 
        workingPanel.setLayout(gridBagLayout);
        displayPanel = new DisplayPanel();
        model = new DisplayModel("");
        //wire the ButtonPanel and DisplayModel
        buttonPanel = new ButtonPanel(model);
               
        initializeConstraints();
        
        //add displayPanel and buttonPanel to the workingPanel
        workingPanel.add(displayPanel, constraints[0]);
        workingPanel.add(buttonPanel, constraints[1]);
        //add the workingPanel to frame
        add(workingPanel);
        //register the DisplayPanel in DispalyModel
        model.addObserver(displayPanel);              
        
        
        setVisible(true);
        setSize(gridBagLayout.preferredLayoutSize(this));  
        this.setResizable(false);   //this calculator is not resizable
        setLocationRelativeTo(null);
    }
    
    /**
     * Add constraints to GridBagLayout of workingPanel
     */
    public void initializeConstraints()
    {
        //there are two components adding to the working panel, so an  
        //array is used to set the GrdBagConstraints for each component
         constraints = new GridBagConstraints[2];        
         for(int x = 0; x < 2; ++x)
        {
            constraints[x] = new GridBagConstraints();
            //specify the minimum amount of space between the component (displayPanel
            // and buttonPanel) and the working panel
            constraints[x].insets = new Insets(5, 5, 5, 5);
            constraints[x].fill = GridBagConstraints.BOTH;
            //the component gets a full share of the extra space amont columns
            constraints[x].weightx = 1.0;
            //the component gets a full share of the extra space amont rows
            constraints[x].weighty = 1.0;
        }
        constraints[0].gridx = 0;
        constraints[0].gridy = 0;
        constraints[1].gridx = 0;
        constraints[1].gridy = 1;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
        public void run() {                
                CalculatorFrame frame = new CalculatorFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
            }
        });
    }
}
