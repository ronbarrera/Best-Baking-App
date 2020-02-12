package com.ronaldbarrera.bestbakingrecipes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM movie ORDER BY id")
    LiveData<List<RecipeEntry>> loadAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeEntry recipeEntry);

    @Delete
    void deleteRecipe(RecipeEntry recipeEntry);

    @Query("SELECT * FROM movie WHERE id = :id")
    RecipeEntry loadRecipeById(int id);
}
