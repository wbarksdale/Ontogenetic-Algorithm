package ontogeneticapp;

import bsh.EvalError;
import bsh.Interpreter;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wfbarksdale
 */
public class FunctionGenerator {

    int[] yvalues;
    int currentX;
    int functionSize;

    public void initializeMathematicalFunction(String aFunction, int functSize){
        functionSize = functSize;
        Interpreter interpreter = new Interpreter();
        yvalues = new int[functionSize];
        for (int i = 0; i < functionSize; i++) {
            try {
                interpreter.set("x", i);
                String functionString = aFunction;
                while (functionString.contains("fib")) {
                    int startIndex = functionString.indexOf("fib") + 4;
                    int endIndex = functionString.indexOf(")", startIndex);
                    String toFibString = functionString.substring(startIndex, endIndex);
                    int toFibInt = (Integer) interpreter.eval(toFibString);
                    String replacementString = Integer.toString(this.fib(toFibInt));
                    String oldFibString = "fib(" + toFibString + ")";
                    functionString = functionString.replace(oldFibString, replacementString);
                }
                double y = Double.parseDouble(interpreter.eval(functionString).toString());
                yvalues[i] = (int) y;
            } catch (EvalError ex) {
                Logger.getLogger(FunctionGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void initializeSequenceFunction(String aSequence, int functSize){
        functionSize = functSize;
        yvalues = new int[aSequence.length()];
        for(int i = 0; i<aSequence.length(); i++){
            yvalues[i] = Integer.parseInt(Character.toString(aSequence.charAt(i)));
        }
    }

    public int get(int x) {
        return yvalues[x%yvalues.length];
    }
    private int fib(int x) {
        int fib1 = 0;
        int fib2 = 1;
        for(int i = 0; i<x; i++){
            int temp = fib2;
            fib2 = fib1 + fib2;
            fib1 = temp;
        }
        return fib2;
    }
}