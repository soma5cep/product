package com.example.product;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityStockDetail extends FragmentActivity{
	private ArrayList<String> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_detail);		
		
		ListView lv = (ListView)findViewById(R.id.list_view);
		items = new ArrayList<String>();
		items.add("!");
		items.add("a");
		items.add("b");
		
		lv.setAdapter(new MyAdapter(this, R.layout.fragment_signal_stock_item_child, items));
		
		FragmentManager fm=getSupportFragmentManager();		
		if(fm.findFragmentById(R.id.bottom_menu) == null) {
			BottomMenu.BottomMenu_1 bottom_menu = new BottomMenu.BottomMenu_1();
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
			Intent intent = new Intent(this, ActivityStockDetailSettings.class);
			startActivity(intent);
			//설정 메뉴로 진입
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


/***************************************
 * 
 * @author NamHoon
 * 		어댑터
 *
 ****************************************/

class MyAdapter extends ArrayAdapter<String> {
	private ArrayList<String> items;
	private Context context;
	private int resource;
	
	public MyAdapter(Context context, int viewResourceId, ArrayList<String> items) {
		super(context, viewResourceId, items);
		this.items = items;
		this.context = context;
		resource = viewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		RelativeLayout v = (RelativeLayout)convertView;
		ViewHolder vh;
		if (v == null) {
			v = (RelativeLayout)View.inflate(context, resource, null);
			vh = new ViewHolder();
			
			vh.signal = (TextView)v.findViewById(R.id.signal);	
			vh.inout = (TextView)v.findViewById(R.id.inout);
			vh.stock_name = (TextView)v.findViewById(R.id.stock_name);
			vh.market_type = (TextView)v.findViewById(R.id.market_type);
			vh.time = (TextView)v.findViewById(R.id.time);
			vh.price_diff_percent = (TextView)v.findViewById(R.id.price_diff_percent);
			vh.price_diff = (TextView)v.findViewById(R.id.price_diff);
			vh.trading_volume = (TextView)v.findViewById(R.id.trading_volume);
			vh.stock_price = (TextView)v.findViewById(R.id.stock_price);
			
			v.setTag(vh);
		}
		else {
			vh = (ViewHolder)v.getTag();
		}
		RTSBox b = items.get(position);
		if(b != null) {
			ConvertRTSBox.convertBoxToRel(b, vh);
		}
		return v;
		*/
		return (RelativeLayout)View.inflate(context, resource, null);
	}
	
	public static class ViewHolder {		
		TextView signal;
		TextView inout;
		TextView stock_name;
		TextView market_type;
		TextView time;
		TextView price_diff_percent;
		TextView price_diff;
		TextView trading_volume;
		TextView stock_price;		
	}
}