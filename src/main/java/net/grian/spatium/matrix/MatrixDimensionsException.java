package net.grian.spatium.matrix;

/**
 * Thrown when operations of matrices are used with matrices that are incompatible in size. For instance, this
 * exception is thrown when two matrices that are not equal in size are being added.
 */
public class MatrixDimensionsException extends MatrixException {
	
	private static final long serialVersionUID = 2434595838278790102L;
	
	public MatrixDimensionsException() {
        super();
    }
    
    public MatrixDimensionsException(String message) {
        super(message);
    }
    
    public MatrixDimensionsException(Matrix a, Matrix b) {
        super(
            "["+a.getRows()+","+a.getColumns()+"]" +
            " & " +
            "["+b.getRows()+","+b.getColumns()+"]");
    }

}
