/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author wfbarksdale
 */
public class Genome {
    short[] germ;
    short[] genome;
    int size;
    int currentIndex;
    
    /*
     * creates a genome from a string representation of a genome
     * @param the string to create the genome from
     */
    public Genome(String genomeString){
        germ = parseGenome(genomeString);
        size = germ.length;
        genome = new short[size];
        System.arraycopy(germ, 0, genome, 0, size);
    }
    private short[] parseGenome(String genomeString){
        StringTokenizer tokenizer = new StringTokenizer(genomeString);
        int length = tokenizer.countTokens();
        short[] newGenes = new short[length];
        for(int i = 0; i<length; i++){
            String op = tokenizer.nextToken();
            switch(op){
                case "noop":
                case "0":
                    newGenes[i] = 0;
                    break;
                case "plus":
                case "1":
                    newGenes[i] = 1;
                    break;
                case "mins":
                case "2":
                    newGenes[i] = 2;
                    break;
                case "mult":
                case "3":
                    newGenes[i] = 3;
                    break;
                case "pdiv":
                case "4":
                    newGenes[i] = 4;
                    break;
                case "push":
                case "5":
                    newGenes[i] = 5;
                    break;
                case "dupl":
                case "6":
                    newGenes[i] = 6;
                    break;
                case "left":
                case "7":
                    newGenes[i] = 7;
                    break;
                case "rght":
                case "8":
                    newGenes[i] = 8;
                    break;
                case "scpy":
                case "9":
                    newGenes[i] = 9;
                    break;
                case "trps":
                case "10":
                    newGenes[i] = 10;
                    break;
                case "load":
                case "11":
                    newGenes[i] = 11;
                    break;
                case "stor":
                case "12":
                    newGenes[i] = 12;
                    break;
                default:
                    System.out.println("Input Genome was not formatted correctly");
                    break;
            }
        }
        return newGenes;
    }
    /*
     * creates a genome from a short[]
     */
    public Genome(short[] newGenome){
        germ = newGenome;
        size = germ.length;
        genome = new short[size];
        System.arraycopy(germ, 0, genome, 0, size);
    }
    /*
     * @param random        the random generator used to generate a random genome
     * @param genomeLength  the length of the genome to generate
     * @param numOperators  the number of valid operators
     */
    public Genome(Opcodes opcodes, int genomeLength){
        size = genomeLength;
        genome = new short[size];
        germ = new short[size];
        for(int i = 0; i < size; i++){
            germ[i] = opcodes.getRandomOpcode();
        }
        System.arraycopy(germ, 0, genome, 0, size);
        currentIndex = 0;
    }
    /*
     * Get the next instruction, and advance currentIndex
     */
    short getNextInstruction(){
        return genome[currentIndex];
    }
    
    /*
     * Reset the genome to its original state (germ state)
     */
    public void reset() {
        System.arraycopy(germ, 0, genome, 0, size);
        currentIndex = 0;
    }

    public void segmentCopy(int copyStartIndex, int lengthOfCopy, int pasteIndex) {
        copyStartIndex = (((currentIndex + copyStartIndex) % size) + size) % size;
        lengthOfCopy = coerceInteger(lengthOfCopy);
        pasteIndex = (((currentIndex + pasteIndex) % size) + size) % size;
        int copyEndIndex = (copyStartIndex + lengthOfCopy) % size;
        int pasteEndIndex = (pasteIndex + lengthOfCopy) %size;
        short[] copiedSegment = new short[lengthOfCopy];
        int count = 0;
        for(int i = copyStartIndex; i != copyEndIndex; i = (i + 1) %size){
            copiedSegment[count] = genome[i];
            count++;
        }
        count = 0;
        for(int i = pasteIndex; i != pasteEndIndex; i= (i+1)%size){
            genome[i] = copiedSegment[count];
            count++;
        }
    }
    public void transpose(int lengthOfSegment, int transposeDistance) {
        lengthOfSegment = lengthOfSegment %size;
        transposeDistance = transposeDistance %size;
       if(lengthOfSegment < 0){
           transposeLeftSegment(Math.abs(lengthOfSegment), transposeDistance);
       }else{
           transposeRightSegment(Math.abs(lengthOfSegment), transposeDistance);
       }
    }

    public void shiftLeft(int shamt) {
        shamt = coerceInteger(shamt);
        currentIndex = ((currentIndex + size) - shamt) %size;
    }

    public void shiftRight(int shamt) {
        shamt = coerceInteger(shamt);
        currentIndex = (currentIndex + shamt) %size;
    }
    
    private int coerceInteger(int i){
        return ((i% size) + size) % size;
    }
    
