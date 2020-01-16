package com.ronaldbarrera.bestbakingrecipes.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapterViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();

    private Context mContext;
    private List<RecipeModel> mRecipeList;

    public RecipesAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        Log.d(TAG, "onBindVidewHolder called");

        if(mRecipeList != null) {
            RecipeModel recipe = mRecipeList.get(position);
            int amountOfServings = recipe.getServings();
            int amountOfIngredients = recipe.getIngredients().size();

            holder.titleTextView.setText(recipe.getName());
            holder.titleTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.servingsTextView.setText(mContext.getString(R.string.servings_text, amountOfServings));
            holder.ingredientsTextView.setText(mContext.getString(R.string.ingredients_text, amountOfIngredients));
        }
    }

    public void setRecipes(List<RecipeModel> recipesEntries) {
        mRecipeList = recipesEntries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }
}
