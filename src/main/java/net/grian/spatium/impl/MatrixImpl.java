package net.grian.spatium.impl;

import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixIndexOutOfBoundsException;
import net.grian.spatium.util.PrimArrays;
import net.grian.spatium.util.Strings;

public class MatrixImpl implements Matrix {

    private final int rows, columns;
    private final float[] content;

    public MatrixImpl(int rows, int columns, float[] content) {
        this.rows = rows;
        this.columns = columns;
        this.content = PrimArrays.clone(content);
    }

    public MatrixImpl(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.content = new float[rows * columns];
    }

    public MatrixImpl(MatrixImpl copyOf) {
        this(copyOf.rows, copyOf.columns, copyOf.content);
    }

    // GETTERS

    @Override
    public float get(int row, int col) {
        validate(row, col);

        return content[row*columns + col];
    }

    @Override
    public float[] getRow(int row) {
        validateRow(row);

        float[] result = new float[columns];
        for (int i = 0; i<columns; i++)
            result[i] = get(row, i);

        return result;
    }

    @Override
    public float[] getColumn(int col) {
        validateCol(col);

        float[] result = new float[rows];
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

    // SETTERS

    @Override
    public Matrix set(int row, int col, float value) {
        validate(row, col);

        content[row*columns + col] = value;
        return this;
    }

    @Override
    public Matrix scale(float factor) {
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
