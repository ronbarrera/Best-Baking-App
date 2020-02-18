package com.ronaldbarrera.bestbakingrecipes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.PictureInPictureParams;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;
import com.ronaldbarrera.bestbakingrecipes.model.StepModel;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

    List<StepModel> mStepList;
    int mStepIndex;
    int mLastStepIndex;

    @BindView(R.id.prev_step_button) Button prev_button;
    @BindView(R.id.next_step_button) Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        Gson gson = new Gson();
        String strOjb = getIntent().getStringExtra("steps_list");
        Type list = new TypeToken<List<StepModel>>() {}.getType();
        mStepList = gson.fromJson(strOjb,list);

        if(savedInstanceState == null) {
            mStepIndex = getIntent().getIntExtra("step_index", 0);

            FragmentManager fragmentManager = getSupportFragmentManager();
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStep(mStepList.get(mStepIndex));
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_framelayout, stepDetailsFragment)
                    .commit();
        } else {
            mStepIndex = savedInstanceState.getInt("current_step_index");
        }
        mLastStepIndex = mStepList.size() - 1;

        updateToolbarTitle();

        if (mStepIndex == 0)
            prev_button.setVisibility(View.INVISIBLE);
        if (mStepIndex == mLastStepIndex)
            next_button.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putInt("current_step_index", mStepIndex);
    }

    public void updateToolbarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(mStepIndex == 0)
            actionBar.setTitle(mStepList.get(mStepIndex).getShortDescription());
        else
            actionBar.setTitle("Step " + mStepList.get(mStepIndex).getId());
    }

    @OnClick(R.id.prev_step_button)
    public void prevOnClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailsFragment prevNewFragment = new StepDetailsFragment();
        prevNewFragment.setStep(mStepList.get(--mStepIndex));
        fragmentManager.beginTransaction()
                .replace(R.id.step_details_framelayout, prevNewFragment)
                .commit();

        if(mStepIndex == 0)
            prev_button.setVisibility(View.INVISIBLE);
        else
            next_button.setVisibility(View.VISIBLE);
        updateToolbarTitle();
    }

    @OnClick(R.id.next_step_button)
    public void nextOnClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailsFragment nextNewFragment = new StepDetailsFragment();
        nextNewFragment.setStep(mStepList.get(++mStepIndex));
        fragmentManager.beginTransaction()
                .replace(R.id.step_details_framelayout, nextNewFragment)
                .commit();

        if(mStepIndex == mLastStepIndex)
            next_button.setVisibility(View.INVISIBLE);
        else
            prev_button.setVisibility(View.VISIBLE);
        updateToolbarTitle();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(
                        new PictureInPictureParams.Builder()
                                .setAspectRatio(new Rational(16, 9))
                                .build());

        }
    }
}
