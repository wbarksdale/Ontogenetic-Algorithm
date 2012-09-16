package ontogeneticapp;

import java.util.Arrays;
/**
 *
 * @author wfbarksdale
 */
public class Population extends Thread{
    public boolean stopFlag = false;
    public StatsWriter writer;
    public Globals globals;
    private Organism[] population;
    private int currentGeneration;
    private int currentRun;
    private int currentBestFitness;
    
    public Population(Globals g, String statsPath){
        globals = g;
        writer = new StatsWriter(statsPath, globals);
        generatePopulation();
    }
    
    
    @Override
    public void run() {
        for (currentRun = 0; currentRun < globals.numRuns; currentRun++) {
            stopFlag = false;
            generatePopulation();
            for (currentGeneration = 0; currentGeneration < globals.eSize; currentGeneration++) {
                evolve();
                if (stopFlag == true) {
                    break;
                }
                currentGeneration++;
            }
        }
        writer.close();
        System.out.println("finished");
    }
    
    private void generatePopulation(){
        population = new Organism[globals.pSize];
        for(int i = 0; i< globals.pSize; i++){
            population[i] = new Organism(globals);
        }
    }
    
    
    public void evolve(){
        evaluatePopulation();
        Arrays.sort(population);
        writer.crunchNumbers();
        if(currentGeneration % 100 == 0){
            updateConsole();
        }
        writer.saveGenome(population[(int) (-20 * Math.log(1.0 - globals.chaos.nextDouble()))%globals.pSize], currentGeneration);
        breedPopulation();
    }
    
    private void updateConsole(){
        System.out.println("Generation " + currentGeneration);
        System.out.println("Top Current Genome, fitness = " + population[0].fitness + "|  " + population[0].getMachineReadableString());
        System.out.println("Best Fitness = " + currentBestFitness);
    }
    public void evaluatePopulation(){
        for (int i = 0; i < globals.pSize; i++) {
            Organism toEvaluate = population[i];
            toEvaluate.revertToGerm();
            if (globals.reverseFunction) {
                for (int j = globals.functionSize-1; j >= 0; j--) {
                    evaluateOrganism(toEvaluate, j);
                }
            } else {
                for (int j = 0; j < globals.functionSize; j++) {
                    evaluateOrganism(toEvaluate, j);
                }
            }
            if(toEvaluate.fitness > currentBestFitness){
                writer.saveGenome(toEvaluate, currentGeneration);
                currentBestFitness = (int) toEvaluate.fitness;
            }
            globals.sumOfFitness += toEvaluate.fitness;
            globals.sumOfSquaredFitness += toEvaluate.fitness*toEvaluate.fitness;
            toEvaluate.age++;
        }
    }
    
    
    private void evaluateOrganism(Organism o, int x){
        int result = o.findSolutionFor(x);
        int expectedResult = globals.function.get(x);
        if(globals.sequenceFunction){
            if(expectedResult == 0){
                if(result <= 0){
                    o.fitness++;
                }
            }else{
                if(result > 0){
                    o.fitness++;
                }
            }
        }else{
            if(result == expectedResult){
                o.fitness++;
            }
        }
    }
    
    
    private void breedPopulation() {
        int cutoff = (int) ((double) globals.pSize * globals.bSize);
        int currentIndex = cutoff;
        if (globals.elitistStrategy) {
            while (currentIndex < globals.pSize) {
                Organism org1 = population[globals.chaos.nextInt(cutoff)];
                Organism org2 = population[globals.chaos.nextInt(cutoff)];
                population[currentIndex] = org1.mateWith(org2);
                currentIndex++;
            }
        }else if(globals.deathStrategy){
            Organism[] newPopulation = new Organism[globals.pSize];
            for(int i = 0; i < globals.pSize; i++){
                Organism org1 = population[globals.chaos.nextInt(cutoff)];
                Organism org2 = population[globals.chaos.nextInt(cutoff)];
                newPopulation[i] = org1.mateWith(org2);
            }
            population = newPopulation;
        }else{
            
        }
    }
}
