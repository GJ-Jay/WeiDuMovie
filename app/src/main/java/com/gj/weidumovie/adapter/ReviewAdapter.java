package com.gj.weidumovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.FilmReviewBean;
import com.gj.weidumovie.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * date:2019/1/26 11:27
 * author:陈国星(陈国星)
 * function:
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VH> {
    private List<FilmReviewBean> list = new ArrayList<>();
    public Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<FilmReviewBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(viewGroup.getContext(), R.layout.item_review_layout, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.review_icon.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        vh.review_name.setText(list.get(i).getCommentUserName());
        vh.review_pinglun.setText(list.get(i).getCommentContent());
        try {
            vh.review_time.setText(DateUtils.dateFormat(new Date(list.get(i).getCommentTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int isGreat = list.get(i).getIsGreat();
        if (isGreat==1){
            vh.review_checkbox_like.setChecked(true);
        }else {
            vh.review_checkbox_like.setChecked(false);
        }
        vh.review_like_num.setText(String.valueOf(list.get(i).getGreatNum()));
        vh.review_pl_num.setText(String.valueOf(list.get(i).getReplyNum()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private final SimpleDraweeView review_icon;
        private final TextView review_name;
        private final TextView review_pinglun;
        private final TextView review_time;
        private final TextView review_like_num;
        private final TextView review_pl_num;
        private final CheckBox review_checkbox_like;
        public VH(@NonNull View itemView) {
            super(itemView);
            review_icon=  itemView.findViewById(R.id.review_icon);
            review_name=  itemView.findViewById(R.id.review_name);
            review_pinglun=  itemView.findViewById(R.id.review_pinglun);
            review_time=  itemView.findViewById(R.id.review_time);
            review_like_num=  itemView.findViewById(R.id.review_like_num);
            review_pl_num=  itemView.findViewById(R.id.review_pl_num);
            review_checkbox_like=  itemView.findViewById(R.id.review_checkbox_like);

        }
    }
}
