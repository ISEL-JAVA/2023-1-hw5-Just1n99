package edu.handong.csee.java.hw5.fileutil;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This is the FileManager class to read and write files
 */
public class FileManager {
	/**
	 * This reads lines from a file user has input
	 */
	public static ArrayList<String> readLinesFromATxtFile(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + filePath);
            System.exit(0);
        }

        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            lines.add(line);
        }

        inputStream.close();

        return lines;
    }
	
	/**
	 * This writes contents to a file the user specifies
	 */
	public static void writeAtxtFile(String filePath, ArrayList<String> lines) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}