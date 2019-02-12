package com.gj.weidumovie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.FindMessageList;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:系统消息
 * Author:GJ<br>
 * Date:2019/1/26 20:57
 */
public class MineRemindMessageListAdapter extends RecyclerView.Adapter<MineRemindMessageListAdapter.MyHolder> {

    ArrayList<FindMessageList> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.mine_remind_message_list,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        FindMessageList findMessageList = list.get(i);
        myHolder.name.setText("电影信息");
        myHolder.title.setText(findMessageList.getContent());
        try {
            myHolder.time.setText(DateUtils.dateFormat(new Date(findMessageList.getPushTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {//移除数据
        list.clear();
    }

    public void addList(List<FindMessageList> result) {
        if(result!=null){
            list.addAll(result);
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView title;
        private final TextView time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mine_remind_message_list_name);
            title = itemView.findViewById(R.id.mine_remind_message_list_title);
            time = itemView.findViewById(R.id.mine_remind_message_list_time);
        }
    }
}
