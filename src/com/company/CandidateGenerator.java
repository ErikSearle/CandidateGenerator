package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CandidateGenerator {

    /**
     * A method for generating potential candidates for the Apriori data mining algorithm. This program will only return
     * candidates for which all of their potential size k subsets exist in l1
     * @param l1 Candidates of size k
     * @return Candidates of size k+1
     */
    public static TreeMap<int[], Integer> generate(TreeMap<int[], Integer> l1){
        TreeSet<int[]> potentialC2 = new TreeSet<>(new IntArrayComparatorItemWise());
        Object[] keyArray = l1.keySet().toArray();

        int pointer1 = 0;
        int pointer2 = 1;
        while(pointer1<keyArray.length-1){
            if(shareAllButLast(keyArray[pointer1], keyArray[pointer2])){
                potentialC2.add(buildPotentialCandidate(keyArray[pointer1], keyArray[pointer2]));
                pointer2++;
                if(pointer2 > keyArray.length-1){
                    pointer1++;
                    pointer2 = pointer1 + 1;
                }
            }
            else{
                pointer1++;
                pointer2 = pointer1 + 1;
            }
        }

        pruneCandidates(potentialC2, l1);

        TreeMap<int[], Integer> returnable = new TreeMap<>(new IntArrayComparatorItemWise());
        for(int[] can: potentialC2) returnable.put(can, 0);
        return returnable;
    }

    /**
     * A helper method that checks if two int arrays differ only by the last item
     * @param first an int array
     * @param second a second int array
     * @return boolean
     */
    private static boolean shareAllButLast(Object first, Object second){
        int[] castFirst = (int[]) first;
        int[] castSecond = (int[]) second;
        for(int i=0; i<castFirst.length-1; i++){
            if(castFirst[i] != castSecond[i]) return false;
        }
        return true;
    }

    /**
     * A helper method that takes two arrays and builds a third array which is larger than the first array by one, and
     * contains all elements cnotained in the first array plus teh last element of the second array
     * @param first an n sized int array
     * @param last an int array
     * @return an n+1 sized int array
     */
    private static int[] buildPotentialCandidate(Object first, Object last){
        int[] castFirst = (int[]) first;
        int[] castLast = (int[]) last;

        int[] returnable = new int[castFirst.length+1];
        for(int i=0; i<castFirst.length; i++){
            returnable[i] = castFirst[i];
        }
        returnable[castFirst.length] = castLast[castLast.length -1];
        return returnable;
    }

    /**
     * A helper method which checks that for each potential candidate of size k+1, all possible subsets of size k exist
     * in l1. If one or more of the subsets does not exist, the potential candidate is removed. nothing is returned as
     * the potCan set is modified directly.
     * @param potCan TreeSet of potential size k+1 candidates
     * @param l1 TreeMap of size k candidates
     */
    private static void pruneCandidates(TreeSet<int[]> potCan, TreeMap<int[], Integer> l1){
        if(potCan.isEmpty()) return;
        if(potCan.first().length == 2) return;
        ArrayList<int[]> cansToRemove = new ArrayList<>();
        for(int[] can: potCan){
            int[] subsetOfCan = Arrays.copyOfRange(can, 1, can.length);
            int excluded = can[0];
            for(int j=0; j<subsetOfCan.length-1; j++){
                if(!l1.containsKey(subsetOfCan)) {
                    cansToRemove.add(can);
                    break;
                }
                int temp = excluded;
                excluded = subsetOfCan[j];
                subsetOfCan[j] = temp;
            }
        }
        for(int[] can: cansToRemove) potCan.remove(can);
    }


    /**
     * A main method for testing the candidate generation program. This only exists as a method in teh class because the
     * requirements for the assignment were that it be all contained in a single file. Ideally, this would be in a
     * separate class file.
     * @param args Command line arguments. args[0] should contain the path the the sample file
     */
    public static void main(String[] args) {
        System.out.println("Candidate Generator");
        TreeMap<int[], Integer> candidates = parseFile(args[0]);
        TreeMap<int[], Integer> newCandidates = CandidateGenerator.generate(candidates);
        for(int[] candidate: newCandidates.keySet()){
            for(int i: candidate) System.out.print(i + " ");
            System.out.print("\n");
        }
    }

    /**
     * A helper method which parses the file into the correct data structure for candidate generation
     * @param inputFileName A string denoting the path to the correct file
     * @return A TreeMap<int[], Integer> containing all of the potential candidates of size k. The Integer in the map is
     * not used in candidate generation but is there to play nicely with the Apriori section of code
     */
    private static TreeMap<int[], Integer> parseFile(String inputFileName){
        Scanner reader = null;
        try{
            reader = new Scanner(new File(inputFileName));
        } catch(IOException e){
            System.out.println("Unable to open file");
            System.exit(1);
        }

        TreeMap<int[], Integer> candidatePool = new TreeMap<>(new IntArrayComparatorItemWise());
        while(reader.hasNextLine()){
            String candidateData = reader.nextLine();
            String[] splitData = candidateData.split(" ");
            int[] candidate = new int[splitData.length];
            for(int i=0; i<candidate.length; i++){
                candidate[i] = Integer.valueOf(splitData[i]);
            }
            candidatePool.put(candidate, 0);
        }
        return candidatePool;
    }
}
