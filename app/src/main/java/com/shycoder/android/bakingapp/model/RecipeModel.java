package com.shycoder.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeModel {

    @SerializedName(value = "name")
    private String mName;

    @SerializedName(value = "ingredients")
    private ArrayList<Ingredient> mIngredients;

    @SerializedName(value = "steps")
    private ArrayList<Steps> mSteps;

    public RecipeModel(String mName, ArrayList<Ingredient> mIngredients, ArrayList<Steps> mSteps) {
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Steps> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        mSteps = steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

}
