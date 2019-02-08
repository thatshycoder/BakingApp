package com.shycoder.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.StepsDetailsActivity;
import com.shycoder.android.bakingapp.adapters.StepsListAdapter;
import com.shycoder.android.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsListFragment extends Fragment {
    private Boolean mTablet;

    @BindView(R.id.rc_steps_list)
    RecyclerView mRecyclerview;

    private Intent mIntent;

    public RecipeStepsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_list, container, false);
        ButterKnife.bind(this, view);

        mIntent = getActivity().getIntent();

        // we are in tablet mode
        mTablet = getResources().getBoolean(R.bool.isTablet);

        getRecipeSteps();

        return view;
    }

    public void getRecipeSteps() {
        final ArrayList<Steps> list = new ArrayList<>();
        ArrayList<Steps> stepsDetail = mIntent.getParcelableArrayListExtra(getResources().getString(R.string.steps));

        for (int i = 0; i < stepsDetail.size(); i++) {

            Steps step = stepsDetail.get(i);

            Steps steps = new Steps(step.getID(), step.getShortDescription(),
                    step.getDescription(), step.getVideoURL(),
                    step.getThumbnailURL());

            list.add(steps);
        }

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        StepsListAdapter recyclerAdapter = new StepsListAdapter();
        recyclerAdapter.setSteps(list);
        mRecyclerview.setAdapter(recyclerAdapter);

        recyclerAdapter.setStepsListAdapter(list, new StepsListAdapter.OnStepClickListener() {
            @Override
            public void OnClick(ArrayList<Steps> stepArrayList, int position) {

                if (!mTablet) {
                    Intent intent = new Intent(getActivity(), StepsDetailsActivity.class);
                    intent.putExtra(getResources().getString(R.string.description), list.get(position).getDescription());
                    intent.putExtra(getResources().getString(R.string.videoUrl), list.get(position).getVideoURL());
                    startActivity(intent);
                } else {

                    StepsDetailsFragment.mTablet = true;

                    StepsDetailsFragment fragment = new StepsDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString(getResources().getString(R.string.description), list.get(position).getDescription());
                    bundle.putString(getResources().getString(R.string.videoUrl), list.get(position).getVideoURL());

                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();

                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    getActivity().findViewById(R.id.step_detail_tab).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.tab_instruction).setVisibility(View.GONE);

                    fragmentTransaction.replace(R.id.step_detail_tab, fragment);
                    fragmentTransaction.commit();


                }
            }
        });
    }
}
