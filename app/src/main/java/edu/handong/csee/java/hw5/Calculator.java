package edu.handong.csee.java.hw5;

import edu.handong.csee.java.hw5.engines.*;
import edu.handong.csee.java.hw5.exceptions.InvalidCommandException;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;

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
        OptionHandler optionHandler = new OptionHandler();
        if (!optionHandler.parseOptions(optionHandler.createOptions(), args)) {
            optionHandler.printHelp(optionHandler.createOptions());
            return;
        }

        if (optionHandler.getHelpRequested()) {
            optionHandler.printHelp(optionHandler.createOptions());
            return;
        }

        String task = optionHandler.getTask().toUpperCase();

        if (task.equals("SQRT") || task.equals("MIN") || task.equals("MAX")) {
            String inputFilePath = optionHandler.getDataInputFilePath();
            String outputFilePath = optionHandler.getDataOutputFilePath();

            if (inputFilePath != null && outputFilePath != null) {
                switch (task) {
                    case "SQRT":
                        SQRTEngine sqrtEngine = new SQRTEngine();
                        sqrtEngine.computeFromFile(inputFilePath, outputFilePath);
                        break;
                    case "MIN":
                        MinEngine minEngine = new MinEngine();
                        minEngine.computeFromFile(inputFilePath, outputFilePath);
                        break;
                    case "MAX":
                        MaxEngine maxEngine = new MaxEngine();
                        maxEngine.computeFromFile(inputFilePath, outputFilePath);
                        break;
                    default:
                        System.out.println("Exception-01: Invalid command: " + task);
                        return;
                }
                return;
            }
        }

        Computable engine = null;

        switch (task) {
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
                    System.out.println("Exception-01: Invalid command: " + task);
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
        }

        engine.setInput(args);
        engine.compute();

        System.out.println("The result of " + task + " is " + engine.getResult() + ".");
    }
}
