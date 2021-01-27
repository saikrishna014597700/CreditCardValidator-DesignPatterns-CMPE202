package com.cmpe202.parsers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.cmpe202.CreditCard;
import com.cmpe202.factory.CreditCardFactoryAbstractClass;
import com.cmpe202.validation.AmexCCValidator;
import com.cmpe202.validation.DiscoverCCValidator;
import com.cmpe202.validation.MasterCCValidator;
import com.cmpe202.validation.ValidationHandler;
import com.cmpe202.validation.VisaCCValidator;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;  

import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element; 

public class XMLFileParser implements FileParser{

	@Override
	public List<CreditCard> parseFile(String fileName) {
		List<CreditCard> listCC = new ArrayList<CreditCard>();
		try {
	         File inputFile = new File(fileName);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("row");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            CreditCard cd = new CreditCard();
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                cd.setCardNumber(eElement.getElementsByTagName("CardNumber").item(0).getTextContent());
		            cd.setExpiryDate(eElement.getElementsByTagName("ExpirationDate").item(0).getTextContent());
		            cd.setCardHolderName(eElement.getElementsByTagName("NameOfCardholder").item(0).getTextContent());
	                try {
			        	String cardNumber = String.format("%.0f",Double.valueOf(eElement.getElementsByTagName("CardNumber").item(0).getTextContent()));
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
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return listCC;
		
	}

	@Override
	public String writeToFile(List<CreditCard> list, String outputFile) throws FileNotFoundException {
		// TODO Auto-generated method stub
		try {
			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
	
	        DocumentBuilder documentBuilder = df.newDocumentBuilder();
	
	        Document document = documentBuilder.newDocument();
	
	        Element root = document.createElement("root");
	        document.appendChild(root);
	        
	        for(CreditCard card: list) {
		        Element record = document.createElement("row");
		
		        root.appendChild(record);
		
		        Element cc = document.createElement("CardNumber");
		        cc.appendChild(document.createTextNode(card.getCardNumber()));
		        record.appendChild(cc);
		
		        Element expd = document.createElement("ExpirationDate");
		        expd.appendChild(document.createTextNode(card.getExpiryDate()));
		        record.appendChild(expd);
		
		        Element name = document.createElement("NameOfCardHolder");
		        name.appendChild(document.createTextNode(card.getCardHolderName()));
		        record.appendChild(name);
		
		        Element valid = document.createElement("Error");
		        valid.appendChild(document.createTextNode(card.getIsError()));
		        record.appendChild(valid);
		        
		        Element cctype = document.createElement("CardType");
		        cctype.appendChild(document.createTextNode(card.getCardType()));
		        record.appendChild(cctype);
	        }
	
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        document.setXmlStandalone(true);
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(new File(outputFile));
	
	        transformer.transform(domSource, streamResult);

	    } catch (ParserConfigurationException pce) {
	        pce.printStackTrace();
	        return "Failure";
	    } catch (TransformerException tfe) {
	        tfe.printStackTrace();
	        return "Failure";
	    }
			return "Success";
		}

}
