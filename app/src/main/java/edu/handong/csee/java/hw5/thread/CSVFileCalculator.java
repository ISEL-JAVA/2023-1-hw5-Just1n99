package edu.handong.csee.java.hw5.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.*;

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
        try (FileWriter writer = new FileWriter(outputFilePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            csvPrinter.printRecord(csvData.get(0));

            for (int i = 1; i < csvData.size(); i++) {
                csvPrinter.printRecord(csvData.get(i));
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
     * The run method that is called when the thread is started.
     */
    @Override
    public void run() {
        csvData = readCSV(inputFilePath);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i < csvData.size(); i++) {
            final int rowNumber = i;
            executor.execute(() -> {
                String task = csvData.get(rowNumber).get(0);
                String inputValues = csvData.get(rowNumber).get(1);
                String result = calculate(task, inputValues);
                csvData.get(rowNumber).add(result);
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error occurred while waiting for the thread pool to terminate: " + e.getMessage());
        }

        writeCSV(csvData, outputFilePath);
    }
}
