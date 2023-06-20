package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;

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
        try {
            if(input.length < 2 || input.length > 2)
                throw new OneInputException("Exception-04: You need one input value for FACTORIAL.");

            else {
                try{
                    n = Integer.parseInt(input[1]);
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[1] + " is not a number value for FACTORIAL.)");
                }
                if(n < 0) 
                    throw new NegativeNumberException("Exception-03: The input value cannot be negative for FACTORIAL.");
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
