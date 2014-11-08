package com.example.product;

import java.util.ArrayList;

import com.example.product.FragmentSignal.FragmentSignalStock.MyExpAdapter.ChildViewHolder;
import com.example.product.FragmentSignal.FragmentSignalStock.MyExpAdapter.GroupViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMyStock extends FragmentActivity{
	private ExpandableListView mExpListView;	
	FragmentManager fm; //onCreate에서 초기화

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_expandablelistview);
		
		mExpListView = (ExpandableListView)findViewById(R.id.explist);
		

		//매번 갱신되게 해야함.
		ArrayList<SignalSortByStock> list = new ArrayList<SignalSortByStock>();

		/* 임시로 데이터 만들기 */
		SignalSortByStock data_t = new SignalSortByStock();

		data_t.total_cnt = 0;
		data_t.indiv_cnt = 12;
		data_t.stock_name = "소마제철소";
		data_t.price = "1,154,000";
		data_t.price_diff = "-98711";
		data_t.price_diff_percent="(-3.12%)";
		
		SignalOfStock stock = new SignalOfStock();
		stock.signal_type = SignalOfStock.INDIV;
		data_t.list.add(stock);
		data_t.list.add(stock);
		data_t.list.add(stock);


		list.add(data_t);
		list.add(data_t);
		list.add(data_t);
	
		
		
		mExpListView.setAdapter(new MyExpAdapter(this, list));
		
		/*Bottom Menu 에 대한 버튼 클릭 리스너를 등록하자. */
		
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
					
					Toast.makeText(ActivityMyStock.this, "all_btn clicked", 0).show();
					break;
				case R.id.alarm_btn :
					/*
					received_signal = MyDataBase.getReceivedSignalList_alarm();
					my_adapter.notifyDataSetChanged();
					*/
					
					Toast.makeText(ActivityMyStock.this, "alarm_btn clicked", 0).show();
					break;
				default :
					//do nothing
					break;
				}
			}		
		};
		bottom_menu.setButtonClickListener(bt_listener);
	}
	
	
	//FragmentSIgnal 에서 종목별 보여주는 부분의 어댑터와 동일하다.
	//일부 코드만 수정
	class MyExpAdapter extends BaseExpandableListAdapter {
		//private int GROUP_CNT = 3;
		//private int CHILD_CNT = 5;
		private int CHILD_CNT_MAX = 5;
		
		Context context;
		ArrayList<SignalSortByStock> list;
		
		/* 뷰홀더 코드 */
		//private ViewHoler viewHolder = null;
		
		public MyExpAdapter(Context context, ArrayList<SignalSortByStock> list) { //부모그룹이랑 차일드 그룹을 받아야 함.
			super();
			this.context = context;
			this.list=list;
		}
		
		@Override
		public String getGroup(int groupPosition) {
			return "Parent";
		}
		
		@Override
		public int getGroupCount() {
			return list.size();
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		
		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			View v = convertView;
			GroupViewHolder viewHolder;
			if(v == null){
	            viewHolder = new GroupViewHolder();
	            v = View.inflate(ActivityMyStock.this, R.layout.activity_my_stock_item_parent, null);		            
	            
	            viewHolder.stock_name = (TextView) v.findViewById(R.id.stock_name);
	            viewHolder.total_cnt = (TextView)v.findViewById(R.id.total_cnt);         
	            viewHolder.indiv_cnt = (TextView)v.findViewById(R.id.indiv_cnt);
	            viewHolder.price = (TextView)v.findViewById(R.id.price);
	            viewHolder.price_diff = (TextView)v.findViewById(R.id.diff);
	            viewHolder.price_diff_percent = (TextView)v.findViewById(R.id.diff_percent);

	            v.setTag(viewHolder);
	        }else{
	            viewHolder = (GroupViewHolder)v.getTag();
	        }								
			SignalSortByStock groupData = list.get(groupPosition);
			viewHolder.stock_name.setText(groupData.stock_name);
			viewHolder.total_cnt.setText(""+groupData.total_cnt);
			viewHolder.indiv_cnt.setText(""+groupData.indiv_cnt);
			viewHolder.price.setText(groupData.price);
			viewHolder.price_diff.setText(groupData.price_diff);
			viewHolder.price_diff_percent.setText(groupData.price_diff_percent);
			
            /* button에 listener를 등록
             * v == null 일때 뷰 홀더를 설정하는 부분에서 코드를 넣을 수도 있다.
             * 위 방법이 더 효율적이지만 일단 아래와 같이 구현.
             *  
             *  */		

			ImageButton bt = (ImageButton)v.findViewById(R.id.go_bt);
			
			bt.setFocusable(false);
			bt.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {							
					Intent intent = new Intent(ActivityMyStock.this, ActivityStockDetail.class);					
					
					//인텐트에 클릭된 신호의 주식이름 정보를 전달
					SignalSortByStock groupdata = list.get(groupPosition);			
					intent.putExtra("stock_name", groupdata.stock_name);						
					
					startActivity(intent);
				}
			});

			
			
			
			/* 전일 종가 대비 변동량 색 변경 */
			if(Integer.parseInt(groupData.price_diff) < 0) {
				viewHolder.price_diff.setTextColor(getResources().getColor(R.color.blue));
				viewHolder.price_diff_percent.setTextColor(getResources().getColor(R.color.blue));
			}
			else if(Integer.parseInt(groupData.price_diff) > 0) {
				viewHolder.price_diff.setTextColor(getResources().getColor(R.color.red));
				viewHolder.price_diff_percent.setTextColor(getResources().getColor(R.color.red));
			}
			else {
				viewHolder.price_diff.setTextColor(getResources().getColor(R.color.black));
				viewHolder.price_diff_percent.setTextColor(getResources().getColor(R.color.black));
			}
			
			/* 숫자가 0보다 클때만 보이게 */
			if(groupData.total_cnt > 0 ) {
				viewHolder.total_cnt.setVisibility(View.VISIBLE);
			}
			else {
				viewHolder.total_cnt.setVisibility(View.GONE);
			}
			if(groupData.indiv_cnt > 0 ) {
				viewHolder.indiv_cnt.setVisibility(View.VISIBLE);
			}
			else {
				viewHolder.indiv_cnt.setVisibility(View.GONE);
			}
			
			
			/* test 용 */
			if(groupPosition == 2) {
				v.findViewById(R.id.total_cnt).setVisibility(View.GONE);
			}						

			return v;
		}
		
		public class GroupViewHolder {			
			TextView stock_name;
			TextView total_cnt;
			TextView indiv_cnt;
			TextView price;
			TextView price_diff;
			TextView price_diff_percent;
		}
		
		@Override
		public String getChild(int groupPosition, int childPosition) {
			return "child";
		}
		
		@Override
		public int getChildrenCount(int groupPosition) {
			if(list.get(groupPosition).list.size() < CHILD_CNT_MAX) {
				return list.get(groupPosition).list.size();
			}
			else {
				return CHILD_CNT_MAX;
			}
	    }
		
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			View v = convertView;
			ChildViewHolder childViewHolder;

			if(v == null){
	            childViewHolder = new ChildViewHolder();
	            v = View.inflate(ActivityMyStock.this, R.layout.activity_my_stock_item_child, null);
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
			
			SignalOfStock data = list.get(groupPosition).list.get(childPosition);
			
			
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
			
			
			
			
			
			
			
			//종목별 코드와 다른점
			// 날짜를 안보이게 한다.
			childViewHolder.date.setVisibility(View.GONE);			

			
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
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
