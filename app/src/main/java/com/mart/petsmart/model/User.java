package com.mart.petsmart.model;

public class User {
    private String profileId;
    private String profileName;
    private String profileImageUrl;
    private String userEmail;

    public User(String profileName, String profileImageUrl, String userEmail) {
        this.profileName = profileName;
        this.profileImageUrl = profileImageUrl;
        this.userEmail = userEmail;
    }

    public User() {
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
