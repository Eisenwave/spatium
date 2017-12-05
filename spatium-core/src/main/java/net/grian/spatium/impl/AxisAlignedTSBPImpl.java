package net.grian.spatium.impl;

import eisenwave.spatium.util.Spatium;
import eisenwave.spatium.enums.Direction;
import eisenwave.spatium.enums.Direction.AxisDirection;
import net.grian.spatium.geo3.AxisAlignedTSBP;
import net.grian.spatium.geo3.Ray3;

public class AxisAlignedTSBPImpl implements AxisAlignedTSBP {

    private static final long serialVersionUID = -7846851080479895321L;

    /** The axis on which the plane is located. This value may not be changed. */
    private Direction dir;

    /** The offset from the origin on the axis of the plane. */
    private double wmin, hmin, wmax, hmax, d;

    /**
     * Constructs a new 3D rectangle using the direction the rectangle is
     * facing, its depth and the 2D coordinates of the underlying 2D rectangle.
     * Depending on the specified direction, the meaning of the parameters
     * changes.
     *
     * <br><br>Generally, the input coordinates are the two following 3D
     * coordinates of the direction's axis. For instance, of the direction is
     * Y, the two following coordinates are Z and X. So the input X and Y 2D
     * coordinates should be Z and X 3D coordinates.
     *
     *
     * @param dir the direction the rectangle is facing
     * @param d the depth of the rectangle, aka. its coordinate on the axis
     * of the direction
     */
    public AxisAlignedTSBPImpl(Direction dir, double wa, double ha, double wb, double hb, double d) {
        this.wmin = Math.min(wa, wb);
        this.hmin = Math.min(ha, hb);
        this.wmax = Math.max(wa, wb);
        this.hmax = Math.max(ha, hb);
        this.dir = dir;
    }

    public AxisAlignedTSBPImpl(AxisAlignedTSBPImpl aabp) {
        this.dir = aabp.dir;
        this.wmin = aabp.wmin;
        this.hmin = aabp.hmin;
        this.wmax = aabp.wmax;
        this.hmax = aabp.hmax;
        this.d = aabp.d;
    }

    // GETTERS

    @Override
    public double getMinX() {
        switch (dir) {
        case POSITIVE_X: return -d;
        case NEGATIVE_X: return  d;

        case POSITIVE_Y:
        case NEGATIVE_Y: return hmin;

        case POSITIVE_Z:
        case NEGATIVE_Z: return wmax;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMinY() {
        switch (dir) {
        case POSITIVE_X:
        case NEGATIVE_X: return hmin;

        case POSITIVE_Y: return -d;
        case NEGATIVE_Y: return  d;

        case POSITIVE_Z:
        case NEGATIVE_Z: return hmin;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMinZ() {
        switch (dir) {
        case POSITIVE_X:
        case NEGATIVE_X: return wmin;

        case POSITIVE_Y:
        case NEGATIVE_Y: return wmin;

        case POSITIVE_Z: return -d;
        case NEGATIVE_Z: return  d;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMaxX() {
        switch (dir) {
        case POSITIVE_X: return  d;
        case NEGATIVE_X: return -d;

        case POSITIVE_Y:
        case NEGATIVE_Y: return hmax;

        case POSITIVE_Z:
        case NEGATIVE_Z: return wmin;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMaxY() {
        switch (dir) {
        case POSITIVE_X:
        case NEGATIVE_X: return hmax;

        case POSITIVE_Y: return  d;
        case NEGATIVE_Y: return -d;

        case POSITIVE_Z:
        case NEGATIVE_Z: return hmax;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMaxZ() {
        switch (dir) {
        case POSITIVE_X:
        case NEGATIVE_X: return wmax;

        case POSITIVE_Y:
        case NEGATIVE_Y: return wmax;

        case POSITIVE_Z: return  d;
        case NEGATIVE_Z: return -d;

        default: return Double.NaN;
        }
    }

    @Override
    public double getMinWidth() {
        return wmin;
    }

    @Override
    public double getMinHeight() {
        return hmin;
    }

    @Override
    public double getMaxWidth() {
        return wmax;
    }

    @Override
    public double getMaxHeight() {
        return hmax;
    }

    @Override
    public double getWidth() {
        return wmax - wmin;
    }

    @Override
    public double getHeight() {
        return hmax - hmin;
    }

    @Override
    public double getDepth() {
        return this.d;
    }

    /**
     * Returns the direction the rectangle is facing. This is the direction the
     * rectangle's normal vector is pointing towards.
     *
     * <br><br>To illustrate this, a cube in an XYZ coordinate system in which
     * positive Z means upwards has a face on its top which's direction equals
     * {@link Direction#POSITIVE_Z}.
     */
    public Direction getDirection() {
        return dir;
    }

    // CHECKERS

    @Override
    public boolean contains(double x, double y, double z) {
        switch (getAxis()) {
        case X: return
                Spatium.equals(d, x) &&
                z >= wmin &&
                z <= wmax &&
                y >= hmin &&
                y <= hmax;


        case Y: return
                Spatium.equals(d, y) &&
                x >= wmin &&
                x <= wmax &&
                z >= hmin &&
                z <= hmax;

        case Z: return
                Spatium.equals(d, z) &&
                x >= wmin &&
                x <= wmax &&
                y >= hmin &&
                y <= hmax;

        default:
            return false;
        }
    }

    /**
     * Checks whether in case of a collision, a ray would penetrate the
     * rectangle on its outside or inside.
     *
     * <br><br>For instance, if the rectangle is facing towards positive X and
     * the ray's directional vector's X coordinate is negative, true is being
     * returned.
     *
     * @param ray the ray
     * @return True, the ray penetrates the rectangle on its outside / inwards
     */
    public boolean mayCollideInwards(Ray3 ray) {
        switch (dir) {
        case POSITIVE_X:
            return ray.getDirection().getX() < 0;

        case NEGATIVE_X:
            return ray.getDirection().getX() > 0;

        case POSITIVE_Y:
            return ray.getDirection().getY() < 0;

        case NEGATIVE_Y:
            return ray.getDirection().getY() > 0;

        case POSITIVE_Z:
            return ray.getDirection().getZ() < 0;

        case NEGATIVE_Z:
            return ray.getDirection().getZ() > 0;

        default: return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AxisAlignedTSBP && equals((AxisAlignedTSBP) obj);
    }

    // SETTERS

    @Override
    public AxisAlignedTSBP translate(double x, double y, double z) {
        boolean positive = dir.direction() == AxisDirection.POSITIVE;

        switch (getAxis()) {
        case X:
            this.d += positive? x : -x;
            this.wmin += z;
            this.wmax += z;
            this.hmin += y;
            this.hmax += y;
            break;

        case Y:
            this.d += positive? y : -y;
            this.wmin += z;
            this.wmax += z;
            this.hmin += x;
            this.hmax += x;
            break;

        case Z:
            this.d += positive? z : -z;
            this.wmin += x;
            this.wmax += x;
            this.hmin += y;
            this.hmax += y;
            break;
        }

        return this;
    }

    @Override
    public AxisAlignedTSBP mirror() {
        this.dir = this.dir.opposite();
        return this;
    }

    @Override
    public AxisAlignedTSBP setDepth(double d) {
        this.d = d;
        return this;
    }

    // MISC

    @Override
    public AxisAlignedTSBPImpl clone() {
        return new AxisAlignedTSBPImpl(this);
    }

}
