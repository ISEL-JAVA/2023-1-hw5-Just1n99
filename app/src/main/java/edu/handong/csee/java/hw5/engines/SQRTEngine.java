package edu.handong.csee.java.hw5.engines;

import edu.handong.csee.java.hw5.exceptions.*;
import org.apache.commons.cli.*;
import edu.handong.csee.java.hw5.clioptions.OptionHandler;
import edu.handong.csee.java.hw5.fileutil.FileManager;
import java.util.ArrayList;
import java.io.File; 

/**
 * This is the SQRTEngine class that finds the square root of the user's input
 */
public class SQRTEngine implements Computable {
    private static final String engineName ="SQRT";
    private double input;
    private double result;

    /**
     * Getter method of encapsulation for engineName
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Getter method of encapsulation for input
     */
    public double getInputs() {
        return input;
    }

    /**
     * Setter method of encapsulation for field variable input
     */
    public void setInputs(double input) {
        this.input = input;
    }

    /**
     * This checks to see if the user inputted 1 non-negative number. If not, return an error message
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
                 
                 if(inputArray.length != 1) {
                    throw new OneInputException("Exception-04: You need one input value for SQRT.");
                 } 
                 
                 try {
                    this.input = Double.parseDouble(inputArray[0]);
                 } catch (NumberFormatException e) {
                    throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + inputArray[0] + " is not a number value for SQRT.)");
                 }
                 
                 if(this.input < 0) {
                    throw new NegativeNumberException("Exception-03: The input value cannot be negative for SQRT.");
                 }
              }
              else {
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
     * This calculates the square root of the user's input
     */
    public void compute() {
        result = Math.sqrt(input);
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
            double number;

            StringBuilder resultLine = new StringBuilder();

            for (String value : values) {
                try {
                   try {
                       number = Double.parseDouble(value.trim());
                       
                   }catch (NumberFormatException e) {
                           throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + value + " is not a number value for MIN.)");
                       }
                    if (number < 0) {
                        throw new NegativeNumberException("Exception-03: The input value cannot be negative for SQRT.");
                    } else {
                        double sqrt = Math.sqrt(number);
                        resultLine.append(sqrt).append(",");
                    }
                }  catch (NegativeNumberException e) {
                   System.out.println(e.getMessage());
                   System.exit(0);
                } catch (MyNumberFormatException e) {
                   System.out.println(e.getMessage());
                   System.exit(0);
                }
            }

            resultLine.deleteCharAt(resultLine.length() - 1);
            outputLines.add(resultLine.toString());
        }

        FileManager.writeAtxtFile(outputFilePath, outputLines);
        System.out.println("The " + outputFilePath + " file has been successfully written.");
    }




}