package net.grian.spatium.transform;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo3.AxisAlignedBB;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixDimensionsException;

import java.util.Objects;

/**
 * Provides methods for applying rotations, creating objects that represent rotations and more.
 */
@SuppressWarnings("WeakerAccess")
public final class Transformations {

    private Transformations() {}

    //TRANSFORM

    /**
     * Transforms a point using a transformation matrix.
     *
     * @param point the point to transform
     * @param m the transformation matrix
     * @throws MatrixDimensionsException if the matrix is not a 3x3 matrix
     */
    public static void transform(Vector3 point, Matrix m) {
        if (m.getRows() != 3 || m.getColumns() != 3)
            throw new MatrixDimensionsException("matrix must be a 3x3 matrix");

        final double x = point.getX(), y = point.getY(), z = point.getZ();
        point.set(
                m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z,
                m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z,
                m.get(2,0)*x + m.get(2,1)*z + m.get(2,2)*z);
    }
    
    /*
    public static Vector3 product(Matrix a, double x, double y, double z) {
        return Vector3.fromXYZ(
                a.get(0,0)*x + a.get(0,1)*y + a.get(0,2)*z,
                a.get(1,0)*x + a.get(1,1)*y + a.get(1,2)*z,
                a.get(2,0)*x + a.get(2,1)*z + a.get(2,2)*z);
    }
     */

    //TRANSLATE

    /**
     * Translates a point.
     *
     * @param point the point
     * @param x the x-amount of the translation
     * @param y the y-amount of the translation
     * @param z the z-amount of the translation
     */
    public static void translate(Vector3 point, double x, double y, double z) {
        point.add(x, y, z);
    }

    /**
     * Translates a bounding box.
     *
     * @param x the x-amount of the translation
     * @param y the y-amount of the translation
     * @param z the z-amount of the translation
     */
    public static void translate(AxisAlignedBB box, double x, double y, double z) {
        box.translate(x, y, z);
    }

    //SCALE

    /**
     * <p>
     *     Scales a point around an anchor instead of the origin.
     * </p>
     * The following special cases exist:
     * <ul>
     *     <li>scaling a point around itself will return a clone of the point
     *     <li>scaling a point with a factor of 0 will return the anchor
     *     <li>scaling a point with a factor of 1 will return a clone of the point
     *     <li>scaling a point with a negative factor is possible and will mirror the point around the anchor
     * </ul>
     *
     * @param point the point to scale
     * @param x the x-coordinate of the anchor
     * @param y the y-coordinate of the anchor
     * @param z the z-coordinate of the anchor
     * @param factor the scale factor
     */
    public static void scale(Vector3 point, double x, double y, double z, double factor) {
        point.set(
                x + (point.getX() - x) * factor,
                y + (point.getY() - y) * factor,
                z + (point.getZ() - z) * factor
        );
    }

    public static void scale(AxisAlignedBB box, double x, double y, double z, double factor) {
        box.translate(-box.getMinX(), -box.getMinY(), -box.getMinZ());
        box.scaleCentric(factor);
        box.translate(x, y, z);
    }

    /**
     * Scales a point around another scale anchor.
     *
     * @param point the point
     * @param anchor the scale anchor
     * @param factor the scale factor
     */
    public static void scale(Vector3 point, Vector3 anchor, double factor) {
        scale(point, anchor.getX(), anchor.getY(), anchor.getZ(), factor);
    }

    //ROTATE

    /**
     * Rotates a point around another anchor point on yaw.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor point
     * @param y the y-coordinate of the anchor point
     * @param z the z-coordinate of the anchor point
     * @param yaw the yaw in degrees
     */
    public static void rotateYaw(Vector3 point, double x, double y, double z, double yaw) {
        point
            .subtract(x, y, z)
            .setYaw(yaw)
            .add(x, y, z);
    }

    /**
     * Rotates a point around another anchor point on pitch.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor point
     * @param y the y-coordinate of the anchor point
     * @param z the z-coordinate of the anchor point
     * @param pitch the pitch in degrees
     */
    public static void rotatePitch(Vector3 point, double x, double y, double z, double pitch) {
        point
            .subtract(x, y, z)
            .setPitch(pitch)
            .add(x, y, z);
    }

    /**
     * Rotates a point around another anchor point on yaw and pitch.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor point
     * @param y the y-coordinate of the anchor point
     * @param z the z-coordinate of the anchor point
     * @param yaw the yaw in degrees
     * @param pitch the pitch in degrees
     */
    public static void rotateYawPitch(Vector3 point, double x, double y, double z, double yaw, double pitch) {
        point
            .subtract(x, y, z)
            .setYawPitch(yaw, pitch)
            .add(x, y, z);
    }

    /**
     * Rotates a point around another anchor point on yaw.
     *
     * @param point the point
     * @param anchor the anchor of the rotation
     * @param yaw the yaw in degrees
     */
    public static void rotateYaw(Vector3 point, Vector3 anchor, double yaw) {
        rotateYaw(point, anchor.getX(), anchor.getY(), anchor.getZ(), yaw);
    }

