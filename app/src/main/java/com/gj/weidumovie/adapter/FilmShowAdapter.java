package com.gj.weidumovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.HotMovieBean;
import com.gj.weidumovie.bean.MoiveBean;

import java.util.ArrayList;
import java.util.List;

public class FilmShowAdapter extends RecyclerView.Adapter<FilmShowAdapter.ViewHolder> {
    private Context context;
    List<MoiveBean> list = new ArrayList<>();
    private ClickListener clickListener;

    public FilmShowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filmshow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.summary.setText(list.get(i).getSummary());
        viewHolder.simpleDraweeView.setImageURI(Uri.parse(list.get(i).getImageUrl()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.click(list.get(i).getId());
            }
        });
        final int followMovie = list.get(i).getFollowMovie();
        if (followMovie==1){
            viewHolder.imageView.setImageResource(R.drawable.com_icon_collection_selectet);
        }else {
            viewHolder.imageView.setImageResource(R.drawable.xing);
        }
        /*viewHolder.imageView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    clickListener.clickOk(list.get(i).getId());
                }else {
                    clickListener.clickNo(list.get(i).getId());
                }
            }
        });*/
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followMovie==1){
                    list.get(i).setFollowMovie(2);
                    viewHolder.imageView.setImageResource(R.drawable.xing);
                    clickListener.clickNo(list.get(i).getId());


                }else {
                    list.get(i).setFollowMovie(1);
                    viewHolder.imageView.setImageResource(R.drawable.com_icon_collection_selectet);
                    clickListener.clickOk(list.get(i).getId());

                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(List<MoiveBean> result) {
        if (result != null) {
            list.addAll(result);
        }
    }

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView name;
        ImageView imageView;
        TextView summary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.filmshow_sim);
            name = itemView.findViewById(R.id.filmshow_name);
            imageView = itemView.findViewById(R.id.filmshow_heart);
            summary = itemView.findViewById(R.id.filmshow_summary);
        }
    }
    public interface ClickListener{
        void click(int id);
        void clickOk(int id);
        void clickNo(int id);
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }
}
