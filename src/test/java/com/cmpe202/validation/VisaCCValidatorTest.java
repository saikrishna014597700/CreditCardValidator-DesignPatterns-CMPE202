package com.cmpe202.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class VisaCCValidatorTest {

	@Test
	public void testValidate() {
		VisaCCValidator amex = new VisaCCValidator();
		assertSame(amex.validate("4120000000000"), "VisaCC");
	}
	
	@Test
	public void testValidateCard() {
		VisaCCValidator amex = new VisaCCValidator();
		assertTrue(amex.validateCard("4120000000000"));
	}

}
