package talentboost.vehicle.assembly.common;
/**
 * {@code} Class to communicate to the user that an invalid argument and/or command was givens
 * @author rados
 *
 */
public class InvalidCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7376877938829034790L;

	public InvalidCommandException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
}
