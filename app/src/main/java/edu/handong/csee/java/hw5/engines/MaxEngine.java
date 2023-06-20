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
        try {
            if(input.length < 3)
                throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for MAX.");

            else {
                inputs = new double[input.length - 1];
                int j = 0;
                for(int i = 1; i < input.length; i++) {
                    try {
                        inputs[j] = Double.parseDouble(input[i]);
                    }catch (NumberFormatException e) {
                        throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + input[i] + " is not a number value for MAX.)");
                    }

                    j++;
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
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + values[j] + " is not a number value for MAX.)");
                }
            }

            MaxEngine maxEngine = new MaxEngine();
            maxEngine.setInputs(numbers);
            maxEngine.compute();

            resultLine.append(maxEngine.getResult());
            outputLines.add(resultLine.toString());
        }

        FileManager.writeAtxtFile(outputFilePath, outputLines);
        System.out.println("The " + outputFilePath + " file has been successfully written.");
    }
}
