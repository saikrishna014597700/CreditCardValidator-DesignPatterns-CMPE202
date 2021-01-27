package com.cmpe202.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class MasterCCValidatorTest {

	@Test
	public void testValidate() {
		MasterCCValidator amex = new MasterCCValidator();
		assertSame(amex.validate("5410000000001111"), "MasterCC");
	}
	
	@Test
	public void testValidateCard() {
		MasterCCValidator amex = new MasterCCValidator();
		assertTrue(amex.validateCard("5410000000001111"));
	}

}
