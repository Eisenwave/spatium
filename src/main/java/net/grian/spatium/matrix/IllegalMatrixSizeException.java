package net.grian.spatium.matrix;

public class IllegalMatrixSizeException extends MatrixException {
	
	private static final long serialVersionUID = 40327185242610835L;
	
	public IllegalMatrixSizeException() {
        super();
    }
    
    public IllegalMatrixSizeException(String s) {
        super(s);
    }

}
