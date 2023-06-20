package edu.handong.csee.java.hw5.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class implements the Runnable interface and provides methods for reading and writing CSV files,
 * as well as performing calculations for SQRT, MAX, and MIN tasks.
 */
public class CSVFileCalculator implements Runnable {
    private String filePath;
    private ArrayList<ArrayList<String>> csvData;

    /**
     * Constructor for CSVFileCalculator class.
     */
    public CSVFileCalculator(String filePath) {
        this.filePath = filePath;
        this.csvData = new ArrayList<>();
    }

    /**
     * Reads the CSV file and returns the data as a 2D ArrayList.
     */
    public ArrayList<ArrayList<String>> readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
    public void writeCSV(ArrayList<ArrayList<String>> csvData, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
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
                result.append(calculateSqrt(inputValues));
                break;
            case "MAX":
                result.append("MAX: ");
                result.append(calculateMax(inputValues));
                break;
            case "MIN":
                result.append("MIN: ");
                result.append(calculateMin(inputValues));
                break;
            default:
                result.append("Invalid task!");
        }

        return result.toString();
    }

    /**
     * Calculates the square root of each input value and returns the result.
     */
    private String calculateSqrt(String inputValues) {
        StringBuilder result = new StringBuilder();
        String[] values = inputValues.split(" ");

        for (String value : values) {
            double number = Double.parseDouble(value);
            double sqrt = Math.sqrt(number);
            result.append(sqrt).append(" ");
        }

        return result.toString().trim();
    }

    /**
     * Finds the maximum value among the input values and returns it.
     */
    private String calculateMax(String inputValues) {
        String[] values = inputValues.split(" ");
        double max = Double.MIN_VALUE;

        for (String value : values) {
            double number = Double.parseDouble(value);
            max = Math.max(max, number);
        }

        return String.valueOf(max);
    }

    /**
     * Finds the minimum value among the input values and returns it.
     */
    private String calculateMin(String inputValues) {
        String[] values = inputValues.split(" ");
        double min = Double.MAX_VALUE;

        for (String value : values) {
            double number = Double.parseDouble(value);
            min = Math.min(min, number);
        }

        return String.valueOf(min);
    }

    /**
     * The run method of the thread.
     */
    @Override
    public void run() {
        ArrayList<ArrayList<String>> csvData = readCSV(filePath);

        for (ArrayList<String> row : csvData) {
            String task = row.get(0);
            String inputValues = row.get(1);
            String result = calculate(task, inputValues);
            row.add(result);
        }

        writeCSV(csvData, filePath);
    }
}
