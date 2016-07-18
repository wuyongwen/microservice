package com.wowfilm.client;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * Created by wen on 2016/7/15 16:07.
 */
public interface GitHubApi {
    class Repository {
        String name;
    }

    class Contributor {
        String login;
    }

    @RequestLine("GET /users/{username}/repos?sort=full_name")
    List<Repository> repos(@Param("username") String owner);

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
