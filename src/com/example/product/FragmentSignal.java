package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;




public class FragmentSignal extends Fragment {
	private ViewPager mPager;
	private SignalPagerAdapter mPagerAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.view_pager, container, false);
		
		mPager = (ViewPager)root.findViewById(R.id.pager);
		mPagerAdapter = new SignalPagerAdapter(getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);

        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		return root;
	}
	
	public void change_frag_0(Fragment fragment) {
		mPagerAdapter.switch_fragment(fragment);
	}
	
	
/*********************************************************************************************************************
//
//				뷰페이저 어댑터
//
*********************************************************************************************************************/
	private class SignalPagerAdapter extends FragmentStatePagerAdapter {
		private static final int NUM_PAGES = 3;
		private final FragmentManager fm;
		private Fragment frag_0;
		
		public void switch_fragment(Fragment fragment) {
			if(fragment instanceof FragmentSignalTotal) {

				fm.beginTransaction().remove(frag_0).commit();
				frag_0 = new FragmentSignalTotalSettings(FragmentSignal.this);
				notifyDataSetChanged();						
			}
			else if(fragment instanceof FragmentSignalTotalSettings) {
				fm.beginTransaction().remove(frag_0).commit();
				frag_0 = new FragmentSignalTotal(FragmentSignal.this);
				notifyDataSetChanged();		
			}
		}
		
		public SignalPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
		@Override
		public int getItemPosition(Object object) {
			if(object instanceof FragmentSignalTotal && frag_0 instanceof FragmentSignalTotalSettings) {
				return POSITION_NONE;				
			}
			else if(object instanceof FragmentSignalTotalSettings && frag_0 instanceof FragmentSignalTotal) {
				return POSITION_NONE;
			}
			return POSITION_UNCHANGED;
		}

		
		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0 :
					if(frag_0 == null) {
						frag_0 = new FragmentSignalTotal(FragmentSignal.this);
					}
					return frag_0;
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

/*********************************************************************************************************************
//
//				신호 - 전체 FRAGMENT
//
*********************************************************************************************************************/	
	/* 전체 */
	public static class FragmentSignalTotal extends ListFragment {
		private Menu myMenu;
		private FragmentSignal parent;
		private View root;		
		
		private ArrayList<ReceivedSignal> received_signal;
		private MyArrayAdapter my_adapter;

		@Override
		public void onDestroy() {
			super.onDestroy();
			myMenu.clear();			
		}
		
		public FragmentSignalTotal() {
			super();
		}
		public FragmentSignalTotal(FragmentSignal parent) {
			super();
			this.parent = parent;
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			root = inflater.inflate(R.layout.fragment_signal_total, container, false);
			FragmentManager fm=getChildFragmentManager();
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
						
						Toast.makeText(getActivity(), "all_btn clicked", 0).show();
						break;
					case R.id.total_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_total();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "total_btn clicked", 0).show();
						break;
					case R.id.indiv_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_indiv();
						my_adapter.notifyDataSetChanged();
						*/
						Toast.makeText(getActivity(), "indiv_btn clicked", 0).show();
						break;
					case R.id.alarm_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_alarm();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "alarm_btn clicked", 0).show();
						break;
					default :
						//do nothing
						break;
					}
				}		
			};
			bottom_menu.setButtonClickListener(bt_listener);
			return root;			
		}		
		
		/* Item 이 클릭되었을 때, 새로운 Activity를 띄운다(예정)
		 * 이 동작을 Fragment에서 구현하지 않고 정석적인 방법으로 Activity에 Listener 구현을 강제하여 
		 * Activity 차원에서 다양한 처리를 할 수 있게 한다.
		 * 
		 * 하지만 복잡하므로 그냥 이 Fragment에서 구현
		 */		
		/*
		OnItemClickedListener mHost;
		
		public interface OnItemClickedListener {
			public void onItemClicked(int index);
		}
		
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			
			try {
				mHost = (OnItemClickedListener)activity;				
			} catch (ClassCastException e) {
				throw new ClassCastException("activity must implement OnItemClickedListener");
			}
		}		
		*/
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.actionbarmenu, menu);
			myMenu=menu;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
				case R.id.action_add_alarm :
					Toast.makeText(getActivity(), "action_add_alarm을 클릭함", 0).show();
					// 전체 신호 추가 Activity
					/*
					Intent intent = new Intent(getActivity(), StockDetail.class);
					startActivity(intent);
					*/		
					
					return true;
				case R.id.action_my_settings :		
					parent.change_frag_0(this);
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
		
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			/* test code */
			
			if(received_signal == null) {
				received_signal = MyDataBase.getReceivedSignalList_all();

				setListAdapter(my_adapter = new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, received_signal));
			
			
				// 기본적으로 divider를 지원하기 때문에 아래 코드가 무색하다.
				// 일단 유지
				ListView mListView = getListView();
				mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				mListView.setDivider(new ColorDrawable(Color.LTGRAY));
				mListView.setDividerHeight(1);
			}
			
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {			
			//mHost.onItemChecked(position);
			l.setItemChecked(position, true);
			Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
			
			//인텐트에 클릭된 신호의 주식이름 정보를 전달
			ReceivedSignal data = received_signal.get(position);			
			intent.putExtra("stock_name", data.stock_name);
			startActivity(intent);
			//Toast.makeText(getActivity(), position+" is clicked", 0).show();
		}
		
		class MyArrayAdapter extends ArrayAdapter<ReceivedSignal> {
			private ArrayList<ReceivedSignal> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<ReceivedSignal> items) {
				super(context, viewResourceId, items);
				this.items = items;
				this.context = context;
				resource = viewResourceId;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				RelativeLayout v = (RelativeLayout)convertView;
				ViewHolder vh;
				if (v == null) {
					v = (RelativeLayout)View.inflate(context, resource, null);
					ImageButton bt = (ImageButton)v.findViewById(R.id.alarm_bt);
					bt.setFocusable(false);
					vh = new ViewHolder();
					
					vh.image = (ImageView)v.findViewById(R.id.image);
					vh.type_color = (View)v.findViewById(R.id.type_color);
					vh.date = (TextView)v.findViewById(R.id.date);
					vh.signal_type = (TextView)v.findViewById(R.id.signal_type);	
					vh.new_tag = (TextView)v.findViewById(R.id.new_tag);	
					vh.signal = (TextView)v.findViewById(R.id.signal);	
					vh.stock_name = (TextView)v.findViewById(R.id.stock_name);	

					v.setTag(vh);
				}
				else {
					vh = (ViewHolder)v.getTag();
				}
				
				
				ReceivedSignal my_rsignal = items.get(position);
				
				
				//아이템의 데이터에 맞게 뷰를 수정
				if(my_rsignal != null) {
					//change image
					//vh.image settings code 필요
					
					//change type_color
					if(my_rsignal.getCondition_type() == ReceivedSignal.EASY) {
						vh.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
					}
					else if(my_rsignal.getCondition_type() == ReceivedSignal.HARD) {
						vh.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));					
					}
					else if(my_rsignal.getCondition_type() == ReceivedSignal.CUSTOM) {
						vh.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_custom));
					}
					
					//change date
					//나중에 시간 Format이 바뀐다면 여기서 적절히 수정.
					
					//change signal_type
					if(my_rsignal.getSignal_type() == ReceivedSignal.TYPE_SIGNAL_TOTAL) {
						vh.signal_type.setText("전체");
						vh.signal_type.setBackgroundColor(getResources().getColor(R.color.signal_type_total));
					}
					else if(my_rsignal.getSignal_type() == ReceivedSignal.TYPE_SIGNAL_INDIV) {
						vh.signal_type.setText("개별");
						vh.signal_type.setBackgroundColor(getResources().getColor(R.color.signal_type_indiv));
					}
					
					//change new_tag
					if(my_rsignal.getIs_new() == ReceivedSignal.NEW) {
						vh.new_tag.setVisibility(View.VISIBLE);
					}
					else if(my_rsignal.getIs_new() == ReceivedSignal.OLD) {
						vh.new_tag.setVisibility(View.GONE);
					}
					
					//change signal
					vh.signal.setText(my_rsignal.getSignal_name());
					
					//change stock_name
					vh.stock_name.setText(my_rsignal.getStock_name());	
				}
				
				return v;
			}
			
			
			public class ViewHolder {
				ImageView image;
				View type_color;
				TextView date;
				TextView signal_type;
				TextView new_tag;
				TextView signal;
				TextView stock_name;
			}
		
		}
		
	}
	/************************************************
	 * 
	 *  신호 - 전체 - 설정
	 * 
	 * 
	 **************************************************/
	public static class FragmentSignalTotalSettings extends ListFragment {
		private Menu myMenu;
		FragmentSignal parent;
		static 	ArrayList<SettedCond> list;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_total_settings, container, false);
			return root;			
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			myMenu.clear();			
		}
		
		public FragmentSignalTotalSettings() {
			super();
		}
		
		public FragmentSignalTotalSettings(FragmentSignal parent) {
			super();
			this.parent = parent;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}
		
	
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.fragment_signal_total_settings_menu, menu);
			myMenu = menu;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
				/* Action_add 동작을 수정하자. 아이콘이랑 이름까지 */
				case R.id.action_alarms :	
					parent.change_frag_0(this);
					return true;
				case R.id.action_add :
					/*
					Intent intent = new Intent(getActivity(), StockDetail.class);
					startActivity(intent);
					*/	
					// 추가하는 동작 수행
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			/* test code */
			if(list == null) {
				list=new ArrayList<SettedCond>();
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
				list.add(new SettedCond());
			}
				
			

			setListAdapter(new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_settings_item, list));
			
			
			/*
			ListView mListView = getListView();
			
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setDivider(new ColorDrawable(Color.LTGRAY));
			mListView.setDividerHeight(1);
			*/
			
		}
		
		
		class MyArrayAdapter extends ArrayAdapter<SettedCond> {
			private ArrayList<SettedCond> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<SettedCond> items) {
				super(context, viewResourceId, items);
				this.items = items;
				this.context = context;
				resource = viewResourceId;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {

				RelativeLayout v = (RelativeLayout)convertView;
				ViewHolder vh;
				if (v == null) {
					v = (RelativeLayout)View.inflate(context, resource, null);
					vh = new ViewHolder();
					
					vh.cond_name = (TextView)v.findViewById(R.id.signal);
					vh.cond_type = (View)v.findViewById(R.id.type_color);	
					vh.image = (ImageView)v.findViewById(R.id.image);	
					vh.alarm_bt = (ImageButton)v.findViewById(R.id.alarm_bt);		
					
					vh.alarm_bt.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(getActivity(), "알람버튼이 눌려졌을때 동작을 수행", 0).show();
							/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
						}						
					});						
					v.setTag(vh);
				}
				else {
					vh = (ViewHolder)v.getTag();
				}
				
				SettedCond data = items.get(position);
				
				vh.cond_name.setText(data.cond_name);
				/* cond_name 에 따라서 이미지를 변경하는 코드를 작성할 것. */
				
				
				//이미지 아래 VIew 색을 변경
				if(data.cond_type == SettedCond.EASY) {
					vh.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
				}
				else if(data.cond_type == SettedCond.HARD) {
					vh.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));
				}
				else if(data.cond_type == SettedCond.CUSTOM) {
					vh.cond_type.setBackgroundColor(getResources().getColor(R.color.condition_type_custom));
				}
				
				//알람 이미지를 변경
				if(data.is_alarm == SettedCond.IS_ALARM) {
					//알람 일때 설정코드
				}
				else if(data.is_alarm == SettedCond.IS_NOT_ALARM) {
					//알람이 아닐때
				}
				
				
				/* 삭제 버튼 리스너를 등록 */
				((ImageButton)v.findViewById(R.id.delete_bt)).setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {							
						Toast.makeText(getActivity(), ""+position+" 번쨰 아이템을 삭제", 0).show();						
						items.remove(position);
						notifyDataSetChanged();
						/* 삭제 헀다는 정보를 서버에 보내야 함 */
					}
				});
				

				return v;
			}
			
		
			public class ViewHolder {
				ImageView image;
				TextView cond_name;
				View cond_type;
				ImageButton alarm_bt;
			}

		}
		

		
	}

	
	
