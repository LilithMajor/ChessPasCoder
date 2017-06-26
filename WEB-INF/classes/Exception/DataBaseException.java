package Exception;

public class DataBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7130564424160539799L;

	public DataBaseException(String string) {
		super(string);
	}

	public DataBaseException() {
		super("Bug in database");
	}
}
