package com.realestate.realestate.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.realestate.model.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
