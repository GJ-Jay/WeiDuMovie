package com.gj.weidumovie;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.fragment.Fragment_Cinema_two;
import com.gj.weidumovie.fragment.Fragment_My_Three;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends WDActivity {

    @BindView(R.id.checkout_frag)
    FrameLayout checkoutFrag;
    @BindView(R.id.radio)
    RadioGroup radio;
    private Fragment_Movie_One fragmentMovieOne;
    private Fragment_Cinema_two fragmentCinemaTwo;
    private Fragment_My_Three fragmentMyThree;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();//开启事务

        fragmentMovieOne = new Fragment_Movie_One();
        fragmentCinemaTwo = new Fragment_Cinema_two();
        fragmentMyThree = new Fragment_My_Three();

        transaction.add(R.id.checkout_frag,fragmentMovieOne).show(fragmentMovieOne);
        transaction.add(R.id.checkout_frag,fragmentCinemaTwo).hide(fragmentCinemaTwo);
        transaction.add(R.id.checkout_frag,fragmentMyThree).hide(fragmentMyThree);
        transaction.commit();//提交事务

        radio.check(radio.getChildAt(0).getId());

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case R.id.checkout_radio_one:
                        transaction1.show(fragmentMovieOne).hide(fragmentCinemaTwo).
                                hide(fragmentMyThree);
                        radio.check(radio.getChildAt(0).getId());
                        break;
                    case R.id.checkout_radio_two:
                        transaction1.show(fragmentCinemaTwo).hide(fragmentMovieOne).
                                hide(fragmentMyThree);
                        radio.check(radio.getChildAt(1).getId());
                        break;
                    case R.id.checkout_radio_three:
                        transaction1.show(fragmentMyThree).hide(fragmentMovieOne).
                                hide(fragmentCinemaTwo);
                        radio.check(radio.getChildAt(2).getId());
                        break;
                }
                transaction1.commit();//提交事务
            }
        });
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
