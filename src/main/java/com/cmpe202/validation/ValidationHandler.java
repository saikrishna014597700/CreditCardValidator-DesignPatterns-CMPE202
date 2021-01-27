package com.cmpe202.validation;


public interface ValidationHandler {
	public String validate(String cardNumber);
	public void nextHandler(ValidationHandler next);
}
