package com.company;

import java.util.*;

public class CandidateGenerator {

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

//    public static LinkedHashSet<TreeSet<Integer>> generate(LinkedHashSet<TreeSet<Integer>> candidatePool){
//        LinkedHashSet<TreeSet<Integer>> newCandidates = new LinkedHashSet<>();
//
//        Object[] candidates = candidatePool.toArray();
//        if(candidates.length == 0){
//            return newCandidates;
//        }
//
//        int sizeOfNewCandidates = ((TreeSet<Integer>) candidates[0]).size() + 1;
//
//        System.out.println("Building candidates of size " + sizeOfNewCandidates);
//
//        /*
//        Check to see if a there are even enough candidates in the set to produce a new candidate. This also avoid a null
//        pointer exception
//         */
//        if(sizeOfNewCandidates > candidates.length){
//            return newCandidates;
//        }
//
//        int[] pointers = new int[sizeOfNewCandidates];
//        for(int i=0; i<sizeOfNewCandidates; i++){
//            pointers[i] = i;
//        }
//
//        do{
//            HashSet<Integer> potentialCandidate = new HashSet<>();
//            for(int i=0; i<sizeOfNewCandidates; i++){
//                potentialCandidate.addAll((TreeSet<Integer>) candidates[pointers[i]]);
//            }
//            if(potentialCandidate.size() == sizeOfNewCandidates) newCandidates.add(new TreeSet<>(potentialCandidate));
//        }while(iteratePointers(pointers, candidates.length-1));
//
//        return newCandidates;
//    }

//    public static boolean iteratePointers(int[] pointers, int pointerMax){
//        for(int i=pointers.length - 1; i>=0; i--){
//            if(pointers[i] < pointerMax - (pointers.length - 1 - i)){
//                pointers[i]++;
//                for(int j=1; j+i < pointers.length; j++){
//                    pointers[i+j] = pointers[i] + j;
//                }
//                return true;
//            }
//        }
//
//        return false;
//    }

    private static boolean shareAllButLast(Object first, Object second){
        int[] castFirst = (int[]) first;
        int[] castSecond = (int[]) second;
        for(int i=0; i<castFirst.length-1; i++){
            if(castFirst[i] != castSecond[i]) return false;
        }
        return true;
    }

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
}
