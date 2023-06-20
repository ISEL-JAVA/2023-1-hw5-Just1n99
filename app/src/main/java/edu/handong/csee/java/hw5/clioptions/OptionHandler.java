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
     * Getter for helpRequested
     */
    public boolean getHelpRequested() {
        return helpRequested;
    }

    /**
     * Setter for helpRequested
     */
    public void setHelpRequested() {
        helpRequested = true;
    }

    /**
     * Getter for task
     */
    public String getTask() {
        return task;
    }

    /**
     * Setter for task
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Getter for dataInputFilePath
     */
    public String getDataInputFilePath() {
        return dataInputFilePath;
    }

    /**
     * Setter for dataInputFilePath
     */
    public void setDataInputFilePath(String dataInputFilePath) {
        this.dataInputFilePath = dataInputFilePath;
    }

    /**
     * Getter for dataOutputFilePath
     */
    public String getDataOutputFilePath() {
        return dataOutputFilePath;
    }

    /**
     * Setter for dataOutputFilePath
     */
    public void setDataOutputFilePath(String dataOutputFilePath) {
        this.dataOutputFilePath = dataOutputFilePath;
    }

    /**
     * Getter for inputValues
     */
    public String getInputValues() {
        return inputValues;
    }

    /**
     * Setter for inputValues
     */
    public void setInputValues(String inputValues) {
        this.inputValues = inputValues;
    }

    /**
     * Create the command-line options
     */
    public Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder("t")
                .longOpt("task")
                .desc("Task name")
                .hasArg()
                .argName("task")
                .build());

        options.addOption(Option.builder("i")
                .longOpt("input")
                .desc("Input file path")
                .hasArg()
                .argName("input")
                .build());

        options.addOption(Option.builder("o")
                .longOpt("output")
                .desc("Output file path")
                .hasArg()
                .argName("output")
                .build());

        options.addOption(Option.builder("v")
                .longOpt("values")
                .desc("Input values")
                .hasArg()
                .argName("values")
                .build());

        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Print help message")
                .build());

        return options;
    }

    /**
     * Parse the command-line options and arguments
     */
    public boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                helpRequested = true;
                return true;
            }

            if (cmd.hasOption("t")) {
                task = cmd.getOptionValue("t");
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

        } catch (ParseException e) {
            System.out.println("Failed to parse command-line options. Reason: " + e.getMessage());
            formatter.printHelp("Calculator", options);
            return false;
        }

        return true;
    }

    /**
     * Print the help message
     */
    public void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Calculator", options);
    }
}
