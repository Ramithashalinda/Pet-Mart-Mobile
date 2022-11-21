package com.mart.petsmart.model;

public class PetsAdsModel {
    Integer id;
    Integer imageUrl;

    public PetsAdsModel(Integer id, Integer imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public PetsAdsModel() {
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

}
