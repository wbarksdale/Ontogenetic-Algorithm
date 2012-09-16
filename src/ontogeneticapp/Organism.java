/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

/**
 *
 * @author wfbarksdale
 */
class Organism implements Comparable<Organism> {

    double fitness = 0;
    int x;
    int instructionCounter = 0;
    Memory memory;
    Globals globals;
    Genome genome;
    int age = 0;
    
    Organism(String genomeString, Globals glob){
        globals = glob;
        memory = new Memory(globals.memorySize);
        genome = new Genome(genomeString);
    }
    Organism(Globals glob, Genome aGenome){
        globals = glob;
        genome = aGenome;
        memory = new Memory(globals.memorySize);
    }
    
    
    /*
     * @param glob      A globals object to use
     * @return          a new organism with a random genome
     */
    Organism(Globals glob) {
        globals = glob;
        genome = new Genome(globals.opcodes, globals.gSize);
        memory = new Memory(globals.memorySize);
    }
    
    
    /*
     * Return a solution for a given x
     * @param the current x to find a solution for
     */
    public int findSolutionFor(int newX) {
        globals.stack.clear();
        x = newX;
        instructionCounter = 0;
        while (instructionCounter < globals.maxInstructions) {
            executeInstruction();
            instructionCounter++;
        }
        if (globals.stack.size() > 0) {
            return globals.stack.pop();
        }
        return 0;
    }
    
    public int step(int newX) {
        x = newX;
        executeInstruction();
        instructionCounter++;
        if (instructionCounter >= globals.maxInstructions) {
            instructionCounter = 0;
            int result = 0;
            if (globals.stack.size() > 0) {
                result = globals.stack.pop();
            }
            globals.stack.clear();
            return result;
        }
        return Integer.MAX_VALUE;
    }

    private void executeInstruction() {
        short opCode = genome.getNextInstruction();
        switch (opCode) {
            case 0:
                break;
            case 1: //add
                add();
                break;
            case 2: //subtraction
                subtract();
                break;
            case 3: //multiplication
                multiply();
                break;
            case 4: //protected division
                divide();
                break;
            case 5://push x
                pushX();
                break;
            case 6://duplicate
                duplicate();
                break;
            case 7://shift left
                shiftLeft();
                break;
            case 8://shift right
                shiftRight();
                break;
            case 9://segment copy
                segmentCopy();
                break;
            case 10://transpose segment
                transposeSegment();
                break;
            case 11://load memory to stack
                loadMemory();
                break;
            case 12://store top of stack to Memory
                storeMemory();
                break;
        }
        globals.opcodeCounts[opCode]++;
        genome.advance();
    }

    public String germToString() {
        return genome.toString();
    }

    public void revertToGerm() {
        genome.reset();
        fitness = 0;
    }

    @Override
    public String toString() {
        return genome.toString();
    }

    @Override
    public int compareTo(Organism t) {
        if (t.fitness > this.fitness) {
            return 1;
        }else if(t.fitness == this.fitness){
            return 0;
        }else{
            return -1;
        }
    }

    private void add() {
        if (globals.stack.size() >= 2) {
            int a = globals.stack.pop();
            int b = globals.stack.pop();
            int c = a + b;
            globals.stack.push(c);
        }
    }

    private void subtract() {
        if (globals.stack.size() >= 2) {
            int a = globals.stack.pop();
            int b = globals.stack.pop();
            int c = a - b;
            globals.stack.push(c);
        }
    }

    private void multiply() {
        if (globals.stack.size() >= 2) {
            int a = globals.stack.pop();
            int b = globals.stack.pop();
            int c = a * b;
            globals.stack.push(c);
        }
    }

    private void divide() {
        if (globals.stack.size() >= 2) {
            int a = globals.stack.pop();
            int b = globals.stack.pop();
            int c = 1;
            if (b != 0) {
                c = a / b;
            }
            globals.stack.push(c);
        }
    }

    private void pushX() {
        globals.stack.push(x);
    }

    private void duplicate() {
        if (globals.stack.size() != 0) {
            int d = globals.stack.peek();
            globals.stack.push(d);
        }
    }

    private void segmentCopy() {
        if (globals.stack.size() >= 3) {
            int copyStartIndex = globals.stack.pop();
            int lengthOfCopy = globals.stack.pop();
            int pasteIndex = globals.stack.pop();
            genome.segmentCopy(copyStartIndex, lengthOfCopy, pasteIndex);
        }
    }

    private void transposeSegment() {
        if (globals.stack.size() >= 2) {
            int lengthOfSegment = globals.stack.pop();
            int transposeDistance = globals.stack.pop();
            genome.transpose(lengthOfSegment, transposeDistance);
        }
    }

    private void shiftLeft() {
        if (globals.stack.size() != 0) {
            int shamt = globals.stack.pop();
            genome.shiftLeft(shamt);
        }
    }

    private void shiftRight() {
        if (globals.stack.size() != 0) {
            int shamt = globals.stack.pop();
            genome.shiftRight(shamt);
        }
    }

    private void loadMemory() {
        if (globals.stack.size() != 0) {
            globals.stack.push(memory.load(globals.stack.pop()));
        }
    }

    private void storeMemory() {
        if (globals.stack.size() >= 2) {
            int toStore = globals.stack.pop();
            int index = globals.stack.pop();
            memory.store(toStore, index);
        }
    }

    Organism mateWith(Organism org2) {
        Genome newGenome = genome.recombineWith(globals, org2.genome);
        return new Organism(globals, newGenome);
    }
        
    
    String getHumanReadableString(){
        return genome.toHumanReadableString();
    }
    String getMachineReadableString(){
        return genome.toMachineReadableString();
    }

    String getMemoryString() {
        return memory.getString();
    }
}
