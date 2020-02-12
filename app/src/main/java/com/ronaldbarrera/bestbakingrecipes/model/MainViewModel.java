package com.ronaldbarrera.bestbakingrecipes.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ronaldbarrera.bestbakingrecipes.database.AppDatabase;
import com.ronaldbarrera.bestbakingrecipes.database.RecipeEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<RecipeEntry>> recipes;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        recipes = database.recipeDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getRecipes() {
        return recipes;
    }
}
