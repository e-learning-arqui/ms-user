package com.discovery.msuser.dto;

public class ResponseDto<T> {

    private String errorMessage;
    private String code;
    private T response;

    public ResponseDto(String errorMessage, String code, T response) {
        this.errorMessage = errorMessage;
        this.code = code;
        this.response = response;
    }

    public ResponseDto() {
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}