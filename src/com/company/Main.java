package com.company;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Candidate Generator");
        LinkedHashSet<TreeSet<Integer>> candidates = parseFile("/home/erik/workspace/CandidateGenerator/sample_candidates.txt");
        LinkedHashSet<TreeSet<Integer>> newCandidates = CandidateGenerator.generate(candidates);
        newCandidates.forEach(new Consumer<TreeSet<Integer>>() {
            @Override
            public void accept(TreeSet<Integer> integers) {
                StringBuilder sb = new StringBuilder();
                for(Integer integer : integers){
                    sb.append(integer).append(" ");
                }
                System.out.println(sb.toString());
            }
        });
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
            String[] splitData = candidateData.split(" ");
            for(String datum : splitData){
                candidate.add(Integer.valueOf(datum));
            }
            candidatePool.add(candidate);
        }
        return candidatePool;
    }
}
