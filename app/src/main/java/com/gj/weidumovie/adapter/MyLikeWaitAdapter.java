package com.gj.weidumovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.bean.BuyTicket;
import com.gj.weidumovie.bean.LikeCinema;

import java.util.ArrayList;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/26 20:57
 */
public class MyLikeWaitAdapter extends RecyclerView.Adapter<MyLikeWaitAdapter.MyHolder> {

    Context context;

    public MyLikeWaitAdapter(Context context) {
        this.context = context;
    }

    ArrayList<BuyTicket> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.buyticket_wait_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        BuyTicket buyTicket = list.get(i);
        myHolder.name.setText(buyTicket.getMovieName());
        myHolder.code.setText("订单号："+buyTicket.getOrderId());
        myHolder.cinema.setText("影院："+buyTicket.getCinemaName());
        myHolder.cinemaRoom.setText("影厅："+buyTicket.getScreeningHall());
        myHolder.time.setText(buyTicket.getBeginTime()+"-"+buyTicket.getEndTime());
        myHolder.num.setText("数量："+buyTicket.getAmount()+"张");
        myHolder.money.setText("金额："+buyTicket.getPrice()+"元");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void remove() {
        list.clear();
    }

    public void addList(BuyTicket buyTicket) {
        if(buyTicket!=null){
            list.add(buyTicket);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView code;
        private final TextView cinema;
        private final TextView cinemaRoom;
        private final TextView time;
        private final TextView num;
        private final TextView money;
        private final Button pay;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.butticket_wait_name);
            code = itemView.findViewById(R.id.butticket_wait_code);
            cinema = itemView.findViewById(R.id.butticket_wait_cinema);
            cinemaRoom = itemView.findViewById(R.id.butticket_wait_cinema_room);
            time = itemView.findViewById(R.id.butticket_wait_time);
            num = itemView.findViewById(R.id.butticket_wait_num);
            money = itemView.findViewById(R.id.butticket_wait_money);
            pay = itemView.findViewById(R.id.butticket_wait_pay);
        }
    }
}
