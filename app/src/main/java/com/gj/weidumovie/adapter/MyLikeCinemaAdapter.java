package com.gj.weidumovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.LikeCinema;
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
public class MyLikeCinemaAdapter extends RecyclerView.Adapter<MyLikeCinemaAdapter.MyHolder> {

    Context context;

    public MyLikeCinemaAdapter(Context context) {
        this.context = context;
    }

    ArrayList<LikeCinema> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mylike_cinema_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        LikeCinema likeCinema = list.get(i);
        myHolder.img.setImageURI(likeCinema.getLogo());
        myHolder.name.setText(likeCinema.getName());
        myHolder.title.setText(likeCinema.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void remove() {
        list.clear();
    }

    public void addList(LikeCinema likeCinema) {
        if(likeCinema!=null){
            list.add(likeCinema);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img;
        private final TextView name;
        private final TextView title;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mylike_cinema_img);
            name = itemView.findViewById(R.id.mylike_cinema_name);
            title = itemView.findViewById(R.id.mylike_cinema_title);
        }
    }
}
