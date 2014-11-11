package com.example.product.j;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.example.product.R;

public class SearchItem extends Activity{
	ActionBar.Tab Tab1, Tab2;
	Fragment fragmentTab1 = new FragmentTab1();
	Fragment fragmentTab2 = new FragmentTab2();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_item);
		ActionBar actionBar = getActionBar();
		
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab1 = actionBar.newTab().setText("종목으로 검색");
		Tab2 = actionBar.newTab().setText("조건으로 검색");
		
		Tab1.setTabListener(new TabListener(fragmentTab1));
		Tab2.setTabListener(new TabListener(fragmentTab2));
		
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}
	
}