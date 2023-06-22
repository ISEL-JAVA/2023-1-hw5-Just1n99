package edu.handong.csee.java.hw5.engines;

import java.util.Arrays;

import org.apache.commons.cli.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import edu.handong.csee.java.hw5.exceptions.*;

/**
 * This is the MinEngine class that finds the smallest number between the set of numbers the user has inputted
 */
public class MinEngine implements Computable {
    private static final String engineName ="MIN";
    private double[] inputs;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for array inputs
     */
    public double[] getInputs() {
        return inputs;
    }

    /**
     * Setter method of encapsulation for field array inputs
     */
    public void setInputs(double[]inputs) {
        this.inputs = inputs;
    } 

    /**
     * This checks to see if the user has inputted at least 2 numbers to be compared. If not, return an error message
     */
    public void setInput(String[] input) {
    	OptionHandler optionHandler = new OptionHandler();
        Options options = optionHandler.createOptions();

        try {
            if (optionHandler.parseOptions(options, input)) {
                if (optionHandler.getHelpRequested()) {
                    optionHandler.printHelp(options);
                    System.exit(0);
                }

                String task = optionHandler.getTask();
                String inputValues = optionHandler.getInputValues();

                if (task != null) {
                    if (inputValues != null && !inputValues.isEmpty()) {
                        String[] inputArray = inputValues.trim().split("\\s+");
                        inputs = new double[inputArray.length];
                        if(inputArray.length < 2) 
                        	throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for MIN.");

                        for (int i = 0; i < inputArray.length; i++) {
                            try {
                                inputs[i] = Double.parseDouble(inputArray[i]);
                            } catch (NumberFormatException e) {
                                throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[i] + " is not a number value for MIN.)");
                            }
                        }
                    } else {
                        optionHandler.printHelp(options); 
                        System.exit(0);
                    }
                } 
            }
        } catch (MinimumInputNumberException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (MyNumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    /**
     * This finds the smallest number inside the array of user's inputs
     */
    public void compute() {
        Arrays.sort(inputs);
        result = inputs[0];
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
