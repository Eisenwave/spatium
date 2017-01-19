package net.grian.spatium.voxel;

import net.grian.spatium.geo.BlockSelection;
import net.grian.spatium.util.ColorMath;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoxelArrayTest {

    @Test
    public void getBoundaries() throws Exception {
        VoxelArray array = new VoxelArray(3, 5, 7);
        BlockSelection bounds = array.getBoundaries();

        assertEquals(BlockSelection.fromPoints(0, 0, 0, 2, 4, 6), bounds);
    }

    @Test
    public void size() throws Exception {
        VoxelArray array = new VoxelArray(3, 5, 7);
        array.fill(ColorMath.SOLID_RED);

        Assert.assertEquals(3*5*7, array.size());
    }

}