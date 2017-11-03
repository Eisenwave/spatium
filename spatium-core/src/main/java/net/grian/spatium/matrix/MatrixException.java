package net.grian.spatium.matrix;

/**
 * Thrown when a {@link Matrix} related problem occurs.
 */
public class MatrixException extends RuntimeException {
	
	private static final long serialVersionUID = 2455105537174482147L;
	
	public MatrixException() {
        super();
    }
    
    public MatrixException(String message) {
        super(message);
    }

}
