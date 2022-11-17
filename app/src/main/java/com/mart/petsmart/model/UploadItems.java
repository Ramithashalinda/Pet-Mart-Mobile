package com.mart.petsmart.model;

import java.util.Date;

public class UploadItems {


    private String title;
    private Double price;
    private int phoneNumber;
    private String description;
    private String postImageUrl;
    private Date uploadedAt;
    private String category;
    private String animalType;
    private String district;



    public UploadItems(){

    }


    public  UploadItems(String title, Double price, int phoneNumber, String description, String postImageUrl, Date uploadedAt, String category, String animalType, String district) {
        this.title = title;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.postImageUrl = postImageUrl;
        this.uploadedAt = uploadedAt;
        this.category = category;
        this.animalType = animalType;
        this.district = district;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
