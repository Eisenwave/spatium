package net.grian.spatium.transform;

import net.grian.spatium.geo.Vector;

/**
 * Provides methods for applying rotations, creating objects that represent rotations and more.
 */
public final class Transformations {

    private Transformations() {}

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
     * @return a new rotated point
     */
    public static Vector rotate(Vector point, Vector axis, float theta) {
        return rotate(point, Quaternion.fromRotation(axis, theta));
    }

    /**
     * Rotates the point using a rotation represented as a quaternion.
     *
     * @param point the point to rotate
     * @param q the rotation
     * @return a new rotated point
     */
    public static Vector rotate(Vector point, Quaternion q) {
        return Quaternion
                .product(q, Quaternion.fromVector(point))
                .multiply(Quaternion.inverse(q))
                .getVector();
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
     * @return a new rotated point
     */
    public static Vector rotate2(Vector point, Vector axis, float theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);

        float
                px = point.getX(), py = point.getY(), pz = point.getZ(),
                qx = q.getX(), qy = q.getY(), qz = q.getZ(), w = q.getW(),
                x2 = qx * 2,   y2 = qy * 2,   z2 = qz * 2,
                xx = qx * x2,  yy = qy * y2,  zz = qz * z2,
                xy = qx * y2,  xz = qx * z2,  yz = qy * z2,
                wx = w  * x2,  wy = w  * y2,  wz = w  * z2;

        return Vector.fromXYZ(
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
     * @return a new rotated point
     */
    public static Vector rotate3(Vector point, Vector axis, float theta) {
        Quaternion q = Quaternion.fromRotation(axis, theta);

        Vector qv = q.getVector();
        Vector t = qv.cross(point).multiply(2);
        return t.clone()
                .multiply(q.getW())
                .add(point)
                .add(qv.cross(t));
    }

}
