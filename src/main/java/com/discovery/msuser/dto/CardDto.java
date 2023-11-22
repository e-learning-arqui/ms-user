package com.discovery.msuser.dto;

import java.util.Date;

public class CardDto {

    private Long id;
    private String bankName;
    private String number;
    private Date expiration;
    private String titular;
    private String cvv;

    private String userKeycloakId;
    public CardDto() {
    }

    public CardDto(Long id,String bankName, String number, Date expiration, String titular, String cvv, String userKeycloakId) {
        this.id = id;
        this.bankName = bankName;
        this.number = number;
        this.expiration = expiration;
        this.titular = titular;
        this.cvv = cvv;
        this.userKeycloakId = userKeycloakId;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserKeycloakId() {
        return userKeycloakId;
    }

    public void setUserKeycloakId(String userKeycloakId) {
        this.userKeycloakId = userKeycloakId;
    }



    @Override
    public String toString() {
        return "CardDTO{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", number='" + number + '\'' +
                ", expiration=" + expiration +
                ", titular='" + titular + '\'' +
                ", cvv='" + cvv + '\'' +
                ", userKeycloakId='" + userKeycloakId + '\'' +
                '}';
    }
}
