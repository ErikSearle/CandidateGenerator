package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Candidate Generator");
        TreeMap<int[], Integer> candidates = parseFile(args[0]);
        TreeMap<int[], Integer> newCandidates = CandidateGenerator.generate(candidates);
        for(int[] candidate: newCandidates.keySet()){
            for(int i: candidate) System.out.print(i + " ");
            System.out.print("\n");
        }
    }

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
