package com.ronaldbarrera.bestbakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;
import com.ronaldbarrera.bestbakingrecipes.ui.MainActivity;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<IngredientModel> mIngredientsList;

    public ListViewRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.SHAREDPREF_KEY, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MainActivity.LIST_SHAREDPREF_KEY, null);
        Type type = new TypeToken<List<IngredientModel>>() {}.getType();
        mIngredientsList = gson.fromJson(json, type);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (mIngredientsList == null) return 0;
        return mIngredientsList.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, mIngredientsList.get(position).getIngredient());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
