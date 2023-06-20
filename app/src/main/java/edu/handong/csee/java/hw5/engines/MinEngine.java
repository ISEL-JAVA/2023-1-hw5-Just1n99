package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import org.apache.commons.cli.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import edu.handong.csee.java.hw5.fileutil.FileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

/**
 * This is the MinEngine class that finds the smallest number between the set of numbers the user has inputted
 */
public class MinEngine implements Computable {
    private static final String engineName = "MIN";
    private double[] inputs;
    private double result;

    /**
     * Getter method for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method for inputs
     */
    public double[] getInputs() {
        return inputs;
    }

    /**
     * Setter method for inputs
     */
    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    /**
     * Sets the input values for computation
     */
    public void setInput(String[] input) {
        try {
            if (input.length < 3)
                throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for MIN.");
            else {
                inputs = new double[input.length - 1];
                for (int i = 1; i < input.length; i++) {
                    try {
                        inputs[i - 1] = Double.parseDouble(input[i]);
                    } catch (NumberFormatException e) {
                        throw new MyNumberFormatException("Exception-05: The input value should be a number. (" + input[i] + " is not a valid number for MIN.)");
                    }
                }
            }
        } catch (MinimumInputNumberException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (MyNumberFormatException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Performs the computation to find the smallest number
     */
    public void compute() {
        Arrays.sort(inputs);
        result = inputs[0];
    }

    /**
     * Returns the result of the computation
     */
    public double getResult() {
        return result;
    }

    /**
     * Setter method for result
     */
    public void setResult(double result) {
        this.result = result;
    }

    /**
     * Computes the result by reading input from a file and writes the result to another file
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

        outputLines.add(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.trim().split(",");
            double[] numbers = new double[values.length];

            StringBuilder resultLine = new StringBuilder();

            for (int j = 0; j < values.length; j++) {
                try {
                    numbers[j] = Double.parseDouble(values[j].trim());
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + values[j] + " is not a number value for MIN.)");
                }
            }

            MinEngine minEngine = new MinEngine();
            minEngine.setInputs(numbers);
            minEngine.compute();

            resultLine.append(minEngine.getResult());
            outputLines.add(resultLine.toString());
        }

        FileManager.writeAtxtFile(outputFilePath, outputLines);
        System.out.println("The " + outputFilePath + " file has been successfully written.");
    }
}
