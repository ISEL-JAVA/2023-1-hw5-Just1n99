package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import org.apache.commons.cli.*;

/** 
 * This is the CubeVolEngine class that takes an input from the user and calculates the volume of the cube
*/
public class CubeVolEngine implements Computable {
    private static final String engineName ="CUBEVOL";
    private double sideLength;
    private double volume;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for field variable sideLength
     */
    public double getSidelength() {
        return sideLength;
    }

    /**
     * Setter method of encapsulation for field variable sideLength
     */
    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
    
    /**
     * This checks if the user only inputted 1 non-negative number. If not, print out an error message
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

                        if (inputArray.length != 1) {
                            throw new OneInputException("Exception-04: You need one input value for CUBEVOL.");
                        }

                        try {
                            sideLength = Double.parseDouble(inputArray[0]);
                        } catch (NumberFormatException e) {
                            throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[0] + " is not a number value for CUBEVOL.)");
                        }

                        if (sideLength < 0) {
                            throw new NegativeNumberException("Exception-03: The input value cannot be negative for CUBEVOL.");
                        }
                    } else {
                    	optionHandler.printHelp(options); 
                        System.exit(0);
                    }
                }
            }
        } catch (OneInputException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (NegativeNumberException e) {
        	System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (MyNumberFormatException e) {
        	System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    
    /**
     * This calculates the volume of the cube
     */
    public void compute() {
        volume = Math.pow(sideLength, 3);
    }

    /**
     * This returns the answer to the main class
     */
    public double getResult() {
        return volume;
    }

    /**
    * Setter method of encapsulation for result
    */
    public void setResult(double volume) {
        this.volume = volume;
    }

}
