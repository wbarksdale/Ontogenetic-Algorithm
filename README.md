Ontogenetic-Algorithm
=====================

This project was the result of a collaborative effort with a professor at the University of Richmond, Dr. Gary Greenfield. The goal was to investigate a strategy of self modification (the "onto" in "ontogenetic") in genetic algorithms. The motivation comes in part from the recognition of transposons (jumping genes) as playing an important role in biological DNA. The work was based off the work of previous authors Spector And Stoffel (citation at the end), who used this strategy to achieve signicantly more effecient genetic algorithms. The result of our study was contrary to the results of Spector and Stoffel. We found that the most successful genomes tended to not contain ontogenetic algorithms, or evolve controls to avoid their execution. A paper describing our results can be found [here](http://http://williambarksdale.com/ontogenetic_programs.pdf).

Interface
=====================
The Interface is quite lackluster, as the sole end user was myself and Dr. Greenfield. Realtime activity of the app running gets dumped to the console. 3 files are dumped to a directory of your choosing, the xx\_RUNINFO.txt contains all the necessary parameters to recreate the run. The xx\_GENOME.txt contains genomes of interest for individual inspection in the Organism view (explained later). And findally the xx.txt file contains the frequency of execution of genetic operators over time. 

All parameters to the algorithm are customizable

 * pSize - the size of the population of genomes
 * eSize - the number of iterations of the algorithm
 * gSize - the number of operators in each genome
 * functionSize - the number of values of f(x) that each genome attempts to provide solutions for
 * maxInstructions - the number of Instructions that are allowed to execute by a genome to provided a solutions
 * crossoverFrequency - genome crossover was used an evolutionary mechanism, this is the frequency with which it occurs
 * mutationFrequency - the probability of a mutation on each dna bit (with 100 gSize and .05 pSize, 5 mutations will occur on average)
 * bSize - the percentage of the population that survives each round
 * function - the f(x) that the algorithm attempts to solve (examples: fib(x), x^2, of 2x + 5)
 * sequence - alternative to mathematical function, this allows solving a sequence regression problem
 * Seg Copy - turn on and off the use of the Segment Copy instruction in genomes
 * Transpose - Turn on and off the use of transpose operators in genomes
 * Indexed Memory - Turn on and off the use of indexed memory by "organisms"
 * Reverse x - reverse the order of inputs to f(x) (example: f(functionSize), then f(functionSize - 1)...etc))
 * Seed	- The seed provided to random for the algorithm to use, this allows recreation of runs
 * Elitism | Aging | Death - three different evolutionary strategies permitted

The organism view allows one to trace program execution operand by operand by pasting a genome into the input text field. You can see the values that the genetic algorithm puts on the stack, as well as indexed memory if used. This was what allowed us to examine impirically how ontogenetic operators were or were not being used in program execution.

The result of this reseach was a paper submitted to the EMCSR 2012 conference. Although the paper was accepted, due to scheduling conflicts, neither my Professor nor myself were able to attend the conference. 

The paper can be read from my website [here](http://http://williambarksdale.com/ontogenetic_programs.pdf).

Spector, L., and K. Stoffel. (1996a). Automatic Generation of Adaptive Programs. In P. Maes, M. Mataric, J.-A. Meyer, J. Pollack & S.W. Wilson (Eds.), From Animals to Animats 4: Proceedings of the Fourth International Conference on Simulation of Adaptive Behavior (pp. 476-483). Cambridge, MA: The MIT Press.