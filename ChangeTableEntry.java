package MakeChange;

/**
 * This class serves as an entry recording coin information in 
 * the making change problem
 * 
 * @author Matthew Jacobs, Michael White
 * @version 1.0.0
 * file: ChangeTableEntry.java
 * Created October 2022
 * Copyright Matthew Jacobs, Michael White
 *
 * Description: This class serves as a data structure to store information
 * needed to solve the making change problem with recursion or dynamic
 * programming.
 * It contains a monetary value, a number of coins and an array containing all 
 * the stored coins
 * */
public class ChangeTableEntry {
    // variables
    
    private int val; // total value of change made
    private int numCoins; // could be replaced with an O(n) method, but is 
    // easier to simply track
    private int[] purse; // previous change table entry in recursion
    
    // constructor
    
    /**
     * @description constructs ChangeTableEntry with given information
     * @param newVal
     * @param newPrevDenom;
     * @param newNumCoins
     */
    public ChangeTableEntry(int newVal, int newNumCoins, int[] newPurse)
    {
        val = newVal;
        numCoins = newNumCoins;
        purse = newPurse;
    }
    
    // Methods
    
    // Accessors
    
    public int getVal()
    {
        return val;
    }
    
    public int getNumCoins()
    {
        return numCoins;
    }
    
    public int[] getPurse()
    {
        return purse;
    }
}
