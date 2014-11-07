package com.example.product;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityStockDetailSettings extends Activity{
	ArrayList<SettedCond> list;
	
	public void myNotifyDataSetChanged() {
		// 이 코드를 데이터 셋이 바뀔 때마다 수행		
		LinearLayout total_linear = (LinearLayout)findViewById(R.id.linear_1);
		LinearLayout indiv_linear = (LinearLayout)findViewById(R.id.linear_2);
		
		total_linear.removeAllViews();
		indiv_linear.removeAllViews();
		
		for(int i=0; i<list.size(); ++i) {
			SettedCond data = list.get(i);
			RelativeLayout layout;
			layout = (RelativeLayout)View.inflate(this, R.layout.activity_stock_detail_settings_item, null);
			if(data.signal_type == SettedCond.TOTAL) {			
				total_linear.addView(layout);
			}
			else if(data.signal_type == SettedCond.INDIV) {					
				indiv_linear.addView(layout);
			}
			else {
				layout = null;
			}
			TextView cond_name = (TextView)layout.findViewById(R.id.signal);
			ImageView image = (ImageView)layout.findViewById(R.id.image);
			View cond_type = (View)layout.findViewById(R.id.type_color);
			ImageButton alarm_bt = (ImageButton)layout.findViewById(R.id.alarm_bt);
			ImageButton delete_bt = (ImageButton)layout.findViewById(R.id.delete_bt);
		
			cond_name.setText(data.cond_name);
			/* cond_name 에 따라서 이미지를 변경하는 코드를 작성할 것. */
		
		
			//이미지 아래 VIew 색을 변경
			if(data.cond_type == SettedCond.EASY) {
				cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
			}
			else if(data.cond_type == SettedCond.HARD) {
				cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));
			}
			else if(data.cond_type == SettedCond.CUSTOM) {
				cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_custom));
			}
		
			//알람 이미지를 변경
			if(data.is_alarm == SettedCond.IS_ALARM) {
				//알람 일때 설정코드
			}
			else if(data.is_alarm == SettedCond.IS_NOT_ALARM) {
				//알람이 아닐때
			}
		
		
			alarm_bt.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(ActivityStockDetailSettings.this, "알람버튼이 눌려졌을때 동작을 수행", 0).show();
					/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
				}						
			});
			
			final int position = i;
		

			delete_bt.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {							
					Toast.makeText(ActivityStockDetailSettings.this, ""+position+" 번째 아이템을 삭제", 0).show();						
					list.remove(position);
					myNotifyDataSetChanged();
					// 삭제 헀다는 정보를 서버에 보내야 함 
				}
			});
		}		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_detail_settings);
		
		
		//타이틀 설정
		Intent intent = getIntent();
		String stock_name = intent.getStringExtra("stock_name");
		if(stock_name != null) {
			setTitle(stock_name);			
		}
		
		if(list == null) {
			/* 서버로부터 데이터를 받아옴 */
			list = new ArrayList<SettedCond>();
			list.add(new SettedCond());
			list.add(new SettedCond());
			list.add(new SettedCond());
			SettedCond data = new SettedCond();
			data.signal_type = SettedCond.INDIV;
			list.add(data);
			list.add(data);
			list.add(data);
			
		}
		
		myNotifyDataSetChanged();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_stock_detail_settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		int id = item.getItemId();
		if (id == R.id.action_add) {			
			// 추가함
			Intent intent = new Intent(this, ActivityCondition.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}