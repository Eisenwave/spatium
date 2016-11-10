import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.transform.Transformations;
import net.grian.spatium.util.PrimMath;

public class Main {

    public static void main(String[] args) {
        doMyHomework();
        doMyHomework2();
        testMatrixSquare();
        testMatrixRotations();
        testMinecraftQuaternions();
        testQuaternionRotations();
        testVectorYawPitchSetters();
    }

    public static void testMatrixSquare() {
        System.out.println("TESTING MATRIX MULTIPLICATION");
        Matrix a = Matrix.create(2, 2, 0, -1, 2, 0);
        Matrix b = Matrix.create(2, 2, 0, -1, 2, 0);
        Matrix c = Matrix.create(3, 1, 2, 1, 1);
        Matrix d = Matrix.create(3, 3,
                1, 0, 1,
                0, 1, 0,
                1, 0, 1);

        System.out.println(a + " * " + b + " = " + Matrix.product(a, b));
        System.out.println(a + "^2 = " + Matrix.square(a));
        System.out.println(c + "^T * "+c +" = "+Matrix.product(Matrix.transpone(c), c));
        System.out.println(c + " * "+c +"^T = "+Matrix.product(c, Matrix.transpone(c)));
        System.out.println(d + "^2 = " + Matrix.square(d));
        System.out.println(d + "^3 = " + Matrix.cube(d));
    }

    public static void doMyHomework() {
        System.out.println("DOING HOMEWORK");
        Matrix a = Matrix.create(3, 3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);
        Matrix m1 = Matrix.create(3, 3,
                1, 0, 0,
                0, 0, 1,
                0, 1, 0);
        Matrix m2 = Matrix.create(3, 3,
                1, 0, 0,
                -2, 1, 0,
                0, 0, 1);
        Matrix b = Matrix.create(2, 3,
                0, 1, 1,
                3, 0, 0);

        System.out.println("A x M1 = "+Matrix.product(a, m1));
        System.out.println("M1 x A = "+Matrix.product(m1, a));
        System.out.println("M2 x A = "+Matrix.product(m2, a));
        System.out.println("B x A  = "+Matrix.product(b, a));

    }

    public static void doMyHomework2() {
        System.out.println("DOING HOMEWORK 2");
        Matrix a = Matrix.create(2, 2,
                -1, 2,
                0,  3);
        Matrix b = Matrix.create(2, 2,
                5, -2,
                0, 0);

        Matrix r = Matrix.create(2, 2,
                0, -2,
                0, 0);

        System.out.println("1: "+Matrix.product(a, b));
        System.out.println("2: "+Matrix.product(b, a));
        System.out.println("3: "+Matrix.product(a, b));
        System.out.println("4: "+Matrix.product(b, a));
        System.out.println(a.equals(Matrix.create(2, 2, -1, 99, 0, 3)));

        int i = 1_000_000;
        int res = 0;
        while (i-- >= 0 && res < 10) {
            r.set(0, 0, PrimMath.randomInt(-50, 50));
            r.set(1, 1, PrimMath.randomInt(-50, 50));

            Matrix ar = Matrix.product(a, r);
            Matrix ra = Matrix.product(r, a);
            if (ar.equals(ra))
                System.out.println("Result #"+ (++res) + ": "+r);
        }

        System.out.println("A x B = "+Matrix.product(a, b));
        System.out.println("B x A = "+Matrix.product(b, a));
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
