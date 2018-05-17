package com.blogspot.android_czy_java.beautytips.gridView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.android_czy_java.beautytips.R;
import com.blogspot.android_czy_java.beautytips.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_grid_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position%2 == 0) {
            holder.mImage.setImageResource(R.drawable.argan_oil);
        } else {
            holder.mImage.setImageResource(R.drawable.beauty);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.image)
        ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context  context = view.getContext();
            Intent detailActivityIntent = new Intent(context, DetailActivity.class);
            Bundle animation = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    mImage, mImage.getTransitionName()).toBundle();
            context.startActivity(detailActivityIntent, animation);
        }
    }
}
