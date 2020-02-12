package com.ronaldbarrera.bestbakingrecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.ronaldbarrera.bestbakingrecipes.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                               String name, String ingredients, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        views.setTextViewText(R.id.appwidget_recipe_text, name);
        views.setTextViewText(R.id.appwidget_list, ingredients);

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_listview, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager, String name, String ingredients, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, name, ingredients, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            //updateAppWidget(context, appWidgetManager, appWidgetId);
//        }

        Log.d("IngredientsWidgetProvider", "onUpdate called");

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(

                    context.getPackageName(),
                    R.layout.ingredients_widget_provider

            );

            // click event handler for the title, launches the app when the user clicks on title
//            Intent titleIntent = new Intent(context, MainActivity.class);
//            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
//            views.setOnClickPendingIntent(R.id.widgetTitleLabel, titlePendingIntent);

            Intent intent = new Intent(context, ListViewWidgetService.class);
            views.setRemoteAdapter(R.id.appwidget_listview, intent);

//            // template to handle the click listener for each item
//            Intent clickIntentTemplate = new Intent(context, DetailsActivity.class);
//            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
//                    .addNextIntentWithParentStack(clickIntentTemplate)
//                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setPendingIntentTemplate(R.id.widgetListView, clickPendingIntentTemplate);

            appWidgetManager.updateAppWidget(appWidgetId, views);
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

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, IngredientsWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.appwidget_listview);
        }
        super.onReceive(context, intent);
    }
}

