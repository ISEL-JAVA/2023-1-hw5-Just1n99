package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;

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
        try{
            if(input.length < 3)
                throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for LCM.");

            else {
                a = new double[input.length - 1];
                int j = 0;
                for(int i = 1; i < input.length; i++) {
                    try {
                        a[j] = Double.parseDouble(input[i]);
                    } catch (NumberFormatException e) {
                        throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[i] + " is not a number value for LCM.)");
                    }

                    if(a[j] < 0)
                        throw new NegativeNumberException("Exception-03: The input value cannot be negative for LCM.");

                    j++;
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
