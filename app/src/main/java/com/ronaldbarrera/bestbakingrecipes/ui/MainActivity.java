package com.ronaldbarrera.bestbakingrecipes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.RecipesApiClient;
import com.ronaldbarrera.bestbakingrecipes.RecipesApiInterface;
import com.ronaldbarrera.bestbakingrecipes.adapter.RecipesAdapter;
import com.ronaldbarrera.bestbakingrecipes.database.AppDatabase;
import com.ronaldbarrera.bestbakingrecipes.database.AppExecutors;
import com.ronaldbarrera.bestbakingrecipes.database.RecipeEntry;
import com.ronaldbarrera.bestbakingrecipes.model.RecipeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recipes_recyclerview)
    RecyclerView mRecyclerView;

    private RecipesAdapter mAdapter;
    private List<RecipeModel> mRecipeList;

    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        boolean isScreenSmall =  getResources().getBoolean(R.bool.is_screen_small);

        RecyclerView.LayoutManager layoutManager;
        if(isScreenSmall) {
            layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(MainActivity.this, 3);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipesAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        fetchRecipes();

    }


    private void fetchRecipes() {
        Log.d(TAG, "fetchRecipes");

        RecipesApiInterface apiInterface = RecipesApiClient.getClient().create(RecipesApiInterface.class);
        Call<List<RecipeModel>> call = apiInterface.getRecipes();

        call.enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                if(response.body() != null) {
                    mRecipeList = response.body();
                    Log.d(TAG, "onResponse : list size " + mRecipeList.size());
                    onSetupUI(mRecipeList);
                    savedDB(mRecipeList);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                Log.d(TAG, "FAILED");
                View view = findViewById(R.id.main_layout);
                Snackbar snackbar = Snackbar
                        .make(view, getString(R.string.no_internet_error), Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        });
    }

    private void savedDB(List<RecipeModel> list) {
        for(RecipeModel recipe : list) {
            final RecipeEntry entry = new RecipeEntry(recipe.getId(), recipe.getName(), recipe.getIngredients());
            Log.d(TAG, "saved in db name = " + entry.getName());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                   mDb.recipeDao().insertRecipe(entry);
//                    if (mTaskId == DEFAULT_TASK_ID) {
//                        // insert new task
//                        mDb.taskDao().insertTask(task);
//                    } else {
//                        //update task
//                        task.setId(mTaskId);
//                        mDb.taskDao().updateTask(task);
//                    }
                   // finish();
                }
            });
        }

    }

    private void onSetupUI(List<RecipeModel> list) {
        Log.d(TAG, "onSetupUI");
        mAdapter.setRecipes(list);
    }


    @Override
    public void onSelectedRecipe(int position) {
        Log.d(TAG, "onSelectedRecipe : " + position);
        Context context = this;
        Class destinationClass = RecipeActivity.class;
        Intent intentToStartRecipeActivity = new Intent(context, destinationClass);

        Gson gson = new Gson();
        intentToStartRecipeActivity.putExtra("recipe", gson.toJson(mRecipeList.get(position)));
        startActivity(intentToStartRecipeActivity);

    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed;
     *         if you return false it will not be shown.
     *
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu.
     *
     * @param item The menu item that was selected by the user
     *
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

