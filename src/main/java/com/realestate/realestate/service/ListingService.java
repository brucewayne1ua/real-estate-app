package com.realestate.realestate.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.realestate.dto.ListingSearchCriteria;
import com.realestate.realestate.model.Listing;
import com.realestate.realestate.repository.ListingRepository;
import com.realestate.specifications.ListingSpecification;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

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
   

    public List<Listing> search(ListingSearchCriteria criteria) {
        Specification<Listing> spec = ListingSpecification.withFilters(criteria);
        return listingRepository.findAll(spec);
    }

    public void delete(Listing listing) {
        listingRepository.delete(listing);
    }
}


