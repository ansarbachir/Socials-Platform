/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author ansar
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PostStatusEnum {
    
UNDER_APPROVAL("Under approval"),
APPROVE("Approve"),
Reject("Reject");

private String status;
    
}
