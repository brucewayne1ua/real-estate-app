package com.realestate.realestate.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.dto.ListingSearchCriteria;
import com.realestate.realestate.model.Listing;
import com.realestate.realestate.model.User;
import com.realestate.realestate.repository.UserRepository;
import com.realestate.realestate.service.ListingService;

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
    @Transactional
    public String saveListing(@ModelAttribute Listing listing,
                              @RequestParam(value = "images", required = false) MultipartFile[] images,
                              Principal principal) {

        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName());
        if (user == null) return "redirect:/login";

        listing.setOwner(user);

        // Сохраняем Listing сначала, чтобы Hibernate "подхватил" @ElementCollection
        listingService.save(listing);

        // Папка для изображений
        String uploadDir = "uploads/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        if (images != null) {
            for (MultipartFile image : images) {
                if (image != null && !image.isEmpty()) {
                    try {
                        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                        File destFile = new File(uploadDir + fileName);
                        image.transferTo(destFile);

                        // Добавляем путь к изображению и обновляем Listing
                        listing.getImagePaths().add("/uploads/" + fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Сохраняем обновленный список изображений
            listingService.save(listing);
        }

        return "redirect:/";
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

    @GetMapping("/search")
    public String searchListing(@ModelAttribute ListingSearchCriteria criteria, Model model) {
        List<Listing> listings = listingService.search(criteria);
        model.addAttribute("listings", listings);
        model.addAttribute("criteria", criteria);
        return "search_results";
    }
}
