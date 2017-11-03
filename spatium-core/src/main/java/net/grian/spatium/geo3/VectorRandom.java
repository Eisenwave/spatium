package net.grian.spatium.geo3;

import net.grian.spatium.geo2.Vector2;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VectorRandom extends Random {
    
    public VectorRandom() {}
    
    public VectorRandom(long seed) {
        super(seed);
    }
    
    @NotNull
    public Vector2 nextVector2(double length) {
        return Vector2.fromXY(nextDouble(), nextDouble()).setLength(length);
    }
    
    @NotNull
    public Vector3 nextVector3(double length) {
        return Vector3.fromXYZ(nextDouble(), nextDouble(), nextDouble()).setLength(length);
    }
    
}
