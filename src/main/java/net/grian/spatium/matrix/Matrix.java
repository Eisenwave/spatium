package net.grian.spatium.matrix;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.MatrixImpl;

/**
 * A two-dimensional matrix of numbers. This matrix, as all classes of Spatium
 * is mutable, although in this case only to an extent
 * 
 * <br><br>The content of the matrix can be modified using
 * {@link #set(int, int, float)} and retreived using {@link #get(int, int)},
 * however the size of the matrix is final and can not be changed.
 * 
 * <br><br>Thus, mathematical operations are provided in the utility methods
 * {@link #sum(Matrix, Matrix)} and {@link #product(Matrix, Matrix)}.
 *
 */
public interface Matrix {
	
	/**
	 * Adds another matrix to this matrix.
	 * 
	 * @param a the first matrix
	 * @param b the second matrix
	 * @return the sum of the matrices
	 * @throws IncompatibleMatrixException if the matrices are not equal in
	 * size
	 */
	public static Matrix sum(Matrix a, Matrix b) {
		if (!a.equalSize(b))
			throw new IncompatibleMatrixException(a, b);
		
		final int row = a.getRows(), col = a.getColumns();
		float[] result = new float[row * col];
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				result[i + j*row] = a.get(row, col) + b.get(row, col);
		
		return create(row, col, result);
	}
	
	public static Matrix sum(Matrix... matrices) {
		if (matrices.length == 0)
			throw new IllegalArgumentException("no matrices given");
		if (matrices.length == 1)
			return matrices[0].clone();
		
		Matrix result = matrices[0];
		for (int i = 1; i < matrices.length; i++)
			result = sum(result, matrices[i]);
		
		return result;
	}
	
	/**
	 * Returns the product of two matrices.
	 * 
	 * @param a the first matrix
	 * @param b the second matrix
	 * @return the product of the matrices
	 * @throws IncompatibleMatrixException if the rows of the first matrix do
	 * not equal the columns of the second matrix
	 */
	public static Matrix product(Matrix a, Matrix b) {
		if (a.getColumns() != b.getRows())
			throw new IncompatibleMatrixException(a, b);
		
		final int
		arow = a.getRows(), acol = a.getColumns(),
		brow = b.getRows(), bcol = b.getColumns();
		float[] result = new float[arow * bcol];
		
		/* outer loop for acquiring the position (i, j) in the product matrix */
		for (int i = 0; i < arow; i++) for (int j = 0; j < bcol; j++) {
			final int index = i*bcol + j;
			
			/* inner loop for calculating the result at (i, j) */
			for (int k = 0, l = 0; k < acol && l < brow; k++, l++)
				result[index] += a.get(i, k) * b.get(l, j);
		}
		
		return create(arow, bcol, result);
	}
	
	/**
	 * Increases or decreases the size of the matrix by a specified amount
	 * of rows or columns. When size is being increased, all the new rows and
	 * columns are filled with zero.
	 * 
	 * @param matrix the matrix to expand
	 * @param row the amount of rows to expand by
	 * @param col the amount of columns to expand by
	 * @return a new matrix
	 * @throws IllegalMatrixSizeException if the resulting matrix is smaller
	 * than 1 in one dimension
	 */
	public static Matrix bigger(Matrix matrix, int row, int col) {
		if (row == 0 && col == 0) return matrix.clone();
		
		final int
		arow = matrix.getRows(),
		acol = matrix.getColumns(),
		brow = arow + row,
		bcol = acol + col;
		
		float[] content = new float[brow * bcol];
		
		for (int i = 0; i < arow && i < brow; i++) for (int j = 0; j < acol && i < bcol; j++) {
			final int index = i * bcol + j;
			content[index] = matrix.get(i, j);
		}
				
		return create(brow, bcol, content);
	}
	
	/**
	 * Returns a new transponed version of a given matrix. This matrix will
	 * have the same amount of rows as the original has columns and the same
	 * amount of columns as the original has rows.
	 * 
	 * @param matrix the matrix
	 * @return a new matrix
	 */
	public static Matrix transpone(Matrix matrix) {
		final int
		row = matrix.getRows(),
		col = matrix.getColumns();
		
		Matrix result = create(col, row);
		
		for (int i = 0; i<row; i++) for (int j = 0; j<col; j++)
			result.set(j, i, matrix.get(i, j));
		
		return result;
	}
	
	/**
	 * Creates a 2x2 scaling matrix for x and y coordinates.
	 * 
	 * @param x the x scale
	 * @param y the y scale
	 * @return a new scaling matrix
	 */
	public static Matrix fromScale(float x, float y) {
		float[] content = new float[4];
		content[0] = x;
		content[3] = y;
		
		return create(2, 2, content);
	}
	
	/**
	 * Creates a 3x3 scaling matrix for x, y and z coordinates.
	 * 
	 * @param x the scale on the x-axis
	 * @param y the scale on the y-axis
	 * @param z the scale on the z-axis
	 * @return a new scaling matrix
	 */
	public static Matrix fromScale(float x, float y, float z) {
		float[] content = new float[9];
		content[0] = x;
		content[4] = y;
		content[8] = z;
		
		return create(3, 3, content);
	}
	
