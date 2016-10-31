import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.transform.Transformations;

public class Main {

    public static void main(String[] args) {
        testMatrixRotations();
        testMinecraftQuaternions();
        testQuaternionRotations();
        testVectorYawPitchSetters();
    }

    public static void testMatrixRotations() {
        System.out.println("TESTING MINECRAFT MATRIX ROTATIONS");
        Vector p = Vector.fromXYZ(0, 0, 1); //straight into z-direction (0 yaw, 0 pitch in Minecraft)
        Matrix matrix = Matrix.fromYaw(90);
        Matrix result = Matrix.product(matrix, Matrix.fromVector(p));
        Vector p2 = Vector.fromXYZ(result.get(0, 0), result.get(1, 0), result.get(2, 0));

        System.out.println(p+" * "+matrix+" = "+p2);
    }

    public static void testMinecraftQuaternions() {
        System.out.println("TESTING MINECRAFT QUATERNIONS");

        Vector p = Vector.fromXYZ(0, 0, 1); //straight into z-direction (0 yaw, 0 pitch in Minecraft)
        Quaternion q = Quaternion.fromYawPitchRoll(45, 45, 0);

        System.out.println(p+" rotated by "+q+" = "+Transformations.rotate(p, q));
    }

    public static void testVectorYawPitchSetters() {
        System.out.println("TESTING VECTOR YAW AND PITCH METHODS");

        Vector p = Vector.fromXYZ(0, 0, 1); //straight into z-direction (0 yaw, 0 pitch in Minecraft)

        System.out.println(p); //expect positive z
        System.out.println(p.getYaw()); //expect 0

        System.out.println(p.setYaw(90)); //expect negative x
        System.out.println(p.getYaw()); //expect 90

        System.out.println(p.setYaw(-90)); //expect positive x
        System.out.println(p.getYaw()); //-90
    }

    public static void testQuaternionRotations() {
        System.out.println("TESTING QUATERNION ROTATIONS OF VECTORS");

        Vector p = Vector.fromXYZ(1, 0, 0);
        Vector axis = Vector.fromXYZ(0, 1, 0);
        float theta = 90;

        System.out.println("p  = " + p);
        System.out.println("p' = p rotated "+theta+" degrees around axis "+axis);
        System.out.println("p' = " + Transformations.rotate (p, axis, Spatium.radians(theta)));
        System.out.println("p' = " + Transformations.rotate2(p, axis, Spatium.radians(theta)));
        System.out.println("p' = " + Transformations.rotate3(p, axis, Spatium.radians(theta)));

        int times = 1_000_000;

        long
                a = measureMillis(times, () -> Transformations.rotate (p, axis, Spatium.radians(theta))),
                b = measureMillis(times, () -> Transformations.rotate2(p, axis, Spatium.radians(theta))),
                c = measureMillis(times, () -> Transformations.rotate3(p, axis, Spatium.radians(theta)));

        System.out.println(times+"x rotate()  takes "+a+"ms");
        System.out.println(times+"x rotate2() takes "+b+"ms");
        System.out.println(times+"x rotate3() takes "+c+"ms");
    }

    public static long measureMillis(int times, Runnable process) {
        long before = System.currentTimeMillis();
        while (times-- > 0) process.run();
        return System.currentTimeMillis() - before;
    }

}
