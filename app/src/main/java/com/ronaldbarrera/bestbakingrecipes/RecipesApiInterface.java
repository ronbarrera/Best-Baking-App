package com.ronaldbarrera.bestbakingrecipes;

import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApiInterface {

    @GET("baking.json")
    Call<List<RecipeModel>> getRecipes();
}
