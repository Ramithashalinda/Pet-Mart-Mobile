package com.mart.petsmart.model;

import java.util.Date;

public class PostViewModel {

    private String postByProfileName;
    private String postByProfileImageUrl;
    private String title;
    private String postImageUrl;
    private Date uploadedAt;
    private String description;
    private Double price;
    private int phoneNumber;
    private String category;
    private String animalType;
    private String district;



    public PostViewModel() {
    }

    public PostViewModel(String postByProfileName, String postByProfileImageUrl, String title, String postImageUrl, Date uploadedAt, String description, Double price, int phoneNumber, String category, String animalType, String district) {
        this.postByProfileName = postByProfileName;
        this.postByProfileImageUrl = postByProfileImageUrl;
        this.title = title;
        this.postImageUrl = postImageUrl;
        this.uploadedAt = uploadedAt;
        this.description = description;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.animalType = animalType;
        this.district = district;
    }

    public String getPostByProfileName() {
        return postByProfileName;
    }

    public void setPostByProfileName(String postByProfileName) {
        this.postByProfileName = postByProfileName;
    }

    public String getPostByProfileImageUrl() {
        return postByProfileImageUrl;
    }

    public void setPostByProfileImageUrl(String postByProfileImageUrl) {
        this.postByProfileImageUrl = postByProfileImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
