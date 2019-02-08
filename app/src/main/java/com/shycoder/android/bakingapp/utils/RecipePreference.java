package com.shycoder.android.bakingapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.model.Ingredient;
import com.shycoder.android.bakingapp.widgets.RecipeWidget;

import java.util.ArrayList;

public class RecipePreference {

    public static final String PREFERENCE_FILE_KEY = "com.shycoder.android.bakingapp";
    public static String m;

    public static void saveIngredientList(Context context, String recipe, ArrayList<Ingredient> ingredientArrayList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < ingredientArrayList.size(); i++) {
            String ingredient = ingredientArrayList.get(i).getIngredient() + " - " +
                    ingredientArrayList.get(i).getQuantity() + " " +
                    ingredientArrayList.get(i).getMeasure();

            m = ingredient;
            editor.putString(String.valueOf(i), ingredient);
        }

        editor.apply();
        updateWidget(context, recipe);
    }


    public static void deleteAllPreferences(Context context) {
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE).edit().clear().apply();
    }

    private static void updateWidget(Context context, String recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_list);
        RecipeWidget.updateRecipeWidget(context, appWidgetManager, appWidgetIds, recipe);
    }
}
