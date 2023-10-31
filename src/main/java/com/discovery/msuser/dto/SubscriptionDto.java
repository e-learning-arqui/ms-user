package com.discovery.msuser.dto;

import java.util.Date;
public class SubscriptionDto {
    private Date startDate;
    private Date deadline;
    private Long cardId;
    private Long subscriptionTypeId;
    private boolean status;

    //construtctor
    public SubscriptionDto() {}

    public SubscriptionDto(Date startDate, Date deadline, Long cardId, Long subscriptionTypeId, boolean status) {
        this.startDate = startDate;
        this.deadline = deadline;
        this.cardId = cardId;
        this.subscriptionTypeId = subscriptionTypeId;
        this.status = status;
    }

    //getters and setters
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(Long subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //TOString

    @Override
    public String toString() {
        return "SubscriptionDto{" +
                "startDate=" + startDate +
                ", deadline=" + deadline +
                ", cardId=" + cardId +
                ", subscriptionTypeId=" + subscriptionTypeId +
                ", status=" + status +
                '}';
    }

}
