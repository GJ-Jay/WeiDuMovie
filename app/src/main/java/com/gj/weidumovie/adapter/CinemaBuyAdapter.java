package com.gj.weidumovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.CinemaBean;

import java.util.ArrayList;
import java.util.List;

public class CinemaBuyAdapter extends RecyclerView.Adapter {

    private Context context;
    private ClickListener clickListener;

    public CinemaBuyAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<CinemaBean> list = new ArrayList<>();
    public void addItem(List<CinemaBean> cinemaBeans) {
        if(cinemaBeans!=null)
        {
            list.addAll(cinemaBeans);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_cinemaadapter, null);
        CinemaVH cinemaVH = new CinemaVH(view);
        return cinemaVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final CinemaBean cinemaBean = list.get(i);
        CinemaVH cinemaVH = (CinemaVH) viewHolder;
        cinemaVH.cinemasdvsone.setImageURI(Uri.parse(cinemaBean.getLogo()));
        cinemaVH.cinematextviewone.setText(cinemaBean.getName());
        cinemaVH.cinematextviewtwo.setText(cinemaBean.getAddress());
        cinemaVH.cinematextviewthree.setText(cinemaBean.getCommentTotal()+"km");

        cinemaVH.moive_like.setVisibility(View.GONE);
        cinemaVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.clickNo(cinemaBean.getId(),cinemaBean.getName(),cinemaBean.getAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }

    //创建ViewHolder
    class CinemaVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView cinemasdvsone;
        public TextView cinematextviewone;
        public TextView cinematextviewtwo;
        public TextView cinematextviewthree;
        public CheckBox moive_like;
        public CinemaVH(@NonNull View itemView) {
            super(itemView);
            cinemasdvsone = itemView.findViewById(R.id.cinemasdvsone);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
            cinematextviewthree = itemView.findViewById(R.id.cinematextviewthree);
            moive_like = itemView.findViewById(R.id.moive_like);

        }
    }
    public interface ClickListener{
        void clickNo(int id,String name,String address);
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }
}
