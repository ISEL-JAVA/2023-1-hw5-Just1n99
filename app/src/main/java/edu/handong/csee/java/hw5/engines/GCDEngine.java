package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import org.apache.commons.cli.*;

/**
 * This is the GCDEngine class that calculates the greatest common divisor of the set of numbers the user inputted
 */
public class GCDEngine implements Computable {
    private static final String engineName ="GCD";
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
     * Getter method of encapsulation for field array a
     */
    public void setA(double[] a) {
        this.a = a;
    }
    

    /**
     * Check to see if the user has inputted at least 2 non-negative numbers. If not, print out an error message
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
                    throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for GCD.");
                 }
                 
                 for (int i = 0; i < inputArray.length; i++) {
                        try {
                            a[i] = Double.parseDouble(inputArray[i]);
                        } catch (NumberFormatException e) {
                            throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[i] + " is not a number value for GCD.)");
                        }
                        if(a[j] < 0) {
                           throw new NegativeNumberException("Exception-03: The input value cannot be negative for GCD.");
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
     * This calculates the GCD between the set of numbers the user has inputted
     */
    public void compute() {
        int x = (int)a[0];
        int y = (int)a[1];
    
        while(x != y && x != 0 && y != 0) {
            if(x > y)
                x = x - y;
    
            else if(y > x)
                y = y - x;
        }
    
        result = x;
    
        for(int i = 2; i < a.length; i++) {
            int num = (int)a[i];
            while(result != num && result != 0 && num != 0) {
                if(result > num)
                    result = result - num;
    
                else if(num > result)
                    num = num - (int)result;
            }
    
            if (result == 0) {
                result = num;
            } 
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