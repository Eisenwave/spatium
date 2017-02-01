package net.grian.spatium.impl;

import net.grian.spatium.geo3.OrientedBB3;
import net.grian.spatium.geo3.Slab3;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.matrix.Matrix;

import javax.annotation.Nonnull;

public class OrientedBB3Impl implements OrientedBB3 {

    private double
            cx, cy, cz, //center
            dx, dy, dz; //half-dimensions

            //ux, uy, uz, //local x-axis (def: 1,0,0)
            //vx, vy, vz, //local y-axis (def: 0,1,0)
            //wx, wy, wz; //local z-axis (def: 0,0,1)
    //transform matrix
    @Nonnull
    private Matrix trans;

    private OrientedBB3Impl(double x, double y, double z, double dx, double dy, double dz, Matrix transform) {
        if (dx < 0) throw new IllegalArgumentException("dx must be positive");
        if (dy < 0) throw new IllegalArgumentException("dy must be positive");
        if (dz < 0) throw new IllegalArgumentException("dz must be positive");
        this.cx = x;  this.cy = y;  this.cz = z;
        this.dx = dx; this.dy = dy; this.dz = dz;
        this.trans = transform;

    }

    public OrientedBB3Impl(Vector3 center, double dx, double dy, double dz, Vector3 u, Vector3 v, Vector3 w) {
        this(
                center.getX(), center.getY(), center.getZ(),
                dx, dy, dz,
                Matrix.create(3, 3,
                        u.getX(), u.getY(), u.getZ(),
                        v.getX(), v.getY(), v.getZ(),
                        w.getX(), w.getY(), w.getZ())
        );
        //this.ux = u.getX(); this.uy = u.getY(); this.uz = u.getZ();
        //this.vx = v.getX(); this.vy = v.getY(); this.vz = v.getZ();
        //this.wx = w.getX(); this.wy = w.getY(); this.wz = w.getZ();
    }

    public OrientedBB3Impl(double x, double y, double z, double dx, double dy, double dz) {
        this(
                x, y, z,
                dx, dy, dz,
                Matrix.identity(3)
        );
    }

    @Override
    public Vector3 getMin() {
        return getCenter().subtract(Matrix.product(trans, dx, dy, dz));
    }

    @Override
    public Vector3 getMax() {
        return getCenter().add(Matrix.product(trans, dx, dy, dz));
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(cx, cy, cz);
    }

    @Override
    public Matrix getTransform() {
        return trans.clone();
    }

    public Vector3 applyTransform(Vector3 vector) {
        return vector.transform(trans);
    }

    @Override
    public Slab3 getSlabX() {
        Vector3 axis = Vector3.fromXYZ(trans.get(0, 0), trans.get(0, 1), trans.get(0, 2));
        double d = axis.dot(cx, cy, cz);
        //double delta = Math.abs( axis.getX()*dx + axis.getY()*dx + axis.getZ()*dx );
        return Slab3.create(axis, d - dx, d + dx);
    }

    @Override
    public Slab3 getSlabY() {
        Vector3 axis = Vector3.fromXYZ(trans.get(1, 0), trans.get(1, 1), trans.get(1, 2));
        double d = axis.dot(cx, cy, cz);
        //double delta = Math.abs( axis.getX()*dy + axis.getY()*dy + axis.getZ()*dy );
        return Slab3.create(axis, d - dx, d + dx);
    }

    @Override
    public Slab3 getSlabZ() {
        Vector3 axis = Vector3.fromXYZ(trans.get(2, 0), trans.get(2, 1), trans.get(2, 2));
        double d = axis.dot(cx, cy, cz);
        //double delta = Math.abs( axis.getX()*dz + axis.getY()*dz + axis.getZ()*dz );
        return Slab3.create(axis, d - dx, d + dx);
    }

    @Override
    public double getSizeX() {
        return dx*2;
    }

    @Override
    public double getSizeY() {
        return dy*2;
    }

    @Override
    public double getSizeZ() {
        return dz*2;
    }

    //CHECKERS

    @Override
    public boolean contains(double x, double y, double z) {
        Vector3 relPoint = Vector3.fromXYZ(x-cx, y-cy, z-cz);
        applyTransform(relPoint);

        return
                Math.abs(relPoint.getX()) <= dx &&
                Math.abs(relPoint.getY()) <= dy &&
                Math.abs(relPoint.getZ()) <= dz;
    }


    //SETTERS

    @Override
    public OrientedBB3 move(double x, double y, double z) {
        this.cx += x;
        this.cy += y;
        this.cz += z;
        return this;
    }

    @Override
    public OrientedBB3 transform(Matrix transform) {
        this.trans = Matrix.product(this.trans, transform);
        return this;
    }

    @Override
    public String toString() {
        return OrientedBB3.class.getSimpleName()+
                "{center="+getCenter()+
                ",dims="+getDimensions()+
                ",transform="+getTransform()+"}";
    }
}
