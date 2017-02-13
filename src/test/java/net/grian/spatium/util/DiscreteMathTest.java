package net.grian.spatium.util;

import net.grian.spatium.Spatium;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class DiscreteMathTest {
    
    @Test
    public void entropy() throws Exception {
        double entropy = DiscreteMath.entropy(.65, .11, .05, .05, .04, .04, .04, .02);
        assertEquals(entropy, 1.8565868237673904, Spatium.EPSILON);
    }
    
    @Test
    public void primeFactors() throws Exception {
        for (int i = 2; i<100_000; i++) {
            int[] factors = DiscreteMath.primeFactors(i);
            assertEquals(i, product(factors));
        }
    }
    
    @Contract(pure = true)
    private static int product(int... numbers) {
        int result = numbers[0];
        for (int i = 1; i<numbers.length; i++)
            result *= numbers[i];
        return result;
    }
    
    @Test
    public void quicksort() throws Exception {
        final int length = 100_000;
        Random random = new Random();
        
        int[] nums = new int[length];
        for (int i = 0; i<length; i++)
            nums[i] = random.nextInt(length);
        
        DiscreteMath.quicksort(nums);
    
        //System.out.println(Arrays.toString(nums));
        for (int i = 1; i<length; i++) {
            int j = i-1;
            if (nums[j] > nums[i])
                throw new AssertionError(j+", "+i+":"+nums[j]+" > "+nums[i]);
        }
    }
    
    @Test
    public void makeKMPTable() throws Exception {
        char[] pattern = {'G', 'E', 'G', 'E', 'B', 'E', 'N'};
        int[] table = DiscreteMath.makeKMPTable(pattern);
    
        char[] pattern2 = "aabaaacaab".toCharArray();
        int[] table2 = DiscreteMath.makeKMPTable(pattern2);
    
        char[] pattern3 = "aaaaaaaaaa".toCharArray();
        int[] table3 = DiscreteMath.makeKMPTable(pattern3);
    
        char[] pattern4 = "aabaaabb".toCharArray();
        int[] table4 = DiscreteMath.makeKMPTable(pattern4);
        
        System.out.println(Arrays.toString(table));
        System.out.println(Arrays.toString(table2));
        System.out.println(Arrays.toString(table3));
        System.out.println(Arrays.toString(table4));
    }
    
}