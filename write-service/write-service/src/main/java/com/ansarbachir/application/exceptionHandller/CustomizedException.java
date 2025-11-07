/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.exceptionHandller;

/**
 *
 * @author ansar
 */
public class CustomizedException extends RuntimeException {
    
    private int code;
    private int httpStatus;
    private String message;
    
    
    public CustomizedException(){
        
    }
    public CustomizedException(String message){
        super(message);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public CustomizedException(int code, int httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    
    public String getMessage(){
       return  message;
    }
    
}
