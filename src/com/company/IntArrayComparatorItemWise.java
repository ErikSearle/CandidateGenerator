package com.company;

import java.util.Comparator;


public class IntArrayComparatorItemWise implements Comparator<int[]> {

    public IntArrayComparatorItemWise(){}

    /**
     * A comparator that compares two int arrays by the items inside. If one array of size k has items exactly equal to
     * the first k items in an array of size >k, the larger array is determined to be greater.
     * @param ints
     * @param t1
     * @return
     */
    @Override
    public int compare(int[] ints, int[] t1) {
        for(int i=0; i<ints.length && i<t1.length; i++){
            if(ints[i] > t1[i]) return 1;
            else if(ints[i] < t1[i]) return -1;
        }
        if(t1.length > ints.length) return -1;
        else if(ints.length > t1.length) return 1;
        else return 0;
    }
}
