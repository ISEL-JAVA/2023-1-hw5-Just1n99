package edu.handong.csee.java.hw5.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Runnable interface and provides methods for reading and writing CSV files,
 * as well as performing calculations for SQRT, MAX, and MIN tasks.
 */
public class CSVFileCalculator implements Runnable {
    private String inputFilePath;
    private String outputFilePath;
    private ArrayList<ArrayList<String>> csvData;

    /**
     * Constructor for CSVFileCalculator class.
     */
    public CSVFileCalculator(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.csvData = new ArrayList<>();
    }

    /**
     * Reads the CSV file and returns the data as a 2D ArrayList.
     */
    public ArrayList<ArrayList<String>> readCSV(String inputFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                ArrayList<String> row = new ArrayList<>();
                String[] values = line.split(",");
                for (String value : values) {
                    row.add(value.trim());
                }
                csvData.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return csvData;
    }

    /**
     * Writes the CSV data to the specified file path.
     */
    public void writeCSV(ArrayList<ArrayList<String>> csvData, String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (ArrayList<String> row : csvData) {
                StringBuilder line = new StringBuilder();
                for (String value : row) {
                    line.append(value).append(",");
                }
                line.deleteCharAt(line.length() - 1); // Remove the trailing comma
                writer.write(line.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
    
    /**
     * Calculates the result for the specified task and input values.
     */
    public String calculate(String task, String inputValues) {
        StringBuilder result = new StringBuilder();

        switch (task) {
            case "SQRT":
                result.append("SQRT: ");
                String[] sqrtValues = inputValues.split(" ");
                for (String value : sqrtValues) {
                    double number = Double.parseDouble(value);
                    double sqrt = Math.sqrt(number);
                    result.append(sqrt).append(" ");
                }
                break;
            case "MAX":
                result.append("MAX: ");
                String[] maxValues = inputValues.split(" ");
                double max = Double.MIN_VALUE;
                for (String value : maxValues) {
                    double number = Double.parseDouble(value);
                    max = Math.max(max, number);
                }
                result.append(max);
                break;
            case "MIN":
                result.append("MIN: ");
                String[] minValues = inputValues.split(" ");
                double min = Double.MAX_VALUE;
                for (String value : minValues) {
                    double number = Double.parseDouble(value);
                    min = Math.min(min, number);
                }
                result.append(min);
                break;
            default:
                result.append("Invalid task!");
        }

        return result.toString();
    }

    /**
     * The run method of the thread.
     */
    @Override
    public void run() {
        ArrayList<ArrayList<String>> csvData = readCSV(inputFilePath);

        for (ArrayList<String> row : csvData) {
            String task = row.get(0);
            String inputValues = row.get(1);
            String result = calculate(task, inputValues);
            row.add(result);
        }

        writeCSV(csvData, outputFilePath);
    }

    /**
     * Main method to execute the program.
     */
    public static void main(String[] args) {
        String inputFilePath = "";
        String outputFilePath = "";

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-i")) {
                inputFilePath = args[i + 1];
            } else if (arg.equals("-o")) {
                outputFilePath = args[i + 1];
            }
        }

        CSVFileCalculator calculator = new CSVFileCalculator(inputFilePath, outputFilePath);
        Thread thread = new Thread(calculator);
        thread.start();
    }
}
