package net.grian.spatium.util;

public final class ArrayMath {
	
	private ArrayMath() {}
	
	@SuppressWarnings("unused")
	public static int[] indexToCoords2D(int index, int x, int y) {
		return new int[] {
				index % x,
				index / x
		};
	}
	
	@SuppressWarnings("unused")
	public static int[] indexToCoords3D(int index, int x, int y, int z) {
		return new int[] {
				index % x,
				index / x % y,
				index / (x * y)
		};
	}
	
	@SuppressWarnings("unused")
	public static int[] indexToCoords4D(int index, int x, int y, int z, int x2) {
		return new int[] {
				index % x,
				index / x % y,
				index / (x * y) % z,
				index / (x * y * z)
		};
	}
	
	@SuppressWarnings("unused")
	public static int[] indexToCoords5D(int index, int x, int y, int z, int x2, int y2) {
		return new int[] {
				index % x,
				index / x % y,
				index / (x * y) % z,
				index / (x * y * z) % x2,
				index / (x * y * z * x2)
		};
	}
	
	@SuppressWarnings("unused")
	public static int[] indexToCoords6D(int index, int x, int y, int z, int x2, int y2, int z2) {
		return new int[] {
				index % x,
				index / x % y,
				index / (x * y) % z,
				index / (x * y * z) % x2,
				index / (x * y * z * x2) % y2,
				index / (x * y * z * x2 * y2)
		};
	}
	
	public static int[] indexToCoordsND(int index, int... sizes) {
		switch (sizes.length) {
		case 0: throw new IllegalArgumentException("empty array");
		case 1: return new int[] {sizes[0]};
		case 2: return indexToCoords2D(index, sizes[0], sizes[1]);
		case 3: return indexToCoords3D(index, sizes[0], sizes[1], sizes[2]);
		};
		
		int lastIndex = sizes.length-1;
		int[] coords = new int[sizes.length];
		
		coords[0] = index % sizes[0];
		coords[lastIndex] = index / PrimArrays.product(sizes, 0, lastIndex-1);
		
		for (int i = 1; i<lastIndex; i++)
			coords[i] = index / PrimArrays.product(sizes, 0, i-1) % sizes[i];
		
		return coords;
	}
	
	@SuppressWarnings("unused")
	public static int coordsToIndex2D(int x, int y, int sizeX, int sizeY) {
		return
				x +
				y*sizeX;
	}
	
	@SuppressWarnings("unused")
	public static int coordsToIndex3D(int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		return 
				x +
				y*sizeX +
				z*sizeX*sizeY;
	}
	
	@SuppressWarnings("unused")
	public static int coordsToIndex4D(int x, int y, int z, int x2, int sizeX, int sizeY, int sizeZ, int sizeX2) {
		return 
				x +
				y*sizeX +
				z*sizeX*sizeY +
				x2*sizeX*sizeY*sizeZ;
	}
	
	@SuppressWarnings("unused")
	public static int coordsToIndex5D(int x, int y, int z, int x2, int y2, int sizeX, int sizeY, int sizeZ, int sizeX2, int sizeY2) {
		return 
				x +
				y*sizeX +
				z*sizeX*sizeY +
				x2*sizeX*sizeY*sizeZ +
				y2*sizeX*sizeY*sizeZ*sizeX2;
	}
	
	@SuppressWarnings("unused")
	public static int coordsToIndex6D(int x, int y, int z, int x2, int y2, int z2, int sizeX, int sizeY, int sizeZ, int sizeX2, int sizeY2, int sizeZ2) {
		return 
				x +
				y*sizeX +
				z*sizeX*sizeY +
				x2*sizeX*sizeY*sizeZ +
				y2*sizeX*sizeY*sizeZ*sizeX2 +
				z2*sizeX*sizeY*sizeZ*sizeX2*sizeY2;
	}
	
	public static int coordsToIndexND(int[] coords, int[] sizes) {
		if (sizes.length != coords.length)
			throw new IllegalArgumentException("lengths don't match ("+coords.length+", "+sizes.length+")");
		
		switch (coords.length) {
		case 0: throw new IllegalArgumentException("empty array");
		case 1: return coords[0];
		case 2: return coordsToIndex2D(coords[0], coords[1], sizes[0], sizes[1]);
		case 3: return coordsToIndex3D(coords[0], coords[1], coords[2], sizes[0], sizes[1], sizes[2]);
		}
		
		int index = coords[0];
		for (int i = 1; i<coords.length; i++)
			index += coords[i] * PrimArrays.product(sizes, 0, i-1);
		
		return index;
	}
	
	

}