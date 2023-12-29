package com.example.testtask2;

import com.example.testtask2.GitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GitLogController {

    @Autowired
    private GitService gitService;

    @GetMapping("/git-log")
    public String showGitLog(Model model) {
        String repositoryPath = gitService.getRepositoryPath();
        if (repositoryPath != null) {
            String commitLog = gitService.getGitLog();
            model.addAttribute("commitLog", commitLog);
            return "git-log";
        } else {
            // Redirect to the main page with an error message
            model.addAttribute("error", "Repository path not set.");
            return "redirect:/";
        }
    }
}
