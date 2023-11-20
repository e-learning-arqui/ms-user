package com.discovery.msuser.entity;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "PAYMENT_CARD")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARD_ID")
    private Long cardId;

    @Column(name = "BANK_NAME", nullable = false, length = 100)
    private String bankName;

    @Column(name = "NUMBER", nullable = false, length = 30)
    private String number;

    @Column(name = "EXPIRATION", nullable = false)
    private Date expiration;

    @Column(name = "TITULAR", nullable = false, length = 100)
    private String titular;

    @Column(name = "CVV", nullable = false, length = 3)
    private String cvv;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "USER_ID")
    private Student userId;

    public Card() {
    }

    public Card(String bankName, String number, Date expiration, String titular, String cvv,boolean status , Student userId) {
        this.bankName = bankName;
        this.number = number;
        this.expiration = expiration;
        this.titular = titular;
        this.cvv = cvv;
        this.userId = userId;
        this.status = status;
    }

    // Getters and setters

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

    public Student getUserId() {
        return userId;
    }

    public void setUserId(Student userId) {
        this.userId = userId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", bankName='" + bankName + '\'' +
                ", number='" + number + '\'' +
                ", expiration=" + expiration +
                ", titular='" + titular + '\'' +
                ", cvv='" + cvv + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +

                '}';
    }
}