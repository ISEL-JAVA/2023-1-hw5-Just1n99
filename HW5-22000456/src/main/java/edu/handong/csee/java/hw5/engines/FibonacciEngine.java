package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;

/**
 * This is the FibonacciEngine class that calculates the fibonacci sequence depending on the user's input
 */
public class FibonacciEngine implements Computable {
    private static final String engineName ="FIBONACCI";
    private int n;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for field variable n
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
     * This checks to see if the user inputted 1 non-negative number. Print out an error message if it is not
     */
    public void setInput(String[] input) {
        try {
            if(input.length < 2 || input.length > 2) 
                throw new OneInputException("Exception-04: You need one input value for FIBONACCI.");

            else {
                try{
                    n = Integer.parseInt(input[1]);
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[1] + " is not a number value for FIBONACCI.)");
                }
                if(n < 0)
                    throw new NegativeNumberException("Exception-03: The input value cannot be negative for FIBONACCI.");
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
     * This calculates the fibonacci sequence of the number the user inputted 
     */
    public void compute() {
        if (n == 0) {
            result = 0;
        }
        else if (n == 1) {
            result = 1;
        }
        
        int a = 0;
        int b = 1;
        
        for (int i = 2; i <= n; i++) {
            result = a + b;
            a = b;
            b = (int) result;
        }
    }

    /**
     * Return the answer to the main class
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
