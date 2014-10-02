package michelle.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 *This class puts the string to a deque, converse the infix 
 * expression to postfix expression and calculate the postfix expression.
 * @author Yu Xiao
 */
public class ParseExp
{
    private String s;
    private Deque<String> expDeque;
    private Deque<String> operatorStack;
    private Queue<String> operandQueue;
    private double expResult = 0;
    private String[] operator = {"+", "-", "*", "/"};
    private String[] num = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."};
    
    /**
     * Constructor
     * @param s 
     */
    public ParseExp(String s)
    {
        super();
        this.s = s;
        expDeque = new ArrayDeque<>();
        operatorStack = new ArrayDeque<>();
        operandQueue = new ArrayDeque<>();
        prepareCalculation();
    }
    
    public void prepareCalculation()
    {
        getExpDeque();  //put the string expression into infix deque
        getOperandQueue();  //get postfix queue
    }
    
    /**
     * This method is used to put the string expression into a deque.
     * The deque can include operands and operators, which are composed of numbers
     * from 0 to 9, operators +, -, *, /, and decimal point.
     * @return 
     */
    public void getExpDeque()
    {
        int i = 0;
        System.out.println("the length of the expression is " + s.length());
        while (i < s.length() ) 
        {
            String chi = s.charAt(i) + "";
            //if the character is an operator, add it to the deque directerly
            if (isOperator(chi)) 
            {                          
                expDeque.offerLast(chi);
                ++i;
            } 
            //if the character is a number, appends the next character 
            //util the next character is not number or decimal point
            else if (isNum(chi))
            {
                String numb = chi;
                int j = i + 1;
                if(j < s.length())
                {          
                    while( j < s.length() && isNum(s.charAt(j) + ""))
                    {
                        numb += s.charAt(j);    //chj is the next character after chi
                        ++j;
                    }
                    expDeque.offerLast(numb); //add the number to the deque
                    i = j;
                }
                else
                {
                    expDeque.offerLast(chi);
                    ++i;
                }               
            }
            //if the character is CE, remove the last character in the deque
            else if(chi.equals("E"))
            {
                expDeque.pollLast();    //remove the last entry from the deque
                ++i;
            }
            else
            {
                expDeque.offerLast(chi);
                ++i;
            }
        } 
    }

    /**
     * This method is used to get the postfix queue
     * @return 
     */
     public void getOperandQueue()
    {
        scanExpDeque(); //seperate operator and operand
        //add all operators to operand queue
        while (!operatorStack.isEmpty())
        {
            //get the first operator from the operator stack
            String topStack = operatorStack.pop();
            //add it to the postfix queue
            operandQueue.offer(topStack);
        }
    }
          
     /**
      * This method is used to calculate the expression.
      * @return 
      */
     public double getExpResult()
    {
        Deque<String> tempStack = new ArrayDeque<>();
        boolean flag = true;       
        
        while (!operandQueue.isEmpty() && flag)
        {           
            String ss = operandQueue.poll();//get the first element of the postfix queue

            if (isOperator(ss))
            {
                //pop out the top two elements of the stack
                String temp1 = tempStack.pop();
                String temp2 = tempStack.pop();
                
                //if a divider is 0.0, output is 0                 
                if (ss.equals("/") && (Double.parseDouble(temp1) == 0.0))
                {                    
                    flag = false;
                }
                else
                {
                    //transfer these two elements from string to double, and calculate
                    double resultD = partCalculate(temp1, temp2, ss);
                    String resultS = resultD + "";
                    tempStack.push(resultS);
                }
            } else
            {
                tempStack.push(ss);
            }
        }
        //when the postfix queue is empty, get the element from tempStack
        try
        {
            if(flag)
            {
                expResult = Double.parseDouble(tempStack.pop());
            }                    
        }
        catch(Exception ex)
        {
            System.out.println("parse double error in function getExpResult.");
        }
        return expResult;
    }
           
    /**
     * This method is used to read the expression from the expression deque 
     * to get the operator stack and operand queue
     */
    private void scanExpDeque()
    {
        while (!expDeque.isEmpty())
        {
            String ss = expDeque.pollFirst();
            //if it's an operator, check the operator stack. 
            if (isOperator(ss))
            {                
                //operatorStack is empty, add ss to the operator stack directly
                if (operatorStack.isEmpty())
                {
                    operatorStack.push(ss);
                } 
                else    
                {   //operatorStack is not empty, remove the last element of 
                    // the operator stack, compare it with ss
                    String topStack = operatorStack.pop();
                    if(topStack.equals("("))
                    {
                        operatorStack.push("(");
                        operatorStack.push(ss);
                        
                    }
                    else if (compareOperator(ss, topStack))
                    {
                        // ss has higher precedence over the top element of the stack
                        //adds the top element which was popped out, then adds ss
                        operatorStack.push(topStack);
                        operatorStack.push(ss);
                    }
                    else
                    {
                        //topStack has precedence over ss, adds ss to operator stack
                        //and add topStack to operand Queue
                        operatorStack.push(ss);
                        operandQueue.offer(topStack);
                    }
                }
            } 
            
            else if(ss.equals("(")) //adds open parentheses to operator stack
            {
                operatorStack.push(ss);
            }
            else if(ss.equals(")"))
            {
                //add operators to postfix queue until finds open parentheses
                //and throws ( and )
                String topStack = operatorStack.pop();
                while( !topStack.equals("("))   
                {                   
                    operandQueue.offer(topStack);                    
                    topStack = operatorStack.pop();
                }
            }
            
            //it's an operand
            else 
            {
                operandQueue.offer(ss);
            }
        }                               
    }
    
    /**
     * Check a string is +, -, *, or /
     * @param op
     * @return 
     */
    private boolean isOperator(String op)
    {
        boolean isOperator = false;
        for(int i = 0; i < operator.length; ++i)
        {
            if( op.equals(operator[i]))
            isOperator = true;
        }
        return isOperator;
    }   
    
    /**
     * Check a string is between 0 and 9, or decimal point
     * @param op
     * @return 
     */
    private boolean isNum(String op)
    {
        boolean isNum = false;
        for(int i = 0; i < num.length; ++i)
        {
            if( op.equals(num[i]))
            {
                isNum = true;
                break;
            }           
        }
        return isNum;
    }   
    
    /**
     * This method is used to define * or / has higher precedence 
     * over + and -.
     * @param op1
     * @param op2
     * @return 
     */
    private boolean compareOperator(String op1, String op2)
    {
        boolean isGreater = false;
        switch (op1)
        {
            case "+":
            case "-":
                isGreater = false;
                break;
            case "*":
            case "/":
                if(op2.equals("+") || op2.equals("-"))
                {
                    isGreater = true;
                }
                else
                    isGreater = false;
        }       
        return isGreater;       
    }
        
    private double partCalculate(String s1, String s2, String op)
    {
        double result = 0;
        
        try
        {
        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        
        switch (op)
        {
            case "+":
                result = d2 + d1;
                break;
            case "-":
                result = d2 - d1;
                break;
            case "*":
                result = d2 * d1;
                break;
            case "/":
                    result = d2 / d1;
 
        }
        }
        catch(Exception ex)
        {
            System.out.println("parse double error in function partCalculate.");
        }
        return result;
    }
}
