package com.portfolio.dto;

import javax.validation.constraints.NotBlank;

public class SkillDto {

    @NotBlank
    private String name;
    @NotBlank
    private String icon;

    public SkillDto() {
    }

    public SkillDto(@NotBlank String name, @NotBlank String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
