package com.example.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentSettings extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_settings, container, false);		
		return root;
	}
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	*/
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_add_alarm :
				// 전체 신호 추가 Activity
				/*
				Intent intent = new Intent(getActivity(), StockDetail.class);
				startActivity(intent);
				*/		
				
				return true;
			case R.id.action_settings :
				//do s.th.
				
				return true;
			default :
				Toast.makeText(getActivity(), "영문이 바보", 0).show();
				return super.onOptionsItemSelected(item);
		}
	}
	
	

}
