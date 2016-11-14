package net.grian.spatium.transform;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo.AxisAlignedBB;
import net.grian.spatium.geo.Vector;

import java.util.Objects;

/**
 * Provides methods for applying rotations, creating objects that represent rotations and more.
 */
@SuppressWarnings("WeakerAccess")
public final class Transformations {

    private Transformations() {}

    /**
     * Translates a point.
     *
     * @param point the point
     * @param x the x-amount of the translation
     * @param y the y-amount of the translation
     * @param z the z-amount of the translation
     */
    public static void translate(Vector point, float x, float y, float z) {
        point.add(x, y, z);
    }

    /**
     * Translates a bounding box.
     *
     * @param x the x-amount of the translation
     * @param y the y-amount of the translation
     * @param z the z-amount of the translation
     */
    public static void translate(AxisAlignedBB box, float x, float y, float z) {
        box.move(x, y, z);
    }

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
    public static void scale(Vector point, float x, float y, float z, float factor) {
        point.set(
                x + (point.getX() - x) * factor,
                y + (point.getY() - y) * factor,
                z + (point.getZ() - z) * factor
        );
    }

    public static void scale(AxisAlignedBB box, float x, float y, float z, float factor) {
        box
                .move(-box.getMinX(), -box.getMinY(), -box.getMinZ())
                .scale(factor)
                .move(x, y, z);
    }

    /**
     * Scales a point around another scale anchor.
     *
     * @param point the point
     * @param anchor the scale anchor
     * @param factor the scale factor
     */
    public static void scale(Vector point, Vector anchor, float factor) {
        scale(point, anchor.getX(), anchor.getY(), anchor.getZ(), factor);
    }

    /**
     * Rotates a point around another anchor point on yaw.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor point
     * @param y the y-coordinate of the anchor point
     * @param z the z-coordinate of the anchor point
     * @param yaw the yaw in degrees
     */
    public static void rotateYaw(Vector point, float x, float y, float z, float yaw) {
        point.subtract(x, y, z).setYaw(yaw).add(x, y, z);
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
    public static void rotatePitch(Vector point, float x, float y, float z, float pitch) {
        point.subtract(x, y, z).setPitch(pitch).add(x, y, z);
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
    public static void rotateYawPitch(Vector point, float x, float y, float z, float yaw, float pitch) {
        point.subtract(x, y, z).setYawPitch(yaw, pitch).add(x, y, z);
    }

    /**
     * Rotates a point around another anchor point on yaw.
     *
     * @param point the point
     * @param anchor the anchor of the rotation
     * @param yaw the yaw in degrees
     */
    public static void rotateYaw(Vector point, Vector anchor, float yaw) {
        rotateYaw(point, anchor.getX(), anchor.getY(), anchor.getZ(), yaw);
    }

    /**
     * Rotates a point around another anchor point on pitch.
     *
     * @param point the point
     * @param anchor the anchor of the rotation
     * @param pitch the pitch in degrees
     */
    public static void rotatePitch(Vector point, Vector anchor, float pitch) {
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
    public static void rotateYawPitch(Vector point, Vector anchor, float yaw, float pitch) {
        rotateYawPitch(point, anchor.getX(), anchor.getY(), anchor.getZ(), yaw, pitch);
    }

    /**
     * Mirrors a point around an anchor point. This does nothing if the point is equal to the anchor point.
     *
     * @param point the point
     * @param x the x-coordinate of the anchor
     * @param y the y-coordinate of the anchor
     * @param z the z-coordinate of the anchor
     */
    public static void mirror(Vector point, float x, float y, float z) {
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
    public static void mirror(Vector point, Vector anchor) {
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
    public static void mirror(Vector point, Axis axis, float depth) {
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
    public static void mirror(Vector point, Axis axis) {
        Objects.requireNonNull(point);
        Objects.requireNonNull(axis);
        switch (axis) {
            case X: point.setX(-point.getX()); break;
            case Y: point.setY(-point.getY()); break;
            case Z: point.setZ(-point.getZ()); break;
        }
    }


    /**
     * <p>
     *     Rotates the point around a given axis with a given angle theta.
     * </p>
     * <p>
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    public static void rotate(Vector point, Vector axis, float theta) {
        rotate(point, Quaternion.fromRotation(axis, theta));
    }

    /**
     * Rotates the point using a rotation represented as a quaternion.
     *
     * @param point the point to rotate
     * @param q the rotation
     */
    public static void rotate(Vector point, Quaternion q) {
        point.set(
                Quaternion
                 .product(q, Quaternion.fromVector(point))
                 .multiply(Quaternion.inverse(q))
                 .getVector()
        );
    }

    /**
     * <p>
     *     Rotates the point around a given axis with a given angle theta.
     * </p>
     * <p>
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    public static void rotate2(Vector point, Vector axis, float theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);

        float
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
     *     Note that the provided axis has to be a "unit vector" or "normalized vector". See {@link Vector#normalize()}
     *     for reference.
     * </p>
     *
     * @param point the point to rotate
     * @param axis the axis around which the point is to be rotated (unit vector)
     * @param theta the angle of the rotation in radians
     */
    public static void rotate3(Vector point, Vector axis, float theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);
        Vector qv = q.getVector();
        Vector t = qv.cross(point).multiply(2);
        point.set(
                t.clone()
                        .multiply(q.getW())
                        .add(point)
                        .add(qv.cross(t))
        );
    }

}
