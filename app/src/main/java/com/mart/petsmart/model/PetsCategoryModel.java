package com.mart.petsmart.model;

public class PetsCategoryModel {

    Integer id;
    Integer imageUrl;
    String type;

    public PetsCategoryModel(Integer id, Integer imageUrl, String type) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public PetsCategoryModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
