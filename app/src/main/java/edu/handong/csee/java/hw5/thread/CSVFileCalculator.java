package edu.handong.csee.java.hw5.thread;

import java.io.*;
import java.util.ArrayList;
import edu.handong.csee.java.hw5.clioptions.*;
import java.util.Arrays;
import edu.handong.csee.java.hw5.exceptions.*;
import edu.handong.csee.java.hw5.clioptions.*;

public class CSVFileCalculator implements Runnable {
    private String inputFilePath;
    private String outputFilePath;
    private String task;

    public CSVFileCalculator(String inputFilePath, String outputFilePath, String task) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.task = task;
    }

    @Override
    public void run() {
        try {
                OptionHandler optionHandler = new OptionHandler();
                optionHandler.setDataOutputFilePath(outputFilePath);
                
                File inputFile = new File(inputFilePath);
                
                if (!inputFile.exists()) {
                	optionHandler.printHelp(optionHandler.createOptions());
                    return;
                }
            ArrayList<ArrayList<String>> csvData = readCSV(inputFilePath);
            if(task.equals("SQRT"))
            	calculateSQRT(csvData);
            else if(task.equals("MAX"))
            	calculateMax(csvData);
            else if(task.equals("MIN"))
            	calculateMin(csvData);
            writeCSV(outputFilePath, csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        if (outputFile.isDirectory()) {
        	
            String outputFileName = outputFile.getName();
            String outputFileNameWithoutExtension = outputFileName.substring(0, outputFileName.lastIndexOf("."));
            String[] args = filePath.split(" ");
            String outputFileNameFromUser = args[Arrays.asList(args).indexOf("-o") + 1];
            String finalOutputFileName = outputFileNameWithoutExtension + "-" + outputFileNameFromUser;
            File finalOutputFile = new File(outputFile, finalOutputFileName);

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
        } else {
            String outputFileName = outputFile.getName();
            String outputFileNameFromUser = outputFileName.substring(outputFileName.lastIndexOf(" ") + 1, outputFileName.lastIndexOf(".csv"));

            String finalOutputFileName = outputFileNameFromUser + ".csv";

            File finalOutputFile = new File(finalOutputFileName);

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
    }



    public void calculateSQRT(ArrayList<ArrayList<String>> csvData) {
    	double number;
        for (int i = 1; i < csvData.size(); i++) {
            ArrayList<String> row = csvData.get(i);
            for (int j = 0; j < row.size(); j++) {
                String value = row.get(j);
                try {
                	try {
                		number = Double.parseDouble(value);
                	} catch(NumberFormatException e) {
                		throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + value + " is not a number value for SQRT.)");
                	}
                    if (number < 0) {
                        throw new NegativeNumberException("Exception-03: The input value cannot be negative for SQRT.");
                    }
                    double sqrt = Math.sqrt(number);
                    row.set(j, String.valueOf(sqrt));
                } catch (MyNumberFormatException e) {
                	System.out.println(e.getMessage());
                	System.exit(0);
                } catch (NegativeNumberException e) {
                	System.out.println(e.getMessage());
                	System.exit(0);
                }
            }
        }
    }

    
    public void calculateMax(ArrayList<ArrayList<String>> csvData) {
        String header = "MAX";
        csvData.get(0).add(header);
        
        double number;
        
        for (int i = 1; i < csvData.size(); i++) {
            ArrayList<String> row = csvData.get(i);
            try {
            	if(row.size() < 2 || csvData.get(1).size() < 2) {
                	throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for MAX.");
                }
            } catch (MinimumInputNumberException e) {
            	System.out.println(e.getMessage());
            	System.exit(0);
            }
            
            double max = Double.MIN_VALUE;
            boolean firstValue = true;
            
            for (String value : row) {
                try {
                	try {
                		number = Double.parseDouble(value);
                	} catch (NumberFormatException e) {
                		throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + value + " is not a number value for MAX.)");
                	}
                    if (firstValue) {
                        max = number;
                        firstValue = false;
                    } else {
                        if (number > max) {
                            max = number;
                        }
                    }
                } catch (MyNumberFormatException e) {
                	System.out.println(e.getMessage());
                	System.exit(0);
                } 
            }
            row.add(String.valueOf(max));
            
        }
    }

    public void calculateMin(ArrayList<ArrayList<String>> csvData) {
        String header = "MIN";
        csvData.get(0).add(header);
        
        
        for (int i = 1; i < csvData.size(); i++) {
            ArrayList<String> row = csvData.get(i);
            double min = Double.MAX_VALUE;
            boolean firstValue = true;
            
            for (String value : row) {
                try {
                	if(row.size() < 2) {
                    	throw new MinimumInputNumberException("Exception-02: You need at least 2 input values for MIN.");
                    }
                	double number;
                	try {
                		number = Double.parseDouble(value);
                	} catch (NumberFormatException e) {
                		throw new MyNumberFormatException("Exception-05: The input value should be converted into a number. (" + value + " is not a number value for MIN.)");
                	}
                    if (firstValue) {
                        min = number;
                        firstValue = false;
                    } else {
                        if (number < min) {
                            min = number;
                        }
                    }
                } catch (NumberFormatException e) {
                	System.out.println(e.getMessage());
                	System.exit(0);
                }  catch (MinimumInputNumberException e) {
                	System.out.println(e.getMessage());
                	System.exit(0);
                }
            }
            row.add(String.valueOf(min));
        }
    }

}
