package net.grian.spatium.impl;

import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixIndexOutOfBoundsException;

public class Matrix2Impl implements Matrix {

    private final double[] content = new double[4];

    public Matrix2Impl(double a, double b, double c, double d) {
        content[0] = a;
        content[1] = b;
        content[2] = c;
        content[3] = d;
    }

    public Matrix2Impl() {}

    public Matrix2Impl(Matrix2Impl copyOf) {
        this(copyOf.content[0], copyOf.content[1], copyOf.content[2], copyOf.content[3]);
    }

    @Override
    public double get(int i, int j) {
        validate(i, j);
        return content[i*2 + j];
    }

    @Override
    public double[] getRow(int i) {
        switch (i) {
            case 0: return new double[] {content[0], content[1]};
            case 1: return new double[] {content[2], content[3]};
            default: throw new MatrixIndexOutOfBoundsException(i+" must be 0-1");
        }
    }

    @Override
    public double[] getColumn(int j) {
        switch (j) {
            case 0: return new double[] {content[0], content[2]};
            case 1: return new double[] {content[1], content[3]};
            default: throw new MatrixIndexOutOfBoundsException(j+" must be 0-1");
        }
    }

    @Override
    public int getRows() {
        return 2;
    }

    @Override
    public int getColumns() {
        return 2;
    }

    @Override
    public double getDeterminant() {
        return content[0]*content[3] - content[1]*content[2];
    }

    @Override
    public Matrix getCofactors() {
        return new Matrix2Impl(content[3], -content[2], -content[1], content[0]);
    }

    @Override
    public Matrix getAdjugate() {
        return new Matrix2Impl(content[3], -content[1], -content[2], content[0]);
    }

    @Override
    public Matrix getInverse() {
        return getAdjugate().scale(1 / getDeterminant());
    }

    @Override
    public Matrix set(int i, int j, double value) {
        validate(i, j);
        content[i*2 + j] = value;
        return this;
    }

    @Override
    public Matrix swap(int i0, int j0, int i1, int j1) {
        validate(i0, j0);
        validate(i1, j1);

        final int from = i0*2 + j0, to = i1*2 + j1;

        double swap = content[to];
        content[to] = content[from];
        content[from] = swap;
        return this;
    }

    @Override
    public Matrix swapRows(int i0, int i1) {
        validateRow(i0);
        validateRow(i1);
        if (i0 != i1) {
            double c = content[2], d = content[3];
            content[2] = content[0];
            content[3] = content[1];
            content[0] = c;
            content[1] = d;
        }
        return this;
    }

    @Override
    public Matrix swapColumns(int j0, int j1) {
        validateCol(j0);
        validateCol(j1);
        if (j0 != j1) {
            double b = content[1], d = content[3];
            content[1] = content[0];
            content[3] = content[2];
            content[0] = b;
            content[2] = d;
        }
        return this;
    }

    @Override
    public Matrix transpose() {
        double c = content[2];
        content[2] = content[1];
        content[1] = c;
        return this;
    }

    @Override
    public Matrix scale(double factor) {
        content[0] *= factor;
        content[1] *= factor;
        content[2] *= factor;
        content[3] *= factor;
        return this;
    }

    @Override
    public Matrix clone() {
        return new Matrix2Impl(this);
    }

    //VALIDATE

    private void validateCol(int col) {
        if (col < 0)
            throw new MatrixIndexOutOfBoundsException("j="+col+" < 0");
        if (col >= 2)
            throw new MatrixIndexOutOfBoundsException("j="+col+" >= "+2+" columns");
    }

    private void validateRow(int row) {
        if (row < 0)
            throw new MatrixIndexOutOfBoundsException("i="+row+" < 0");
        if (row >= 2)
            throw new MatrixIndexOutOfBoundsException("i="+row+" >= "+2+" rows");
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= 2)
            throw new MatrixIndexOutOfBoundsException("row  "+row+" < 0 or >= "+2);
        if (col < 0 || col >= 2)
            throw new MatrixIndexOutOfBoundsException("col "+col+" < 0 or >= "+2);
    }

}
