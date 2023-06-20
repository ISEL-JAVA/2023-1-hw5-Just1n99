package edu.handong.csee.java.hw5.engines;

/**
 * This class is the interface for the other class to access
 */
public interface Computable {
    /**
     * This checks to see if user inputted correctly and save it into the private fields variables in each class
     */
    public void setInput(String[] input);

    /**
     * This for other classes to access and use for calculations depending on engineName
     */
    public void compute();

    /**
     * Allows other classes to return answer to the main class
     */
    public double getResult();

}
