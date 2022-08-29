package com.portfolio.dto;

import javax.validation.constraints.NotBlank;

public class PersonDto {

    @NotBlank
    private String title;

    @NotBlank
    private String paragraph;

    @NotBlank
    private String url;

    @NotBlank
    private String email;

    @NotBlank
    private String github;

    @NotBlank
    private String linkedin;

    public PersonDto() {
    }

    public PersonDto(@NotBlank String title, @NotBlank String paragraph, @NotBlank String url, @NotBlank String email, @NotBlank String github, @NotBlank String linkedin) {
        this.title = title;
        this.paragraph = paragraph;
        this.url = url;
        this.email = email;
        this.github = github;
        this.linkedin = linkedin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
