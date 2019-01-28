package com.gj.weidumovie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/26 20:57
 */
public class MyLikeMovieAdapter extends RecyclerView.Adapter<MyLikeMovieAdapter.MyHolder> {

    ArrayList<LikeMovie> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.mylike_movie_item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        LikeMovie likeMovie = list.get(i);
        myHolder.img.setImageURI(likeMovie.getImageUrl());
        myHolder.name.setText(likeMovie.getName());
        myHolder.title.setText("简介："+likeMovie.getSummary());
        try {
            myHolder.time.setText(DateUtils.dateFormat(new Date(likeMovie.getReleaseTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(LikeMovie likeMovie) {
        if(likeMovie!=null){
            list.add(likeMovie);
        }
    }

    public void remove() {
        list.clear();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img;
        private final TextView name;
        private final TextView title;
        private final TextView time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mylike_movie_img);
            name = itemView.findViewById(R.id.mylike_movie_name);
            title = itemView.findViewById(R.id.mylike_movie_title);
            time = itemView.findViewById(R.id.mylike_movie_time);
        }
    }
}
