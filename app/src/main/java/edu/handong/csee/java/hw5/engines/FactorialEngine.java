package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import org.apache.commons.cli.*;

/**
 * This is the FactorialEngine class that calculates the factorial of the user's input
 */
public class FactorialEngine implements Computable {
    private static final String engineName ="FACTORIAL";
    private int n;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for field variablen
     */
    public int getNumber() {
        return n;
    }

    /**
     * Setter method of encapsulation for field variable n
     */
    public void setNumber(int n) {
        this.n = n;
    }

    /**
     * This checks to see if the user has inputted 1 non-negative number. If not, print out an error message
     */
    public void setInput(String[] input) {
        OptionHandler optionHandler = new OptionHandler();
        Options options = optionHandler.createOptions();
        
        try {
        	if(optionHandler.parseOptions(options, input)) {
        		if(optionHandler.getHelpRequested()) {
        			optionHandler.printHelp(options);
        			System.exit(0);
        		}
        	}
        	
        	String task = optionHandler.getTask();
        	String inputValues = optionHandler.getInputValues();
        	
        	if(task != null) {
        		if(inputValues != null && !inputValues.isEmpty()) {
        			String[] inputArray = inputValues.trim().split("\\s+");
        			
        			if(inputArray.length != 1) {
        				throw new OneInputException("Exception-04: You need one input value for FACTORIAL.");
        			}
        			
        			try {
        				n = Integer.parseInt(inputArray[0]);
        			} catch (NumberFormatException e) {
        				throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[0] + " is not a number value for FACTORIAL.)");
        			}
        			
        			if(n < 0) 
        				throw new NegativeNumberException("Exception-03: The input value cannot be negative for FACTORIAL.");
        		}
        		else {
        			optionHandler.printHelp(options); 
                    System.exit(0);
        		}
        	}
        } catch (OneInputException e) {
        	System.out.println(e.getMessage());
        	System.exit(0);
        } catch (NegativeNumberException e) {
        	System.out.println(e.getMessage());
        	System.exit(0);
        } catch (MyNumberFormatException e) {
        	System.out.println(e.getMessage());
        	System.exit(0);
        }

    }

    /** 
     * This calculates the factorial of the user's input
    */
    public void compute() {
        result = 1;
        for(int i = 1; i <= n; i++) {
           result *= i;
        }
    }

    /**
     * Return the final answer to the main class
     */
    public double getResult() {
        return result;
    }

    /**
    * Setter method of encapsulation for result
    */
    public void setResult(double result) {
        this.result = result;
    }

}
