package com.example.testtask2;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GitService {
    private static String repositoryPath;

    // Get the current repository path
    public static String getRepositoryPath() {
        return repositoryPath;
    }

    // Set the repository path
    public static void setRepositoryPath(String path) {
        repositoryPath = path;
    }

    // Get the Git log as a formatted string
    public String getGitLog() {
        StringBuilder gitLog = new StringBuilder();

        try {
            Repository repository = openRepository();
            Git git = new Git(repository);
            Iterable<RevCommit> commits = git.log().all().call();

            // Iterate through commits and append information to gitLog
            for (RevCommit commit : commits) {
                appendGitLogInfo(gitLog, commit);
            }

        } catch (GitAPIException | IOException e) {
            // Handle exceptions and append an error message to gitLog
            handleGitLogError(gitLog, e);
        }

        return gitLog.toString();
    }

    // Append information from a commit to the gitLog StringBuilder
    private void appendGitLogInfo(StringBuilder gitLog, RevCommit commit) {
        gitLog.append("Commit: ").append(commit.getId().getName()).append("<br>");
        gitLog.append("Author: ").append(commit.getAuthorIdent().getName()).append("<br>");
        gitLog.append("Date: ").append(commit.getAuthorIdent().getWhen()).append("<br>");
        gitLog.append("Message: ").append(commit.getFullMessage()).append("<br><br>");
    }

    // Handle exceptions when retrieving Git log and append an error message to gitLog
    private void handleGitLogError(StringBuilder gitLog, Exception e) {
        e.printStackTrace();
        gitLog.append("Error retrieving Git log: ").append(e.getMessage()).append("<br>");
        gitLog.append("Maybe you didn't type in your directory correctly");
    }

    // Open the Git repository using RepositoryBuilder
    private Repository openRepository() throws IOException {
        return new FileRepositoryBuilder().setGitDir(new File(repositoryPath, ".git")).build();
    }
}
