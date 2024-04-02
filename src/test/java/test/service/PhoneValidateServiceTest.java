package test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import test.exception.InvalidPhoneNumberException;

@ExtendWith(MockitoExtension.class)
public class PhoneValidateServiceTest {
	@InjectMocks private PhoneValidateService phoneValidator;
	
	@Test
	public void testValidateAndFormatPhoneValid() {
		String formatted = phoneValidator.validateAndFormatPhone("123-456-7890");
		assertEquals(formatted, "1234567890");
	}
	
	@Test
	public void testValidateAndFormatPhoneValid2() {
		String formatted = phoneValidator.validateAndFormatPhone("1234567890");
		assertEquals(formatted, "1234567890");
	}
	
	@Test
	public void testValidateAndFormatPhoneValid3() {
		String formatted = phoneValidator.validateAndFormatPhone("123.456.7890");
		assertEquals(formatted, "1234567890");
	}
	
	@Test
	public void testValidateAndFormatPhoneValid4() {
		String formatted = phoneValidator.validateAndFormatPhone("123 456 7890");
		assertEquals(formatted, "1234567890");
	}
	
	@Test
	public void testValidateAndFormatPhoneValid5() {
		String formatted = phoneValidator.validateAndFormatPhone("(123) 456 7890");
		assertEquals(formatted, "1234567890");
	}
	
	@Test
	public void testValidateAndFormatPhoneInvalid() {
		assertThrows(InvalidPhoneNumberException.class,() -> 
			phoneValidator.validateAndFormatPhone("12345678"));
	}
	
	@Test
	public void testValidateAndFormatPhoneInvalid2() {
		assertThrows(InvalidPhoneNumberException.class,() -> 
			phoneValidator.validateAndFormatPhone("12-12-111"));
	}
}
