/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;

import java.util.List;

/**
 *
 * @author ansar
 */
public record PostsPageResponse(
        List<PostDTO> posts,
        int currentPage,
        int totalPages,
        long totalElements
) {}
