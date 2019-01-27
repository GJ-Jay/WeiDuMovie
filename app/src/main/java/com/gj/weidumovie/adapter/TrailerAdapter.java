package com.gj.weidumovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.gj.weidumovie.bean.MovieDetailsBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * date:2019/1/26 11:27
 * author:陈国星(陈国星)
 * function:
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.VH> {
    private List<MovieDetailsBean.ShortFilmListBean> list = new ArrayList<>();
    public Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<MovieDetailsBean.ShortFilmListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(viewGroup.getContext(), R.layout.item_trailer_layout, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.video_player.thumbImageView.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        vh.video_player.setUp(list.get(i).getVideoUrl()
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(context).load(Uri.parse(list.get(i).getImageUrl())).into(vh.video_player.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private final JZVideoPlayerStandard video_player;
        public VH(@NonNull View itemView) {
            super(itemView);
            video_player=  itemView.findViewById(R.id.video_player);
        }
    }
}
