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
}
