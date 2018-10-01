package com.company;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class CandidateGenerator {

    public static LinkedHashSet<TreeSet<Integer>> generate(LinkedHashSet<TreeSet<Integer>> candidatePool){
        LinkedHashSet<TreeSet<Integer>> newCandidates = new LinkedHashSet<>();

        Object[] candidates = candidatePool.toArray();
        int sizeOfNewCandidates = ((TreeSet<Integer>) candidates[0]).size() + 1;
        int[] pointers = new int[sizeOfNewCandidates];
        for(int i=0; i<sizeOfNewCandidates; i++){
            pointers[i] = i;
        }

        do{
            HashSet<Integer> potentialCandidate = new HashSet<>();
            for(int i=0; i<sizeOfNewCandidates; i++){
                potentialCandidate.addAll((TreeSet<Integer>) candidates[pointers[i]]);
            }
            if(potentialCandidate.size() == sizeOfNewCandidates) newCandidates.add(new TreeSet<>(potentialCandidate));
        }while(iteratePointers(pointers, candidates.length-1));

        return newCandidates;
    }

    public static boolean iteratePointers(int[] pointers, int pointerMax){
        for(int i=pointers.length - 1; i>=0; i--){
            if(pointers[i] < pointerMax - (pointers.length - 1 - i)){
                pointers[i]++;
                for(int j=1; j+i < pointers.length; j++){
                    pointers[i+j] = pointers[i] + j;
                }
                return true;
            }
        }

        return false;
    }
}
