package com.portfolio.dto;

import javax.validation.constraints.NotBlank;

public class ProjectDto {

    @NotBlank
    private String name;

    @NotBlank
    private String paragraph;

    @NotBlank
    private String img;

    @NotBlank
    private String github;

    public ProjectDto() {
    }

    public ProjectDto(@NotBlank String name, @NotBlank String paragraph, @NotBlank String img, @NotBlank String github) {
        this.name = name;
        this.paragraph = paragraph;
        this.img = img;
        this.github = github;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }
}
