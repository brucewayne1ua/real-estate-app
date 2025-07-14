package com.realestate.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.realestate.realestate.model.Listing;
import com.realestate.realestate.model.User;
import com.realestate.realestate.repository.ListingRepository;
import com.realestate.realestate.repository.UserRepository;

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

    @GetMapping("/user_profile/{id}")
    public String showUserProfile (@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);

        List<Listing> userListings = listingRepository.findByOwner(user);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        model.addAttribute("userListings", userListings);
        return "user_profile";
    }

    @GetMapping("/chats")
    public String userChats() {
        return "chats";
    }
}
