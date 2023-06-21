package edu.handong.csee.java.hw5.thread;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVFileCalculator implements Runnable {
    private String inputFilePath;
    private String outputFilePath;

    public CSVFileCalculator(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        try {
            ArrayList<ArrayList<String>> csvData = readCSV(inputFilePath);
            calculate(csvData);
            writeCSV(outputFilePath, csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread finished: " + Thread.currentThread().getName());
    }

    public ArrayList<ArrayList<String>> readCSV(String filePath) throws IOException {
        ArrayList<ArrayList<String>> csvData = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<String> row = new ArrayList<>();
            String[] values = line.split(",");
            for (String value : values) {
                row.add(value.trim());
            }
            csvData.add(row);
        }
        br.close();

        return csvData;
    }

    public void writeCSV(String filePath, ArrayList<ArrayList<String>> csvData) throws IOException {
        File outputFile = new File(filePath);

        outputFile.getParentFile().mkdirs();

        String inputFileName = new File(inputFilePath).getName();
        String outputFileName = outputFile.getName();

        String inputFileNameWithoutExtension = inputFileName.substring(0, inputFileName.lastIndexOf("."));

        String outputFileNameWithoutExtension = outputFileName.substring(0, outputFileName.lastIndexOf("."));

        String finalOutputFileName = "";

        if (outputFileNameWithoutExtension.isEmpty()) {
            finalOutputFileName = inputFileNameWithoutExtension + "-result.csv";
        } else {
            finalOutputFileName = outputFileNameWithoutExtension + "-result.csv";
        }

        File finalOutputFile = new File(outputFile.getParentFile(), finalOutputFileName);

        BufferedWriter bw = new BufferedWriter(new FileWriter(finalOutputFile));
        for (ArrayList<String> row : csvData) {
            StringBuilder sb = new StringBuilder();
            for (String value : row) {
                sb.append(value).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
    }

    public void calculate(ArrayList<ArrayList<String>> csvData) {
        for (int i = 1; i < csvData.size(); i++) {
            ArrayList<String> row = csvData.get(i);
            for (int j = 0; j < row.size(); j++) {
                String value = row.get(j);
                try {
                    double number = Double.parseDouble(value);
                    double sqrt = Math.sqrt(number);
                    row.set(j, String.valueOf(sqrt));
                } catch (NumberFormatException e) {
                }
            }
        }
    }
}
