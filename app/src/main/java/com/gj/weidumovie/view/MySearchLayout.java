package com.gj.weidumovie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.WDApplication;

/**
 * date:2019/1/25 8:37
 * author:陈国星(陈国星)
 * function:
 */
public class MySearchLayout extends RelativeLayout {
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
    }
}
