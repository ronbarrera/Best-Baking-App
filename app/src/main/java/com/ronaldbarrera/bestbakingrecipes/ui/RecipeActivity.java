package com.ronaldbarrera.bestbakingrecipes.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.adapter.IngredientsAdapter;
import com.ronaldbarrera.bestbakingrecipes.adapter.StepsAdapter;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;
import com.ronaldbarrera.bestbakingrecipes.model.StepModel;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipeListFragment.OnStepClickListener{

    private static final String TAG = RecipeActivity.class.getSimpleName();

    RecipeModel recipe;

    private boolean mTwoPane;
    private  FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


            Gson gson = new Gson();
            String strOjb = getIntent().getStringExtra("recipe");
            recipe = gson.fromJson(strOjb, RecipeModel.class);

            Log.d(TAG, "onCreate : " + recipe.getName());

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
            Log.d(TAG, "mTwoPane = " + mTwoPane);



    }

    @Override
    public void onStepSelected(int position) {
        Log.d(TAG, "onStepSelected. Position: " + position);

        if(mTwoPane) {

            StepDetailsFragment newFragment = new StepDetailsFragment();
            // Give the correct image resources to the new fragment'
            newFragment.setStep(recipe.getSteps().get(position));
            newFragment.setIsTwoPane(mTwoPane);
            // Replace the old head fragment with a new one
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_framelayout, newFragment)
                    .commit();
        } else {


            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, StepDetailsActivity.class);
            Gson gson = new Gson();
            intent.putExtra("steps_list", gson.toJson(recipe.getSteps()));
            intent.putExtra("step_index", position);
            startActivity(intent);

        }


    }
}
