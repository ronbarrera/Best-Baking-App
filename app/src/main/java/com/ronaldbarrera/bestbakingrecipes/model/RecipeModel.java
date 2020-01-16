package com.ronaldbarrera.bestbakingrecipes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("ingredients")
    List<IngredientModel> ingredients;

    @SerializedName("steps")
    List<StepModel> steps;

    @SerializedName("servings")
    int servings;

    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(List<StepModel> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
