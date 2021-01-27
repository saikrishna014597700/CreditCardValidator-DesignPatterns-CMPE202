package com.cmpe202.parsers;

import java.io.FileNotFoundException;
import java.util.List;

import com.cmpe202.CreditCard;

public class Parser {

	private FileParser parser;

	public void changeParser(FileParser parser) {
		this.parser = parser;
	}
	
	public List<CreditCard> parseFile(String fileName) {
		return parser.parseFile(fileName);
	}
	
	public String writeToFile(List<CreditCard> list, String outputFile) throws FileNotFoundException {
		return parser.writeToFile(list, outputFile);
	}
	
	public String getParserName() {
		return parser.getClass().getSimpleName();
	}

}
