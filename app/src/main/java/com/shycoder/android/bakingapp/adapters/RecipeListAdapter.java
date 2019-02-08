package com.shycoder.android.bakingapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.RecipeDetailsActivity;
import com.shycoder.android.bakingapp.model.Ingredient;
import com.shycoder.android.bakingapp.model.RecipeModel;
import com.shycoder.android.bakingapp.model.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListHolder> {

    private List<RecipeModel> mList;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Steps> mSteps;
    private Activity mContext;

    public RecipeListAdapter(List<RecipeModel> list, Activity context) {
        mList = list;
        mContext = context;
    }

    public void setRecipe(List<RecipeModel> list) {
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    @NonNull
    public RecipeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_receipe_list, parent, false);

        return new RecipeListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListHolder holder, final int position) {
        final RecipeModel recipeModel = mList.get(position);

        holder.recipe.setText(recipeModel.getName());

        holder.recipeListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIngredients = new ArrayList<>();
                mSteps = new ArrayList<>();

                mIngredients = recipeModel.getIngredients();
                mSteps = recipeModel.getSteps();

                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra(mContext.getResources().getString(R.string.recipe), recipeModel.getName());
                intent.putParcelableArrayListExtra(mContext.getResources().getString(R.string.ingredient), mIngredients);
                intent.putParcelableArrayListExtra(mContext.getResources().getString(R.string.steps), mSteps);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecipeListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe)
        TextView recipe;
        @BindView(R.id.recipe_list_container)
        View recipeListContainer;

        private RecipeListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
