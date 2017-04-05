package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.matrix.Matrices;
import net.grian.spatium.matrix.Matrix;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrientedBBTest {

    @Test
    public void preserveSlabThickness() throws Exception {
        OrientedBB box = OrientedBB.fromAABB(AxisAlignedBB.fromPoints(-1, -1, 1, 1, 1, 3));
        {
            Slab3 slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
        box.rotateY(Spatium.radians(45));
        {
            Slab3 slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
    }
    
    /**
     * Test a few simple assertions on an OBB without any transformation.
     */
    @Test
    public void get_simpleOBB() throws Exception {
        OrientedBB box = OrientedBB.fromAABB(AxisAlignedBB.fromPoints(-1, -1, -1, 0, 1, 2));
        assertEquals(1, box.getSizeX(), Spatium.EPSILON);
        assertEquals(2, box.getSizeY(), Spatium.EPSILON);
        assertEquals(3, box.getSizeZ(), Spatium.EPSILON);
        assertEquals(6, box.getVolume(), Spatium.EPSILON);
        assertEquals(Axis.X.vector(), box.getAxisX());
        assertEquals(Axis.Y.vector(), box.getAxisY());
        assertEquals(Axis.Z.vector(), box.getAxisZ());
        assertEquals(Vector3.fromXYZ(-0.5, 0, 0.5), box.getCenter());
        assertEquals(AxisAlignedBB.fromPoints(-1, -1, -1, 0, 1, 2), box.getBoundaries());
    }
    
    private final static Matrix ROT_Y_45 = Matrix.fromRotY(Spatium.radians(45));
    
    /**
     * Test a few simple assertions on an OBB without any transformation.
     */
    @Test
    public void get_rotatedOBB() throws Exception {
        AxisAlignedBB aabb = AxisAlignedBB.fromPoints(-1, -1, -1, 1, 1, 1);
        OrientedBB box = OrientedBB.fromAABB(aabb);
        
        assertEquals(Vector3.fromXYZ(0, 0, 0), box.getCenter());
        assertEquals(8, box.getVolume(), Spatium.EPSILON);
        assertEquals(aabb, box.getBoundaries());
        
        box.transform(ROT_Y_45);
        assertEquals(ROT_Y_45, box.getTransform());
        
        Vector3 expX = Vector3.fromXYZ( CacheMath.HALF_SQRT_2, 0, CacheMath.HALF_SQRT_2);
        Vector3 expY = Axis.Y.vector();
        Vector3 expZ = Vector3.fromXYZ(-CacheMath.HALF_SQRT_2, 0, CacheMath.HALF_SQRT_2);
        Vector3 expMin = Vector3.fromXYZ(0, -1, -CacheMath.SQRT_2);
        Vector3 expMax = Vector3.fromXYZ(0,  1,  CacheMath.SQRT_2);
        AxisAlignedBB expBounds = AxisAlignedBB.fromCenterDims(0,0,0, CacheMath.SQRT_2, 1, CacheMath.SQRT_2);
    
        /*System.out.println(ROT_Y_45.toString());
        System.out.println(box.getAxisX());
        System.out.println(Matrices.product(ROT_Y_45, 1, 0, 0));
        System.out.println(Matrices.product(ROT_Y_45, 1, 1, 1));
        System.out.println(Matrices.product(ROT_Y_45, 1, 1, 1));*/
        assertEquals(Vector3.fromXYZ(0, 0, 0), box.getCenter());
        assertEquals(8, box.getVolume(), Spatium.EPSILON);
        assertEquals(expX, box.getAxisX());
        assertEquals(expY, box.getAxisY());
        assertEquals(expZ, box.getAxisZ());
        assertEquals(expMin, box.getMin());
        assertEquals(expMax, box.getMax());
        assertEquals(expBounds, box.getBoundaries());
    }
    
    @Test
    public void getBoundaries_unaffectedBy90DegRot() throws Exception {
        AxisAlignedBB aabb = AxisAlignedBB.fromPoints(-1, -1, -1, 1, 1, 1);
        OrientedBB box = OrientedBB.fromAABB(aabb);
        box.rotateY(Spatium.radians(90));
        assertEquals(aabb, box.getBoundaries());
    }

}