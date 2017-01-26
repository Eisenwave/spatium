package net.grian.spatium.impl;

import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixDimensionsException;
import net.grian.spatium.matrix.MatrixIndexOutOfBoundsException;
import net.grian.spatium.util.PrimArrays;
import net.grian.spatium.util.Strings;

public class MatrixImpl implements Matrix {

    private final int rows, columns;
    private final double[] content;

    public MatrixImpl(int rows, int columns, double[] content) {
        this.rows = rows;
        this.columns = columns;
        this.content = PrimArrays.clone(content);
    }

    public MatrixImpl(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.content = new double[rows * columns];
    }

    public MatrixImpl(MatrixImpl copyOf) {
        this(copyOf.rows, copyOf.columns, copyOf.content);
    }

    // GETTERS

    @Override
    public double get(int i, int j) {
        validate(i, j);

        return content[indexOf(i, j)];
    }

    private int indexOf(int i, int j) {
        return i*columns + j;
    }

    @Override
    public double[] getRow(int i) {
        validateRow(i);

        double[] result = new double[columns];
        for (int j = 0; j<columns; j++)
            result[j] = content[indexOf(i, j)];

        return result;
    }

    @Override
    public double[] getColumn(int j) {
        validateCol(j);

        double[] result = new double[rows];
        for (int i = 0; i<rows; i++)
            result[i] = content[indexOf(i, j)];

        return result;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public double getDeterminant() {
        if (rows != columns)
            throw new MatrixDimensionsException("matrix must be square matrix");

        if (rows == 1) return content[0];
        if (rows == 2) return content[0]*content[3] - content[1]*content[2];

        return internalDeterminant(this, rows);
    }

    private static double internalDeterminant(Matrix matrix, int n) {
        if (n == 3) return internalDeterminant3(matrix);

        Matrix temp = Matrix.create(n, n);
        double result = 0;

        for (int p = 0; p < n; p++) {
            int h = 0, k = 0;

            for (int i = 1; i < n; i++) for (int j = 0; j < n; j++) {
                if(j==p) continue;

                temp.set(h, k, matrix.get(i, j));

                if(++k == n-1) {
                    h++;
                    k = 0;
                }
            }

            result += matrix.get(0, p) * minusOnePow(p) * internalDeterminant(temp, n-1);
        }

        return result;
    }

    private static double internalDeterminant3(Matrix m) {
        return
                  m.get(0,0)*m.get(1,1)*m.get(2,2)
                + m.get(0,1)*m.get(1,2)*m.get(2,0)
                + m.get(0,2)*m.get(1,0)*m.get(2,1)
                - m.get(0,2)*m.get(1,1)*m.get(2,0)
                - m.get(0,0)*m.get(1,2)*m.get(2,1)
                - m.get(0,1)*m.get(1,0)*m.get(2,2);
    }

    /**
     * Returns -1 to an integer power.
     *
     * @param power the power
     * @return -1 to an integer power
     */
    private static int minusOnePow(int power) {
        return power%2 == 0 ? 1 : -1;
    }

    // SETTERS

    @Override
    public Matrix set(int i, int j, double value) {
        validate(i, j);

        content[indexOf(i, j)] = value;
        return this;
    }

    @Override
    public Matrix swap(int i0, int j0, int i1, int j1) {
        validate(i0, j0);
        validate(i1, j1);

        double swap = get(i1, j1);
        this.set(i1, i1, get(i0, j0));
        this.set(i0, j0, swap);
        return this;
    }

    @Override
    public Matrix swapRows(int i0, int i1) {
        validateRow(i0);
        validateRow(i1);

        for (int j = 0; j<columns; j++) {
            final int from = indexOf(i0, j), to = indexOf(i1, j);

            double swap = content[to];
            content[to] = content[from];
            content[from] = swap;
        }
        return this;
    }

    @Override
    public Matrix swapColumns(int j0, int j1) {
        validateCol(j0);
        validateCol(j1);

        for (int i = 0; i<rows; i++) {
            final int from = indexOf(i, j0), to = indexOf(i, j1);

            double swap = content[to];
            content[to] = content[from];
            content[from] = swap;
        }
        return this;
    }

    @Override
    public Matrix scale(double factor) {
        for (int k = 0; k<content.length; k++)
            content[k] *= factor;
        return this;
    }

    // MISC

    @Override
    public Matrix clone() {
        return new MatrixImpl(this);
    }

    @Override
    public String toString() {
        String[] rowArray = new String[rows];
        for (int i = 0; i < rows; i++) {
            rowArray[i] = "[" + Strings.join(", ", getRow(i)) + "]";
        }
        return "[" + Strings.join(", ", rowArray) + "]";
    }

    // VALIDATE

    private void validateCol(int col) {
        if (col < 0)
            throw new MatrixIndexOutOfBoundsException("col "+col+" < 0");
        if (col >= columns)
            throw new MatrixIndexOutOfBoundsException("col "+col+" >= "+columns+" columns");
    }

    private void validateRow(int row) {
        if (row < 0)
            throw new MatrixIndexOutOfBoundsException("row "+row+" < 0");
        if (row >= rows)
            throw new MatrixIndexOutOfBoundsException("row "+row+" >= "+rows+" rows");
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= rows)
            throw new MatrixIndexOutOfBoundsException("row "+row+" < 0 or >= "+rows);
        if (col < 0 || col >= columns)
            throw new MatrixIndexOutOfBoundsException("col "+col+" < 0 or >= "+columns);
    }

}
