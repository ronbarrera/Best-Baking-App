package com.ronaldbarrera.bestbakingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class StepModel {

    @SerializedName("id")
    int id;

    @SerializedName("shortDescription")
    String shortDescription;

    @SerializedName("description")
    String description;

    @SerializedName("videoURL")
    String videoURL;

    @SerializedName("thumbnailURL")
    String thumbnailURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
