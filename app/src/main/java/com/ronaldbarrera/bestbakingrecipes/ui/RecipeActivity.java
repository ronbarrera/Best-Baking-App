package com.ronaldbarrera.bestbakingrecipes.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;

public class RecipeActivity extends AppCompatActivity implements RecipeListFragment.OnStepClickListener{

    private RecipeModel recipe;
    private boolean mTwoPane;
    private  FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

            Gson gson = new Gson();
            String strOjb = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strOjb, RecipeModel.class);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(recipe.getName());

            if(savedInstanceState == null) {

                fragmentManager = getSupportFragmentManager();

                RecipeListFragment recipeListFragment = new RecipeListFragment();
                recipeListFragment.setRecipe(recipe);
                fragmentManager.beginTransaction()
                        .add(R.id.master_list_fragment, recipeListFragment)
                        .commit();

                if (findViewById(R.id.step_details_framelayout) != null) {
                    mTwoPane = true;

                    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                    stepDetailsFragment.setStep(recipe.getSteps().get(0));
                    stepDetailsFragment.setIsTwoPane(mTwoPane);
                    fragmentManager.beginTransaction()
                            .add(R.id.step_details_framelayout, stepDetailsFragment)
                            .commit();
                } else {
                    mTwoPane = false;
                }
            }
    }

    @Override
    public void onStepSelected(int position) {
        if(mTwoPane) {
            StepDetailsFragment newFragment = new StepDetailsFragment();
            newFragment.setStep(recipe.getSteps().get(position));
            newFragment.setIsTwoPane(mTwoPane);
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_framelayout, newFragment)
                    .commit();
        } else {
            final Intent intent = new Intent(this, StepDetailsActivity.class);
            Gson gson = new Gson();
            intent.putExtra("steps_list", gson.toJson(recipe.getSteps()));
            intent.putExtra("step_index", position);
            startActivity(intent);
        }
    }
}
