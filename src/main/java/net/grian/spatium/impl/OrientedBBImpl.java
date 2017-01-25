package net.grian.spatium.impl;

import net.grian.spatium.geo.OrientedBB;
import net.grian.spatium.geo.Slab;
import net.grian.spatium.geo.Vector;

public class OrientedBBImpl implements OrientedBB {

    private double
            cx, cy, cz, //center
            du, dv, dw, //half-dimensions
            ux, uy, uz, //local x-axis
            vx, vy, vz, //local y-axis
            wx, wy, wz; //local z-axis

    public OrientedBBImpl(Vector center, Vector u, Vector v, Vector w, double du, double dv, double dw) {
        if (du < 0) throw new IllegalArgumentException("du must be positive");
        if (dv < 0) throw new IllegalArgumentException("dv must be positive");
        if (dw < 0) throw new IllegalArgumentException("dw must be positive");
        this.cx = center.getX(); this.cy = center.getY(); this.cz = center.getZ();
        this.ux = u.getX(); this.uy = u.getY(); this.uz = u.getZ();
        this.vx = v.getX(); this.vy = v.getY(); this.vz = v.getZ();
        this.wx = w.getX(); this.wy = w.getY(); this.wz = w.getZ();
        this.du = du; this.dv = dv; this.dw = dw;
    }

    @Override
    public Vector getMin() {
        return Vector.fromXYZ(
                cx - ux*du - vx*dv - wx*dw,
                cy - uy*du - vy*dv - wy*dw,
                cz - uz*du - vz*dv - wz*dw
        );
    }

    @Override
    public Vector getMax() {
        return Vector.fromXYZ(
                cx + ux*du + vx*dv + wx*dw,
                cy + uy*du + vy*dv + wy*dw,
                cz + uz*du + vz*dv + wz*dw
        );
    }

    @Override
    public Vector getCenter() {
        return Vector.fromXYZ(cx, cy, cz);
    }

    @Override
    public Vector getU() {
        return Vector.fromXYZ(ux, uy, uz);
    }

    @Override
    public Vector getV() {
        return Vector.fromXYZ(vx, vy, vz);
    }

    @Override
    public Vector getW() {
        return Vector.fromXYZ(wx, wy, wz);
    }

    @Override
    public Slab getSlabU() {
        Vector axis = getU();
        double d = axis.dot(cx, cy, cz);
        return Slab.create(axis, d - du, d + du);
    }

    @Override
    public Slab getSlabV() {
        Vector axis = getV();
        double d = axis.dot(cx, cy, cz);
        return Slab.create(axis, d - dv, d + dv);
    }

    @Override
    public Slab getSlabW() {
        Vector axis = getW();
        double d = axis.dot(cx, cy, cz);
        return Slab.create(axis, d - dw, d + dw);
    }

    @Override
    public double getSizeU() {
        return du*2;
    }

    @Override
    public double getSizeV() {
        return dv*2;
    }

    @Override
    public double getSizeW() {
        return dw*2;
    }
}
