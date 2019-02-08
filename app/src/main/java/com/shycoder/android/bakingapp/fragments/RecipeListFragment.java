package com.shycoder.android.bakingapp.fragments;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.util.Util;
import com.shycoder.android.bakingapp.MainActivity;
import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.adapters.RecipeListAdapter;
import com.shycoder.android.bakingapp.model.RecipeModel;
import com.shycoder.android.bakingapp.utils.APIService;
import com.shycoder.android.bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.rc_recipe_list)
    RecyclerView mRecyclerview;

    @BindView(R.id.recipe_list_error)
    TextView mError;

    @BindView(R.id.recipe_list_progress)
    ProgressBar mProgressBar;

    public List<RecipeModel> mList;

    APIService mApiService;

    Boolean isTablet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        if (Utils.isNetworkAvailable(getContext())) {
            isTablet = getActivity().getResources().getBoolean(R.bool.isTablet);
            getRecipeList();
        } else {
            showInternetError();
        }

        return view;
    }

    public void getRecipeList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

         mApiService = retrofit.create(APIService.class);

         completeFetchRecipe();


    }

    private void completeFetchRecipe() {
        Observable<List<RecipeModel>> observable = mApiService.getRecipeList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<RecipeModel>>() {
            @Override
            public void onCompleted() {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mProgressBar.setVisibility(View.GONE);
                showUnknownError();
            }

            @Override
            public void onNext(List<RecipeModel> recipe) {

                mList = new ArrayList<>();

                for (int i = 0; i < recipe.size(); i++) {

                    RecipeModel recipeModel = new RecipeModel(recipe.get(i).getName(), recipe.get(i).getIngredients(),
                            recipe.get(i).getSteps());

                    mList.add(recipeModel);
                }


                RecipeListAdapter recyclerAdapter = new RecipeListAdapter(mList, getActivity());
                recyclerAdapter.setRecipe(mList);

                if(isTablet) {
                    mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                    mRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            int position = parent.getChildAdapterPosition(view); // item position
                            int spanCount = 5;
                            int spacing = 10;//spacing between views in grid

                            if (position >= 0) {
                                int column = position % spanCount; // item column

                                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                                if (position < spanCount) { // top edge
                                    outRect.top = spacing;
                                }
                                outRect.bottom = spacing; // item bottom
                            } else {
                                outRect.left = 0;
                                outRect.right = 0;
                                outRect.top = 0;
                                outRect.bottom = 0;
                            }
                        }
                    });
                } else {
                    mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                mRecyclerview.setAdapter(recyclerAdapter);

            }
        });
    }

    public void showInternetError() {
        mError.setVisibility(View.VISIBLE);
        mError.setText(this.getString(R.string.error_no_internet));
    }

    public void showUnknownError() {
        mProgressBar.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mError.setText(this.getString(R.string.error_unknown));
    }
}
