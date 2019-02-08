package com.shycoder.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shycoder.android.bakingapp.R;
import com.shycoder.android.bakingapp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListHolder> {

    private ArrayList<Steps> mList;

    private OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void OnClick(ArrayList<Steps> stepArrayList, int position);
    }

    public StepsListAdapter() {
        mList = new ArrayList<>();
    }
    public void setStepsListAdapter(ArrayList<Steps> list, OnStepClickListener onStepClickListener) {
        mList = list;
        mCallback = onStepClickListener;
    }

    public void setSteps(ArrayList<Steps> list) {
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    @NonNull
    public StepsListAdapter.StepsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_recipe_steps_list, parent, false);

        return new StepsListAdapter.StepsListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListAdapter.StepsListHolder holder, final int position) {
        final Steps stepsModel = mList.get(position);

        holder.steps.setText(stepsModel.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StepsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.steps)
        TextView steps;

        private StepsListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.OnClick(mList, getAdapterPosition());
        }
    }
}
