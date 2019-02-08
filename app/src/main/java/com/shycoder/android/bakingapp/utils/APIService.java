package com.shycoder.android.bakingapp.utils;

import com.shycoder.android.bakingapp.model.RecipeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

public interface APIService {

    @GET("baking.json")
    rx.Observable<List<RecipeModel>> getRecipeList();

    @GET("baking.json")
    rx.Observable<ArrayList<RecipeModel>> getWidgetRecipes();
}
