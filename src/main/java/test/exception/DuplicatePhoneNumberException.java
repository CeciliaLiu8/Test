package test.exception;

public class DuplicatePhoneNumberException extends RuntimeException {
	private static final long serialVersionUID = 2140534627434127608L;
	private static final String MESSAGE = "Duplicate phone number: %s";
  
	public DuplicatePhoneNumberException(String phone) {
		super(String.format(MESSAGE, phone).trim());
	}
}