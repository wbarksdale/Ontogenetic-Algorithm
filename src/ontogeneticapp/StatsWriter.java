package ontogeneticapp;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wfbarksdale
 */
public class StatsWriter{

    FileWriter output;
    FileWriter genomes;
    String header = "mean   sd   variance   noop   plus    mins    mult    pdiv    push    " 
            + "dupl    left    rght    scpy    trps    load    store \n";
    Globals globals;
    public StatsWriter(String path, Globals glob) {
        globals = glob;
        try {
            String userHomeDir = System.getProperty("user.home", ".");
            path = userHomeDir + path;
            output = new FileWriter(path);
            output.write(header);
            FileWriter runInfo = new FileWriter(path.replace(".txt", "_RUNINFO.txt"));
            runInfo.write(globals.toString());
            runInfo.close();
            genomes = new FileWriter(path.replace(".txt", "_GENOMES.txt"));
        } catch (Exception e) {
            System.out.println("output file error: " + e.getMessage());
            System.out.println(e.fillInStackTrace());
        }
    }
    
    public void close() {
        try {
            genomes.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(StatsWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crunchNumbers() {
        try {
            output.write((double) globals.sumOfFitness/globals.pSize + "   ");
            output.write(Math.pow(calculateVarianceFitness(), .5) + "   ");
            output.write((double) calculateVarianceFitness() + "   ");
            for(int i = 0; i<globals.maxOperators; i++){
                output.write((double) globals.opcodeCounts[i]/(globals.pSize*globals.maxInstructions*globals.functionSize) + "   ");
            }
            output.write("\n");
            resetGlobalCounters();
        } catch (IOException ex) {
            Logger.getLogger(StatsWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private double calculateVarianceFitness(){
        double variance = (double) (globals.sumOfSquaredFitness - Math.pow(globals.sumOfFitness, 2)/globals.pSize)/globals.pSize;
        return variance;
    }
    
    private void resetGlobalCounters(){
        for(int i = 0; i<globals.maxOperators; i++){
            globals.opcodeCounts[i] = 0;
        }
        globals.sumOfFitness = 0;
        globals.sumOfSquaredFitness = 0;
    }
    void saveGenome(Organism organism, int generation) {
        try {
            genomes.write("Generation " + generation + ", Fitness = " + organism.fitness + ": " + organism.getMachineReadableString());
            genomes.write("\n");
        } catch (Exception ex) {
            System.out.println("error in saveGenome");
        }
    }
}
