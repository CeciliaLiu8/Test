package test.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.exception.InvalidPhoneNumberException;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneValidateService {

	public String validateAndFormatPhone(String phone) {
		String formattedPhone = "";
		String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(phone);
	    boolean valid  = matcher.matches();
	    if(!valid) {
	    	throw new InvalidPhoneNumberException();
	    }
	    formattedPhone = phone.replaceAll("[^0-9]", "");
	    log.info("phone formatted from {} to {}", phone, formattedPhone);
		return formattedPhone;
	}
}
