/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

import com.sun.org.apache.xerces.internal.util.IntStack;
import java.util.ArrayList;

/**
 *
 * @author wfbarksdale
 */
public class Opcodes {
    short[] opcodes;
    Globals globals;
    Opcodes(Globals glob, boolean segcopy, boolean transpose, boolean memory){
        globals = glob;
        IntStack aStack = new IntStack();
        for(short i = 0; i<9; i++){
            aStack.push(i);
        }
        if(segcopy){
            aStack.push(9);
        }
        if(transpose){
            aStack.push(10);
        }
        if(memory){
            aStack.push(11);
            aStack.push(12);
        }
        opcodes = new short[aStack.size()];
        int stackSize = aStack.size();
        for(int i = 0; i<stackSize; i++){
            opcodes[i] = (short) aStack.pop();
        }
    }
    short getRandomOpcode(){
        return opcodes[globals.chaos.nextInt(opcodes.length)];
    }
}
