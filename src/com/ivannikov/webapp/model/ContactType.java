package com.ivannikov.webapp.model;

public enum ContactType {

    TELEPHONE("Telephone"),
    SKYPE("Skype"){
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("E-mail"){
        @Override
        public String toHtml0(String value) {
            return "<a href='email:" + value + "'>" + value + "</a>";
        }
    },
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

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
