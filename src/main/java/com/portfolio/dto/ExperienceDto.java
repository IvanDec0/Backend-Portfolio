package com.portfolio.dto;

import javax.validation.constraints.NotBlank;

public class ExperienceDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    @NotBlank
    private String dateStart;

    private String dateEnd;

    public ExperienceDto() {
    }

    public ExperienceDto(@NotBlank String name, @NotBlank String description, @NotBlank String dateStart, String dateEnd) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
