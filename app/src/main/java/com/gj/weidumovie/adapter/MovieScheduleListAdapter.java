package com.gj.weidumovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.MovieScheduleBean;

import java.util.ArrayList;
import java.util.List;

public class MovieScheduleListAdapter extends RecyclerView.Adapter {
    private Context context;

    public MovieScheduleListAdapter(Context context) {
        this.context = context;
    }
    private List<MovieScheduleBean> list = new ArrayList<>();
    public void addItem(List<MovieScheduleBean> moiveBeans) {
        if(moiveBeans!=null)
        {
            this.list=moiveBeans;
        }
    }

    private OnItemClick clickCb;

    public void setMovieFlowAdapter(OnItemClick clickCb) {
        this.clickCb = clickCb;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_movie_buy_layout,null);
        MovieVH movieVH = new MovieVH(view);
        return movieVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MovieScheduleBean movieScheduleBean = list.get(i);
        MovieVH movieVH = (MovieVH) viewHolder;
        movieVH.buy_item_screeningHall.setText(movieScheduleBean.getScreeningHall());
        movieVH.buy_item_start.setText(movieScheduleBean.getBeginTime());
        movieVH.buy_item_end.setText(movieScheduleBean.getEndTime());

        String price = String.valueOf(movieScheduleBean.getPrice());
        SpannableString spannableString = new SpannableString(price);
        if (price.contains(".")){
            spannableString.setSpan(new RelativeSizeSpan(0.5f), price.indexOf("."), price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        movieVH.buy_item_price.setText(spannableString);

        movieVH.buy_item_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCb.clickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MovieVH extends RecyclerView.ViewHolder {
        public ImageView buy_item_ok;
        public TextView buy_item_screeningHall;
        public TextView buy_item_start;
        public TextView buy_item_end;
        public TextView buy_item_price;
        public MovieVH(View itemView) {
            super(itemView);
            buy_item_ok =  itemView.findViewById(R.id.buy_item_ok);
            buy_item_screeningHall =  itemView.findViewById(R.id.buy_item_screeningHall);
            buy_item_start =  itemView.findViewById(R.id.buy_item_start);
            buy_item_end =  itemView.findViewById(R.id.buy_item_end);
            buy_item_price =  itemView.findViewById(R.id.buy_item_price);
        }
    }

    public interface OnItemClick {
        void clickItem(int position);
    }

}
