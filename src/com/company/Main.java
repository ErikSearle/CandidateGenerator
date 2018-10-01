package com.company;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        LinkedHashSet<TreeSet<Integer>> candidates = parseFile("sample_candidates.txt");
        LinkedHashSet<TreeSet<Integer>> newCandidates = CandidateGenerator.generate(candidates);
        System.out.println("Hi mom");
    }

    private static LinkedHashSet<TreeSet<Integer>> parseFile(String inputFileName){
        Scanner reader = null;
        try{
            reader = new Scanner(new File(inputFileName));
        } catch(IOException e){
            System.out.println("Unable to open file");
            System.exit(1);
        }

        LinkedHashSet<TreeSet<Integer>> candidatePool = new LinkedHashSet<>();
        while(reader.hasNextLine()){
            String candidateData = reader.nextLine();
            TreeSet<Integer> candidate = new TreeSet<>();
            for(int i=0; i<candidateData.length(); i+=2){
                candidate.add(Integer.valueOf(String.valueOf(candidateData.charAt(i))));
            }
            candidatePool.add(candidate);
        }
        return candidatePool;
    }
}
