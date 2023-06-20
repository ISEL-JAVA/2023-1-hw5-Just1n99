package edu.handong.csee.java.hw5.clioptions;

import org.apache.commons.cli.*;

/**
 * This is the OptionHandler class that handles command-line options and arguments
 */
public class OptionHandler {
    private String task;
    private String dataInputFilePath;
    private String dataOutputFilePath;
    private String inputValues;
    private boolean helpRequested;
    
    private CommandLineParser parser;
    private HelpFormatter formatter;
    private CommandLine cmd;
    
    /**
     * Constructor for OptionHandler
     */
    public OptionHandler() {
    	parser = new DefaultParser();
    	formatter = new HelpFormatter();
    }
    /**
     * getter for HelpRequested
     */
    public boolean getHelpRequested() {
    	return helpRequested;
    }
    
    /**
     * setter for HelpRequested
     */
    public void setHelpRequested(boolean helpRequested) {
    	this.helpRequested = helpRequested;
    }
    
    /**
     * getter for Task
     */
    public String getTask() {
    	return task;
    }
    
    /**
     * setter for Task
     */
    public void setTask (String task) {
    	this.task = task;
    }
    
    /**
     * getter for DataInputFilePath
     */
    public String getDataInputFilePath() {
    	return dataInputFilePath;
    }
    
    /**
     * setter for DataInputFilePath
     */
    public void setDataInputFilePath(String dataInputFilePath) {
    	this.dataInputFilePath = dataInputFilePath;
    }
    
    /**
     * getter for DataOutputFilePath
     */
    public String getDataOutputFilePath() {
    	return dataOutputFilePath;
    }
    
    /**
     * setter for DataOutputFilePath
     */
    public void setDataOutputFilePath(String dataOutputFilePath) {
    	this.dataOutputFilePath = dataOutputFilePath;
    }
    
    /**
     * getter for InputValues
     */
    public String getInputValues() {
    	return inputValues;
    }
    
    /**
     * setter for InputValues
     */
    public void setInputValues(String inputValues) {
    	this.inputValues = inputValues;
    }
    
    /**
     * Creating Options and their descriptions for users to see and use
     */
    public Options createOptions() {
    	Options options = new Options();
    	
    	options.addOption("h", "help", false, "Show the help page");
    	options.addOption(Option.builder("i").longOpt("ipath")
    					.desc("Set a path for a data input file. It works with -t SQRT. e.g., -t SQRT -i file.csv -o output.csv")
    					.argName("A data file path to read")
    					.hasArg()
    					.build());
    	
    	options.addOption(Option.builder("o").longOpt("opath")
				.desc("Set a path for a data output file.")
				.argName("A data file path to write")
				.hasArg()
				.build());
    	
        options.addOption(Option.builder("t").longOpt("task")
        				.desc("Set a task. The -t or -i options must be set as well.")
        				.argName("A task name")
        				.hasArg()
        				.build());
        
        options.addOption(Option.builder("v").longOpt("values")
				.desc("Set input values (separator: space), e.g. \"23 21 2\"")
				.argName("Input values for a task.")
				.hasArg()
				.build());
        
        return options;
    }
    
    /**
     * Read the command line and see if it viable or not
     */
    public boolean parseOptions(Options options, String[] args) {
        try {
            cmd = parser.parse(options, args);
            
            if (cmd.hasOption("h")) {
                helpRequested = true;
                return true;
            }

            if (cmd.hasOption("t")) {
                task = cmd.getOptionValue("t");
            } else {
                return false;
            }

            if (cmd.hasOption("i")) {
                dataInputFilePath = cmd.getOptionValue("i");
            }

            if (cmd.hasOption("o")) {
                dataOutputFilePath = cmd.getOptionValue("o");
            }

            if (cmd.hasOption("v")) {
                inputValues = cmd.getOptionValue("v");
            }

            return true;
        } catch (ParseException e) {
            System.out.println("Failed to parse command-line options. Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Print the help page if user makes a mistake or types in the h option
     */
    public void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "Math Calculator";
        String footer = "\nThis is the 2023-1 HW4 help page.\n";
        String usage = "calculator [-h] [-i <A data file path to read>] [-o <A data file\n    path to write>] -t <A task name> [-v <Input values for a task.>]";

        formatter.printHelp(usage, header, options, footer);
    }

}
