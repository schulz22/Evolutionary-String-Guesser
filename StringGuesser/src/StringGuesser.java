import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class StringGuesser {
  
  public static final int GEN_SIZE = 2000;
  
  public static final double MUT_CHANCE = .15;
  
  public static final int FITTEST_PASS_ON = 1400;
  
  public static final int NUM_FITTEST = 10;
  
  public static int generations = 0;
  
  static String answer;
  
  static Random rand = new Random();
  
  static StringBuilder sb = new StringBuilder("");
  
  public static String alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  
  public StringGuesser(String ans) {
    answer = ans;
    generations = 0;
  }
  
  public static int howFit(String guess, String answer) {
    String theGuess = guess;
    //System.out.println(theGuess);
    int fitness = 0;
    
//    if(guess.length() != answer.length()) {
//      return fitness;
//    }
    
    for (int i = 0; i < theGuess.length(); i++) {
      //System.out.println(i);
      if (theGuess.charAt(i) == answer.charAt(i)) {
        fitness++;
      }
    }
    
    
    return fitness;
  }
  
  public static String[] generateFirstGen(int length, int wordLength) {
    String[] firstGen = new String[length];
    //String individual;
    for (int i = 0; i < length; i++) {
      firstGen[i] = "";
      for (int j = 0; j < wordLength; j++) {
        firstGen[i] += alphabet.charAt(rand.nextInt(alphabet.length()));
      }
      //firstGen[i] = individual;
      
      
      
    }
    generations = 1;
    
    return firstGen;
  }
  
  public static String[] sortByFitness(String[] gen) {
    ArrayList<Integer> usedInds = new ArrayList<Integer>();
    int[] fitnesses = new int[gen.length];
    
    for (int i = 0; i < gen.length; i++) {
      if (gen[i] == null) {
        gen[i] = "";
        
      }
      fitnesses[i] = howFit(gen[i], StringGuesser.answer);
    }
    
    String[] sorted = new String[gen.length];
    int max = 0;
    int topInd = 0;
    while (usedInds.size() < gen.length) {
      max = 0;
      topInd = 0;
      for (int i = 0; i < gen.length; i++) {
        //System.out.println(usedInds.size());

        if (fitnesses[i] >= max && usedInds.indexOf(i) == -1) {
          max = fitnesses[i];
          topInd = i;      
                   
        }
        
        
              
      }
      usedInds.add(topInd);
      sorted[usedInds.size() - 1] = gen[topInd];
    }
    
    System.out.println("Generation's fittest: " + sorted[0] + " Fitness: " + (howFit(sorted[0], answer) * 100 / answer.length()) + "%");

    return sorted;
  }
  
  public static boolean checkIfDone(String[] gen, String answer) {
    if(howFit(gen[0], answer) == answer.length()) {
      return true;
    }else {
      return false;
    }
  }
  
  
  public static String[] generateNextGen(String[] prevGen) {
    String[] nextGen = new String[prevGen.length];
    String fittest = prevGen[0];
    
    
    
    //int inheritFrom = 0;
    nextGen[0] = prevGen[0];
    //System.out.println("Generation " + generations + "'s fittest: " + fittest);
    for (int i = 1; i < FITTEST_PASS_ON; i++) {
      for (int j = 0; j < answer.length(); j++) {
        if (rand.nextDouble() <= MUT_CHANCE) {
          //sb.append(fittest);
          //inheritFrom = rand.nextInt(NUM_FITTEST);
          //nextGen[i] = fittest.replace(fittest.charAt(j), alphabet.charAt(rand.nextInt(alphabet.length())));
          
          nextGen[i] = fittest.substring(0, j) + alphabet.charAt(rand.nextInt(alphabet.length())) + fittest.substring(j + 1, fittest.length());

          
          //nextGen[i] = sb.setCharAt(j, alphabet.charAt(rand.nextInt(alphabet.length())));
        } 
        
        
      }
      
    }
    
    
    
    for (int i = (int)FITTEST_PASS_ON; i < prevGen.length; i++) {
      nextGen[i] = "";
      for (int j = 0; j < answer.length(); j++) {
        nextGen[i] += alphabet.charAt(rand.nextInt(alphabet.length()));
      }
    }
    
    
    
    
    //generations++;
    return nextGen;
  }
  
  public static String[] generateNextGenAlt(String[] prevGen) {
    String[] nextGen = new String[prevGen.length];
    String fittest = prevGen[0];
    
    
    
    //int inheritFrom = 0;
    nextGen[0] = prevGen[0];
    //System.out.println("Generation " + generations + "'s fittest: " + fittest);
    for (int i = 1; i < FITTEST_PASS_ON; i++) {
      innerloop:
      for (int j = 0; j < answer.length(); j++) {
        if (rand.nextDouble() <= MUT_CHANCE) {
          //sb.append(fittest);
          //inheritFrom = rand.nextInt(NUM_FITTEST);
          //nextGen[i] = fittest.replace(fittest.charAt(j), alphabet.charAt(rand.nextInt(alphabet.length())));
          
          nextGen[i] = fittest.substring(0, j) + alphabet.charAt(rand.nextInt(alphabet.length())) + fittest.substring(j + 1, fittest.length());
          break innerloop;
          
          //nextGen[i] = sb.setCharAt(j, alphabet.charAt(rand.nextInt(alphabet.length())));
        } 
        
        
      }
      
    }

    
    
    for (int i = (int)FITTEST_PASS_ON; i < prevGen.length; i++) {
      nextGen[i] = "";
      for (int j = 0; j < answer.length(); j++) {
        nextGen[i] += alphabet.charAt(rand.nextInt(alphabet.length()));
      }
    }
    
    
    
    
    generations++;
    return nextGen;
  }

  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    
    
    System.out.print("Enter String to be guessed (letters and numbers): ");
    
    
    String theAnswer = scnr.nextLine();
    //int answerSize = theAnswer.length();
    System.out.println();
    
    StringGuesser.answer = theAnswer;
    
        
    String[] generation = generateFirstGen(GEN_SIZE, answer.length());
    String[] generationNorm = generation;
    String[] generationAlt = generation;
    
    int higherGen = 0;
    int altGens = 1;
    int normGens = 1;
    //System.out.println(generation.toString());
    while (!checkIfDone(generationAlt, answer) || !checkIfDone(generationNorm, answer)) {
//      for(int i = 0; i < generation.length; i++) {
//        System.out.println(generation[i]);
//      }
      
      if (altGens >= normGens) {
        higherGen = altGens;
      }else {
        higherGen = normGens;
      }
      
      System.out.println("Generation: " + higherGen);
      
      if (!checkIfDone(generationNorm, answer)){
        System.out.print("Normal: ");
        generationNorm = sortByFitness(generationNorm);
      }else {
        System.out.println("Normal: Finished at generation " + normGens);
      }
      
      if (!checkIfDone(generationAlt, answer)) {
        System.out.print("Alt:    ");
        generationAlt = sortByFitness(generationAlt);
      }else {
        System.out.println("Alt:    Finished at generation " + altGens);
      }
      
      if (!checkIfDone(generationNorm, answer)){
        generationNorm = generateNextGen(generationNorm);
        normGens++;

      }
      if (!checkIfDone(generationAlt, answer)) {
        generationAlt = generateNextGenAlt(generationAlt);
        altGens++;
      }
        
      
    }
    
    System.out.println("Answer: " + generationNorm[0]);
    System.out.println("Achieved by original in " + (normGens) + " generations.");
    System.out.println("Achieved by alternative in " + (altGens) + " generations.");

    
  }

}
