package com.example.bankee.Model;

public class UserDetails {

    private String userEmail;
    private String userUID;
    private String userName;
    private String userAddress;
    private String userNumber;
    private String imageLink;
    private CardDetails cardDetails; // Added CardDetails field


    public UserDetails() {

    }

    public UserDetails(String userEmail, String userUID, String userName, String userAddress, String userNumber, String imageLink, CardDetails cardDetails) {
        this.userEmail = userEmail;
        this.userUID = userUID;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNumber = userNumber;
        this.imageLink = imageLink;
        this.cardDetails = cardDetails;
    }

    public UserDetails(String userEmail, String userUID, String userName, String userAddress, String userNumber, String imageLink) {
        this.userEmail = userEmail;
        this.userUID = userUID;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userNumber = userNumber;
        this.imageLink = imageLink;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }
}
