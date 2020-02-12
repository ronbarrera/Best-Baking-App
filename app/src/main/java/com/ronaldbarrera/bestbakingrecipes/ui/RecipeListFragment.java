package com.ronaldbarrera.bestbakingrecipes.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.adapter.IngredientsAdapter;
import com.ronaldbarrera.bestbakingrecipes.adapter.StepsAdapter;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;
import com.ronaldbarrera.bestbakingrecipes.model.StepModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private RecipeModel recipe;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    // Mandatory empty constructor
    public RecipeListFragment() {
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);


        Log.d(TAG, "onCreateView called");

        if(savedInstanceState != null) {
            Log.d(TAG, "saveInstanceState != null");
            Gson gson = new Gson();
            String strOjb = savedInstanceState.getString("recipe");
            Type list = new TypeToken<RecipeModel>() {}.getType();
            recipe = gson.fromJson(strOjb,list);
        }

        List<IngredientModel> ingredientList = recipe.getIngredients();
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_recyclerview);
        IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        List<StepModel> stepList = recipe.getSteps();
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_recyclerview);
        StepsAdapter mStepsAdapter = new StepsAdapter(getContext(), stepList, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        stepsRecyclerView.setLayoutManager(layoutManager2);
        stepsRecyclerView.setAdapter(mStepsAdapter);


        return rootView;
    }


    @Override
    public void setOnItemClickListener(int position) {
        Log.d(TAG, "setOnItemClickListerner position : " + position);
        mCallback.onStepSelected(position);
    }

    public void setRecipe(RecipeModel recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        Gson gson = new Gson();
        currentState.putString("recipe", gson.toJson(recipe));
    }
}
