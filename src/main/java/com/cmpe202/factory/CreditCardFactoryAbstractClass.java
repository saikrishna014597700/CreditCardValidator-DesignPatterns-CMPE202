package com.cmpe202.factory;


public class CreditCardFactoryAbstractClass {
	public CreditCardFactory getCreditCardType(String cardType) {
		  if(cardType == null){
	         return null;
	      }		
	      if(cardType.equalsIgnoreCase("AmExCC"))
	      {
	         return new AmEx();
	         
	      } else if(cardType.equalsIgnoreCase("DiscoverCC"))
	      {
	         return new Discover();
	         
	      } else if(cardType.equalsIgnoreCase("MasterCC"))
	      {
	         return new Master();
	      } else if(cardType.equalsIgnoreCase("VisaCC"))
	      {
		         return new Visa();
		  }
	      
	      return null;
	}
}
