package com.example.testtask2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HelloController {

    // Variable to store the entered text
    private static String storedText = "";

    // Display the form with the stored text
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("storedText", storedText);
        return "index";
    }

    // Save the entered text and redirect to the form page
    @PostMapping("/saveText")
    public String saveText(@RequestParam("text") String text) {
        storedText = text;
        return "redirect:/";
    }

    // Set the Git repository path and redirect to the Git log page
    @PostMapping("/setRepositoryPath")
    public String setRepositoryPath(@RequestParam("repositoryPath") String repositoryPath) {
        // Save the entered repository path (for simplicity, stored in a static variable)
        GitService.setRepositoryPath(repositoryPath);
        return "redirect:/git-log";
    }
}
