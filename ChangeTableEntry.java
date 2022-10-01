package MakeChange;

/**
 *
 * @author swocc
 */
public class ChangeTableEntry {
    // variables
    
    private int val; // total value of change made
    private int numCoins;
    private int prevDenom;
    private ChangeTableEntry prev; // previous change table entry in recursion
    
    // constructor
    
    /**
     * @description constructs ChangeTableEntry with given information
     * @param newVal
     * @param newPrevDenom;
     * @param newNumCoins
     */
    public ChangeTableEntry(int newVal, int newNumCoins, int newPrevDenom, ChangeTableEntry newPrev)
    {
        val = newVal;
        numCoins = newNumCoins;
        prevDenom = newPrevDenom;
        prev = newPrev;
    }
    
    // Methods
    
    // Accessors
    
    public int getVal()
    {
        return val;
    }
    
    public int getPrevDenom()
    {
        return prevDenom;
    }
    
    public int getNumCoins()
    {
        return numCoins;
    }
    
    public ChangeTableEntry getPrev()
    {
        return prev;
    }
}
