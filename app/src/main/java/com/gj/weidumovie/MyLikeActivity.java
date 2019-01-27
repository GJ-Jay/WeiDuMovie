package com.gj.weidumovie;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.fragment.mylike.Fragment_Mylike_Cinema;
import com.gj.weidumovie.fragment.mylike.Fragment_Mylike_Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLikeActivity extends WDActivity {

    @BindView(R.id.mylike_radio1)
    RadioButton mylikeRadio1;
    @BindView(R.id.mylike_radio2)
    RadioButton mylikeRadio2;
    @BindView(R.id.mylike_radio)
    RadioGroup mylikeRadio;
    @BindView(R.id.mylike_frag)
    FrameLayout myliskeFrag;
    private Fragment_Mylike_Movie mylikeMovie;
    private Fragment_Mylike_Cinema mylikeCinema;
    private FragmentManager manager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_like;
    }

    @Override
    protected void initView() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mylikeMovie = new Fragment_Mylike_Movie();
        mylikeCinema = new Fragment_Mylike_Cinema();
        transaction.add(R.id.mylike_frag, mylikeMovie).show(mylikeMovie);//页面切换
        transaction.add(R.id.mylike_frag, mylikeCinema).hide(mylikeCinema);
        transaction.commit();

        mylikeRadio.check(mylikeRadio.getChildAt(0).getId());//默认第一个页面选中
        mylikeRadio1.setBackgroundResource(R.drawable.my_like_btnshape);
        mylikeRadio1.setTextColor(getResources().getColor(R.color.white));

        mylikeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case R.id.mylike_radio1:
                        transaction1.show(mylikeMovie).hide(mylikeCinema);
                        mylikeRadio.check(mylikeRadio.getChildAt(0).getId());
                        mylikeRadio1.setBackgroundResource(R.drawable.my_like_btnshape);
                        mylikeRadio2.setBackgroundResource(R.drawable.my_like_btn_falseshape);
                        mylikeRadio1.setTextColor(getResources().getColor(R.color.white));
                        mylikeRadio2.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    case R.id.mylike_radio2:
                        transaction1.show(mylikeCinema).hide(mylikeMovie);
                        mylikeRadio.check(mylikeRadio.getChildAt(1).getId());
                        mylikeRadio2.setBackgroundResource(R.drawable.my_like_btnshape);
                        mylikeRadio1.setBackgroundResource(R.drawable.my_like_btn_falseshape);
                        mylikeRadio2.setTextColor(getResources().getColor(R.color.white));
                        mylikeRadio1.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                }
                transaction1.commit();//提交事务
            }
        });
    }

    @OnClick(R.id.mylike_back)
    public void setClick(){
        finish();
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
