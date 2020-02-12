package com.ronaldbarrera.bestbakingrecipes.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    @TypeConverter
    public String fromIngredientList(List<IngredientModel> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<IngredientModel>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<IngredientModel> toIngredientList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<IngredientModel>>() {}.getType();
        List<IngredientModel> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