	/**
	 * Creates a n x n scaling matrix for varying coordinates.
	 * 
	 * @param coords the coordinates
	 * @return a new scaling matrix
	 */
	public static Matrix fromScale(float... coords) {
		if (coords.length == 0)
			throw new IllegalMatrixSizeException("no coords given");
		
		final int size = coords.length;
		float[] content = new float[size * size];

        //noinspection ManualArrayCopy
        for (int i = 0; i<size; i++)
			content[i * size + i] = coords[i];
		
		return Matrix.create(size, size, content);
	}
	
	/**
	 * Creates a 3x3 rotation matrix for Minecraft yaw, pitch and roll.
	 * 
	 * @param yaw the yaw of the rotation
	 * @param pitch the pitch of the rotation
	 * @param roll the roll of the rotation
	 * @return a new rotation matrix
	 */
	public static Matrix fromRotation(float yaw, float pitch, float roll) {
		float[] content = new float[9];
		//TODO implement rotation matrices
		
		return Matrix.create(3, 3, content);
	}
	
	/**
	 * Creates a new matrix with specified content. Note that the input content
	 * is being interpreted depending on the given rows and columns.
	 * 
	 * <br><br>For example, a 3x3 matrix will be filled in the following way:
	 * <blockquote>
	 *   {@code [(0,0) (0,1) (0,2) (1,0) (1,1) (1,2) (2,0) (2,1) (2,2)]}
	 *   <br>when the indices {@code (i,j)} represent the row index {@code (i)}
	 *   and the column index {@code (j)}.
	 * </blockquote>
	 * Should the provided array not meet the size requirements
	 * {@code length = rows * columns}, the matrix can not be created.
	 * 
	 * @param rows the amount of rows of the matrix (> 0)
	 * @param columns the amount of columns of the matrix (> 0)
	 * @param content the content to fill the matrix with
	 * @return a new matrix
	 * @throws IllegalMatrixSizeException if the content array's length is not
	 * equal to {@code rows * columns}
	 */
	public static Matrix create(int rows, int columns, float... content) {
		return new MatrixImpl(rows, columns, content);
	}
	
	/**
	 * Creates a new empty matrix.
	 * 
	 * @param rows the amount of rows of the matrix (> 0)
	 * @param columns the amount of columns of the matrix (> 0)
	 * @return a new matrix
	 * @throws IllegalMatrixSizeException if either the amount of rows or the
	 * amount of columns <= 0
	 */
	public static Matrix create(int rows, int columns) {
		return create(rows, columns, null);
	}
	
	// GETTERS
	
	/**
	 * Returns the value at the specified row and column indices.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @return the value at the specified row and column
	 */
	public abstract float get(int row, int col);
	
	/**
	 * Returns an array containing all values at the specified row index.
	 * 
	 * @param row the row index
	 * @return all values at the specified row index
	 */
	public abstract float[] getRow(int row);
	
	/**
	 * Returns an array containing all values at the specified column index.
	 * 
	 * @param col the column index
	 * @return all values at the specified column index
	 */
	public abstract float[] getColumn(int col);
	
	/**
	 * Returns the amount of rows of this matrix.
	 * @return the amount of rows
	 */
	public abstract int getRows();
	
	/**
	 * Returns the amount of columns of this matrix.
	 * @return the amount of columns
	 */
	public abstract int getColumns();
	
	// CHECKERS
	
	/**
	 * Returns whether this matrix is equal to another matrix. This condition
	 * is fulfilled if the matrices are equal in size and equal in all values.
	 * @param matrix the matrix
	 * @return whether this matrix is equal to another matrix
	 */
	public default boolean equals(Matrix matrix) {
		int row = getRows(), col = getColumns();
		if (row != matrix.getRows() || col != matrix.getColumns())
			return false;
		
		while (--row >= 0) while (--col >= 0)
			if (!Spatium.equals(this.get(row, col), matrix.get(row, col)))
				return false;
			
		return true;
	}
	
	/**
	 * Returns whether this and the compared matrix are equal in size.
	 *
	 * @param matrix the matrix
	 * @return whether this and the compared matrix are equal in size
	 */
	public default boolean equalSize(Matrix matrix) {
		return
				this.getRows()    == matrix.getRows() &&
				this.getColumns() == matrix.getColumns();
	}
	
	// SETTERS
	
	/**
	 * Sets the value at a specified row and column index to a specified value.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @param value the value
	 * @return itself
	 */
	public abstract Matrix set(int row, int col, float value);
	
	/**
	 * Scales the matrix by a factor.
	 * 
	 * @param factor the factor
	 * @return itself
	 */
	public abstract Matrix scale(float factor);
	
	// MISC
	
	/**
	 * Returns a copy of this matrix.
	 * @return a copy of this matrix
	 */
	public abstract Matrix clone();

}
