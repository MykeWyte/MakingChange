package MakeChange;

import java.util.Scanner;

/**
 * This class solves the "making change problem
 * 
 * @author Matthew Jacobs, Michael White
 * @version 1.0.0
 * file: MakeChange.java
 * Created October 2022
 * Copyright Matthew Jacobs, Michael White
 *
 * Description: This class exists to solve the "making change" problem.
 * It takes parameters in the form of a list of coin denominations
 * and then a list of values to make change for. It returns how many of 
 * each coin denomination is needed to reach the desired value. It can be 
 * used to do the bottom-up approach and the recursive approach with and
 * without memoization.
 */
public class MakeChange {
    public static void main(String[] args) {
        /* SETUP ON PROGRAMMER SIDE!
         * MODIFY MODE VALUE TO RUN DIFFERENT MODES
         * MODE 0 = BOTTOM UP
         * MODE 1 = RECURSIVE (NO MEMOIZATION)
         * MODE 2 = RECURSIVE (WITH MEMOIZATION)
         */
        final int MODE = 2;
        
        // get information from command line
        
        // set up scanner to read from stdin
        Scanner scnr = new Scanner(System.in);
        
        // get denomination array
        int numDenoms = scnr.nextInt();
        
        int[] denoms = new int[numDenoms];
        for (int i = 0; i < numDenoms; i++)
        {
            denoms[i] = scnr.nextInt();
        }
        
        // get change amounts to calculate
        
        int numCalcs = scnr.nextInt();
        
        for (int i = 0; i < numCalcs; i++)
        {
            // TODO: add timing for testing
            
            // initialize result data
            ChangeTableEntry result = null;
            
            // run method to get coin denominations
            
            // get calculate value and set up table
            int calcNum = scnr.nextInt();
            ChangeTableEntry[] changeTable = new ChangeTableEntry[calcNum + 1];
            // reset for every problem, could be stored and resized for more efficiency on a per-problem basis
            // has 1 more index for the 0 value to break recursion
            
            // different modes to solve
            if (MODE == 0)
            {
                // TODO: Write the bottom-up code
            }
            else
            {
                result = recursiveSolve(calcNum, denoms, changeTable, MODE);
            }
            
            // TODO: Go through purse and output the total number of coins 
            // and how many of each denomination was used to screen
        }
        return;
    }
    
    /**
     * @description recursively calls itself on the values 1 coin less than 
     * itself and connects to the fewest coins solution.
     * @param changeVal
     * @param denoms
     * @param changeTable
     * @param mode
     */
    private static ChangeTableEntry recursiveSolve(int changeVal, int[] denoms, ChangeTableEntry[] changeTable, int mode)
    {
        // determine if recursion breaks
        // value memoized
        if (mode == 2 && changeTable[changeVal] != null)
        {
            return changeTable[changeVal];
        }
        // value 0, bottom of recursion
        if (changeVal == 0)
        {
            ChangeTableEntry base = new ChangeTableEntry(0, 0, new int[denoms.length]);
            if (mode == 2)
            {
                changeTable[0] = base;
            }
            return base;
        }
        
        // subtract possible denoms
        int minCoins = Integer.MAX_VALUE;
        int denomIndex = -1;
        ChangeTableEntry best = null;
        ChangeTableEntry curr = null;
        for (int i = 0; i < denoms.length; i++)
        {
            // make recursive call
            int solveNum = changeVal - denoms[i];
            curr = recursiveSolve(solveNum, denoms, changeTable, mode);
            
            // compare to find best
            if (curr.getNumCoins() < minCoins)
            {
                // update best info
                minCoins = curr.getNumCoins();
                denomIndex = i;
                best = curr;
            }
        }
        
        // create new table entry
        int [] newPurse = new int [denoms.length];
        int [] oldPurse = best.getPurse();
        for (int i = 0; i < oldPurse.length; i++) // copy old purse and add coin
        {
            newPurse[i] = oldPurse[i];
        }
        newPurse[denomIndex]++;
        
        ChangeTableEntry currEntry = new ChangeTableEntry(changeVal, best.getNumCoins() + 1, newPurse);
        // memoize if necessary
        if (mode == 2)
        {
            changeTable[changeVal] = currEntry;
        }
        return currEntry;
    }
}
