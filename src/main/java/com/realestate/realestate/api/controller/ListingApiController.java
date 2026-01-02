package com.realestate.realestate.api.controller;

import com.realestate.dto.ListingDto;
import com.realestate.realestate.model.Listing;
import com.realestate.realestate.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingApiController {

    private final ListingService listingService;

    // GET /api/listings - все объявления
    @GetMapping
    public List<ListingDto> getAll() {
        return listingService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    // GET /api/listings/{id} - одно объявление по ID
    @GetMapping("/{id}")
    public ListingDto getById(@PathVariable Long id) {
        Listing listing = listingService.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        return toDto(listing);
    }

    // POST /api/listings - создать новое объявление
    @PostMapping
    public ListingDto create(@RequestBody ListingDto dto) {
        Listing listing = new Listing();
        listing.setTitle(dto.title());
        listing.setDescription(dto.description());
        listing.setPrice(dto.price());
        listing.setImagePaths(dto.imagePaths());
        listingService.save(listing);
        return toDto(listing);
    }

    // PUT /api/listings/{id} - редактировать существующее объявление
    @PutMapping("/{id}")
    public ListingDto update(@PathVariable Long id, @RequestBody ListingDto dto) {
        Listing listing = listingService.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        listing.setTitle(dto.title());
        listing.setDescription(dto.description());
        listing.setPrice(dto.price());
        listing.setImagePaths(dto.imagePaths());
        return toDto(listing);
    }

    // DELETE /api/listings/{id} - удалить объявление
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Listing listing = listingService.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        listingService.delete(listing);
    }

    // Преобразование Entity → DTO
    private ListingDto toDto(Listing l) {
        return new ListingDto(
                l.getId(),
                l.getTitle(),
                l.getDescription(),
                l.getPrice(),
                l.getImagePaths()
        );
    }
}
