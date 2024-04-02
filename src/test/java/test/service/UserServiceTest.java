package test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import test.entity.User;
import test.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock private UserRepository reporsitory;
	@Mock private PhoneValidateService phoneValidator;
	@InjectMocks private UserService service;
	
	private User user;
	private User savedUser;
	@BeforeEach
	public void setUp() {
		user = User.builder().phone("123-456-7890").firstName("Princess Peach").build();
		savedUser = User.builder().phone("1234567890").firstName("Princess Peach").build();
	}
	
	@Test
	public void testAddUserSuccess() {
		when(phoneValidator.validateAndFormatPhone(anyString())).thenReturn("1234567890");
		when(reporsitory.save(any(User.class))).thenReturn(savedUser);
		
		User saved = service.addUser(user);
		assertNotNull(saved);
		assertEquals(saved.getPhone(), "1234567890");
	}
	
	@Test
	public void testFindUserByPhone() {
		when(phoneValidator.validateAndFormatPhone(anyString())).thenReturn("1234567890");
		when(reporsitory.findByPhone(anyString())).thenReturn(Optional.of(savedUser));
		
		User found = service.findUserByPhone(user.getPhone());
		assertNotNull(found);
		assertEquals(found.getPhone(), "1234567890");
	}
	
	@Test
	public void testFindUserByPhoneNotFound() {
		when(phoneValidator.validateAndFormatPhone(anyString())).thenReturn("1234567890");
		when(reporsitory.findByPhone(anyString())).thenReturn(Optional.empty());
		
		User found = service.findUserByPhone(user.getPhone());
		assertNull(found);
	}
}
