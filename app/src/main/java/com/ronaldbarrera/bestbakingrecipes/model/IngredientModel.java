package com.ronaldbarrera.bestbakingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class IngredientModel {

    @SerializedName("quantity")
    String quantity;

    @SerializedName("measure")
    String measure;

    @SerializedName("ingredient")
    String ingredient;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
