package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnegative;
import java.util.ArrayList;
import java.util.List;

import static net.grian.spatium.util.PrimArrays.*;

public final class DiscreteMath {

    @Contract(pure = true)
    public static boolean divisible(int num, int divisor) {
        return (num % divisor) == 0;
    }

    public static int divisorCount(int num) {
        if (num == 0) return 2;
        if (num < 0) return divisorCount(-num);
        int count = 1, max = num / 2;
        for (int i = 1; i <= max; i++)
            if (num % i == 0) count++;
        return count;
    }
    
    public static int[] primeFactors(@Nonnegative int num) {
        if (num < 2) return new int[0];
        if (num == 2) return new int[] {2};
        List<Integer> result = new ArrayList<>();
        
        // Print the number of 2s that divide n
        while (num%2 == 0) {
            result.add(2);
            num /= 2;
        }
    
        //start at 3 and skip one element since num must be odd
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            while (num%i == 0) {
                result.add(i);
                num /= i;
            }
        }
        
        //unless the number is prime, it would have been divided down to 1 at this point
        //this check is necessary to add a prime number as its own factor
        if (num > 2)
            result.add(num);
        
        return PrimArrays.asInts(result);
    }
    
    public static void quicksort(int[] array) {
        if (array.length < 2) return;
        
        quicksort(array, 0, array.length-1);
    }
    
    public static void quicksort(int[] array, int min, int max) {
        if (min == max) return;
        if (min+1 == max) {
            if (array[max] < array[min]) swap(array, min, max);
            return;
        }
        
        final int
            pivot = (min+max)/2,
            x = array[pivot];
        
        int i = min, j = max;
        do {
            while (array[i] < x) i++;
            while (array[j] > x) j--;
            
            if (i < j)
                swap(array, i++, j--);
        } while (i < j);
        
        if (min < j) quicksort(array, min, j);
        if (max > i) quicksort(array, i, max);
    }
    
    /*
    my professor's shitcode
    void TabelleBauen()
    {
        int PatPos;
        
        int VglInd;
        
        Tabelle[0] = -1;
        VglInd = 0;
        
        for (PatPos = 1; PatPos <= PatternLength -1; PatPos = PatPos+1)
        {
            if (Pattern[PatPos] == Pattern[VglInd])
                Tabelle[PatPos] = Tabelle[VglInd];
            else
                Tabelle[PatPos] = VlgInd;
            
            while ((VglInd >= 0) && (Pattern[PatPos]) != Pattern[VglInd]))
                VlgInd = Tabelle[VglInd];
            
            VglInd = VglInd+1;
        }
    }
    */
    
    @Contract(pure = true)
    public static int[] makeKMPTable(char[] pattern) {
        if (pattern.length == 0) return new int[0];
        
        int[] table = new int[pattern.length];
        table[0] = -1;
        
        for (int i = 1, compare = 0; i < pattern.length; i++) {
            if (pattern[i] == pattern[compare]) {
                table[i] = table[compare];
            }
            else {
                table[i] = compare;
                compare = table[compare];
            }
            
            compare++;
        }
        
        return table;
    }

    private static void invert(int[] nums) {
        for (int i = 0; i<nums.length; i++)
            nums[i] = -nums[i];
    }

}
