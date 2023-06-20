package edu.handong.csee.java.hw5;

import edu.handong.csee.java.hw5.engines.*;
import edu.handong.csee.java.hw5.exceptions.InvalidCommandException;

/**
 * This is the Calculator class that is the main class for the user to use
 */
public class Calculator {
    /**
     * This is the main method of the Calculator class
     */
    public static void main(String[] args) {

        Calculator myCalculator = new Calculator();

        myCalculator.run(args);
    }
 
    /**
     * This executes the Calculator class for users to use
     */
    public void run(String[] args) {
        try {
            if(args.length == 0) {
                throw new InvalidCommandException("Please put a computing engine option such as LCM, GCD, SQRT, FACTORIAL, FIBONACCI, MAX, MIN, CUBEVOL, and SPHEREVOL. For example, ./app  MAX 12 4 5 45 100");
            } 
        } catch (InvalidCommandException e) {
        	System.out.println("Exception-01: Invalid command: ");
            System.out.println(e.getMessage());
            System.exit(0);
        }

        String engineName = args[0].toUpperCase();

        Computable engine =null;

        switch(engineName) {
            case "LCM":
                engine = new LCMEngine();
                break;
            case "GCD":
                engine = new GCDEngine();
                break;
            case "SQRT":
                engine = new SQRTEngine();
                break;
            case "FACTORIAL":
                engine = new FactorialEngine();
                break;
            case "FIBONACCI":
                engine = new FibonacciEngine();
                break;
            case "MAX":
                engine = new MaxEngine();
                break;
            case "MIN":
                engine = new MinEngine();
                break;
            case "CUBEVOL":
                engine = new CubeVolEngine();
                break;
            case "SPHEREVOL":
                engine = new SphereVolEngine();
                break;
            default:
                try {
                    throw new InvalidCommandException("Please put a computing engine option such as LCM, GCD, SQRT, FACTORIAL, FIBONACCI, MAX, MIN, CUBEVOL, and SPHEREVOL. For example, ./app  MAX 12 4 5 45 100");
                } catch (InvalidCommandException e) {
                	System.out.println("Exception-01: Invalid command: " + engineName);
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
        }

        engine.setInput(args);
        engine.compute();

        System.out.println("The result of " +  engineName + " is " + engine.getResult() + ".");

    }


}