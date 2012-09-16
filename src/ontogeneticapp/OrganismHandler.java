/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

import com.sun.org.apache.xerces.internal.util.IntStack;

/**
 *
 * @author wfbarksdale
 */
public class OrganismHandler {
    Organism organism;
    int currentX;
    int previousReturn;
    int expectedReturn;
    boolean reverse;
    OrganismHandler(Organism org, boolean inReverse, int functionSize){
        organism = org;
        reverse = inReverse;
        if(inReverse){
            currentX = functionSize-1;
        }else{
            currentX = 0;
        }
    }
    int getCurrentX(){
        return currentX;
    }
    boolean step(){
        int returnedValue = organism.step(currentX);
        if(organism.instructionCounter == 0){
            previousReturn = returnedValue;
            expectedReturn = organism.globals.function.get(currentX);
            if(reverse){
                currentX--;
            }else{
                currentX++;
            }
            return true;
        }
        return false;
    }
    String getGenomeString() {
        return organism.getHumanReadableString();
    }

    int getInstructionCount() {
        return organism.instructionCounter;
    }
    public String getStackString() {
        IntStack stack = organism.globals.stack;
        String toReturn = "| ";
        for (int i = 0; i < stack.size(); i++) {
            toReturn += stack.elementAt(i) + " | ";
        }
        return toReturn;
    }

    String getMemoryString() {
        return organism.getMemoryString();
    }
}
