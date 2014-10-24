package com.example.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;


public class FragmentSignal extends Fragment {
	private static final int NUM_PAGES = 3;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_signal, container, false);
		
		mPager = (ViewPager)root.findViewById(R.id.pager);
		mPagerAdapter = new SignalPagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		

        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		return root;
	}
	
	
	private class SignalPagerAdapter extends FragmentPagerAdapter {
		
		
		public SignalPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0 :
					return new FragmentSignalTotal();
				case 1 :
					return new FragmentSignalStock();
				case 2 :
					return new FragmentSignalSignal();
				default :
					return null;
			}
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch(position) {
				case 0 :
					return "전체";
				case 1 :
					return "종목별";
				case 2 :
					return "신호별";
				default :
					return "";
			}
		}
	}
	
	/* 전체 */
	public static class FragmentSignalTotal extends ListFragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_total, container, false);
			return root;			
		}
	}
	
	/* 종목별 */
	public static class FragmentSignalStock extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_stock, container, false);
			return root;			
		}
	}
	
	/* 신호별 */
	public static class FragmentSignalSignal extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_signal, container, false);
			return root;			
		}
	}
	
	
}