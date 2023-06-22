	package edu.handong.csee.java.hw5;
	
	import edu.handong.csee.java.hw5.engines.*;
	import edu.handong.csee.java.hw5.exceptions.InvalidCommandException;
	import edu.handong.csee.java.hw5.clioptions.OptionHandler;
	import edu.handong.csee.java.hw5.thread.CSVFileCalculator;
	
	import java.io.File;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	
	public class Calculator {
	    public static void main(String[] args) {
	        Calculator myCalculator = new Calculator();
	        myCalculator.run(args);
	    }
	
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
	            String inputPath = optionHandler.getDataInputFilePath();
	            String outputPath = optionHandler.getDataOutputFilePath();

	            if (inputPath != null && outputPath != null) {
	                File inputFile = new File(inputPath);

	                if (inputFile.isDirectory()) {
	                    File[] files = inputFile.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

	                    if (files != null && files.length > 0) {
	                        ExecutorService executor = Executors.newFixedThreadPool(files.length);

	                        for (File file : files) {
	                            String outputFilePath = outputPath + File.separator + file.getName();
	                            CSVFileCalculator calculator = new CSVFileCalculator(file.getAbsolutePath(), outputFilePath, task);
	                            executor.execute(calculator);
	                        }

	                        executor.shutdown();

	                    } else {
	                        System.out.println("No CSV files found in the directory: " + inputPath);
	                    }
	                } else {
	                    CSVFileCalculator calculator = new CSVFileCalculator(inputPath, outputPath, task);
	                    Thread csvThread = new Thread(calculator);
	                    csvThread.start();

	                    String outputFileName = new File(outputPath).getName();
	                    System.out.println("The " + outputFileName + " file has been successfully written.");
	                }
	            }
	        } else {
	            Computable engine = null;
	
	            switch (task) {
	                case "LCM":
	                    engine = new LCMEngine();
	                    break;
	                case "GCD":
	                    engine = new GCDEngine();
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
	                        throw new InvalidCommandException("Please put a valid computing engine option such as LCM, GCD, SQRT, FACTORIAL, FIBONACCI, MAX, MIN, CUBEVOL, or SPHEREVOL.");
	                    } catch (InvalidCommandException e) {
	                        System.out.println("Invalid command: " + task);
	                        System.out.println(e.getMessage());
	                        System.exit(0);
	                    }
	            }
	
	            engine.setInput(args);
	            engine.compute();
	
	            System.out.println("The result of " + task + " is " + engine.getResult() + ".");
	        }
	    }
	}