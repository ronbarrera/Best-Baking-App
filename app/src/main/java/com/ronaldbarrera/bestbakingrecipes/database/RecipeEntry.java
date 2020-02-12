package com.ronaldbarrera.bestbakingrecipes.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;

import java.util.Date;
import java.util.List;

@Entity(tableName = "movie")
public class RecipeEntry {

    @PrimaryKey
    private int id;
    private String name;
    private List<IngredientModel> ingredients;

//    @Ignore
//    public RecipeEntry(String name, List<IngredientModel> ingredients) {
//        this.name = name;
//        this.ingredients = ingredients;
//
//    }

    public RecipeEntry(int id, String name, List<IngredientModel> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

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

    @Override
    public String toString() {
        return getName();
    }

    public String ingredientsToString() {

        StringBuilder sb = new StringBuilder();

        for(IngredientModel i : ingredients) {
            sb.append("- ");
            sb.append(i.getIngredient());
            sb.append("\n");
        }
        return sb.toString();
    }
}
