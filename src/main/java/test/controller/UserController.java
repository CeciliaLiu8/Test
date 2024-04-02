package test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.entity.User;
import test.exception.DuplicatePhoneNumberException;
import test.exception.InvalidPhoneNumberException;
import test.exception.InvalidUserException;
import test.exception.UserNotFoundException;
import test.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  
  @PostMapping("/users")
  public ResponseEntity<User> addUser(@RequestBody  User user) {
	  if(user == null || StringUtils.isBlank(user.getPhone()) || StringUtils.isBlank(user.getFirstName())) {
		  throw new InvalidUserException();
	  }
	  User foundUser = userService.findUserByPhone(user.getPhone());
	  if(foundUser != null) {
		  throw new DuplicatePhoneNumberException(user.getPhone());
	  }
	  return  ResponseEntity.ok().body(userService.addUser(user));
  }
  
  @PutMapping("/users/{phoneNumber}")
  public ResponseEntity<User> validateUser(@PathVariable(value = "phoneNumber") String phoneNumber) {
	  if (StringUtils.isBlank(phoneNumber)) {
		  throw new InvalidPhoneNumberException();
	  }
	  log.info("finding phoneNumber: "+phoneNumber);
	  User foundUser = userService.findUserByPhone(phoneNumber);
	  if(foundUser == null) {
		  throw new UserNotFoundException(phoneNumber);
	  }
	  return  ResponseEntity.ok().body(foundUser);
  }
}
