package com.example.kirill.testtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.kirill.testtask.realm.Shot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 16.08.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Shot> shots = new ArrayList<Shot>();
    private Presenter presenter;
    private Context context;
    private int height;

    public RecyclerAdapter(List<Shot> shots, Presenter presenter) {
        this.shots = shots;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shot_item, parent, false);
        height = parent.getHeight()/2;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shot shot = shots.get(position);
        holder.title.setText(shot.getTitle());

        if(shot.getDescription() != null ){
            holder.description.setText(shot.getDescription());
        }


        String url;
        url = (shot.getHidpi() ==null) ? (shot.getNormal() == null) ? shot.getTeaser():shot.getNormal()  : shot.getHidpi();
        Picasso.with(context).load(url).into(holder.image);


        //подгрузка предыдущих shot
        if(position == (shots.size()-1)) presenter.loadPreviousShots();

    }

    @Override
    public int getItemCount() {
        if (shots == null)return 0;
        return shots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height , 1));

            title = (TextView) itemView.findViewById(R.id.title_item);
            description = (TextView) itemView.findViewById(R.id.description_item);
            image = (ImageView) itemView.findViewById(R.id.image_item);

        }
    }
}
