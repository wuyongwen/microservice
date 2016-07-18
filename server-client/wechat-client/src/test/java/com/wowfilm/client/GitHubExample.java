package com.wowfilm.client;


import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;

import java.io.IOException;
import java.util.List;

/**
 * Inspired by {@code com.example.retrofit.GitHubClient}
 */
public class GitHubExample {
    static GitHubApi connect() {
        Decoder decoder = new GsonDecoder();
        return Feign.builder()
                .decoder(decoder)
                .errorDecoder(new GitHubExample.GitHubErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(GitHubApi.class, "https://api.github.com");
    }

    static class GitHubClientError extends RuntimeException {
        private String message; // parsed from json

        @Override
        public String getMessage() {
            return message;
        }
    }

    public static void main(String... args) {
        GitHubApi github = GitHubExample.connect();

        System.out.println("Let's fetch and print a list of the contributors to this org.");
        List<GitHubApi.Contributor> contributors = github.contributors("netflix", "feign");
        for (GitHubApi.Contributor contributor : contributors) {
            System.out.println(contributor.login);
        }

        System.out.println("Now, let's cause an error.");
        try {
            github.contributors("netflix", "some-unknown-project");
        } catch (GitHubClientError e) {
            System.out.println(e.getMessage());
        }
    }

    static class GitHubErrorDecoder implements ErrorDecoder {

        final Decoder decoder;
        final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

        GitHubErrorDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                return (Exception) decoder.decode(response, GitHubClientError.class);
            } catch (IOException fallbackToDefault) {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}