    /**
     * Rotates a point around another anchor point on pitch.
     *
     * @param point the point
     * @param anchor the anchor of the rotation
     * @param pitch the pitch in degrees
     */
    public static void rotatePitch(Vector3 point, Vector3 anchor, double pitch) {
        rotatePitch(point, anchor.getX(), anchor.getY(), anchor.getZ(), pitch);
    }

    /**
     * Rotates a point around another anchor point on yaw and pitch.
     *
     * @param point the point
     * @param anchor the anchor of the rotation
     * @param yaw the yaw in degrees
     * @param pitch the pitch in degrees
     */
    public static void rotateYawPitch(Vector3 point, Vector3 anchor, double yaw, double pitch) {
        rotateYawPitch(point, anchor.getX(), anchor.getY(), anchor.getZ(), yaw, pitch);
    }

    //MIRROR

    /**
     * Mirrors a point around an anchor point. This does nothing if the point is equal to the anchor point.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor
     * @param y the y-coordinate of the anchor
     * @param z the z-coordinate of the anchor
     */
    public static void mirror(Vector3 point, double x, double y, double z) {
        point.set(
                x - (point.getX() - x),
                y - (point.getY() - y),
                z - (point.getZ() - z)
        );
    }

    /**
     * Mirrors a point around an anchor point. This does nothing if the point is equal to the anchor point.
     *
     * @param point the point
     * @param anchor the anchor point
     */
    public static void mirror(Vector3 point, Vector3 anchor) {
        mirror(point, anchor.getX(), anchor.getY(), anchor.getZ());
    }

    /**
     * <p>
     *     Mirrors a point on an axis with a depth. For instance, in <code>R<sup>2</sup></code> a point {@code (2,2)}
     *     mirrored on the x-axis with depth {@code 1} would result in {@code (2,0)}.
     * </p>
     *
     * @param point the point
     * @param axis the axis of mirroring
     * @param depth the mirroring offset of the axis
     */
    public static void mirror(Vector3 point, Axis axis, double depth) {
        Objects.requireNonNull(point);
        Objects.requireNonNull(axis);
        switch (axis) {
            case X: mirror(point, depth, point.getY(), point.getZ()); break;
            case Y: mirror(point, point.getX(), depth, point.getZ()); break;
            case Z: mirror(point, point.getX(), point.getY(), depth); break;
        }
    }

    /**
     * Mirrors a point on an axis.
     *
     * @param point the point
     * @param axis the mirror axis
     */
    public static void mirror(Vector3 point, Axis axis) {
        Objects.requireNonNull(point);
        Objects.requireNonNull(axis);
        switch (axis) {
            case X: point.setX(-point.getX()); break;
            case Y: point.setY(-point.getY()); break;
            case Z: point.setZ(-point.getZ()); break;
        }
    }

    //AXIS ROTATE

    /**
     * <p>
     *     Rotates the point around a given axis with a given angle theta.
     * </p>
     * <p>
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector3#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static void rotate(Vector3 point, Vector3 axis, double theta) {
        rotate(point, Quaternion.fromRotation(axis, theta));
    }

    /**
     * Rotates the point using a rotation represented as a quaternion.
     *
     * @param point the point to rotate
     * @param q the rotation
     */
    @Deprecated
    public static void rotate(Vector3 point, Quaternion q) {
        point.set(Quaternion.product(q, point));
    }

    /**
     * <p>
     *     Rotates the point around a given axis with a given angle theta.
     * </p>
     * <p>
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector3#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    @Deprecated
    public static void rotate2(Vector3 point, Vector3 axis, double theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);

        double
                px = point.getX(), py = point.getY(), pz = point.getZ(),
                qx = q.getX(), qy = q.getY(), qz = q.getZ(), w = q.getW(),
                x2 = qx * 2,   y2 = qy * 2,   z2 = qz * 2,
                xx = qx * x2,  yy = qy * y2,  zz = qz * z2,
                xy = qx * y2,  xz = qx * z2,  yz = qy * z2,
                wx = w  * x2,  wy = w  * y2,  wz = w  * z2;

        point.set(
                px * (1 - (yy + zz)) + py * (xy - wz)       + pz * (xz + wy),
                px * (xy + wz)       + py * (1 - (xx + zz)) + pz * (yz - wx),
                px * (xz - wy)       + py * (yz + wx)       + pz * (1 - (xx + yy))
        );
    }

    /**
     * <p>
     *     Rotates the point around a given axis with a given angle theta.
     * </p>
     * <p>
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector3#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    @Deprecated
    public static void rotate3(Vector3 point, Vector3 axis, double theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);
        Vector3 qv = q.getVector();
        Vector3 t = qv.cross(point).multiply(2);
        
        point.set(t);
        point.multiply(q.getW());
        point.add(point);
        point.add(qv.cross(t));
    }

}
