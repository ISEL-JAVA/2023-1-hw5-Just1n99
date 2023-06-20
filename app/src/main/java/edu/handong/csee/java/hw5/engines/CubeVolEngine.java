package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;

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
        try{
            if(input.length < 2 || input.length > 2)
                throw new OneInputException("Exception-04: You need one input value for CUBEVOL.");

            else {
                try{
                    sideLength = Double.parseDouble(input[1]);
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[1] + " is not a number value for CUBEVOL.)");
                }

                if(sideLength < 0)
                    throw new NegativeNumberException("Exception-03: The input value cannot be negative for CUBEVOL.");
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
