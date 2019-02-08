package com.shycoder.android.bakingapp.widgets;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shycoder.android.bakingapp.model.Ingredient;
import com.shycoder.android.bakingapp.model.RecipeModel;
import com.shycoder.android.bakingapp.utils.APIService;
import com.shycoder.android.bakingapp.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {


    private List<Ingredient> mCollection = new ArrayList<>();
    private Context mContext;
    APIService mApiService;


    public WidgetDataProvider(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, mCollection.get(position).getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // true if items in list won't change
    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void getCollection() {
        mCollection.clear();

        if (Utils.isNetworkAvailable(mContext)) {

            getWidgetRecipes();

            Observable<ArrayList<RecipeModel>> observable = mApiService.getWidgetRecipes().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Observer<ArrayList<RecipeModel>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(ArrayList<RecipeModel> recipe) {

                    try {
                        if (recipe.size() >= 1) {
                            mCollection = recipe.get(1).getIngredients();
                            Log.d(this.getClass().getName(), mCollection.toString());
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
        }
    }

    public void getWidgetRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(APIService.class);
    }

    @Override
    public void onDataSetChanged() {
        getCollection();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

}