/*********************************************************************************************************************
//
//					신호 - 종목별 FRAGMENT
//
*********************************************************************************************************************/	
	/* 종목별 */
	public static class FragmentSignalStock extends Fragment {
		private LayoutInflater inflater;
		private ExpandableListView mExpListView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			this.inflater = inflater;
			View root = inflater.inflate(R.layout.simple_expandablelistview, container, false);
			mExpListView = (ExpandableListView)root.findViewById(R.id.explist);
			
			
			ArrayList<SignalSortByStock> list = new ArrayList<SignalSortByStock>();

			/* 임시로 데이터 만들기 */
			SignalSortByStock data_t = new SignalSortByStock();
			
			data_t.total_cnt = 33;
			data_t.indiv_cnt = 12;
			data_t.stock_name = "소마제철소";
			data_t.price = "1,154,000";
			data_t.price_diff = "-98711";
			data_t.price_diff_percent="(-3.12%)";
			data_t.list.add(new SignalOfStock());
			data_t.list.add(new SignalOfStock());
			data_t.list.add(new SignalOfStock());
			
			list.add(data_t);
			list.add(data_t);
			list.add(data_t);
			
			
			
			
			
			
			
			mExpListView.setAdapter(new MyExpAdapter(getActivity(), list));
			
			/* Test 용 임시 listener */
			mExpListView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					TextView signal = (TextView)v.findViewById(R.id.signal);
					TextView signal_type = (TextView)v.findViewById(R.id.signal_type);
					
					Toast.makeText(getActivity(), "chlid position = " + childPosition +"\n"+
														"signal Top = " + signal.getTop() + "\n" +
														"signal Bottom = " + signal.getBottom() + "\n" +
														"signal Baseline = " + signal.getBaseline() + "\n" +
														"signal_type Top = " + signal_type.getTop() + "\n" +
														"signal_type Bottom = " + signal_type.getBottom() + "\n" +
														"signal_type Baseline = " + signal_type.getBaseline() + "\n", Toast.LENGTH_LONG).show();
					
					return false;
				}
			});
			
			
			/* 하단 메뉴 설정 */
			FragmentManager fm=getChildFragmentManager();
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
						
						Toast.makeText(getActivity(), "all_btn clicked", 0).show();
						break;
					case R.id.total_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_total();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "total_btn clicked", 0).show();
						break;
					case R.id.indiv_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_indiv();
						my_adapter.notifyDataSetChanged();
						*/
						Toast.makeText(getActivity(), "indiv_btn clicked", 0).show();
						break;
					case R.id.alarm_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_alarm();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "alarm_btn clicked", 0).show();
						break;
					default :
						//do nothing
						break;
					}
				}		
			};
			bottom_menu.setButtonClickListener(bt_listener);

			return root;			
		}
		
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
		            v = inflater.inflate(R.layout.fragment_signal_stock_item_parent, parent, false);		            
		            
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
				ImageButton bt = (ImageButton)v.findViewById(R.id.go_btn);
				bt.setFocusable(false);
				bt.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {							
						Intent intent = new Intent(getActivity(), ActivityStockDetail.class);					
						
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
		            v = inflater.inflate(R.layout.fragment_signal_stock_item_child, null);
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

	
/*********************************************************************************************************************
//
//						신호 - 신호별 FRAGMENT
//
*********************************************************************************************************************/	
	/* 신호별 */
	public static class FragmentSignalSignal extends Fragment {
		private LayoutInflater inflater;
		private ExpandableListView mExpListView;
		private int GROUP_CNT = 3;
		private int CHILD_CNT = 5;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			this.inflater = inflater;
			View root = inflater.inflate(R.layout.simple_expandablelistview, container, false);
			mExpListView = (ExpandableListView)root.findViewById(R.id.explist);
			
			/* test */
			ArrayList<SignalSortBySignal> list = new ArrayList<SignalSortBySignal>();
			SignalSortBySignal tt = new SignalSortBySignal();
			tt.list.add(new SignalOfSignal());
			tt.list.add(new SignalOfSignal());
			tt.list.add(new SignalOfSignal());
			list.add(tt);
			list.add(tt);
			list.add(tt);

			
			mExpListView.setAdapter(new MyExpAdapter(getActivity(), list));
			
			/* 하단 메뉴 설정 */
			FragmentManager fm=getChildFragmentManager();
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
						
						Toast.makeText(getActivity(), "all_btn clicked", 0).show();
						break;
					case R.id.total_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_total();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "total_btn clicked", 0).show();
						break;
					case R.id.indiv_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_indiv();
						my_adapter.notifyDataSetChanged();
						*/
						Toast.makeText(getActivity(), "indiv_btn clicked", 0).show();
						break;
					case R.id.alarm_btn :
						/*
						received_signal = MyDataBase.getReceivedSignalList_alarm();
						my_adapter.notifyDataSetChanged();
						*/
						
						Toast.makeText(getActivity(), "alarm_btn clicked", 0).show();
						break;
					default :
						//do nothing
						break;
					}
				}		
			};
			bottom_menu.setButtonClickListener(bt_listener);
			
			
			return root;			
		}
		
		class MyExpAdapter extends BaseExpandableListAdapter {
			//private int GROUP_CNT = 3;
			//private int CHILD_CNT = 5;
			private int CHILD_CNT_MAX = 5;
			
			Context context;
			ArrayList<SignalSortBySignal> list;
			
			/* 뷰홀더 코드 */
			//private ViewHoler viewHolder = null;
			
			public MyExpAdapter(Context context, ArrayList<SignalSortBySignal> list) { //부모그룹이랑 차일드 그룹을 받아야 함.
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
			public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
				View v = convertView;
				GroupViewHolder viewHolder;
				if(v == null){
		            viewHolder = new GroupViewHolder();
		            v = inflater.inflate(R.layout.fragment_signal_signal_item_parent, parent, false);
		            
		            /* inflate 한 View를 수정 */
					ImageButton bt = (ImageButton)v.findViewById(R.id.go_btn);
					bt.setFocusable(false);
					bt.setOnClickListener(new Button.OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), ActivitySignalDetail.class);
							startActivity(intent);
						}
					});
		            
		            viewHolder.image = (ImageView) v.findViewById(R.id.image);
		            viewHolder.type_color = (View)v.findViewById(R.id.type_color);         
		            viewHolder.signal = (TextView)v.findViewById(R.id.signal);
		            viewHolder.signal_cnt = (TextView)v.findViewById(R.id.signal_cnt);
		            viewHolder.alarm = (ImageView)v.findViewById(R.id.alarm_image);

		            v.setTag(viewHolder);
		        }else{
		            viewHolder = (GroupViewHolder)v.getTag();
		        }								
				SignalSortBySignal groupData = list.get(groupPosition);
				
				
				
				/*이미지 변경 코드 작성할 것 */
				
				//이미지 밑에 색을 변경
				if(groupData.cond_type == SignalSortBySignal.EASY) {
					viewHolder.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_easy));
				}
				else if(groupData.cond_type == SignalSortBySignal.HARD) {
					viewHolder.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_hard));
				}
				else if(groupData.cond_type == SignalSortBySignal.CUSTOM) {
					viewHolder.type_color.setBackgroundColor(getResources().getColor(R.color.condition_type_custom));
				}
				
				
				//시그널 명 설정
				viewHolder.signal.setText(groupData.signal_name);
				
				//카운트 설정
				if(groupData.signal_type == SignalSortBySignal.TOTAL) {
					viewHolder.signal_cnt.setText(""+groupData.total_cnt);
					viewHolder.signal_cnt.setBackgroundResource(R.drawable.total_cnt_oval);
				}
				else if(groupData.signal_type == SignalSortBySignal.INDIV) {
					viewHolder.signal_cnt.setText(""+groupData.indiv_cnt);
					viewHolder.signal_cnt.setBackgroundResource(R.drawable.indiv_cnt_oval);
				}
				
				/*알람 뷰를 변경하는 코드를 작성할 것*/
				//알람 설정
				if(groupData.is_alarm == SignalSortBySignal.IS_ALARM) {
					//알람 일때 설정코드
				}
				else if(groupData.is_alarm == SignalSortBySignal.IS_NOT_ALARM) {
					//알람이 아닐때
				}

				return v;
			}
			
			public class GroupViewHolder {	
				ImageView image;
				View type_color;
				TextView signal;
				TextView signal_cnt;
				ImageView alarm;
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
		            v = inflater.inflate(R.layout.fragment_signal_signal_item_child, null);
		            
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
				
				SignalOfSignal data = list.get(groupPosition).list.get(childPosition);
				
				
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
}