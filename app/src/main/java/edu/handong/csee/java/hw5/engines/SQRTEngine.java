package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;

/**
 * This is the SQRTEngine class that finds the square root of the user's input
 */
public class SQRTEngine implements Computable {
    private static final String engineName ="SQRT";
    private double input;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for input
     */
    public double getInputs() {
        return input;
    }

    /**
     * Setter method of encapsulation for field variable input
     */
    public void setInputs(double input) {
        this.input = input;
    }

    /**
     * This checks to see if the user inputted 1 non-negative number. If not, return an error message
     */
    public void setInput(String[] input) {
        try {
            if(input.length < 2 || input.length > 2)
                throw new OneInputException("Exception-04: You need one input value for SQRT.");

            else {
                try {
                    this.input = Double.parseDouble(input[1]);
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[1] + " is not a number value for SQRT.)");
                }
                if(this.input < 0)
                    throw new NegativeNumberException("Exception-03: The input value cannot be negative for SQRT.");
            }
        } catch (OneInputException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (NegativeNumberException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (MyNumberFormatException e) {
            System.out.print(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * This calculates the square root of the user's input
     */
    public void compute() {
        result = Math.sqrt(input);
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
