package net.grian.spatium.matrix;

import net.grian.spatium.Spatium;
import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {
    
    @Test
    public void floydWarshall() {
        final double inf = Double.POSITIVE_INFINITY;
        
        Matrix matrix = Matrix.create(4, 4,
            0,   inf, -2, inf,
            4,     0,  3, inf,
            inf, inf,  0,   2,
            inf,  -1, inf,  0);
        Matrix expected = Matrix.create(4, 4,
            0, -1, -2, 0,
            4,  0,  2, 4,
            5,  1,  0, 2,
            3, -1,  1, 0);
        
        Matrix.floydWarshall(matrix);
        assertEquals(matrix, expected);
    }
    
    @Test
    public void pow() {
        Matrix matrix = Matrix.create(5, 5,
            3, 0, 0, -2, 4,
            0, 2, 0, 0, 0,
            0, -1, 0, 5, -3,
            -4, 0, 1, 0, 6,
            0, -1, 0, 3, 2);
        
        matrix.scale(2);
        System.out.println(matrix.getDeterminant());
    }

    @Test
    public void create() throws Exception {
        Matrix matrix = Matrix.create(3, 3,
                1, 0, 0,
                0, 2, 0,
                0, 0, 3);

        assertEquals(matrix.get(0, 0), 1, Spatium.EPSILON);
        assertEquals(matrix.get(1, 1), 2, Spatium.EPSILON);
        assertEquals(matrix.get(2, 2), 3, Spatium.EPSILON);
    }

    @Test
    public void set() throws Exception {
        Matrix matrix = Matrix.create(3, 3);
        matrix.set(0, 0, 1);
        matrix.set(1, 1, 2);
        matrix.set(2, 2, 3);

        assertEquals(matrix.get(0, 0), 1, Spatium.EPSILON);
        assertEquals(matrix.get(1, 1), 2, Spatium.EPSILON);
        assertEquals(matrix.get(2, 2), 3, Spatium.EPSILON);
    }

    @Test
    public void identicalGetSet() throws Exception {
        Matrix matrix = Matrix.create(64, 64);
        for (int k = 0; k<4096; k++) {
            final int
                    i = PrimMath.randomInt(63, 63),
                    j = PrimMath.randomInt(63, 63),
                    value = PrimMath.randomInt(Integer.MAX_VALUE);

            assertEquals(value, matrix.set(i, j, value).get(i, j), Spatium.EPSILON);
        }
    }

    @Test
    public void getRow() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        assertArrayEquals(new double[] {1, 2, 3, 4}, matrix.getRow(0), Spatium.EPSILON);
        assertArrayEquals(new double[] {1, 8, 27, 64}, matrix.getRow(2), Spatium.EPSILON);
    }

    @Test
    public void getColumns() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        assertArrayEquals(new double[] {1, 1, 1, 1}, matrix.getColumn(0), Spatium.EPSILON);
        assertArrayEquals(new double[] {3, 9, 27, 81}, matrix.getColumn(2), Spatium.EPSILON);
    }

    @Test
    public void swapRows() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        try {
            assertArrayEquals(new double[]{1, 16, 81, 256}, matrix.swapRows(0, 3).getRow(0), Spatium.EPSILON);
            assertArrayEquals(new double[]{1, 8, 27, 64}, matrix.swapRows(1, 2).getRow(1), Spatium.EPSILON);
        } catch (Throwable error) {
            System.out.println(matrix);
            throw error;
        }
    }

    @Test
    public void swapColumns() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        try {
            assertArrayEquals(new double[]{4, 16, 64, 256}, matrix.swapColumns(0, 3).getColumn(0), Spatium.EPSILON);
            assertArrayEquals(new double[]{3, 9, 27, 81}, matrix.swapColumns(1, 2).getColumn(1), Spatium.EPSILON);
        } catch (Throwable error) {
            System.out.println(matrix);
            throw error;
        }
    }

    @Test
    public void swap() throws Exception {
        Matrix actual = Matrix.create(3, 3,
                1, 2, 3,
                4, 5, 6,
                7, 8, 9);

        actual.swap(1, 1, 1, 1);
        actual.swap(1, 0, 0, 1);
        actual.swap(2, 0, 0, 2);

        Matrix expected = Matrix.create(3, 3,
                1, 4, 7,
                2, 5, 6,
                3, 8, 9);

        assertEquals(expected, actual);
    }

    @Test
    public void transpose() throws Exception {
        Matrix actual = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        Matrix expected = Matrix.create(4, 4,
                1, 1, 1, 1,
                2, 4, 8, 16,
                3, 9, 27, 81,
                4, 16, 64, 256);

        assertEquals(expected, actual.transpose());
    }

    @Test
    public void getDeterminant() throws Exception {
        {
            boolean failed = false;
            try {
                Matrix.create(1, 2).getDeterminant();
            } catch (MatrixDimensionsException ex) {
                failed = true;
            }
            assertTrue(failed);
        }
        {
            Matrix matrix = Matrix.create(1, 1,
                    1);
            assertEquals(1, matrix.getDeterminant(), Spatium.EPSILON);
        }
        {
            Matrix matrix = Matrix.create(2, 2,
                    1, 2,
                    1, 4);
            assertEquals(2, matrix.getDeterminant(), Spatium.EPSILON);
        }
        {
            Matrix matrix = Matrix.create(3, 3,
                    1, 2, 3,
                    1, 4, 9,
                    1, 8, 27);
            assertEquals(12, matrix.getDeterminant(), Spatium.EPSILON);
        }
        {
            Matrix matrix = Matrix.create(4, 4,
                    1, 2, 3, 4,
                    1, 4, 9, 16,
                    1, 8, 27, 64,
                    1, 16, 81, 256);
            assertEquals(288, matrix.getDeterminant(), Spatium.EPSILON);
        }
    }

    @Test
    public void getCofactors() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        Matrix cofactors = Matrix.create(4, 4,
                1152, -864, 384, -72,
                -1248, 1368, -672, 132,
                432, -576, 336, -72,
                -48, 72, -48, 12);

        assertEquals(cofactors, matrix.getCofactors());
    }

    @Test
    public void getInverse() throws Exception {
        Matrix matrix = Matrix.create(4, 4,
                1, 2, 3, 4,
                1, 4, 9, 16,
                1, 8, 27, 64,
                1, 16, 81, 256);

        Matrix inverse = Matrix.create(4, 4,
                 96, -104,  36, -4,
                -72,  114, -48,  6,
                 32,  -56,  28, -4,
                 -6,   11,  -6,  1).scale(1 / 24D);

        assertEquals(inverse, matrix.getInverse());
    }

}