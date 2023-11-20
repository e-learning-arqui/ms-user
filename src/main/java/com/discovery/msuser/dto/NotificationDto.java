package com.discovery.msuser.dto;

import java.util.Date;

public class NotificationDto {

    private String message;
    private Date date;

    public NotificationDto() {
    }

    public NotificationDto(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
