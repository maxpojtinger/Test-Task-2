package com.example.testtask2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HelloController {

    private String storedText = ""; // Variable to store the entered text

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("storedText", storedText); // Pass the stored text to the view
        return "index";
    }

    @PostMapping("/saveText")
    public String saveText(String text) {
        storedText = text; // Save the entered text to the variable
        return "redirect:/"; // Redirect to the form page
    }

    @PostMapping("/setRepositoryPath")
    public String setRepositoryPath(@RequestParam("repositoryPath") String repositoryPath) {
        // Save the entered repository path to a variable or database
        // For simplicity, let's store it in a static variable for now
        GitService.setRepositoryPath(repositoryPath);
        return "redirect:/git-log";
    }
}