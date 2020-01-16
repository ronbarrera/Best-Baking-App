package com.ronaldbarrera.bestbakingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class IngredientModel {

    @SerializedName("quantity")
    float quantity;

    @SerializedName("measure")
    String measure;

    @SerializedName("ingredient")
    String ingredient;
}
