package net.grian.spatium.matrix;

/**
 * Thrown when a matrix of size 0 or negative size is being created.
 */
public class IllegalMatrixSizeException extends MatrixException {

    private static final long serialVersionUID = 40327185242610835L;

    public IllegalMatrixSizeException() {
        super();
    }
    
    public IllegalMatrixSizeException(String s) {
        super(s);
    }

}
