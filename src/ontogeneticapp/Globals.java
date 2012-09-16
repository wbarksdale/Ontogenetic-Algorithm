/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

import com.sun.org.apache.xerces.internal.util.IntStack;
import java.util.Random;


/**
 *
 * @author wfbarksdale
 */
class Globals {
    //May not be odd
    public int pSize; // must be a multiple of 4
    public int eSize;
    public int gSize;
    public double crossoverFrequency;
    public double mutationFrequency;
    public IntStack stack;
    public int maxInstructions;
    public Random chaos;
    public long seed;
    public double maxBonus = 5.0;
    public int maxOperators = 13;
    public double bSize;
    public long[] opcodeCounts;
    public long sumOfFitness = 0;
    public long sumOfSquaredFitness = 0;
    public int functionSize;
    public boolean reverseFunction;
    public boolean useSegCopy;
    public boolean useMemory;
    public boolean useTranspose;
    public String functionString;
    public boolean sequenceFunction;
    public boolean elitistStrategy;
    public boolean agingStrategy;
    public boolean deathStrategy;
    public int numRuns;
    public int maxAge;
    int memorySize = 2;
    FunctionGenerator function;
    Opcodes opcodes;
    
    public Globals(){
        stack = new IntStack();
    }
    public String toString(){
        return  "pSize = " + pSize + "\n" +
                "eSize = " + eSize + "\n" +
                "gSize = " + gSize + "\n" +
                "bSize = " + bSize + "\n" +
                "numRuns = " + numRuns + "\n" +
                "crossoverFrequency = " + crossoverFrequency + "\n" +
                "mutationFrequency = " + mutationFrequency + "\n" +
                "maxOperators = " + maxOperators + "\n" +
                "seed = " + seed + "\n" +
                "maxInstructions = " + maxInstructions + "\n" +
                "functionSize = " + functionSize + "\n" +
                "reverse? = " + reverseFunction + "\n" +
                "sequence? = " + sequenceFunction + "\n" +
                "useMemory? = " + useMemory + "\n" +
                "memorySize = " + memorySize + "\n" +
                "useSegCopy? = " + useSegCopy + "\n" +
                "useTranspose? = " + useTranspose + "\n" +
                "function = " + functionString + "\n" +
                "deathStrategy = " + deathStrategy + "\n" +
                "agingStrategy = " + agingStrategy + "\n" +
                "maxAge = " + maxAge + "\n" + 
                "elitismStrategy = " + elitistStrategy + "\n";
    }
}
