package com.realestate.realestate.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.realestate.realestate.model.User;
import com.realestate.realestate.repository.UserRepository;
import com.realestate.realestate.repository.ListingRepository;
import com.realestate.realestate.model.Listing;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @Autowired
    public UserController(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "redirect:/login";
        }

        List<Listing> userListings = listingRepository.findByOwner(user);

        model.addAttribute("user", user);
        model.addAttribute("userListings", userListings);

        return "profile";
    }

    @GetMapping("/chats")
    public String userChats() {
        return "chats";
    }
}
