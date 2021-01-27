package com.cmpe202;

public class CreditCard {
	
	private String cardNumber;
	private String expirationDate;
	private String nameOfCardHolder;
	private String cardType;
	private String isError;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expirationDate;
	}

	public void setExpiryDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardHolderName() {
		return nameOfCardHolder;
	}

	public void setCardHolderName(String nameOfCardHolder) {
		this.nameOfCardHolder = nameOfCardHolder;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
}
