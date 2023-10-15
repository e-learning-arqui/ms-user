package com.discovery.msuser.dto;

import java.util.Date;

public class CardDto {
    private String bankName;
    private String number;
    private Date expiration;
    private String titular;
    private String cvv;
    public CardDto() {
    }

    public CardDto( String bankName, String number, Date expiration, String titular, String cvv) {
        this.bankName = bankName;
        this.number = number;
        this.expiration = expiration;
        this.titular = titular;
        this.cvv = cvv;
    }

    // Getters and setters


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }



    @Override
    public String toString() {
        return "CardDTO{" +
                ", bankName='" + bankName + '\'' +
                ", number='" + number + '\'' +
                ", expiration=" + expiration +
                ", titular='" + titular + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
