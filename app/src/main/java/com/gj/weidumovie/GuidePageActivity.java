package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gj.weidumovie.core.WDActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页
 */
public class GuidePageActivity extends WDActivity {

    /*@BindView(R.id.welcome_jump)
    Button mWelcomeJump;*/
    @BindView(R.id.welcome_viewpager)
    ViewPager mWelcomeViewpager;
    @BindView(R.id.welcome_buttom_one)
    RadioButton welcomeButtomOne;
    @BindView(R.id.welcome_buttom_two)
    RadioButton welcomeButtomTwo;
    @BindView(R.id.welcome_buttom_three)
    RadioButton welcomeButtomThree;
    @BindView(R.id.welcome_buttom_four)
    RadioButton welcomeButtomFour;
    @BindView(R.id.radiogroup)
    RadioGroup mRadiogroup;
    private int currentItem = 0;
    private int flaggingWidth;
    private GestureDetector mGestureDetector;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initView() {
        final ArrayList<View> mList = new ArrayList<>();
        View one = View.inflate(this, R.layout.welcome_page_one, null);
        View two = View.inflate(this, R.layout.welcome_page_two, null);
        View three = View.inflate(this, R.layout.welcome_page_three, null);
        View four = View.inflate(this, R.layout.welcome_page_four, null);
        mList.add(one);
        mList.add(two);
        mList.add(three);
        mList.add(four);
        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;


        //设置页面
        mWelcomeViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = mList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        mRadiogroup.check(mRadiogroup.getChildAt(0).getId());//默认第一个页面选中
        //页面改变按钮也跟着变化
        mWelcomeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mRadiogroup.check(mRadiogroup.getChildAt(i).getId());
                currentItem = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (currentItem == 3) {
                            if ((e1.getRawX() - e2.getRawX()) >= flaggingWidth) {

                                Intent intent = new Intent(
                                        GuidePageActivity.this,
                                        HomeActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                            }
                        }
                        return false;
                    }

                });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
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
