package com.example.testtask2;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitService {
    private static String repositoryPath;

    public static String getRepositoryPath() {
        return repositoryPath;
    }

    public static void setRepositoryPath(String path) {
        repositoryPath = path;
    }

    public String getGitLog() {
        StringBuilder gitLog = new StringBuilder();

        try (Repository repository = openRepository()) {
            Git git = new Git(repository);
            Iterable<RevCommit> commits = git.log().all().call();

            for (RevCommit commit : commits) {
                gitLog.append("Commit: ").append(commit.getId().getName()).append("<br>");
                gitLog.append("Author: ").append(commit.getAuthorIdent().getName()).append("<br>");
                gitLog.append("Date: ").append(commit.getAuthorIdent().getWhen()).append("<br>");
                gitLog.append("Message: ").append(commit.getFullMessage()).append("<br><br>");
            }

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            gitLog.append("Error retrieving Git log: ").append(e.getMessage()).append("<br>").append("Maybe you didn't type in your directory correctly");
        }

        return gitLog.toString();
    }

    private Repository openRepository() throws IOException {
        // Using RepositoryBuilder to build the Repository instance
        RepositoryBuilder builder = new RepositoryBuilder();
        Repository repository = builder
                .setGitDir(new File(repositoryPath, ".git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();

        return repository;
    }
}
