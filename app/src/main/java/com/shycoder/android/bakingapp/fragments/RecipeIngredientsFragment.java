package com.shycoder.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.model.Ingredient;
import com.shycoder.android.bakingapp.utils.RecipePreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsFragment extends Fragment {
    private Intent mIntent;

    @BindView(R.id.ingredient)
    TextView mIngredients;

    public static String mRecipeIngredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, view);

        mIntent = getActivity().getIntent();

        getIngredients();

        return view;
    }

    private void getIngredients() {

        ArrayList<Ingredient> ingredientDetail = mIntent.getParcelableArrayListExtra(getResources().getString(R.string.ingredient));
        String recipe = mIntent.getStringExtra(getResources().getString(R.string.recipe));
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ingredientDetail.size(); i++) {

            stringBuilder.append(ingredientDetail.get(i).getIngredient());
            stringBuilder.append("(");
            stringBuilder.append(String.valueOf(ingredientDetail.get(i).getQuantity()));
            stringBuilder.append(ingredientDetail.get(i).getMeasure());
            stringBuilder.append(")");
            stringBuilder.append("\n");
            stringBuilder.append("\n");

            mIngredients.setText(stringBuilder);

            mRecipeIngredients = stringBuilder.toString();

            RecipePreference.deleteAllPreferences(getActivity());
            RecipePreference.saveIngredientList(getActivity(), recipe, ingredientDetail);
        }
    }
}
