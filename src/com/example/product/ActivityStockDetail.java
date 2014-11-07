package com.example.product;

import java.util.ArrayList;

import com.example.product.FragmentSignal.FragmentSignalStock.MyExpAdapter.ChildViewHolder;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityStockDetail extends FragmentActivity{
	private ArrayList<SignalOfStock> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_detail);		
		
		
		//타이틀 설정
		Intent intent = getIntent();
		String stock_name = intent.getStringExtra("stock_name");
		if(stock_name != null) {
			setTitle(stock_name);			
		}
		
		
		// 해당 주식 종목에 대해 정보를 받아옴
		/* 서버에 리퀘스트를 보내는 코드 
		 * 
		 * 
		 * 
		 * */
		
		// 임시 test 코드 
		DetailOfStock detail = new DetailOfStock();
		detail.price = "150,000";
		detail.price_diff = "-3500";
		detail.price_diff_percent="(-12.5%)";
		detail.high_price="170,000";
		detail.low_price="145,000";
		detail.trading_volume="48,000";
		detail.trading_volume_avr="77,000";
		
		
		
		// 받아온 데이터를 화면에 띄움
		
		//가격 정보
		TextView price = (TextView)findViewById(R.id.price);
		price.setText(detail.price);
		
		
		//price_diff 에 따라 값을 설정		
		//price_diff 값에 쉼표가 들어가면  parse가 안된다. 주의!
		TextView price_diff = (TextView)findViewById(R.id.price_diff);
		TextView price_diff_percent = (TextView)findViewById(R.id.price_diff_percent);
		if(Integer.parseInt(detail.price_diff) > 0) {
			price_diff.setTextColor(getResources().getColor(R.color.red));
			price_diff_percent.setTextColor(getResources().getColor(R.color.red));	
		}
		else if(Integer.parseInt(detail.price_diff) < 0) {
			price_diff.setTextColor(getResources().getColor(R.color.blue));
			price_diff_percent.setTextColor(getResources().getColor(R.color.blue));	
		}
		else {
			price_diff.setTextColor(getResources().getColor(R.color.black));
			price_diff_percent.setTextColor(getResources().getColor(R.color.black));	
		}		
		price_diff.setText(detail.price_diff);
		price_diff_percent.setText(detail.price_diff_percent);
		
		//고가 저가 설정
		TextView high_price = (TextView)findViewById(R.id.highest_price);
		TextView low_price = (TextView)findViewById(R.id.lowest_price);
		
		if(detail.high_price == detail.low_price) {
			high_price.setTextColor(getResources().getColor(R.color.black));
			low_price.setTextColor(getResources().getColor(R.color.black));
		}
		else {
			high_price.setTextColor(getResources().getColor(R.color.red));
			low_price.setTextColor(getResources().getColor(R.color.blue));
		}
		high_price.setText(detail.high_price);
		low_price.setText(detail.low_price);
		
		//거래량 설정		
		TextView trading_volume = (TextView)findViewById(R.id.trade_volume);
		TextView trading_volume_avr = (TextView)findViewById(R.id.trade_volume_average);
		trading_volume.setText(detail.trading_volume);
		trading_volume_avr.setText(detail.trading_volume_avr);
		

				
		
		
		
		

		ListView lv = (ListView)findViewById(R.id.list_view);
		
		
		/*test */
		items = new ArrayList<SignalOfStock>();
		SignalOfStock tt = new SignalOfStock();		
		items.add(tt);
		items.add(tt);
		items.add(tt);
		
		lv.setAdapter(new MyAdapter(this, R.layout.activity_stock_detail_item, items));
		
		/* 하단 메뉴 설정 */
		FragmentManager fm=getSupportFragmentManager();
		BottomMenu.BottomMenu_1 bottom_menu;
		if((bottom_menu = (BottomMenu.BottomMenu_1)fm.findFragmentById(R.id.bottom_menu)) == null) {
			bottom_menu = new BottomMenu.BottomMenu_1();
			fm.beginTransaction().add(R.id.bottom_menu, bottom_menu, "bottom_menu").commit();
		}
		

		//클릭 리스너 등록		
		Button.OnClickListener bt_listener = new Button.OnClickListener() {						
			@Override
			public void onClick(View v) {
				switch(v.getId()) {
				case R.id.all_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_all();
					my_adapter.notifyDataSetChanged();
					*/
					
					Toast.makeText(ActivityStockDetail.this, "all_btn clicked", 0).show();
					break;
				case R.id.total_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_total();
					my_adapter.notifyDataSetChanged();
					*/
					
					Toast.makeText(ActivityStockDetail.this, "total_btn clicked", 0).show();
					break;
				case R.id.indiv_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_indiv();
					my_adapter.notifyDataSetChanged();
					*/
					Toast.makeText(ActivityStockDetail.this, "indiv_btn clicked", 0).show();
					break;
				case R.id.alarm_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_alarm();
					my_adapter.notifyDataSetChanged();
					*/
					
					Toast.makeText(ActivityStockDetail.this, "alarm_btn clicked", 0).show();
					break;
				default :
					//do nothing
					break;
				}
			}		
		};
		bottom_menu.setButtonClickListener(bt_listener);
		
		
		
		/* 주식의 상세 정보를 출력하는 부분 하기 */
		
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
			//인텐트에 주식의 이름을 전달		
			intent.putExtra("stock_name", getTitle());		
			//설정 메뉴로 진입
			startActivity(intent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



/***************************************
 * 
 * @author NamHoon
 * 		어댑터
 *
 ****************************************/


	/* FragmentSignal의 종목별 어댑터의 ChildView 하는 부분과 동일 따라서 복붙 */
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