package net.grian.spatium.voxel;

import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.util.PrimMath;

import java.io.Serializable;

public class ByteBitField3D implements BitField3D, Serializable, Cloneable {

    private final byte[][][] content;

    private final int sizeX, sizeY, sizeZ;

    public ByteBitField3D(int x, int y, int z) {
        if (x == 0 || y == 0 || z == 0) throw new IllegalArgumentException("size 0 voxel array");
        final int lengthY = PrimMath.floorAsInt((float) y/Byte.SIZE);
        this.content = new byte[x][lengthY][z];
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
    }

    @SuppressWarnings("ManualArrayCopy")
    public ByteBitField3D(ByteBitField3D copyOf) {
        final int lengthY = copyOf.content[0].length;
        this.content = new byte[copyOf.content.length][lengthY][copyOf.content[0][0].length];
        this.sizeX = copyOf.getSizeX();
        this.sizeY = copyOf.getSizeY();
        this.sizeZ = copyOf.getSizeZ();

        for (int x = 0; x<sizeX; x++) for (int y = 0; y<lengthY; y++) for (int z = 0; z<sizeZ; z++)
            this.content[x][y][z] = copyOf.content[x][y][z];
    }

    public ByteBitField3D(BitField3D copyOf) {
        this(copyOf.getSizeX(), copyOf.getSizeY(), copyOf.getSizeZ());

        for (int x = 0; x<sizeX; x++) for (int y = 0; y<sizeY; y++) for (int z = 0; z<sizeZ; z++)
            if (copyOf.contains(x, y, z)) enable(x, y, z);
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public int getSizeZ() {
        return sizeZ;
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return (content[x][y/Byte.SIZE][z] >> (y%Byte.SIZE)) == 1;
    }

    public void enable(int x, int y, int z) {
        content[x][y/Byte.SIZE][z] |= (1 << (y%Byte.SIZE));
    }

    public void enable(BlockVector pos) {
        enable(pos.getX(), pos.getY(), pos.getZ());
    }

    public void disable(int x, int y, int z) {
        content[x][y/Byte.SIZE][z] &= ~(1 << (y%Byte.SIZE));
    }

    public void disable(BlockVector pos) {
        disable(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public String toString() {
        return ByteBitField3D.class.getSimpleName()+"{x="+getSizeX()+",y="+getSizeY()+",z="+getSizeZ()+"}";
    }

    @Override
    public ByteBitField3D clone() {
        return new ByteBitField3D(this);
    }

}
