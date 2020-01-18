package com.ronaldbarrera.bestbakingrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.StepModel;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>  {

    private Context mContext;
    private List<StepModel> mStepList;
    private final StepsAdapterOnClickHandler mClickHandler;

    public StepsAdapter(Context context, List<StepModel> stepList, StepsAdapterOnClickHandler mClickHandler) {
        this.mContext = context;
        this.mStepList = stepList;
        this.mClickHandler = mClickHandler;
    }


    public interface StepsAdapterOnClickHandler {
        void setOnItemClickListener(int position);
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_item, parent, false);
        return  new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsAdapterViewHolder holder, int position) {
        int stepNumber = mStepList.get(position).getId();
        String stepDescription = mStepList.get(position).getShortDescription();
        String s = mContext.getString(R.string.step_number_label, (stepNumber+1), stepDescription);

        holder.step_textview.setText(s);
    }

    @Override
    public int getItemCount() {
        return mStepList == null ? 0 : mStepList.size();
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView step_textview;

        public StepsAdapterViewHolder(@NonNull View view) {
            super(view);
            step_textview = view.findViewById(R.id.step_number_label);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.setOnItemClickListener(adapterPosition);
        }
    }
}
