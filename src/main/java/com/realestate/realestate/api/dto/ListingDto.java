package com.realestate.realestate.api.dto;

import java.math.BigDecimal;
import java.util.List;

// DTO только для API
public record ListingDto(
        Long id,
        String title,
        String description,
        BigDecimal price,
        List<String> imagePaths
) {}
