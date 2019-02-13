package com.gj.weidumovie.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.WDApplication;

/**
 * date:2019/1/25 8:37
 * author:陈国星(陈国星)
 * function:
 */
public class MySearchLayout extends RelativeLayout {

    private ObjectAnimator translationX;
    private ObjectAnimator translationZ;

    public MySearchLayout(Context context) {
        super(context);
        init();
    }

    public MySearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        View view = View.inflate(getContext(), R.layout.one_search_layout,this);
        ImageView imageView= view.findViewById(R.id.one_image_sou);
        TextView textView= view.findViewById(R.id.one_sou_ok);
        translationX = ObjectAnimator.ofFloat(this, "translationX", 0,-280);
        translationZ = ObjectAnimator.ofFloat(this, "translationX", -280,0);
        translationZ.setDuration(1000);
        translationZ.setInterpolator(new LinearInterpolator());
        translationZ.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                translationZ.pause();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        translationX.setDuration(1000);
        translationX.setInterpolator(new LinearInterpolator());
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                translationX.pause();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    translationX.start();
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                translationZ.start();
            }
        });
    }
}
