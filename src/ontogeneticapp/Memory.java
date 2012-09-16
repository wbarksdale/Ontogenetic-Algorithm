/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

/**
 *
 * @author wfbarksdale
 */
public class Memory {
    int size;
    int[] memory;
    
    Memory(int aSize){
        size = aSize;
        memory = new int[size];
    }
    int load(int i){
        return memory[convertToIndex(i)];
    }
    void store(int toStore, int i){
        memory[convertToIndex(i)] = toStore;
    }
    int convertToIndex(int i){
        return ((i %size) + size)%size;
    }
    String getString(){
        String memoryString = "";
        for(int i = 0; i<size; i++){
            memoryString += "[" + memory[i] + "] ";
        }
        return memoryString;
    }
    
}
