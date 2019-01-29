package com.gj.weidumovie.core;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gj.weidumovie.util.LogUtils;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {
	public Gson mGson = new Gson();
	public SharedPreferences mShare = WDApplication.getShare();

	private Unbinder unbinder;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
		long time = System.currentTimeMillis();
		View view = inflater.inflate(getLayoutId(),container,false);
		unbinder = ButterKnife.bind(this,view);
		initView();
		LogUtils.e(this.toString()+"页面加载使用："+(System.currentTimeMillis()-time));
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

		@Override
	public void onResume() {
		super.onResume();
		//if (!MTStringUtils.isEmpty(getPageName()))
			MobclickAgent.onPageStart(getPageName()); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		//if (!MTStringUtils.isEmpty(getPageName()))
			MobclickAgent.onPageEnd(getPageName());// 统计页面
	}

	/**
	 * 设置页面名字 用于友盟统计
	 */
	public abstract String getPageName();
	/**
	 * 设置layoutId
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();
}
