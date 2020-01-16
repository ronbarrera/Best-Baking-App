package com.ronaldbarrera.bestbakingrecipes.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.bestbakingrecipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_textview) TextView titleTextView;
    @BindView(R.id.servings_textview) TextView servingsTextView;
    @BindView(R.id.ingredients_textview) TextView ingredientsTextView;

    public RecipesAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
