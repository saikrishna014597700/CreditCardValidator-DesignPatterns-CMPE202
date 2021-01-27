package com.cmpe202.parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cmpe202.CreditCard;

public class JSONFileParserTest {

	@Test
	public void testParseFile() {
		JSONFileParser xmlParser = new JSONFileParser();
		try {
			String s = xmlParser.parseFile("Sample.json").get(0).getCardType();
			assertEquals(s,"Master");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteToFile() {
		JSONFileParser xmlParser = new JSONFileParser();
		try {
			List<CreditCard> list = new ArrayList<CreditCard>();
			CreditCard card = new CreditCard();
			card.setCardNumber("5410000000000000");
			card.setCardHolderName("Alice");
			card.setExpiryDate("3/20/30");
			list.add(card);
			String s = xmlParser.writeToFile(list, "SampleOutput.json");
			assertEquals(s,"Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
