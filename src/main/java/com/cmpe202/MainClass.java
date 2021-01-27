package com.cmpe202;

import java.io.FileNotFoundException;
import java.util.List;

import com.cmpe202.parsers.CSVFileParser;
import com.cmpe202.parsers.JSONFileParser;
import com.cmpe202.parsers.Parser;
import com.cmpe202.parsers.XMLFileParser;

public class MainClass {

	public static void main(String[] args) {
		System.out.print(args);
		String inputFile = args[0];
		String outputfile = args[1];
		Parser p = new Parser();
		if(inputFile.endsWith(".xml")) {
			p.changeParser(new XMLFileParser());
		}
		else if(inputFile.endsWith(".json")) {
			p.changeParser(new JSONFileParser());
		}
		else if(inputFile.endsWith(".csv")) {
			p.changeParser(new CSVFileParser());
		}
		List<CreditCard> c = p.parseFile(inputFile);
		try {
			p.writeToFile(c, outputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
