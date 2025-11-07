/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;

import java.util.Objects;

/**
 *
 * @author ansar
 */
public record  LoginRequestDto(String email,String password) {

public boolean isEmailNull(){
    return (this.email() == null || Objects.equals(this.email(), "")); 
}

public boolean isPasswordNull(){
    return (this.password() == null || Objects.equals(this.password(), "")); 
}

public boolean isRequestNull(){
    return isEmailNull() || isPasswordNull();
}
}
