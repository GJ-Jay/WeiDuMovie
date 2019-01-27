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
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.MovieDetailsBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * date:2019/1/26 11:27
 * author:陈国星(陈国星)
 * function:
 */
public class StillsAdapter extends RecyclerView.Adapter<StillsAdapter.VH> {
    private List<String> list = new ArrayList<>();
    public Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(viewGroup.getContext(), R.layout.item_stills_layout, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.stills_phono.setImageURI(Uri.parse(list.get(i)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private final SimpleDraweeView stills_phono;
        public VH(@NonNull View itemView) {
            super(itemView);
            stills_phono=  itemView.findViewById(R.id.stills_phono);
        }
    }
}
