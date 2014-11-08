package com.example.product;

import java.util.ArrayList;

import com.example.product.FragmentSignal.FragmentSignalSignal.MyExpAdapter.ChildViewHolder;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySignalDetail extends FragmentActivity {
	SignalDetail signaldetail;  //static 이면 안됨. 매번 새로운 정보를 받아야 한다.
	ArrayList<SignalOfSignal> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signal_detail);
		
		
		//타이틀 설정
		Intent intent = getIntent();
		String signal_name = intent.getStringExtra("signal_name");
		if(signal_name != null) {
			setTitle(signal_name);			
		}

		
		// 포함조건 동적으로 추가
		// 시그널 디테일 서버에서 받아오기
		signaldetail = new SignalDetail();
		signaldetail.compose_signal.add(new SimpleCond());
		signaldetail.compose_signal.add(new SimpleCond());
		signaldetail.compose_signal.add(new SimpleCond());
		
		// 해당 조건에 대한 시그널을 서버에서 받아오기 
		list = new ArrayList<SignalOfSignal>();
		list.add(new SignalOfSignal());
		list.add(new SignalOfSignal());

		
		ArrayList<SimpleCond> cps = signaldetail.compose_signal; // 포함조건

		// MyAdapter는 ActivityStockDetail 에 있음
		//어댑터 등록
		ListView lv = (ListView)findViewById(R.id.list_view);		
		lv.setAdapter(new MyAdapter(this, R.layout.activity_signal_detail_item, list));
		
		LinearLayout cond_linear = (LinearLayout)findViewById(R.id.cond_linear);
		
		// 여기로 진입하면 안됨.
		if(cond_linear.getChildCount() != 0) {
			//차일드가 있으면 안됨.
			Toast.makeText(this, "경고! 포함조건 linear에 차일드가 있음!", 0).show();
			cond_linear.removeAllViews();
		}
		
		// 동적으로 포함조건 목록을 넣어줌
		for(int i=0; i<cps.size(); ++i) {
			SimpleCond data = cps.get(i);
			LinearLayout horizon_linear;
			RelativeLayout rel_root;
			if(i % 2 == 0) {
				horizon_linear = (LinearLayout)View.inflate(this, R.layout.simple_horizontal_linear_with_two_itmes, null);
				cond_linear.addView(horizon_linear);
				rel_root = (RelativeLayout)horizon_linear.findViewById(R.id.first_rel);
			}
			else {
				horizon_linear = (LinearLayout)cond_linear.getChildAt(i/2);
				rel_root = (RelativeLayout)horizon_linear.findViewById(R.id.second_rel);
			}
			RelativeLayout v = (RelativeLayout)View.inflate(this, R.layout.simple_condition, rel_root);
			
			ImageView image = (ImageView)v.findViewById(R.id.image);
			View cond_type = (View)v.findViewById(R.id.type_color);
			TextView cond_name = (TextView)v.findViewById(R.id.condition_name);
			
			/* 이미지 교체 코드 작성 */
			
			//타입 색 변경 코드 작성
			//이미지 아래 VIew 색을 변경
			if(data.cond_type == SimpleCond.EASY) {
				cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
			}
			else if(data.cond_type == SimpleCond.HARD) {
				cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));
			}
			
			//조건 이름 변경
			cond_name.setText(data.cond_name);			
		}	

		
		FragmentManager fm=getSupportFragmentManager();	
		BottomMenu.BottomMenu_2 bottom_menu = null;
		if(fm.findFragmentById(R.id.bottom_menu) == null) {
			bottom_menu = new BottomMenu.BottomMenu_2();
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
					
					Toast.makeText(ActivitySignalDetail.this, "all_btn clicked", 0).show();
					break;
				case R.id.alarm_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_alarm();
					my_adapter.notifyDataSetChanged();
					*/
					
					Toast.makeText(ActivitySignalDetail.this, "alarm_btn clicked", 0).show();
					break;
				default :
					//do nothing
					break;
				}
			}		
		};
		bottom_menu.setButtonClickListener(bt_listener);
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
	
	
	class MyAdapter extends ArrayAdapter<SignalOfSignal> {
		private ArrayList<SignalOfSignal> items;
		private Context context;
		private int resource;

		public MyAdapter(Context context, int viewResourceId, ArrayList<SignalOfSignal> items) {
			super(context, viewResourceId, items);
			this.items = items;
			this.context = context;
			resource = viewResourceId;
		}

		//FragmentSignal 에서 신호-신호별 exp 어댑터의 childView 부분.
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			ChildViewHolder childViewHolder;

			if(v == null){
	            childViewHolder = new ChildViewHolder();
	            v = View.inflate(context, resource, null);
	            
	            childViewHolder.stock_name = (TextView)v.findViewById(R.id.stock_name);
	            childViewHolder.price = (TextView)v.findViewById(R.id.price);
	            childViewHolder.date = (TextView)v.findViewById(R.id.date);
	            childViewHolder.alarm_bt = (ImageButton)v.findViewById(R.id.alarm_bt);
	            v.setTag(childViewHolder);
	            
				ImageButton bt = (ImageButton)v.findViewById(R.id.alarm_bt);
				bt.setFocusable(false);
				/* 버튼 리스너 등록할 것 */
	        }else{
	            childViewHolder = (ChildViewHolder)v.getTag();
	        }
			
			SignalOfSignal data = items.get(position);
	
			//주식명을 변경
			childViewHolder.stock_name.setText(data.stock_name);
			
			//주식 가격을 변경
			childViewHolder.price.setText(data.price);
			
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
			TextView stock_name;
			TextView price;
			TextView date;
			ImageButton alarm_bt;
		}
	}
}

