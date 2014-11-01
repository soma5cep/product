package com.example.product;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ActivitySignalDetail extends FragmentActivity {
	private ArrayList<String> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signal_detail);		
		
		ListView lv = (ListView)findViewById(R.id.list_view);
		items = new ArrayList<String>();
		items.add("!");
		items.add("a");
		items.add("b");
		
		
		
		// MyAdapter는 ActivityStockDetail 에 있음
		
		lv.setAdapter(new MyAdapter(this, R.layout.fragment_signal_signal_item_child, items));
		
		FragmentManager fm=getSupportFragmentManager();		
		if(fm.findFragmentById(R.id.bottom_menu) == null) {
			BottomMenu.BottomMenu_2 bottom_menu = new BottomMenu.BottomMenu_2();
			fm.beginTransaction().add(R.id.bottom_menu, bottom_menu, "bottom_menu").commit();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_stock_detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		int id = item.getItemId();
		if (id == R.id.action_my_settings) {
			Toast.makeText(this, "신호별로 종목을 설정하는 페이지는 차후 버전에서 구현", 0).show();

			//설정 메뉴로 진입
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

