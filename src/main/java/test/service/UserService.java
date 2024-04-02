package test.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.entity.User;
import test.repository.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PhoneValidateService phoneValidateService;
	
	public User addUser(User user) {
		user.setPhone(phoneValidateService.validateAndFormatPhone(user.getPhone()));
		log.info("user is valid");
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	public User findUserByPhone(final String phone) {
		String formattedPhone = phoneValidateService.validateAndFormatPhone(phone);
		return userRepository.findByPhone(formattedPhone).orElse(null);
	}
}
