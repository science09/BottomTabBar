package com.example.bottomtabbar;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private LinearLayout ll_top;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
	}

	private void initView() {
		ll_top = (LinearLayout) findViewById(R.id.ll_top);
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();

		Fragment activityfragment = TestFragment.newInstance("界面一");
		Fragment groupFragment = TestFragment.newInstance("界面二");
		Fragment friendsFragment = TestFragment.newInstance("界面三");
		Fragment chatFragment = TestFragment.newInstance("界面四");

		fragmentsList.add(activityfragment);
		fragmentsList.add(groupFragment);
		fragmentsList.add(friendsFragment);
		fragmentsList.add(chatFragment);

		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);

		BottomTabBar navigationBar = new BottomTabBar(this);
		navigationBar.attachToParent(ll_top, new String[] { "首页", "消息", "发现",
				"设置" }, mPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> fragmentsList;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList = fragments;
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentsList.get(arg0);
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

	}

}
