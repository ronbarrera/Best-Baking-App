package com.ronaldbarrera.bestbakingrecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.ronaldbarrera.bestbakingrecipes.R;
import com.ronaldbarrera.bestbakingrecipes.ui.MainActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHAREDPREF_KEY, MODE_PRIVATE);
        String widget_title = sharedPreferences.getString(MainActivity.TITLE_SHAREDPREF_KEY, null);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        Intent titleIntent = new Intent(context, MainActivity.class);
        PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_default_label, titlePendingIntent);

        if(widget_title != null) {
            views.setViewVisibility(R.id.appwidget_default_label, View.GONE);

            views.setTextViewText(R.id.appwidget_title_text, widget_title);
            views.setViewVisibility(R.id.appwidget_title_text, View.VISIBLE);
            views.setOnClickPendingIntent(R.id.appwidget_title_text, titlePendingIntent);

            views.setViewVisibility(R.id.appwidget_ingredients_label_text, View.VISIBLE);
            views.setViewVisibility(R.id.appwidget_listview, View.VISIBLE);
        }

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_listview, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

