package net.grian.spatium.matrix;

/**
 * Thrown when a matrix' element is being accessed with an index that is not within the boundaries of the matrix.
 */
public class MatrixIndexOutOfBoundsException extends MatrixException {
	
	private static final long serialVersionUID = -5803847998369962269L;
	
    public MatrixIndexOutOfBoundsException() {
        super();
    }
    
    public MatrixIndexOutOfBoundsException(String s) {
        super(s);
    }

}
