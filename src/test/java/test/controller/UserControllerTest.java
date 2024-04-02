package test.controller;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import test.entity.User;
import test.service.UserService;
import test.exception.InvalidUserException;
import test.exception.DuplicatePhoneNumberException;
import test.exception.InvalidPhoneNumberException;
import test.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@Mock private UserService userService;
	@InjectMocks private UserController controller;
	
	private User user;
	private User savedUser;
	@BeforeEach
	public void setUp() {
		user = User.builder().phone("123-456-7890").firstName("Princess Peach").build();
		savedUser = User.builder().id("testId").phone("1234567890").firstName("Princess Peach").build();
	}
	
	@Test
	public void testAddUserSuccess() {
		when(userService.findUserByPhone(anyString())).thenReturn(null);
		when(userService.addUser(any(User.class))).thenReturn(savedUser);
		
		ResponseEntity<User> response = controller.addUser(user);
		User user = response.getBody();
		assertNotNull(user);
		assertEquals(user.getPhone(), "1234567890");
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithNullUser() {
		 assertThrows(InvalidUserException.class,() -> controller.addUser(null));
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithNullPhone() {
		 assertThrows(InvalidUserException.class,() -> 
		 	controller.addUser(User.builder().firstName("testFirstName").build()));
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithFirstName() {
		assertThrows(InvalidUserException.class,() -> 
		 	controller.addUser(User.builder().phone("testPhoneNumber").build()));
	}
	
	@Test
	public void testAddUserThrowsDuplicatePhoneNumberException() {
		when(userService.findUserByPhone(anyString())).thenReturn(null);
		when(userService.findUserByPhone(anyString())).thenReturn(savedUser);
		assertThrows(DuplicatePhoneNumberException.class,() -> controller.addUser(savedUser));
	}
	
	@Test
	public void testValidateUserSuccess() {
		when(userService.findUserByPhone(anyString())).thenReturn(savedUser);
		ResponseEntity<User> response = controller.validateUser("123-456-7890");
		User user = response.getBody();
		assertNotNull(user);
		assertEquals(user.getPhone(), "1234567890");
	}
	
	@Test
	public void testValidateUserThrowsInvalidPhoneNumberException() {
		assertThrows(InvalidPhoneNumberException.class,() -> controller.validateUser(null));
	}
	
	@Test
	public void testValidateUserThrowsUserNotFoundException() {
		when(userService.findUserByPhone(anyString())).thenReturn(null);
		assertThrows(UserNotFoundException.class,() -> controller.validateUser("456-123-7890"));
	}
}
