package com.realestate.dto;
import java.math.BigDecimal;
import java.util.List;

public record ListingDto(
    Long id,
        String title,
        String description,
        Double price,          // ← совпадает с Entity
        List<String> imagePaths
) {}
