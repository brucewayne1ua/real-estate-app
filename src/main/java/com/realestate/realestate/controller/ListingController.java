package com.realestate.realestate.controller;

import com.realestate.realestate.model.Listing;
import com.realestate.realestate.model.User;
import com.realestate.realestate.repository.UserRepository;
import com.realestate.realestate.service.ListingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ListingController {

    private final ListingService listingService;
    private final UserRepository userRepository;

    @Autowired
    public ListingController(ListingService listingService, UserRepository userRepository) {
        this.listingService = listingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("listings", listingService.getAll());
        return "index";
    }

    @GetMapping("/listings/new")
    public String newListingForm(Model model) {
        model.addAttribute("listing", new Listing());
        return "new_listing";
    }

    @PostMapping("/listings")
    public String saveListing(@ModelAttribute Listing listing, Principal principal) {
        User user = (User) userRepository.findByEmail(principal.getName());
        Optional<User> optionalUser = Optional.ofNullable(user);
        if (optionalUser.isPresent()) {
            listing.setOwner(optionalUser.get());
            listingService.save(listing);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/listings/{id}")
    public String viewListing(@PathVariable Long id, Model model) {
        Optional<Listing> optionalListing = listingService.findById(id);
        if (optionalListing.isPresent()) {
            model.addAttribute("listing", optionalListing.get());
            return "view_listing";
        } else {
            return "redirect:/";
        }
    }
}
