package net.grian.spatium.impl;

import net.grian.spatium.matrix.Matrix;

public class Matrix2Impl implements Matrix {

    private double a, b, c, d;

    public Matrix2Impl(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Matrix2Impl() {}

    public Matrix2Impl(Matrix2Impl copyOf) {
        this(copyOf.a, copyOf.b, copyOf.c, copyOf.d);
    }
    
    private int indexOf(int i, int j) {
        return i*2 + j;
    }

    @Override
    public double get(int i, int j) {
        switch (indexOf(i, j)) {
            case 0: return a;
            case 1: return b;
            case 2: return c;
            case 3: return d;
            default: throw new IndexOutOfBoundsException("i="+i+", j="+j);
        }
    }

    @Override
    public double[] getRow(int i) {
        switch (i) {
            case 0: return new double[] {a, b};
            case 1: return new double[] {c, d};
            default: throw new IndexOutOfBoundsException(i+" must be 0-1");
        }
    }

    @Override
    public double[] getColumn(int j) {
        switch (j) {
            case 0: return new double[] {a, c};
            case 1: return new double[] {b, d};
            default: throw new IndexOutOfBoundsException(j+" must be 0-1");
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
        return a*d - c*b;
    }
    
    @Override
    public double getTrace() {
        return a + d;
    }
    
    @Override
    public Matrix getCofactors() {
        return new Matrix2Impl(d, -c, -b, a);
    }

    @Override
    public Matrix getAdjugate() {
        return new Matrix2Impl(d, -b, -c, a);
    }

    @Override
    public Matrix getInverse() {
        Matrix adjugate = getAdjugate();
        adjugate.scale(1 / getDeterminant());
        return adjugate;
    }
    
    public Matrix2Impl getSquare() {
        double b_c = b * c;
        
        return new Matrix2Impl(
            a*a + b_c,
            a*b + b*d,
            c*a + d*c,
            b_c + d*d);
    }

    @Override
    public void set(int i, int j, double value) {
        switch (indexOf(i, j)) {
            case 0: a = value; break;
            case 1: b = value; break;
            case 2: c = value; break;
            case 3: d = value; break;
            default: throw new IndexOutOfBoundsException("i="+i+", j="+j);
        }
    }

    @Override
    public void swap(int i0, int j0, int i1, int j1) {
        double swap = get(i1, j1);
        set(i1, j1, get(i0, j0));
        set(j0, j0, swap);
    }

    @Override
    public void swapRows(int i0, int i1) {
        validateRow(i0);
        validateRow(i1);
        if (i0 != i1) {
            double cswap = c, dswap = d;
            c = a;
            d = b;
            a = cswap;
            b = dswap;
        }
    }

    @Override
    public void swapColumns(int j0, int j1) {
        validateCol(j0);
        validateCol(j1);
        if (j0 != j1) {
            double bswap = b, dswap = d;
            b = a;
            d = c;
            b = bswap;
            c = dswap;
        }
    }

    @Override
    public void transpose() {
        double swap = c;
        c = b;
        b = swap;
    }

    @Override
    public void scale(double factor) {
        a *= factor;
        b *= factor;
        c *= factor;
        d *= factor;
    }
    
    @Override
    public String toString() {
        return "[["+a+","+b+"],["+c+","+d+"]]";
    }
    
    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Matrix clone() {
        return new Matrix2Impl(this);
    }

    //VALIDATE

    private void validateCol(int col) {
        if (col < 0)
            throw new IndexOutOfBoundsException("j="+col+" < 0");
        if (col >= 2)
            throw new IndexOutOfBoundsException("j="+col+" >= "+2+" columns");
    }

    private void validateRow(int row) {
        if (row < 0)
            throw new IndexOutOfBoundsException("i="+row+" < 0");
        if (row >= 2)
            throw new IndexOutOfBoundsException("i="+row+" >= "+2+" rows");
    }

}
