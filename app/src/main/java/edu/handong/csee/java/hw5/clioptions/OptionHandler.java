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
       
       options.addOption(Option.builder("h").longOpt("help")
                    .desc("Show the help page")
                    .build());
       
       options.addOption(Option.builder("i").longOpt("ipath")
                   .desc("Set a path for a data input file. It must work with -t SQRT, -t MAX, or -t MIN and -o options together. e.g., -t SQRT -i file.csv -o output.csv")
                   .argName("A data file/directory path to read")
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
                    .required()
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
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            helpRequested = cmd.hasOption("h");
            dataInputFilePath = cmd.getOptionValue("i");
            dataOutputFilePath = cmd.getOptionValue("o");
            task = cmd.getOptionValue("t");
            inputValues = cmd.getOptionValue("v");

        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Print the help page if user makes a mistake or types in the h option
     */
    public void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "Math Calculator";
        String footer = "\nThis is the 2023-1 HW5 help page.";

        formatter.printHelp("calculator", header, options, footer, true);
    }

}