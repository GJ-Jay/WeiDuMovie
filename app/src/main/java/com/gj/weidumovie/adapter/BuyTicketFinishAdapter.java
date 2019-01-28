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
import com.gj.weidumovie.bean.BuyTicket;

import java.util.ArrayList;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/26 20:57
 */
public class BuyTicketFinishAdapter extends RecyclerView.Adapter<BuyTicketFinishAdapter.MyHolder> {

    Context context;

    public BuyTicketFinishAdapter(Context context) {
        this.context = context;
    }

    ArrayList<BuyTicket> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.buyticket_finish_item, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        BuyTicket buyTicket = list.get(i);
        myHolder.name.setText(buyTicket.getMovieName());
        myHolder.startName.setText(buyTicket.getBeginTime()+"-"+buyTicket.getEndTime());
        myHolder.code.setText("订单号："+buyTicket.getOrderId());
        myHolder.time.setText("下单时间"+buyTicket.getBeginTime());
        myHolder.cinema.setText("影院："+buyTicket.getCinemaName());
        myHolder.cinemaRoom.setText("影厅："+buyTicket.getScreeningHall());
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
        private final TextView startName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.butticket_finish_name);
            startName = itemView.findViewById(R.id.butticket_finish_start_time);
            code = itemView.findViewById(R.id.butticket_finish_code);
            time = itemView.findViewById(R.id.butticket_finish_time);
            cinema = itemView.findViewById(R.id.butticket_finish_cinema);
            cinemaRoom = itemView.findViewById(R.id.butticket_finish_cinema_room);
            num = itemView.findViewById(R.id.butticket_finish_num);
            money = itemView.findViewById(R.id.butticket_finish_money);
        }
    }
}
