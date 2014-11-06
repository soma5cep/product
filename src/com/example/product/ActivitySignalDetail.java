package com.example.product;

import java.util.ArrayList;

import com.example.product.ActivityStockDetail.MyAdapter.ChildViewHolder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
		
		//lv.setAdapter(new MyAdapter(this, R.layout.fragment_signal_signal_item_child, items));
		
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
	
	
	class MyAdapter extends ArrayAdapter<SignalOfStock> {
		private ArrayList<SignalOfStock> items;
		private Context context;
		private int resource;

		public MyAdapter(Context context, int viewResourceId, ArrayList<SignalOfStock> items) {
			super(context, viewResourceId, items);
			this.items = items;
			this.context = context;
			resource = viewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			ChildViewHolder childViewHolder;
			if (v == null) {
				v = View.inflate(context, resource, null);
				childViewHolder = new ChildViewHolder();


				childViewHolder.image = (ImageView)v.findViewById(R.id.image);
				childViewHolder.cond_type = (View)v.findViewById(R.id.type_color);
				childViewHolder.signal_name = (TextView)v.findViewById(R.id.signal);
				childViewHolder.signal_type = (TextView)v.findViewById(R.id.signal_type);
				childViewHolder.date = (TextView)v.findViewById(R.id.date);
				childViewHolder.alarm_bt = (ImageButton)v.findViewById(R.id.alarm_bt);
				v.setTag(childViewHolder);

				ImageButton bt = (ImageButton)v.findViewById(R.id.alarm_bt);
				bt.setFocusable(false);
				/* 버튼 리스너 등록할 것 */
			}else{
				childViewHolder = (ChildViewHolder)v.getTag();
			}

			SignalOfStock data = items.get(position);


			childViewHolder.signal_name.setText(data.signal_name);
			/* 이미지 교체 작업 코드 작성할 것*/			

			//이미지 밑에 색을 변경
			if(data.cond_type == SignalOfStock.EASY) {
				childViewHolder.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
			}
			else if(data.cond_type == SignalOfStock.HARD) {
				childViewHolder.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));
			}
			else if(data.cond_type == SignalOfStock.CUSTOM) {
				childViewHolder.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_custom));
			}

			//전체/개별 태그를 설정
			if(data.signal_type == SignalOfStock.TOTAL) {
				childViewHolder.signal_type.setText("전체");
				childViewHolder.signal_type.setBackgroundColor(getResources().getColor(R.color.signal_type_total));
			}
			else if(data.signal_type == SignalOfStock.INDIV) {
				childViewHolder.signal_type.setText("개별");
				childViewHolder.signal_type.setBackgroundColor(getResources().getColor(R.color.signal_type_indiv));
			}		

			//날짜를 설정
			childViewHolder.date.setText(data.date);


			//알람 설정
			if(data.is_alarm == SignalOfStock.IS_ALARM) {
				//알람 일때 설정코드
			}
			else if(data.is_alarm == SignalOfStock.IS_NOT_ALARM) {
				//알람이 아닐때
			}


			return v;

		}


		public class ChildViewHolder {		
			ImageView image;
			TextView signal_name;
			View cond_type;
			TextView signal_type;
			TextView date;
			ImageButton alarm_bt;
		}
	}
}

