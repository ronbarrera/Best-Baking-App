package com.ronaldbarrera.bestbakingrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;

import java.util.List;

import butterknife.BindView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private Context mContext;
    private List<IngredientModel> mIngredients;

    public IngredientsAdapter(Context context, List<IngredientModel> ingredientList) {
        this.mContext = context;
        this.mIngredients = ingredientList;
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsAdapterViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int position) {

        String quantity = mIngredients.get(position).getQuantity();
        String measure = mIngredients.get(position).getMeasure();
        String ingredient = mIngredients.get(position).getIngredient();

        String s = mContext.getString(R.string.ingredient_text, quantity, measure, ingredient);
        holder.ingredientTitle.setText(s);
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientTitle;

        public IngredientsAdapterViewHolder(@NonNull View view) {
            super(view);
            ingredientTitle = view.findViewById(R.id.ingredient_name_textview);
        }
    }
}