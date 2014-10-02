package michelle.calculator;

import java.util.Observable;

/**
 *This class represents a model that needs to inform 
 * DisplayPanel when it is changed
 * @author Yu Xiao
 */
public class DisplayModel extends Observable
{
    private String displayText;
    private String expression = "";
    
    /**
     * Constructor sets the initial value of the display text
     * @param initialText 
     */
    public DisplayModel(String initialText)
    {
        super();
        setDisplayText(initialText);
    }
    
    /**
     * Set displayedText as called from ButtonPanel
     * @param displayedText 
     */
    public void setDisplayText(String displayedText)
    {
        this.displayText = displayedText;
        
        switch (displayedText)
        {
            case "+/-":               
                break;
            case "C":   //clear the expression when click "C"
                expression = "";
                break;
            case "CE":
                expression += "E";  //expression adds E when click CE
                break;
            case "=":   //calculate the exprssion, and set the displayText with result
                ParseExp result = new ParseExp(expression);
                displayText = expression + "=" + result.getExpResult();
                break;
            default:    //appends the expression for all other click
                expression += displayedText;
                System.out.println(expression);
        }  
         // must call setChanged before notifyObservers to
        // indicate model has changed
        setChanged();
        // Overloaded version that allows new display Text
        // to be sent in the notification. Including the changed text
        // in the notification means that the observers do not have to
        // call back to the data object
        notifyObservers(displayText);
    }
    
   
    /**
     * Get the displayed text
     * @return displayText
     */
    public String getDisplayText()
    {
        return displayText;
    }
    
    public String getExpression()
    {
        return expression;
    }
    
}
