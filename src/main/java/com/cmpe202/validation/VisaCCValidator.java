package com.cmpe202.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisaCCValidator implements ValidationHandler {

	private ValidationHandler next = null;

	@Override
	public String validate(String cardNumber) {
		if(validateCard(cardNumber)) {
			String regex = "^4[0-9]{12,15}$";
			Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(cardNumber);
	        if(m.matches()) {
	        	return "VisaCC";
	        }else {
	        	if (next != null) {
					return next.validate(cardNumber);
				}
	        }
        }
		return null;
	}
	
	protected boolean validateCard(String cardNumber) {
		if(cardNumber!= null && cardNumber.length() > 0 && cardNumber.matches("[0-9]+")) {
			return true;
		}	
		return false;
	}

	@Override
	public void nextHandler(ValidationHandler next) {
		this.next = next;
	}
}
