package com.ronaldbarrera.bestbakingrecipes.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;
import com.ronaldbarrera.bestbakingrecipes.widget.IngredientsWidgetProvider;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.database.RecipeEntry;
import com.ronaldbarrera.bestbakingrecipes.model.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner = findViewById(R.id.spinner2);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, recipeEntries -> {
            Log.d("SettingsActivity", "viewmodel size of entries = " + recipeEntries.size());
            Log.d("SettingsActivity", "viewmodel size of ingredients = " + recipeEntries.get(0).getIngredients().size());

            ArrayAdapter<RecipeEntry> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, recipeEntries);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    updateIngredientsWidget(recipeEntries.get(position).getName(), recipeEntries.get(position).ingredientsToString() );
                    saveData(recipeEntries.get(position).getIngredients());
                    AppWidgetManager mgr = AppWidgetManager.getInstance(getApplicationContext());
                    ComponentName cn = new ComponentName(getApplicationContext(), IngredientsWidgetProvider.class);
                    mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.appwidget_listview);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        });


    }

    private void saveData(List<IngredientModel> mIngredientList) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mIngredientList);
        editor.putString("ingredient_list", json);
        editor.apply();

        Log.d("SettingsActivity", "saveData called size " + mIngredientList.size());
    }

    public void updateIngredientsWidget(String name, String ingredients) {

        Log.d("Settings", "name = " + name);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        //IngredientsWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, name, ingredients, appWidgetIds);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Normally, calling setDisplayHomeAsUpEnabled(true) (we do so in onCreate here) as well as
         * declaring the parent activity in the AndroidManifest is all that is required to get the
         * up button working properly. However, in this case, we want to navigate to the previous
         * screen the user came from when the up button was clicked, rather than a single
         * designated Activity in the Manifest.
         *
         * We use the up button's ID (android.R.id.home) to listen for when the up button is
         * clicked and then call onBackPressed to navigate to the previous Activity when this
         * happens.
         */
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}