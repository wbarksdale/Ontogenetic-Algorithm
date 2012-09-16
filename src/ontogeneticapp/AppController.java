/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

import com.sun.org.apache.xerces.internal.util.IntStack;
import java.util.StringTokenizer;

/**
 *
 * @author wfbarksdale
 */
public class AppController {

    MainWindow appWindow;
    private Population p;
    private OrganismHandler organismHandler;

    public AppController(MainWindow aWindow) {
        appWindow = aWindow;
        appWindow.setController(this);
    }

    public void createNewPopulation(Globals globals, String statsPath) {
        p = new Population(globals, statsPath);
        p.start();
        System.out.println("p started");
    }

    void stopSimulation() {
        p.stopFlag = true;
    }

    public boolean step() {
        boolean didReturnResult = organismHandler.step();
        if(didReturnResult){
            appWindow.setPreviousReturns(Integer.toString(organismHandler.previousReturn), Integer.toString(organismHandler.expectedReturn));
        }
        updateOrganismView();
        return didReturnResult;
    }

    public void jump() {
        boolean finished = step();
        while (!finished) {
            finished = step();
        }
    }

    public void updateOrganismView() {
        appWindow.setGenomeLabel(organismHandler.getGenomeString());
        appWindow.setStackLabel(organismHandler.getStackString());
        appWindow.setXLabel("" + organismHandler.getCurrentX());
        appWindow.setInstructionCountLabel("" + organismHandler.getInstructionCount());
        appWindow.setMemoryLabel("" + organismHandler.getMemoryString());
    }

    public void loadGenome(String genomeString, String functionString, boolean isSequenceProblem, Globals globals) {
        //Initialize the function
        globals.function = new FunctionGenerator();
        if(isSequenceProblem){
            globals.function.initializeSequenceFunction(functionString, 100);
        }else{
            globals.function.initializeMathematicalFunction(functionString, 100);
        }
        StringTokenizer tokenizer = new StringTokenizer(genomeString);
        globals.gSize = tokenizer.countTokens();
        globals.maxInstructions = tokenizer.countTokens();
        organismHandler = new OrganismHandler(new Organism(genomeString, globals), globals.reverseFunction, globals.functionSize);
        updateOrganismView();
    }
}
