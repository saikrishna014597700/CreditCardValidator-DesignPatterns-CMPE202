package com.cmpe202.validation;

import static org.junit.Assert.*;

import org.junit.Test;


public class DiscoverCCValidatorTest extends DiscoverCCValidator {

	@Test
	public void testValidate() {
		DiscoverCCValidator amex = new DiscoverCCValidator();
		assertSame(amex.validate("6011000000000000"), "DiscoverCC");
	}
	
	@Test
	public void testValidateCard() {
		DiscoverCCValidator amex = new DiscoverCCValidator();
		assertFalse(amex.validateCard("aaa"));
	}

}
