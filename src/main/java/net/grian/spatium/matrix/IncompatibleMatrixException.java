package net.grian.spatium.matrix;

public class IncompatibleMatrixException extends MatrixException {
	
	private static final long serialVersionUID = 2434595838278790102L;
	
	public IncompatibleMatrixException() {
        super();
    }
    
    public IncompatibleMatrixException(String message) {
        super(message);
    }
    
    public IncompatibleMatrixException(Matrix a, Matrix b) {
        super(
        		"["+a.getRows()+","+a.getColumns()+"]" +
        		" & " +
        		"["+b.getRows()+","+b.getColumns()+"]");
    }

}
