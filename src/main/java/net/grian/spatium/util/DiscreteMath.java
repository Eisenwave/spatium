package net.grian.spatium.util;

public final class DiscreteMath {

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

    private static void invert(int[] nums) {
        for (int i = 0; i<nums.length; i++)
            nums[i] = -nums[i];
    }

}
