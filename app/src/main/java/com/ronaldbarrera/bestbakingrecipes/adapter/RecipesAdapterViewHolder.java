package com.ronaldbarrera.bestbakingrecipes.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;
import com.ronaldbarrera.bestbakingrecipes.adapter.RecipesAdapter.RecipesAdapterOnClickHandler;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = RecipesAdapterViewHolder.class.getSimpleName();

    @BindView(R.id.title_textview) TextView titleTextView;
    @BindView(R.id.servings_textview) TextView servingsTextView;
    @BindView(R.id.ingredients_textview) TextView ingredientsTextView;

    private RecipesAdapterOnClickHandler mClickHandler;

    public RecipesAdapterViewHolder(@NonNull View itemView, RecipesAdapterOnClickHandler clickHandler) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mClickHandler = clickHandler;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        mClickHandler.onSelectedRecipe(adapterPosition);
    }
}
