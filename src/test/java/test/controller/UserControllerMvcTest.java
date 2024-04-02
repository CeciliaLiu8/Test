package test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import test.entity.User;
import test.service.UserService;

@WebMvcTest({UserController.class})
@ActiveProfiles("test")
public class UserControllerMvcTest {
	
	@MockBean private UserService userService;
	@Autowired private MockMvc mockMvc;
	
	private User user;
	private User savedUser;
	@BeforeEach
	public void setUp() {
		user = User.builder().phone("123-456-7890").firstName("Princess Peach").build();
		savedUser = User.builder().id("testId").phone("1234567890").firstName("Princess Peach").build();
	}
	
	@Test
	public void testAddUserSuccess() throws Exception {
		when(userService.findUserByPhone(anyString())).thenReturn(null);
		when(userService.addUser(any(User.class))).thenReturn(savedUser);
		final String userAsJson = toJsonString(user);
		
		MvcResult mvcResult = mockMvc
								.perform(post("/users")
										.content(userAsJson)
										.contentType(MediaType.APPLICATION_JSON_VALUE))
								.andExpect(status().isOk()).andReturn();
		assertEquals(mvcResult.getResponse().getContentAsString(), toJsonString(savedUser));
	}
	
	private String toJsonString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithNullUser() throws Exception {
		byte[] content = null;		
		mockMvc
        	.perform(
        			post("/users")
        			.content(content)
        			.contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithNullPhone() throws Exception {
		user.setPhone(null);
		mockMvc
	        .perform(
	            post("/users")
	                .content(toJsonString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testAddUserThrowsInvalidUserExceptionWithNullFirstName() throws Exception {
		user.setFirstName(null);
		mockMvc
	        .perform(
	            post("/users")
	                .content(toJsonString(user))
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
	        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testValidateUserSuccess() throws Exception {
		when(userService.findUserByPhone(anyString())).thenReturn(savedUser);
		MvcResult mvcResult = 
				mockMvc.perform(put("/users/{phoneNumber}", "123-456-7890"))
        			.andExpect(status().isOk()).andReturn();
		
		assertEquals(mvcResult.getResponse().getContentAsString(), toJsonString(savedUser));
	}
	
}
