package com.cmpe202.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cmpe202.CreditCard;
import com.cmpe202.factory.CreditCardFactoryAbstractClass;
import com.cmpe202.validation.AmexCCValidator;
import com.cmpe202.validation.DiscoverCCValidator;
import com.cmpe202.validation.MasterCCValidator;
import com.cmpe202.validation.ValidationHandler;
import com.cmpe202.validation.VisaCCValidator;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVFileParser implements FileParser{

	@Override
	public List<CreditCard> parseFile(String fileName) {
		List<CreditCard> listCC = new ArrayList<CreditCard>();
		try { 
			  
	        // Create an object of filereader 
	        // class with CSV file as a parameter. 
	        FileReader filereader = new FileReader(fileName); 
	  
	        // create csvReader object passing 
	        // file reader as a parameter 
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord;
	        csvReader.readNext();
	        // we are going to read data line by line
	        
	        while ((nextRecord = csvReader.readNext()) != null) { 
	        	CreditCard cd = new CreditCard();
	        	cd.setCardNumber(nextRecord[0]);
	            cd.setExpiryDate(nextRecord[1]);
	            cd.setCardHolderName(nextRecord[2]);
	        	try {
		        	String cardNumber = String.format("%.0f",Double.valueOf(nextRecord[0]));
		        	
		            cd.setCardNumber(cardNumber);
		            
		            ValidationHandler visaHandler = new VisaCCValidator();
					ValidationHandler masterHandler = new MasterCCValidator();
					ValidationHandler amexHandler = new AmexCCValidator();
					ValidationHandler discoverHandler = new DiscoverCCValidator();	
					
					visaHandler.nextHandler(masterHandler);
					masterHandler.nextHandler(amexHandler);
					amexHandler.nextHandler(discoverHandler);
					
					String cc = visaHandler.validate(cardNumber);
					if(cc==null) {
						cd.setCardType("Invalid Card");
						cd.setIsError("Error");
					}else {
						CreditCardFactoryAbstractClass cdf = new CreditCardFactoryAbstractClass();
						String name = cdf.getCreditCardType(cc).getClass().getSimpleName();
						cd.setCardType(name);
						cd.setIsError("No Error");
					}
				}catch(NumberFormatException e){
					cd.setCardType("Invalid Card");
					cd.setIsError("Error");
				}
				listCC.add(cd);
	        } 
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    }
		return listCC;
	}
	
	public String writeToFile(List<CreditCard> list, String outputFile) throws FileNotFoundException {
		File file = new File(outputFile); 
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        // adding header to csv 
	        String[] header = { "CardNumber", "ExpirationDate", "NameOfCardHolder", "CardType", "Error" };
	        writer.writeNext(header); 
	        
	        for(CreditCard cd : list) {
	        	String[] row = new String[5];
	        	row[0] = cd.getCardNumber();
	        	row[1] = cd.getExpiryDate();
    			row[2] = cd.getCardHolderName();
    			row[3] = cd.getCardType();
    			row[4] = cd.getIsError();
    			writer.writeNext(row); 
	        }
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	        return "Failure";
	    } 
	    return "Success";
	}

}
