package test.exception;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = -5539167694306361013L;
	private static final String MESSAGE = "Invalid user";
  
	public InvalidUserException() {
		super(MESSAGE);
	}
}
