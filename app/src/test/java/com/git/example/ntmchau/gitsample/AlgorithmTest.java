package com.git.example.ntmchau.gitsample;

import org.junit.Test;

public class AlgorithmTest {

    @Test
    public void run() {
        String s = "bpoiexpqhmebhhu";
        System.out.println("results=" + lengthOfLongestSubstring(s));
    }

    private int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        if (s.length() == 1) return 1;

        int size = s.length();
        int result = 1;
        int startIndex = 0;
        int endIndex = -1;
        int repeatPos = 0;

        for (int i = 0; i < size; i++) {

            if (endIndex >= 0 && i > endIndex) {
                int newLongSize = endIndex - startIndex + 1;
                if (newLongSize > result) {
                    result = newLongSize;
                }
                startIndex = repeatPos + 1;
                endIndex = -1;
            }

            for (int k = startIndex+1; k<endIndex; k++) {
                
            }

            int j = i + 1;
            while (((endIndex == -1 && j < size) || (j < endIndex) )&& s.charAt(i) != s.charAt(j)) j++;

            if (j != size && j != endIndex) repeatPos = i;

            if (endIndex == -1 || j - 1 < endIndex) endIndex = j - 1;

            int newLongSize = endIndex - startIndex + 1;
            if (j == size && i == size - 1 && newLongSize > result) {
                result = newLongSize;
            }

        }

        return result;

    }

}
