package com.cmpe202.parsers;

import java.io.FileNotFoundException;
import java.util.List;

import com.cmpe202.CreditCard;

public interface FileParser {
	public List<CreditCard> parseFile(String fileName);

	public String writeToFile(List<CreditCard> list, String outputFile) throws FileNotFoundException;
}
