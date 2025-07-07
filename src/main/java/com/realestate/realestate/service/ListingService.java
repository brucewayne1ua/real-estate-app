package com.realestate.realestate.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.realestate.model.Listing;
import com.realestate.realestate.repository.ListingRepository;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    // Просто возвращаем список Listing, без кастинга
    public List<Listing> getAll() {
        return listingRepository.findAll();
    }

    public void save(Listing listing) {
        listingRepository.save(listing);
    }

    // Просто возвращаем Optional<Listing>, без кастинга
    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }
}
