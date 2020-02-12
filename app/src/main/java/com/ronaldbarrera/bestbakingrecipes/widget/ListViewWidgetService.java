package com.ronaldbarrera.bestbakingrecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.model.IngredientModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    //Cursor mCursor;

    List<IngredientModel> mCursor;

    public ListViewRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        Log.d("ListViewWidget", "onDataChanged() called");

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ingredient_list", null);
        Type type = new TypeToken<List<IngredientModel>>() {}.getType();
        mCursor = gson.fromJson(json, type);

//        if (mCursor == null) {
//            mCursor = new List<IngredientModel>();
//        }

        if(mCursor != null)
            Log.d("ListViewWidgetService", "size of ingredients is : " + mCursor.size());
        else
            Log.d("ListViewWidgetService", "mCursor is null");
        // Get all plant info ordered by creation time
//        Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANTS).build();
//        if (mCursor != null) mCursor.close();
//        mCursor = mContext.getContentResolver().query(
//                PLANT_URI,
//                null,
//                null,
//                null,
//                PlantContract.PlantEntry.COLUMN_CREATION_TIME
//        );
    }

    @Override
    public void onDestroy() {
        //mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

//        if (position == AdapterView.INVALID_POSITION ||
//                mCursor == null || !mCursor.moveToPosition(position)) {
//            return null;
//        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, mCursor.get(position).getIngredient());

        //Intent fillInIntent = new Intent();
        //fillInIntent.putExtra(CollectionAppWidgetProvider.EXTRA_LABEL, mCursor.getString(1));
        //rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
//        if (mCursor == null || mCursor.getCount() == 0) return null;
//        mCursor.moveToPosition(position);
//        int idIndex = mCursor.getColumnIndex(PlantContract.PlantEntry._ID);
//        int createTimeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_CREATION_TIME);
//        int waterTimeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME);
//        int plantTypeIndex = mCursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_PLANT_TYPE);
//
//        long plantId = mCursor.getLong(idIndex);
//        int plantType = mCursor.getInt(plantTypeIndex);
//        long createdAt = mCursor.getLong(createTimeIndex);
//        long wateredAt = mCursor.getLong(waterTimeIndex);
//        long timeNow = System.currentTimeMillis();
//
//        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.plant_widget);
//
//        // Update the plant image
//        int imgRes = PlantUtils.getPlantImageRes(mContext, timeNow - createdAt, timeNow - wateredAt, plantType);
//        views.setImageViewResource(R.id.widget_plant_image, imgRes);
//        views.setTextViewText(R.id.widget_plant_name, String.valueOf(plantId));
//        // Always hide the water drop in GridView mode
//        views.setViewVisibility(R.id.widget_water_button, View.GONE);
//
//        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
//        Bundle extras = new Bundle();
//        extras.putLong(PlantDetailActivity.EXTRA_PLANT_ID, plantId);
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtras(extras);
//        views.setOnClickFillInIntent(R.id.widget_plant_image, fillInIntent);
//
//        return views;

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
