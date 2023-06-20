package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import org.apache.commons.cli.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import edu.handong.csee.java.hw5.fileutil.FileManager;
import java.util.ArrayList;
import java.io.File; 

/**
 * This is the MaxEngine class that finds the largest number of the numbers the user inputted
 */
public class MaxEngine implements Computable {
    private static final String engineName ="MAX";
    private double[] inputs;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for field array inputs
     */
    public double[] getInputs() {
        return inputs;
    }

    /**
     * Setter method of encapsulation for field array inputs
     */
    public void setInputs(double[] inputs) {
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
            }

            String task = optionHandler.getTask();
            String inputValues = optionHandler.getInputValues();

            if (task != null) {
                if (inputValues != null && !inputValues.isEmpty()) {
                    String[] inputArray = inputValues.trim().split("\\s+");

                    if (inputArray.length != 1) {
                        throw new OneInputException("Exception-04: You need one input value for " + getEngineName() + ".");
                    }

                    try {
                        this.inputs = new double[]{Double.parseDouble(inputArray[0])};
                    } catch (NumberFormatException e) {
                        throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[0] + " is not a number value for " + getEngineName() + ")");
                    }

                    if (this.inputs[0] < 0) {
                        throw new NegativeNumberException("Exception-03: The input value cannot be negative for " + getEngineName() + ".");
                    }
                } else {
                    optionHandler.printHelp(options);
                    System.exit(0);
                }
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
     * This iterates and finds the largest number
     */
    public void compute() {
        double max = 0;
        for(int i = 0; i < inputs.length; i++) {
            if(inputs[i] > max)
                max = inputs[i];
        }

        result = max;
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
    
    /**
     * This reads and writes contents from a file to another file
     */
    public void computeFromFile(String inputFilePath, String outputFilePath) {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            OptionHandler optionHandler = new OptionHandler();
            Options options = optionHandler.createOptions();
            optionHandler.printHelp(options);
            return;
        }

        ArrayList<String> lines = FileManager.readLinesFromATxtFile(inputFilePath);
        ArrayList<String> outputLines = new ArrayList<>();

        outputLines.add(lines.get(0) + ",MAX"); 

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.trim().split(",");
            double[] numbers = new double[values.length];

            StringBuilder resultLine = new StringBuilder();

            for (int j = 0; j < values.length; j++) {
                try {
                    numbers[j] = Double.parseDouble(values[j].trim());
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + values[j] + " is not a number value for MAX.)");
                }
            }

            MaxEngine maxEngine = new MaxEngine();
            maxEngine.setInputs(numbers);
            maxEngine.compute();

            resultLine.append(maxEngine.getResult());
            outputLines.add(line + "," + resultLine.toString());
        }

        FileManager.writeAtxtFile(outputFilePath, outputLines);
        System.out.println("The " + outputFilePath + " file has been successfully written.");
    }

}
