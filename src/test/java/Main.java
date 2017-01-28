import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.transform.Transformations;
import net.grian.spatium.util.DiscreteMath;
import net.grian.spatium.util.PrimMath;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        long times = 1_000_000;

        long custom2 = measureTime(() -> {
            double x = random.nextDouble(), y = random.nextDouble();
            Math.sqrt(x*x + y*y);
        }, times);

        long stock2 = measureTime(() -> {
            double x = random.nextDouble(), y = random.nextDouble();
            Math.hypot(x, y);
        }, times);

        long custom3 = measureTime(() -> {
            double x = random.nextDouble(), y = random.nextDouble(), z = random.nextDouble();
            Math.sqrt(x*x + y*y * z*z);
        }, times);

        long stock3 = measureTime(() -> {
            double x = random.nextDouble(), y = random.nextDouble(), z = random.nextDouble();
            Math.hypot(x, Math.hypot(y, z));
        }, times);

        System.out.println(custom2+", "+stock2+", "+custom3+", "+stock3);
    }

    public static long measureTime(Runnable runnable, long times) {
        long before = System.currentTimeMillis();
        while (times-- > 0) {
            runnable.run();
        }
        return System.currentTimeMillis() - before;
    }

    public static void runTests() {
        doMyHomework();
        doMyHomework2();
        doMyHomework3();
        doMyHomework4();
        testPlanes();
        testMatrixSquare();
        testMatrixRotations();
        testMinecraftQuaternions();
        testQuaternionRotations();
        testVectorYawPitchSetters();
    }

    public static void doMyHomework4() {
        System.out.println("DOING HOMEWORK 4");
        long[] nums = {20, 4, 9, 22, 13, 21, 12, 6};
        for (long num : nums) {
            double res1 = Math.pow(num, 13) % 33;
            System.out.println(num+" ^ 13 (mod 33) = "+res1);
            double res2 = Math.pow(res1, 17) % 33;
            System.out.println(res1+" ^ 17 (mod 33) = "+res2);
        }
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
        System.out.println(c + "^T * "+c +" = "+Matrix.product(Matrix.transpose(c), c));
        System.out.println(c + " * "+c +"^T = "+Matrix.product(c, Matrix.transpose(c)));
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

    public static void doMyHomework3() {
        System.out.println("DOING HOMEWORK 3");
        for (int i = 1; i<=100; i++)
            if (DiscreteMath.divisorCount(i) == 5) System.out.println(i);

    }

    public static void testPlanes() {
        System.out.println("TESTING PLANES");

        Plane plane = Plane.fromGeneral(5, 2, -9, 9);
        System.out.println("(5x + 2y - 9z + 9 = 0) -> "+plane);
        Vector point = Vector.fromXYZ(-1.8f, 0f, 0f);
        System.out.println(plane+" contains "+point+" = "+plane.contains(point));

        Plane min = Plane.fromGeneral(2, 2, 2, 6);
        Vector o = Vector.fromXYZ(0, 0, 0);
        Vector a = Vector.fromXYZ(3, 3, 3);
        System.out.println("n = "+min.getNormal());
        System.out.println("o = "+o);
        System.out.println("dot of n and o = "+min.getNormal().dot(o));

        System.out.println("d of min = "+min.getDepth());
        System.out.println("l of n of min = "+min.getNormal().getLength());

        System.out.println("distance of "+min+" and "+o+" = "+min.signedDistanceTo(o));
        System.out.println("distance of "+min+" and "+a+" = "+min.signedDistanceTo(a));
    }

    public static void testMatrixRotations() {
        System.out.println("TESTING MINECRAFT MATRIX ROTATIONS");
        Vector p = Vector.fromXYZ(0, 0, 1); //straight into z-direction (0 yaw, 0 pitch in Minecraft)
        Matrix matrix = Matrix.fromRotY(90);
        Matrix result = Matrix.product(matrix, Matrix.fromVector(p));
        Vector p2 = Vector.fromXYZ(result.get(0, 0), result.get(1, 0), result.get(2, 0));

        System.out.println(p+" * "+matrix+" = "+p2);
    }

    public static void testMinecraftQuaternions() {
        System.out.println("TESTING MINECRAFT QUATERNIONS");

        Vector p = Vector.fromXYZ(0, 0, 1); //straight into z-direction (0 yaw, 0 pitch in Minecraft)
        Quaternion q = Quaternion.fromYawPitchRoll(45, 45, 0);
        Vector result = p.clone();
        Transformations.rotate(result, q);

        System.out.println(p+" rotated by "+q+" = "+result);
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

        Vector r1 = p.clone(), r2 = p.clone(), r3 = p.clone();
        Transformations.rotate (r1, axis, Spatium.radians(theta));
        Transformations.rotate2(r2, axis, Spatium.radians(theta));
        Transformations.rotate3(r3, axis, Spatium.radians(theta));

        System.out.println("p' = " + r1);
        System.out.println("p' = " + r2);
        System.out.println("p' = " + r3);

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
