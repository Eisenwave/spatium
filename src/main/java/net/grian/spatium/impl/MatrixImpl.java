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
    public double get(int row, int col) {
        validate(row, col);

        return content[row*columns + col];
    }

    @Override
    public double[] getRow(int row) {
        validateRow(row);

        double[] result = new double[columns];
        for (int i = 0; i<columns; i++)
            result[i] = get(row, i);

        return result;
    }

    @Override
    public double[] getColumn(int col) {
        validateCol(col);

        double[] result = new double[rows];
        for (int i = 0; i<rows; i++)
            result[i] = get(i, col);

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

        return internalDeterminant(this, rows);
    }

    private static double internalDeterminant(Matrix matrix, int n) {
        if (n == 1) return matrix.get(0,0);
        if (n == 2) return matrix.get(0,0)*matrix.get(1,1) - matrix.get(0,1)*matrix.get(1,0);

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

                result += matrix.get(0, p) * Math.pow(-1, p) * internalDeterminant(temp, n-1);
            }
        }

        return result;
    }

    // SETTERS

    @Override
    public Matrix set(int row, int col, double value) {
        validate(row, col);

        content[row*columns + col] = value;
        return this;
    }

    @Override
    public Matrix scale(double factor) {
        for (int i = 0; i<rows; i++)
            for (int j = 0; j<columns; j++)
                content[i * j*rows] *= factor;

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
