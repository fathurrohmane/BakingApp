package com.elkusnandi.bakingapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.elkusnandi.bakingapp.data.model.Ingredient;
import com.elkusnandi.bakingapp.data.model.Recipe;

import java.util.Locale;

/**
 * Created by Fathurrohman on 14-Jan-17.
 */
public class MySharedPreference {

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static String getWidgetRecipeTitle(Context context) {
        return getSharedPreference(context).getString("recipe_title", "no_data");
    }

    public static String getWidgetRecipeIngredients(Context context) {
        return getSharedPreference(context).getString("recipe_ingredient", "no_ingredient");
    }

    public static void setWidgetRecipe(Context context, Recipe recipe) {
        SharedPreferences mPrefs = getSharedPreference(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        if (recipe != null) {
            prefsEditor.putString("recipe_title", recipe.getTitle());
            String ingredients = "";
            for (Ingredient ingredient : recipe.getIngeredients()) {
                ingredients +=
                        String.format(
                                Locale.getDefault()
                                , "%s %1.0f %s \n"
                                , ingredient.getIngredient()
                                , ingredient.getQuantity()
                                , ingredient.getMeasurement());
            }
            prefsEditor.putString("recipe_ingredient", ingredients);
        }
        prefsEditor.apply();
    }
}
