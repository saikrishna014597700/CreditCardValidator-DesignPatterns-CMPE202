package com.cmpe202.parsers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cmpe202.CreditCard;

public class XMLFileParserTest {

	@Test
	public void testParseFile() {
		XMLFileParser xmlParser = new XMLFileParser();
		try {
			String s = xmlParser.parseFile("Sample.xml").get(1).getCardType();
			assertEquals(s,"Visa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteToFile() {
		XMLFileParser xmlParser = new XMLFileParser();
		try {
			List<CreditCard> list = new ArrayList<CreditCard>();
			CreditCard card = new CreditCard();
			card.setCardNumber("5410000000000000");
			card.setCardHolderName("Alice");
			card.setExpiryDate("3/20/30");
			list.add(card);
			String s = xmlParser.writeToFile(list, "SampleOutput.xml");
			assertEquals(s,"Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
