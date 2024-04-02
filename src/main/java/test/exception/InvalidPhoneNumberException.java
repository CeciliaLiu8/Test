package test.exception;

public class InvalidPhoneNumberException extends RuntimeException {
	private static final long serialVersionUID = -3140225888553353801L;
	private static final String MESSAGE = "Invalid phone number";
  
	public InvalidPhoneNumberException() {
		super(MESSAGE);
	}
}
