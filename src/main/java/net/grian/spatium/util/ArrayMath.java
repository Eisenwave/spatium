package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

public final class ArrayMath {
    
    private ArrayMath() {}
    
    @Contract(value = "_, _, _ -> !null", pure = true)
    @SuppressWarnings("unused")
    public static int[] indexToCoords2(int index, int x, int y) {
        return new int[] {
            index % x,
            index / x
        };
    }
    
    @SuppressWarnings("unused")
    public static int[] indexToCoords3(int index, int x, int y, int z) {
        return new int[] {
            index % x,
            index / x % y,
            index / (x * y)
        };
    }
    
    @SuppressWarnings("unused")
    public static int[] indexToCoords4(int index, int x, int y, int z, int x2) {
        return new int[] {
            index % x,
            index / x % y,
            index / (x * y) % z,
            index / (x * y * z)
        };
    }
    
    @SuppressWarnings("unused")
    public static int[] indexToCoords5(int index, int x, int y, int z, int x2, int y2) {
        return new int[] {
            index % x,
            index / x % y,
            index / (x * y) % z,
            index / (x * y * z) % x2,
            index / (x * y * z * x2)
        };
    }
    
    @SuppressWarnings("unused")
    public static int[] indexToCoords6(int index, int x, int y, int z, int x2, int y2, int z2) {
        return new int[] {
            index % x,
            index / x % y,
            index / (x * y) % z,
            index / (x * y * z) % x2,
            index / (x * y * z * x2) % y2,
            index / (x * y * z * x2 * y2)
        };
    }
    
    public static int[] indexToCoords(int index, int... sizes) {
        switch (sizes.length) {
        case 0: throw new IllegalArgumentException("empty array");
        case 1: return new int[] {sizes[0]};
        case 2: return indexToCoords2(index, sizes[0], sizes[1]);
        case 3: return indexToCoords3(index, sizes[0], sizes[1], sizes[2]);
        }
        
        int lastIndex = sizes.length-1;
        int[] coords = new int[sizes.length];
        
        coords[0] = index % sizes[0];
        coords[lastIndex] = index / product(sizes, 0, lastIndex-1);
        
        for (int i = 1; i<lastIndex; i++)
            coords[i] = index / product(sizes, 0, i-1) % sizes[i];
        
        return coords;
    }
    
    @SuppressWarnings("unused")
    public static int coordsToIndex2(int x, int y, int sizeX, int sizeY) {
        return
            x +
            y*sizeX;
    }
    
    @SuppressWarnings("unused")
    public static int coordsToIndex3(int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
        return 
            x +
            y*sizeX +
            z*sizeX*sizeY;
    }
    
    @SuppressWarnings("unused")
    public static int coordsToIndex4(int x, int y, int z, int x2, int sizeX, int sizeY, int sizeZ, int sizeX2) {
        return 
            x +
            y*sizeX +
            z*sizeX*sizeY +
            x2*sizeX*sizeY*sizeZ;
    }
    
    @SuppressWarnings("unused")
    public static int coordsToIndex5(int x, int y, int z, int x2, int y2, int sizeX, int sizeY, int sizeZ, int sizeX2, int sizeY2) {
        return 
            x +
            y*sizeX +
            z*sizeX*sizeY +
            x2*sizeX*sizeY*sizeZ +
            y2*sizeX*sizeY*sizeZ*sizeX2;
    }
    
    @SuppressWarnings("unused")
    public static int coordsToIndex6(int x, int y, int z, int x2, int y2, int z2, int sizeX, int sizeY, int sizeZ, int sizeX2, int sizeY2, int sizeZ2) {
        int factor = sizeX;

        return
            x +
            y*factor +
            z*(factor *= sizeY) +
            x2*(factor *= sizeZ) +
            y2*(factor *= sizeX2) +
            z2* factor * sizeY2;
    }
    
    public static int coordsToIndex(int[] coords, int[] sizes) {
        if (sizes.length != coords.length)
            throw new IllegalArgumentException("lengths don't match ("+coords.length+", "+sizes.length+")");
        
        switch (coords.length) {
            case 0: throw new IllegalArgumentException("empty array");
            case 1: return coords[0];
            case 2: return coordsToIndex2(coords[0], coords[1], sizes[0], sizes[1]);
            case 3: return coordsToIndex3(coords[0], coords[1], coords[2], sizes[0], sizes[1], sizes[2]);
        }
        
        int index = coords[0];
        for (int i = 1; i<coords.length; i++)
            index += coords[i] * product(sizes, 0, i);
        
        return index;
    }
    
    private static double product(double[] doubles, int start, int limit) {
        double product = doubles[start];
        for (int i = start+1; i<limit; i++)
            product *= doubles[i];
        
        return product;
    }

    private static int product(int[] ints, int start, int limit) {
        int product = ints[start];
        for (int i = start+1; i<limit; i++)
            product *= ints[i];

        return product;
    }

}
