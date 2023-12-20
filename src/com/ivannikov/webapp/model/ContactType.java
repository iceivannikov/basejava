package com.ivannikov.webapp.model;

public enum ContactType {

    TELEPHONE("Telephone"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    PROFILE_GITHUB("Profile Git Hub"),
    PROFILE_LINKEDIN("Profile LinkedIn"),
    PROFILE_STACKOVERFLOW("Profile Stackoverflow"),
    WEBSITE("Website");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
