package com.cmpe202.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class AmexCCValidatorTest extends AmexCCValidator {

	@Test
	public void testValidate() {
		AmexCCValidator amex = new AmexCCValidator();
		System.out.print(amex.validate("341000000000000"));
		assertSame(amex.validate("341000000000000"), "AmExCC");
	}
	
	@Test
	public void testValidateCard() {
		AmexCCValidator amex = new AmexCCValidator();
		assertTrue(amex.validateCard("341000000000000"));
	}

}
