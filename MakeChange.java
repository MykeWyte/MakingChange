package MakeChange;

import java.util.Scanner;

/**
 * This class solves the "making change" problem
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
        final int MODE = 0;
        String output = "";
        
        
        // get information from command line
        
        // set up scanner to read from stdin
        Scanner scnr = new Scanner(System.in);
        
        // get denomination array
        //System.out.println("Please enter the number of denominations you want to use:");
        int numDenoms = scnr.nextInt();
  
        //System.out.println("Please enter the denominations in increasing order:");
        int[] denoms = new int[numDenoms];
        for (int i = 0; i < numDenoms; i++)
        {
            denoms[i] = scnr.nextInt();
        }
        
        // get change amounts to calculate
        //System.out.println("Please enter the number of calculations to be done:");
        int numCalcs = scnr.nextInt();
        
        //System.out.println("Please enter the calculations:");
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
                result = iterativeSolve(calcNum, denoms, changeTable);
            }
            else
            {
                result = recursiveSolve(calcNum, denoms, changeTable, MODE);
            }
            
            // TODO: Go through purse and output the total number of coins 
            // and how many of each denomination was used to screen
            int[] purse = result.getPurse();
            
            output += (calcNum + " cents =");
            for (int DnomIndex = numDenoms - 1; DnomIndex >= 0; DnomIndex--)
            {
                if(purse[DnomIndex] == 0){
                    continue;
                }
                output += (" " + denoms[DnomIndex] + ":" + purse[DnomIndex]);
            }
            output += "\n";
        } 
        //System.out.println("Printing change conversion:");
        System.out.print(output);
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
            int solveNum = changeVal - denoms[i];
            // checking if solveNum is negative, if so begin loop again
            if(solveNum < 0)
            {
                continue;
            }
            // make recursive call
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
    
    /**
     * @description iterator works up to the desired value by 1 in order to
     * find the fewest coin solution
     * @param changeVal
     * @param denoms
     * @param changeTable
     */
    private static ChangeTableEntry iterativeSolve(int changeVal, int[] denoms, ChangeTableEntry[] changeTable)
    {
        changeTable[0] = new ChangeTableEntry(0, 0, new int[denoms.length]);
        for(int i = 1; i <= changeVal; i++)
        {
            int minCoins = Integer.MAX_VALUE;
            int denomIndex = -1;
            int best = -1;
            for(int j = 0; j < denoms.length; j++)
            {
                int solveNum = i - denoms[j];
                // checking if solveNum is negative, if so begin loop again
                if(solveNum < 0)
                {
                    continue;
                }
                
                int curr = changeTable[solveNum].getNumCoins();
                
                if (curr < minCoins)
                {
                    // update best info
                    minCoins = curr;
                    denomIndex = j;
                    best = solveNum;
                }
            }
            
            // create new table entry
            int [] newPurse = new int [denoms.length];
            int [] oldPurse = changeTable[best].getPurse();
            for (int k = 0; k < oldPurse.length; k++) // copy old purse and add coin
            {
                newPurse[k] = oldPurse[k];
            }
            newPurse[denomIndex]++;
            changeTable[i] = new ChangeTableEntry(i, changeTable[best].getNumCoins() + 1, newPurse);
        }
        return changeTable[changeVal];
    }
}