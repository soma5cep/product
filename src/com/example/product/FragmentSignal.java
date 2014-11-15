package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.product.j.SearchDetail;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
	public static class FragmentSignalTotal extends Fragment {
		// 받은 시그널 정보
		static ArrayList<signal_result> signal_list_all;
		static ArrayList<signal_result> signal_list_total;
		static ArrayList<signal_result> signal_list_indiv;
		static ArrayList<signal_result> signal_list_alarm;
		
		
		MyArrayAdapter adapter_all;
		MyArrayAdapter adapter_total;
		MyArrayAdapter adapter_indiv;
		MyArrayAdapter adapter_alarm;
				
		
		
		private Menu myMenu;
		private FragmentSignal parent;
		private View root;
		private PullToRefreshListView ptrlistview;
		private ListView listview;
		
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
			ptrlistview = (PullToRefreshListView)root.findViewById(R.id.ptr_list);
			listview = ptrlistview.getRefreshableView();
			
			
			//static 변수들 초기화
			if(signal_list_all == null) {
				signal_list_all = new ArrayList<signal_result>();
			}
			if(signal_list_total == null) {
				signal_list_total = new ArrayList<signal_result>();
			}
			if(signal_list_indiv == null) {
				signal_list_indiv = new ArrayList<signal_result>();
			}
			if(signal_list_alarm == null) {
				signal_list_alarm = new ArrayList<signal_result>();
			}		
			
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
			
			//리스트뷰 클릭할떄 리스너
			listview.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
					//pull to refresh를 적용하면서 position이 안맞음 따라서 수정
					position = position-1;
					
					
					//mHost.onItemChecked(position);
					listview.setItemChecked(position, true);
					Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
					
					//인텐트에 클릭된 신호의 주식이름 정보를 전달
					ReceivedSignal data = received_signal.get(position);			
					intent.putExtra("stock_name", data.stock_name);
					startActivity(intent);
					//Toast.makeText(getActivity(), position+" is clicked", 0).show();				
				}
				
			});
			
			// Set a listener to be invoked when the list should be refreshed.
			ptrlistview.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

					// Do work to refresh the list here.
					
					new GetDataTask().execute();
				}
			});
			
			// Add an end-of-list listener
			ptrlistview.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

				@Override
				public void onLastItemVisible() {
					Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
				}
			});
			
			
			
			
			
			return root;			
		}		
		
		//test용 데이터 받아오는 클래스
		// 지금은 4초 딜레이만 있다. 
		private class GetDataTask extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
				// Simulates a background job.
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void v) {
				//mListItems.addFirst("Added after refresh...");
				//mAdapter.notifyDataSetChanged();

				// Call onRefreshComplete when the list has been refreshed.
				ptrlistview.onRefreshComplete();

				super.onPostExecute(v);
			}
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
					Intent intent = new Intent(getActivity(), SearchDetail.class);
					startActivity(intent);
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
		
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			adapter_all = new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, signal_list_all);
			adapter_total = new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, signal_list_total);
			adapter_indiv = new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, signal_list_indiv);
			adapter_alarm = new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, signal_list_alarm);
			
			// 기본적으로 제일 처음에는 all 이 보여진다.
			listview.setAdapter(adapter_all);

		}
		
		/*
		@Override
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
		*/
		
		class MyArrayAdapter extends ArrayAdapter<signal_result> {
			private ArrayList<signal_result> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<signal_result> items) {
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
					vh.alarm_bt = (ImageButton)v.findViewById(R.id.alarm_bt);

					v.setTag(vh);
				}
				else {
					vh = (ViewHolder)v.getTag();
				}
				
				
				signal_result my_rsignal = items.get(position);
				
				
				/*임시로 주석처리 코드를 돌리기 위해
				 *
				//리스너 등록은 if ( v== null) 에서 하는게 아니라 밖에서 함.
				vh.alarm_bt.setOnClickListener(new ImageButton.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 
						signal_result data = items.get(position);
						if(data.is_alarm == ReceivedSignal.IS_ALARM){
							data.is_alarm = ReceivedSignal.IS_NOT_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm);
						}
						else if(data.is_alarm == ReceivedSignal.IS_NOT_ALARM){
							data.is_alarm = ReceivedSignal.IS_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm_clicked);
						}
						
						// 서버에 알리는 코드를 작성
					}						
				});	
				
				
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
					vh.date.setText(my_rsignal.time);
					
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
					
					//change alarm_bt image
					if(my_rsignal.is_alarm == ReceivedSignal.IS_ALARM){
						vh.alarm_bt.setImageResource(R.drawable.push_alarm_clicked);
					}
					else if(my_rsignal.is_alarm == ReceivedSignal.IS_NOT_ALARM){
						vh.alarm_bt.setImageResource(R.drawable.push_alarm);
					}
				}
				*/
				
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
				ImageButton alarm_bt;
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
		
		//list 가 static으로 되어있다. 이 것을 잘 처리해야 하는데 나중에 처리하기가 힘들다고 한다면
		//static으로 설정하지 말고 매번 서버에서 받아오게 바꾼다.
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
					
					Intent intent = new Intent(getActivity(), SearchDetail.class);
					intent.putExtra("type", "total");
					startActivity(intent);
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
					
				//change alarm_bt image
				if(data.is_alarm == SettedCond.IS_ALARM){
					vh.alarm_bt.setImageResource(R.drawable.push_alarm_clicked);
				}
				else if(data.is_alarm == SettedCond.IS_NOT_ALARM){
					vh.alarm_bt.setImageResource(R.drawable.push_alarm);
				}
				
				
				//리스너 등록은 if ( v== null) 에서 하는게 아니라 밖에서 함.
				vh.alarm_bt.setOnClickListener(new ImageButton.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
						SettedCond data = items.get(position);
						if(data.is_alarm == SettedCond.IS_ALARM){
							data.is_alarm = SettedCond.IS_NOT_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm);
						}
						else if(data.is_alarm == SettedCond.IS_NOT_ALARM){
							data.is_alarm = SettedCond.IS_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm_clicked);
						}
						
						// 서버에 알리는 코드를 작성
					}						
				});	
				
				
				
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
			
			final ArrayList<SignalSortByStock> list = new ArrayList<SignalSortByStock>();

			/* 임시로 데이터 만들기 */
			
			
			for(int i=0; i<3; ++i) {
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
			}

			
			
			mExpListView.setAdapter(new MyExpAdapter(getActivity(), list));
			
			/* Test 용 임시 listener */
			mExpListView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					/*
					TextView signal = (TextView)v.findViewById(R.id.signal);
					TextView signal_type = (TextView)v.findViewById(R.id.signal_type);
					
					Toast.makeText(getActivity(), "chlid position = " + childPosition +"\n"+
														"signal Top = " + signal.getTop() + "\n" +
														"signal Bottom = " + signal.getBottom() + "\n" +
														"signal Baseline = " + signal.getBaseline() + "\n" +
														"signal_type Top = " + signal_type.getTop() + "\n" +
														"signal_type Bottom = " + signal_type.getBottom() + "\n" +
														"signal_type Baseline = " + signal_type.getBaseline() + "\n", Toast.LENGTH_LONG).show();
					*/
					
					Intent intent = new Intent(getActivity(), ActivitySignalDetail.class);
					
					//인텐트에 클릭된 신호의 주식이름 정보를 전달
					SignalOfStock data = list.get(groupPosition).list.get(childPosition);
					intent.putExtra("signal_name", data.signal_name);
					startActivity(intent);					
					
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

				ImageButton bt = (ImageButton)v.findViewById(R.id.go_bt);
				
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
			public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
				
				
				//리스너 등록은 if ( v== null) 에서 하는게 아니라 밖에서 함.
				childViewHolder.alarm_bt.setOnClickListener(new ImageButton.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
						SignalOfStock data = list.get(groupPosition).list.get(childPosition);
						if(data.is_alarm == Flag.IS_ALARM){
							data.is_alarm = Flag.IS_NOT_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm);
						}
						else if(data.is_alarm == Flag.IS_NOT_ALARM){
							data.is_alarm = Flag.IS_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm_clicked);
						}
						
						// 서버에 알리는 코드를 작성
					}						
				});	
				
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
				
				//change alarm_bt image
				if(data.is_alarm == Flag.IS_ALARM){
					childViewHolder.alarm_bt.setImageResource(R.drawable.push_alarm_clicked);
				}
				else if(data.is_alarm == Flag.IS_NOT_ALARM){
					childViewHolder.alarm_bt.setImageResource(R.drawable.push_alarm);
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
		ArrayList<SignalSortBySignal> list;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			this.inflater = inflater;
			View root = inflater.inflate(R.layout.simple_expandablelistview, container, false);
			mExpListView = (ExpandableListView)root.findViewById(R.id.explist);
			
			/* test */
			list = new ArrayList<SignalSortBySignal>();
			for(int i=0; i<3; ++i) {
				SignalSortBySignal tt = new SignalSortBySignal();
				tt.list.add(new SignalOfSignal());
				tt.list.add(new SignalOfSignal());
				tt.list.add(new SignalOfSignal());
				list.add(tt);
			}

			
			mExpListView.setAdapter(new MyExpAdapter(getActivity(), list));
			
			
			/* Test 용 임시 listener */
			mExpListView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					/*
					TextView signal = (TextView)v.findViewById(R.id.signal);
					TextView signal_type = (TextView)v.findViewById(R.id.signal_type);
					
					Toast.makeText(getActivity(), "chlid position = " + childPosition +"\n"+
														"signal Top = " + signal.getTop() + "\n" +
														"signal Bottom = " + signal.getBottom() + "\n" +
														"signal Baseline = " + signal.getBaseline() + "\n" +
														"signal_type Top = " + signal_type.getTop() + "\n" +
														"signal_type Bottom = " + signal_type.getBottom() + "\n" +
														"signal_type Baseline = " + signal_type.getBaseline() + "\n", Toast.LENGTH_LONG).show();
					*/
					
					Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
					
					//인텐트에 클릭된 신호의 주식이름 정보를 전달
					SignalOfSignal data = list.get(groupPosition).list.get(childPosition);
					intent.putExtra("stock_name", data.stock_name);
					startActivity(intent);					
					
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
				
				final SignalSortBySignal groupData = list.get(groupPosition);
				
				
				if(v == null){
		            viewHolder = new GroupViewHolder();
		            v = inflater.inflate(R.layout.fragment_signal_signal_item_parent, parent, false);
		            
		            /* inflate 한 View를 수정 */
					ImageButton bt = (ImageButton)v.findViewById(R.id.go_btn);
					bt.setFocusable(false);
					bt.setOnClickListener(new Button.OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), ActivitySignalDetail.class);
							
							//인텐트에 클릭된 신호의 주식이름 정보를 전달		
							intent.putExtra("signal_name", groupData.signal_name);
							startActivity(intent);
						}
					});
		            
		            viewHolder.image = (ImageView) v.findViewById(R.id.image);
		            viewHolder.type_color = (View)v.findViewById(R.id.type_color);         
		            viewHolder.signal = (TextView)v.findViewById(R.id.signal);
		            viewHolder.signal_cnt = (TextView)v.findViewById(R.id.signal_cnt);
		            viewHolder.alarm = (ImageButton)v.findViewById(R.id.alarm_bt);
		            viewHolder.alarm.setFocusable(false);
		            
		            v.setTag(viewHolder);
		        }else{
		            viewHolder = (GroupViewHolder)v.getTag();
		        }			
				
				
				//리스너 등록은 if ( v== null) 에서 하는게 아니라 밖에서 함.
				viewHolder.alarm.setOnClickListener(new ImageButton.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
						if(groupData.is_alarm == Flag.IS_ALARM){
							groupData.is_alarm = Flag.IS_NOT_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm);
						}
						else if(groupData.is_alarm == Flag.IS_NOT_ALARM){
							groupData.is_alarm = Flag.IS_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm_clicked);
						}
						
						// 서버에 알리는 코드를 작성
					}						
				});	
				
				
				
				
				
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
				
				//change alarm_bt image
				if(groupData.is_alarm == Flag.IS_ALARM){
					viewHolder.alarm.setImageResource(R.drawable.push_alarm_clicked);
				}
				else if(groupData.is_alarm == Flag.IS_NOT_ALARM){
					viewHolder.alarm.setImageResource(R.drawable.push_alarm);
				}

				return v;
			}
			
			public class GroupViewHolder {	
				ImageView image;
				View type_color;
				TextView signal;
				TextView signal_cnt;
				ImageButton alarm;
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
				
				final SignalOfSignal data = list.get(groupPosition).list.get(childPosition);
				
				
				//리스너 등록은 if ( v== null) 에서 하는게 아니라 밖에서 함.
				childViewHolder.alarm_bt.setOnClickListener(new ImageButton.OnClickListener() {
					@Override
					public void onClick(View v) {
						/* 알람 버튼이 눌리면 이미지를 바꾸고, 서버에 알람버튼을 눌렀다고 알림 */
						if(data.is_alarm == Flag.IS_ALARM){
							data.is_alarm = Flag.IS_NOT_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm);
						}
						else if(data.is_alarm == Flag.IS_NOT_ALARM){
							data.is_alarm = Flag.IS_ALARM;
							((ImageView)v).setImageResource(R.drawable.push_alarm_clicked);
						}
						
						// 서버에 알리는 코드를 작성
					}						
				});	
				
				
				//주식명을 변경
				childViewHolder.stock_name.setText(data.stock_name);
				
				//주식 가격을 변경
				childViewHolder.price.setText(data.price);
				
				//날짜를 설정
				childViewHolder.date.setText(data.date);
				
				//change alarm_bt image
				if(data.is_alarm == Flag.IS_ALARM){
					childViewHolder.alarm_bt.setImageResource(R.drawable.push_alarm_clicked);
				}
				else if(data.is_alarm == Flag.IS_NOT_ALARM){
					childViewHolder.alarm_bt.setImageResource(R.drawable.push_alarm);
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