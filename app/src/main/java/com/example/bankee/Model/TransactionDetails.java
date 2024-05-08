package com.example.bankee.Model;

public class TransactionDetails {


    private String senderEmail;
    private String reciverEmail;
    private int sendAmmount;
    private long transactionTime;
    private String transactionId;
    private TranSactionType type;

    public TransactionDetails() {

    }

    public TransactionDetails(String senderEmail, String reciverEmail, int sendAmmount, long transactionTime, String transactionId, TranSactionType type) {
        this.senderEmail = senderEmail;
        this.reciverEmail = reciverEmail;
        this.sendAmmount = sendAmmount;
        this.transactionTime = transactionTime;
        this.transactionId = transactionId;
        this.type = type;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReciverEmail() {
        return reciverEmail;
    }

    public void setReciverEmail(String reciverEmail) {
        this.reciverEmail = reciverEmail;
    }

    public int getSendAmmount() {
        return sendAmmount;
    }

    public void setSendAmmount(int sendAmmount) {
        this.sendAmmount = sendAmmount;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TranSactionType getType() {
        return type;
    }

    public void setType(TranSactionType type) {
        this.type = type;
    }
}


