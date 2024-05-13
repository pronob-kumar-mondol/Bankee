package com.example.bankee.Model;

public class CardDetails {
    private String cardType;
    private String cardNumber;
    private String expDate;
    private String cvvCode;
    private int balance;


    public CardDetails() {
    }

    public CardDetails(String cardType, String cardNumber, String expDate, String cvvCode, int balance) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cvvCode = cvvCode;
        this.balance = balance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
