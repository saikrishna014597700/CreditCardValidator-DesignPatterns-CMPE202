package com.cmpe202.parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cmpe202.CreditCard;
import com.cmpe202.factory.CreditCardFactoryAbstractClass;
import com.cmpe202.validation.AmexCCValidator;
import com.cmpe202.validation.DiscoverCCValidator;
import com.cmpe202.validation.MasterCCValidator;
import com.cmpe202.validation.ValidationHandler;
import com.cmpe202.validation.VisaCCValidator;

public class JSONFileParser implements FileParser{

	@Override
	public List<CreditCard> parseFile(String fileName) {
		List<CreditCard> listCC = new ArrayList<CreditCard>();
		JSONParser jsonParser = new JSONParser();
		 try{
		     FileReader reader = new FileReader(fileName);
	         Object obj = null;
			try {
				obj = jsonParser.parse(reader);
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
	
	         JSONArray users = (JSONArray) obj;
           
            for(int i=0;i<users.size();i++) {
	            String[] entry_array= users.get(i).toString().split(",");
	            String[] cardNumberSplit= entry_array[0].split(":");
	            String[] dateSplit= entry_array[1].split(":");
	            String[] cardholderNameSplit= entry_array[2].split(":");
	           
	            String ccnumber= cardNumberSplit[1];
	            ccnumber = ccnumber.replaceAll("^\\s+","");
	            ccnumber = ccnumber.replaceAll("\\s+$","");
	            CreditCard cd = new CreditCard();
	            cd.setCardNumber(ccnumber);
	            cd.setExpiryDate(dateSplit[1]);
	            cd.setCardHolderName(cardholderNameSplit[1]);
	            
	        	try {
		        	String cardNumber = String.format("%.0f",Double.valueOf(ccnumber));
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
	       } catch (FileNotFoundException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		return listCC;
			
	}
	
	@SuppressWarnings("unchecked")
	public String writeToFile(List<CreditCard> list, String outputFile) throws FileNotFoundException {
		JSONArray ccList = new JSONArray();
		for(CreditCard cc:list) {
		   JSONObject ccobject = new JSONObject();
	       ccobject.put("CardNumber", cc.getCardNumber());
	       ccobject.put("ExpirationDate", cc.getExpiryDate());
	       ccobject.put("NameOfCardHolder", cc.getCardHolderName());
	       ccobject.put("IsValid", cc.getIsError());
	       ccobject.put("Type", cc.getCardType());
	       ccList.add(ccobject);
		}
		try{
            FileWriter  file = new FileWriter(outputFile,false);
            ccList.writeJSONString(ccList, file);
	        file.close();
        }catch (IOException e) {
           e.printStackTrace();
           return "Failure";
       }
		return "Success";
	}

}
