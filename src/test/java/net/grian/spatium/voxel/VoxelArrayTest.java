package net.grian.spatium.voxel;

import net.grian.spatium.geo3.BlockSelection;
import net.grian.spatium.util.ColorMath;
import net.grian.spatium.util.RuntimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

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
    
    /*
    @Test
    public void memoryUsage() throws Exception {
        int tests = 10000;
        
        long sizeA = RuntimeUtil.sizeOf(() -> new int[10][10], tests);
        System.out.println(sizeA+" bytes");
        
        long sizeB = RuntimeUtil.sizeOf(() -> new int[10*10], tests);
        System.out.println(sizeB+" bytes");
    }
    */

    @Test
    public void contains() throws Exception {
        VoxelArray array = new VoxelArray(32, 32, 32);
        Random random = new Random();
        array.forEachPosition(pos -> {
            if (random.nextBoolean())
                array.setRGB(pos, ColorMath.DEBUG1);
        });

        array.forEachPosition(pos -> {
            if (array.contains(pos.getX(), pos.getY(), pos.getZ())) {
                VoxelArray.Voxel voxel = array.getVoxel(pos);
                assertNotNull(voxel);
                assertTrue(voxel.getAlpha() != 0);
            }
        });
    }

}