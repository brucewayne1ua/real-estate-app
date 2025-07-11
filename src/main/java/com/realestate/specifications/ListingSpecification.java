package com.realestate.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.realestate.dto.ListingSearchCriteria;
import com.realestate.realestate.model.Listing;

import jakarta.persistence.criteria.Predicate;

public class ListingSpecification {  // <-- твой класс, имя файла совпадает

    public static Specification<Listing> withFilters(ListingSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
                String keyword = "%" + criteria.getQuery().toLowerCase() + "%";
                Predicate titlePredicate = cb.like(cb.lower(root.get("title")), keyword);
                Predicate descriptionPredicate = cb.like(cb.lower(root.get("description")), keyword);
                predicates.add(cb.or(titlePredicate, descriptionPredicate));
            } else {
                if (criteria.getTitle() != null && !criteria.getTitle().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
                }

                if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
                }
            }

            if (criteria.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), criteria.getPrice()));
            }

            if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("city")), criteria.getCity().toLowerCase()));
            }

            if (criteria.getSquareMeters() != null && !criteria.getSquareMeters().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("squareMeters")), criteria.getSquareMeters().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
