package com.elkusnandi.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.elkusnandi.bakingapp.R;
import com.elkusnandi.bakingapp.data.MySharedPreference;
import com.elkusnandi.bakingapp.feature.main.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static final String ACTION_ADD_RECIPE = "action_add_recipe";

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        PendingIntent pendingIntent;
        Intent intent;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        String recipe_title = MySharedPreference.getWidgetRecipeTitle(context);

        if (recipe_title.equals("no_data")) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("pick_widget", true);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setTextViewText(R.id.appwidget_title, context.getString(R.string.add_recipe));
            views.setOnClickPendingIntent(R.id.appwidget_title, pendingIntent);
            views.setTextViewText(R.id.appwidget_ingredient, context.getString(R.string.no_ingredient));

        } else {
            views.setTextViewText(R.id.appwidget_title, recipe_title);
            String ingredients = MySharedPreference.getWidgetRecipeIngredients(context);
            views.setTextViewText(R.id.appwidget_ingredient, ingredients);
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("pick_widget", false);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.appwidget_title, pendingIntent);

        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

