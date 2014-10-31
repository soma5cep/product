package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.viewpagerindicator.TabPageIndicator;

public class ActivityCondition extends FragmentActivity {
	FragmentManager fm; //onCreate에서 초기화

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition);		
		
		
		
		/* 프래그먼트 관리 */
		fm = getSupportFragmentManager();
		/* 부모의 id로 fragment를 찾기, fragment가 없으면 추가 */
		if(fm.findFragmentById(R.id.frame) == null) {
			FragmentEasyCondition fg_easy = new FragmentEasyCondition();
			fm.beginTransaction().add(R.id.frame, fg_easy, "fragment_easy_condition").commit();
		}
		
	}
	
	public void tabButtonOnClick(View view) {
		
		Fragment fragment = fm.findFragmentById(R.id.frame);
		
		//여기로 진입하면 에러임
		if(fragment == null) {
			FragmentEasyCondition fg_easy = new FragmentEasyCondition();	
			fm.beginTransaction().add(R.id.frame, fg_easy, "fragment_easy_condtion").commit();
			return;
		}
		
		switch(view.getId()) {
			case R.id.easy_condition_bt :
				if(fragment.getTag() == "fagment_easy_condition") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					FragmentEasyCondition fg_easy = new FragmentEasyCondition();
					fm.beginTransaction().replace(R.id.frame, fg_easy, "fragment_easy_condtion").commit();
				}
				
				break;
				
			case R.id.detail_condition_bt :
				if(fragment.getTag() == "fragment_detail_condition") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					
					FragmentDetailCondition fg_detail = new FragmentDetailCondition();
					fm.beginTransaction().replace(R.id.frame, fg_detail, "fragment_detail_condition").commit();
				}
				break;
		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	/***********************************************
	 * 
	 * 간단조건 Fragment
	 * @author Namhoon
	 *
	 ***********************************************/
	public static class FragmentEasyCondition extends ListFragment {
		
		
		
		/*아래 주석은 나중에 검토 하자 아이템을 클릭하였을때 어떻게 할 것인가 하는 부분임 */
		
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
		
		/* 이 WORLDS 배열은 테스트용 */
		public static String[] WORDS = {
			"boy", "girl", "school", "Hello", "go"
		};
		

		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			/* test code */
			
			ArrayList<String> dd=new ArrayList<String>();
			dd.add("aaa");
			dd.add("bbb");
			dd.add("ccc");
			dd.add("d");
			dd.add("e");
			dd.add("f");
			

			setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, dd));
			
			ListView mListView = getListView();
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setDivider(new ColorDrawable(Color.LTGRAY));
			mListView.setDividerHeight(1);		
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {			
			//mHost.onItemChecked(position);
			/*
			l.setItemChecked(position, true);
			Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
			startActivity(intent);
			*/
			//Toast.makeText(getActivity(), position+" is clicked", 0).show();
		}
		
		class MyArrayAdapter extends ArrayAdapter<String> {
			private ArrayList<String> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<String> items) {
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
				RelativeLayout v = (RelativeLayout)View.inflate(context, resource, null);
				return v;
			}
			
			/*
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
			*/
		}
	}
	
	
	
	
	
	
	/***********************************************
	 * 
	 * 일반조건 Fragment
	 * @author Namhoon
	 *
	 ***********************************************/
	
	public static class FragmentDetailCondition extends Fragment {
		private ViewPager mPager;
		private MyPagerAdapter mPagerAdapter;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.view_pager, container, false);
			
			mPager = (ViewPager)root.findViewById(R.id.pager);
			mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
			mPager.setAdapter(mPagerAdapter);

	        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
	        mIndicator.setViewPager(mPager);
			
			return root;
		}
		
	/*********************************************************************************************************************
	//
//					뷰페이저 어댑터
	//
	*********************************************************************************************************************/
		private class MyPagerAdapter extends FragmentStatePagerAdapter {
			private static final int NUM_PAGES = 4;
			private final FragmentManager fm;

			public MyPagerAdapter(FragmentManager fm) {
				super(fm);
				this.fm = fm;
			}
			@Override
			public int getCount() {
				return NUM_PAGES;
			}

			@Override
			public Fragment getItem(int position) {
				switch(position) {
					case 0 :
						return new FragmentDetailConditionList();
					case 1 :
						return new FragmentDetailConditionList();
					case 2 :
						return new FragmentDetailConditionList();
					case 3 :
						return new FragmentDetailConditionList();					
					default :
						return null;
				}
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				switch(position) {
					case 0 :
						return "재무";
					case 1 :
						return "시세";
					case 2 :
						return "기술";
					case 3 :
						return "패턴";
					default :
						return "";
				}
			}
			
		}

	/*********************************************************************************************************************
	//
//					일반조건 - List
	//
	*********************************************************************************************************************/	
		/* 전체 */
		public static class FragmentDetailConditionList extends ListFragment {
			
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

			/* 이 WORLDS 배열은 테스트용 */
			public static String[] WORDS = {
				"boy", "girl", "school", "Hello", "go"
			};
			

			
			public void onActivityCreated(Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
				
				/* test code */
				
				ArrayList<String> dd=new ArrayList<String>();
				dd.add("aaa");
				dd.add("bbb");
				dd.add("ccc");
				dd.add("d");
				dd.add("e");
				dd.add("f");
				

				setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, dd));
				
				ListView mListView = getListView();
				mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				mListView.setDivider(new ColorDrawable(Color.LTGRAY));
				mListView.setDividerHeight(1);
				
			}
			
			public void onListItemClick(ListView l, View v, int position, long id) {			
				//mHost.onItemChecked(position);
				/*
				l.setItemChecked(position, true);
				Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
				startActivity(intent);
				*/
				//Toast.makeText(getActivity(), position+" is clicked", 0).show();
			}
			
			class MyArrayAdapter extends ArrayAdapter<String> {
				private ArrayList<String> items;
				private Context context;
				private int resource;

				public MyArrayAdapter(Context context, int viewResourceId, ArrayList<String> items) {
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
					RelativeLayout v = (RelativeLayout)View.inflate(context, resource, null);
					return v;
				}
				
				/*
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
				*/
			}
			
		}
	}
}
