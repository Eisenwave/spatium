package net.grian.spatium.voxel;

import net.grian.spatium.array.BooleanArray3;
import net.grian.spatium.geo.BlockVector;
import org.junit.Test;

import static org.junit.Assert.*;

public class ByteBitField3DTest {

    @Test
    public void enable() throws Exception {
        BooleanArray3 map = new BooleanArray3(10, 20, 30);
        BlockVector pos = BlockVector.fromXYZ(7, 13, 23);

        map.enable(pos);
        assertTrue(map.contains(pos));
        assertFalse(map.contains(pos.getX(), pos.getY()+1, pos.getZ()));
    }

    @Test
    public void disable() throws Exception {
        BooleanArray3 map = new BooleanArray3(10, 20, 30);
        BlockVector pos = BlockVector.fromXYZ(7, 13, 23);

        map.enable(pos);
        assertTrue(map.contains(pos));

        map.disable(pos);
        assertFalse(map.contains(pos));
    }

}