package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import edu.handong.csee.java.hw5.exceptions.*;
import org.apache.commons.cli.*;

/** 
 * This class is a class of LCMEngine that finds the lowest common multiple amongst a set of numbers from the user's input 
*/
public class LCMEngine implements Computable{
    private static final String engineName ="LCM";
    private double[] a;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }
    
    /**
     * Getter method of encapsulation for field array a
     */
    public double[] getA() {
        return a;
    }

    /**
     * Setter method of encapsulation for field array a
     */
    public void setA(double[] a) {
        this.a = a;
    }

    /**
     * This checks to see if the user has inputted at least 2 sets of numbers that are non-negative. 
     * If they are, the a are then saved into an array.
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
                 a = new double [inputArray.length];
                 int j = 0;
                 
                 if(inputArray.length < 2) {
                    throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for LCM.");
                 }
                 
                 for (int i = 0; i < inputArray.length; i++) {
                        try {
                            a[i] = Double.parseDouble(inputArray[i]);
                        } catch (NumberFormatException e) {
                            throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[i] + " is not a number value for LCM.)");
                        }
                        if(a[j] < 0) {
                           throw new NegativeNumberException("Exception-03: The input value cannot be negative for LCM.");
                        }
                        j++;
                 }
              }
              else {
                optionHandler.printHelp(options); 
                    System.exit(0);
             }
           }
        } catch (MinimumInputNumberException e) {
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
     * This calculates the lowest common multiple amongst the set of numbers in the array and saves it into the result variable
     */
    public void compute() {
        int x = 1;
        int y = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == 0) {
                    result = 0;
                    break;
                }

                if (a[i] == 1) {
                    counter++;
                    continue;
                }

                if (a[i] % y == 0) {
                    divisible = true;
                    a[i] = a[i] / y;
                }
            }

            if (divisible) {
                x *= y;
            } else {
                y++;
            }

            if (counter == a.length) {
                result = x;
                break;
            }
        }
    }

    /**
     * This returns the value of result to the main class
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