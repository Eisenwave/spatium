package net.grian.spatium.impl;

import net.grian.spatium.geo.OrientedBB;
import net.grian.spatium.geo.Vector;

public class OrientedBBImpl implements OrientedBB {

    private float
            x, y, z,
            du, dv, dw,
            ux, uy, uz,
            vx, vy, vz,
            wx, wy, wz;

    public OrientedBBImpl(Vector origin, Vector u, Vector v, Vector w, float du, float dv, float dw) {
        this.x = origin.getX(); this.y = origin.getY(); this.z = origin.getZ();
        this.ux = u.getX(); this.uy = u.getY(); this.uz = u.getZ();
        this.vx = v.getX(); this.vy = v.getY(); this.vz = v.getZ();
        this.wx = w.getX(); this.wy = w.getY(); this.wz = w.getZ();
        this.du = du; this.dv = dv; this.dw = dw;
    }

    @Override
    public Vector getMin() {
        return Vector.fromXYZ(x, y, z);
    }

    @Override
    public Vector getMax() {
        return Vector.fromXYZ(
                x + ux*du + vx*dv + wx*dw,
                y + uy*du + vy*dv + wy*dw,
                z + uz*du + vz*dv + wz*dw
        );
    }

    @Override
    public Vector getCenter() {
        return Vector.fromXYZ(
                x + (ux*du + vx*dv + wx*dw) * 0.5f,
                y + (uy*du + vy*dv + wy*dw) * 0.5f,
                z + (uz*du + vz*dv + wz*dw) * 0.5f
        );
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
    public float getSizeU() {
        return du;
    }

    @Override
    public float getSizeV() {
        return dv;
    }

    @Override
    public float getSizeW() {
        return dw;
    }
}