    public Genome recombineWith(Globals globals, Genome mateGenome) {
        int crossoverPoint = -1;
        if(globals.chaos.nextDouble() < globals.crossoverFrequency){
            crossoverPoint = globals.chaos.nextInt(size);
        }
        short[] newGenome = new short[size];
        boolean copyThisGenome = true;
        for(int i = 0; i<size; i++){
            if(i == crossoverPoint){
                copyThisGenome = false;
            }
            if(copyThisGenome){
                if(globals.chaos.nextDouble() > globals.mutationFrequency){
                    newGenome[i] = this.genome[i];
                }else{
                    newGenome[i] = (short) globals.opcodes.getRandomOpcode();
                }
            }else{
                if(globals.chaos.nextDouble() > globals.mutationFrequency){
                    newGenome[i] = mateGenome.genome[i];
                }else {
                    newGenome[i] = (short) globals.opcodes.getRandomOpcode();
                }
            }
        }
        return new Genome(newGenome);
    }
    private String opToString(int op) {
        switch (op) {
            case 0:
                return "<font color=white>noop</font>";
            case 1:
                return "<font color=006600>plus</font>";
            case 2:
                return "<font color=00CC66>mins</font>";
            case 3:
                return "<font color=66FF33>mult</font>";
            case 4:
                return "<font color=336600>pdiv</font>";
            case 5:
                return "<font color=purple>push</font>";
            case 6:
                return "<font color=FF00FF>dupl</font>";
            case 7:
                return "<font color=3333CC>left</font>";
            case 8:
                return "<font color=202081>rght</font>";
            case 9:
                return "<font color=FF3300>scpy</font>";
            case 10:
                return "<font color=990000>trps</font>";
            case 11:
                return "<font color=FFA927>load</font>";
            case 12:
                return "<font color=FFA927>store</font>";
        }
        return "unrecognized";
    }
    
    public String toHumanReadableString(){
        String genomeString = "";
        for(int i = 0; i< size; i++){
            if(i == currentIndex){
                genomeString += ">";
            }
            genomeString = genomeString + opToString(genome[i]) + " ";
        }
        return genomeString;
    }
    /*
     * returns a string of numbers
     */
    public String toMachineReadableString(){
        String genomeString = "";
        for(int i = 0; i < size; i++){
            genomeString += Integer.toString((int) genome[i]) + " ";
        }
        return genomeString;
    }

    private void transposeLeftSegment(int segmentLength, int transposeDistance) {
        int startIndex = currentIndex;
        int endIndex = coerceInteger(currentIndex - segmentLength + 1);
        short[] segment = new short[segmentLength];
        //copy out the segment
        for(int i = 0; i<segmentLength; i++){
            segment[coerceInteger(segmentLength-1-i)] = genome[coerceInteger(startIndex - i)];
        }
        if(transposeDistance < 0){
            while(transposeDistance < 0){
                genome[startIndex] = genome[coerceInteger(endIndex - 1)];
                startIndex = coerceInteger(startIndex -1);
                endIndex = coerceInteger(endIndex -1);
                transposeDistance++;
            }
        }else{
            while(transposeDistance > 0){
                genome[endIndex] = genome[coerceInteger(startIndex + 1)];
                startIndex = coerceInteger(startIndex +1);
                endIndex = coerceInteger(endIndex +1);
                transposeDistance--;
            }
        }
        for(int i = 0; i<segmentLength; i++){
            genome[endIndex] = segment[i];
            endIndex = coerceInteger(endIndex +1);
        }
    }
    
    private void transposeRightSegment(int segmentLength, int transposeDistance) {
        int startIndex = currentIndex;
        int endIndex = coerceInteger(currentIndex + segmentLength - 1);
        short[] segment = new short[segmentLength];
        //copy out the segment
        for(int i = 0; i<segmentLength; i++){
            segment[i] = genome[coerceInteger(startIndex + i)];
        }
        if(transposeDistance < 0){
            while(transposeDistance < 0){
                genome[endIndex] = genome[coerceInteger(startIndex - 1)];
                startIndex = coerceInteger(startIndex -1);
                endIndex = coerceInteger(endIndex -1);
                transposeDistance++;
            }
        }else{
            while(transposeDistance > 0){
                genome[startIndex] = genome[coerceInteger(endIndex + 1)];
                startIndex = coerceInteger(startIndex +1);
                endIndex = coerceInteger(endIndex +1);
                transposeDistance--;
            }
        }
        for(int i = 0; i<segmentLength; i++){
            genome[startIndex] = segment[i];
            startIndex = coerceInteger(startIndex +1);
        }
    }

    void advance() {
        currentIndex = coerceInteger(currentIndex + 1);
    }
}
