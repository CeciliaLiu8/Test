package test.exception;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3346625787740582957L;
	private static final String MESSAGE = "User with phone number: %s, is not found";
	  
	public UserNotFoundException(String phone) {
		super(String.format(MESSAGE, phone).trim());
	}
}
