package com.example.product;


import java.util.ArrayList;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


// 데이터 베이스 코드는 전부 Static으로 선언하여 어디서든지 접근할 수 있게 한다.
public class MyDataBase {
/*
	// 받은 시그널 정보
	static ArrayList<signal_result> signal_list_all;
	static ArrayList<signal_result> signal_list_total;
	static ArrayList<signal_result> signal_list_indiv;
	static ArrayList<signal_result> signal_list_alarm;
	
	// 받은 시그널 종목별
	static ArrayList<signal_results_of_stock_item> signal_by_stock_list_all;
	static ArrayList<signal_results_of_stock_item> signal_by_stock_list_total;
	static ArrayList<signal_results_of_stock_item> signal_by_stock_list_indiv;
	static ArrayList<signal_results_of_stock_item> signal_by_stock_list_alarm;
	
	// 받은 시그널 정보 신호별
	static ArrayList<signal_results_signal_condition> signal_by_cond_list_all;
	static ArrayList<signal_results_signal_condition> signal_by_cond_list_total;
	static ArrayList<signal_results_signal_condition> signal_by_cond_list_indiv;
	static ArrayList<signal_results_signal_condition> signal_by_cond_list_alarm;

	*/
	
	//설정된 신호 목록 (전체)
	static ArrayList<user_signal_condition> my_condition_total;

	//선택가능한 신호 목록
	static ArrayList<predefined_condition_type> pred_cond_list;
	static ArrayList<condition_type> cond_list;
	
	
	//전체 종목 이름과 코드 정보 < 검색용 >
	static ArrayList<stock_item> stock_item_list;
	
	
	//내종목
	
	
	static Context my_context; // application context 이다.
	static RequestQueue queue;
	
	static String device_id;
	
	static int limit = 10;
	static String URL = "http://14.63.165.145:5000/";
	static String users;

	//이 코드는 Application 에서 onCreate할떄 실행
	
	
	// 초기화
	public static void initialize(Context context) {	
		my_context = context;
		queue = Volley.newRequestQueue(my_context);
		TelephonyManager tm =(TelephonyManager)my_context.getSystemService(Context.TELEPHONY_SERVICE);
		device_id = tm.getDeviceId();
		users = "users/" + device_id +"/" ;
	}
	
	// 전체 대상 시그널 리스트를 받아옴
	public static void updateSignal_list(int group, int before_after, int last_position, 
					Response.Listener<signal_results> resp_listener, 
					Response.ErrorListener resp_error_listener) {		
		
		String my_url = URL;	
		my_url += users +"signal?first_filter=";
        String first_filter = "total";
        my_url += first_filter;
        my_url +="&second_filter=";
        String second_filter;
        switch(group) {
        	case 0 :
        		second_filter = "total";
        		break;
        	case 1 :
        		second_filter = "to_all_stock_items";
        		break;
        	case 2 :
        		second_filter = "to_specific_stock_item";
        		break;
        	case 3 :
        		second_filter = "alarm";
        		break;
        	default :
        		second_filter = "total";
        		Log.e("product", "method updateSignal_list parameter group has invalid value");
        		break;        		
        }    
        my_url += second_filter;

        switch(before_after) {
	        case -1 : // default 첫 시작할 때 최신 갯수 받아올때,
	        	my_url += "&limit="+limit;
	        	break;
	        case 0 : //before
	        	my_url += "&limit="+limit+"&before="+last_position;
	        	break;
	        case 1 : //after
	        	my_url += "&after="+last_position;
	        	break;
        }


        GsonRequest<signal_results> myReq = new GsonRequest<signal_results>(
                my_url,
                signal_results.class,
                null,
                resp_listener,
                resp_error_listener
                );
        queue.add(myReq);
	}
	
	// 신호별 시그널 리스트를 받아옴
	public static void updateSignal_by_cond_list(int group,
			Response.Listener<signal_results_signal_conditions> resp_listener, 
			Response.ErrorListener resp_error_listener) {		

		String my_url = URL;	
		my_url += users +"signal?first_filter=";
		String first_filter = "signal";
		my_url += first_filter;
		my_url +="&second_filter=";
		String second_filter;
		switch(group) {
		case 0 :
			second_filter = "total";
			break;
		case 1 :
			second_filter = "to_all_stock_items";
			break;
		case 2 :
			second_filter = "to_specific_stock_item";
			break;
		case 3 :
			second_filter = "alarm";
			break;
		default :
			second_filter = "total";
			Log.e("product", "method updateSignal_list parameter group has invalid value");
			break;        		
		}    
		my_url += second_filter;


		GsonRequest<signal_results_signal_conditions> myReq = new GsonRequest<signal_results_signal_conditions>(
				my_url,
				signal_results_signal_conditions.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);
	}
	
	// 종목별 시그널 리스트를 받아옴
	public static void updateSignal_by_stock_list(int group,
			Response.Listener<signal_results_of_stock_items> resp_listener, 
			Response.ErrorListener resp_error_listener) {		

		String my_url = URL;	
		my_url += users +"signal?first_filter=";
		String first_filter = "stock_item";
		my_url += first_filter;
		my_url +="&second_filter=";
		String second_filter;
		switch(group) {
		case 0 :
			second_filter = "total";
			break;
		case 1 :
			second_filter = "to_all_stock_items";
			break;
		case 2 :
			second_filter = "to_specific_stock_item";
			break;
		case 3 :
			second_filter = "alarm";
			break;
		default :
			second_filter = "total";
			Log.e("product", "method updateSignal_list parameter group has invalid value");
			break;        		
		}    
		my_url += second_filter;


		GsonRequest<signal_results_of_stock_items> myReq = new GsonRequest<signal_results_of_stock_items>(
				my_url,
				signal_results_of_stock_items.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);
	}
	
	// 기본 조건들의 리스트를 받아옴
	public static void getAvailable_predefined_conditions(
			Response.Listener<available_predefined_conditions> resp_listener, 
			Response.ErrorListener resp_error_listener) {
		String my_url = URL+users+"available_predefined_conditions";
		//for example http://14.63.165.145:5000/21354321654/available_predefined_conditions"

		GsonRequest<available_predefined_conditions> myReq = new GsonRequest<available_predefined_conditions>(
				my_url,
				available_predefined_conditions.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);
	}
	
	// 고급 조건들의 리스트를 받아옴
	public static void getAvailable_conditions(
			Response.Listener<available_conditions> resp_listener, 
			Response.ErrorListener resp_error_listener) {
		String my_url = URL+users+"available_conditions";
		//for example http://14.63.165.145:5000/21354321654/available_conditions"
		
		GsonRequest<available_conditions> myReq = new GsonRequest<available_conditions>(
				my_url,
				available_conditions.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);		
	}
	
	
	// 전체를 대상으로 설정한 조건들을 받아오기
	public static void getSignal_conditions(
			Response.Listener<signal_conditions> resp_listener, 
			Response.ErrorListener resp_error_listener) 
	{		
		String my_url = URL+users+"conditions";
		GsonRequest<signal_conditions> myReq = new GsonRequest<signal_conditions>(
				my_url,
				signal_conditions.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);			
	}
	
	// 종목 상세 페이지 받아오기
	public static void getStock_detail(int stock_item_id,
			Response.Listener<stock_detail> resp_listener, 
			Response.ErrorListener resp_error_listener) 
	{
		String my_url = URL+users+"stocks/"+stock_item_id;	
		GsonRequest<stock_detail> myReq = new GsonRequest<stock_detail>(
				my_url,
				stock_detail.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);			
	}
	
	// 신호 상세 페이지 받아오기
	public static void getSignal_condition_detail(int user_signal_condition_id,
			Response.Listener<signal_condition_detail> resp_listener, 
			Response.ErrorListener resp_error_listener) 
	{
		String my_url = URL+users+"conditions/"+user_signal_condition_id;	
		GsonRequest<signal_condition_detail> myReq = new GsonRequest<signal_condition_detail>(
				my_url,
				signal_condition_detail.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);			
	}
	
	// 특정 종목 대상 시그널 리스트를 받아옴
	public static void getStockSignal_list(int group, int before_after, int last_position, int id, 
					Response.Listener<signal_results> resp_listener, 
					Response.ErrorListener resp_error_listener) {		
		
		String my_url = URL;	
		my_url += users +"signal?first_filter=";
        String first_filter = "stock_item";
        my_url += first_filter;
        my_url +="&second_filter=";
        String second_filter;
        switch(group) {
        	case 0 :
        		second_filter = "total";
        		break;
        	case 1 :
        		second_filter = "to_all_stock_items";
        		break;
        	case 2 :
        		second_filter = "to_specific_stock_item";
        		break;
        	case 3 :
        		second_filter = "alarm";
        		break;
        	default :
        		second_filter = "total";
        		Log.e("product", "method updateSignal_list parameter group has invalid value");
        		break;        		
        }    
        my_url += second_filter;

        switch(before_after) {
	        case -1 : // default 첫 시작할 때 최신 갯수 받아올때,
	        	my_url += "&limit="+limit;
	        	break;
	        case 0 : //before
	        	my_url += "&limit="+limit+"&before="+last_position;
	        	break;
	        case 1 : //after
	        	my_url += "&after="+last_position;
	        	break;
        }
        my_url += "&id=" + id;

        GsonRequest<signal_results> myReq = new GsonRequest<signal_results>(
                my_url,
                signal_results.class,
                null,
                resp_listener,
                resp_error_listener
                );
        queue.add(myReq);
	}

	
	// 특정 조건 대상 시그널 리스트를 받아옴
	public static void getCondSignal_list(int group, int before_after, int last_position, int id, 
					Response.Listener<signal_results> resp_listener, 
					Response.ErrorListener resp_error_listener) {		
		
		String my_url = URL;	
		my_url += users +"signal?first_filter=";
        String first_filter = "stock_item";
        my_url += first_filter;
        my_url +="&second_filter=";
        String second_filter;
        switch(group) {
        	case 0 :
        		second_filter = "total";
        		break;
        	case 1 :
        		second_filter = "to_all_stock_items";
        		break;
        	case 2 :
        		second_filter = "to_specific_stock_item";
        		break;
        	case 3 :
        		second_filter = "alarm";
        		break;
        	default :
        		second_filter = "total";
        		Log.e("product", "method updateSignal_list parameter group has invalid value");
        		break;        		
        }    
        my_url += second_filter;

        switch(before_after) {
	        case -1 : // default 첫 시작할 때 최신 갯수 받아올때,
	        	my_url += "&limit="+limit;
	        	break;
	        case 0 : //before
	        	my_url += "&limit="+limit+"&before="+last_position;
	        	break;
	        case 1 : //after
	        	my_url += "&after="+last_position;
	        	break;
        }
        my_url += "&id=" + id;

        GsonRequest<signal_results> myReq = new GsonRequest<signal_results>(
                my_url,
                signal_results.class,
                null,
                resp_listener,
                resp_error_listener
                );
        queue.add(myReq);
	}
	
	
	// 내종목
	public static void getMy_Stock(int group, 
			Response.Listener<signal_results_of_stock_items> resp_listener, 
			Response.ErrorListener resp_error_listener) {		

		String my_url = URL;	
		my_url += users +"signal?option=my_stocks";
		
		String second_filter = "";
		if(group == 0) { //all
			second_filter = "total";
		}
		else if(group == 3) { // alarm
			second_filter = "alarm";
		}
		
		my_url += "&second_filter="+second_filter;

		GsonRequest<signal_results_of_stock_items> myReq = new GsonRequest<signal_results_of_stock_items>(
				my_url,
				signal_results_of_stock_items.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);
	}
	
	// 주식 종목 목록 얻기
	public static void getStock_list(
			Response.Listener<stock_items> resp_listener, 
			Response.ErrorListener resp_error_listener) {		

		String my_url = URL + "stocks";	

		GsonRequest<stock_items> myReq = new GsonRequest<stock_items>(
				my_url,
				stock_items.class,
				null,
				resp_listener,
				resp_error_listener
				);
		queue.add(myReq);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	/*
	private static ArrayList<ReceivedSignal> received_signal_list;
	private static ArrayList<ReceivedSignal> result;
	private static Context main_activity;
	

	
	public static ArrayList<ReceivedSignal> getReceivedSignalList_all() {
		//시그널 추가 함수를 넣기
		ArrayList<ReceivedSignal> newList;
		newList = ForTest.getRandReceivedSignal();
		received_signal_list.addAll(0, newList);
		
		
		result.clear();
		result.addAll(received_signal_list);
		return result;
	}
	
	// 이 아래의 3개의 함수는 반드시 getReceivedSignalList_all()이 호출된 다음에 호출되어야 한다.
	// 왜냐하면 received_signal_list 에 데이터가 있어야 하기 때문.
	// 이를 지키기 위해 received_signal_list 가 null 이면 getReceivedSignalList_all()을 호출한다.
	public static ArrayList<ReceivedSignal> getReceivedSignalList_total() {
		//정상적인 동작에서는 이 if 함수에 들어갈 리가 없다. 
		//화면이 로딩되면 맨 처음 화면을 띄우면서 getReceivedSignalList_all()을 호출하여
		//전체 정보를 보여주기 때문
		if(received_signal_list == null) {
			getReceivedSignalList_all();
		}
		ArrayList<ReceivedSignal> temp = (ArrayList<ReceivedSignal>)received_signal_list.clone();
		for(int i=0; i<temp.size(); ) {
			ReceivedSignal t = temp.get(i);
			//TOTAL 만 출력해야 하므로 INDIV 면 제거
			if(t.getSignal_type() == ReceivedSignal.TYPE_SIGNAL_INDIV) {
				temp.remove(i);
			}
			else {
				++i;
			}
		}
		result.clear();
		result.addAll(temp);
		return result;
	}
	public static ArrayList<ReceivedSignal> getReceivedSignalList_indiv() {
		//정상적인 동작에서는 이 if 함수에 들어갈 리가 없다. 
		//화면이 로딩되면 맨 처음 화면을 띄우면서 getReceivedSignalList_all()을 호출하여
		//전체 정보를 보여주기 때문
		if(received_signal_list == null) {
			getReceivedSignalList_all();
		}
		ArrayList<ReceivedSignal> temp = (ArrayList<ReceivedSignal>)received_signal_list.clone();
		for(int i=0; i<temp.size(); ) {
			ReceivedSignal t = temp.get(i);
			//INDIV 만 출력해야 하므로 TOTAL이면 제거
			if(t.getSignal_type() == ReceivedSignal.TYPE_SIGNAL_TOTAL) {
				temp.remove(i);
			}
			else {
				++i;
			}
		}
		result.clear();
		result.addAll(temp);
		return result;
	}	
	public static ArrayList<ReceivedSignal> getReceivedSignalList_alarm() {
		//정상적인 동작에서는 이 if 함수에 들어갈 리가 없다. 
		//화면이 로딩되면 맨 처음 화면을 띄우면서 getReceivedSignalList_all()을 호출하여
		//전체 정보를 보여주기 때문
		if(received_signal_list == null) {
			getReceivedSignalList_all();
		}
		ArrayList<ReceivedSignal> temp = (ArrayList<ReceivedSignal>)received_signal_list.clone();
		for(int i=0; i<temp.size(); ) {
			ReceivedSignal t = temp.get(i);
			//ALARM이 아니면 제거
			if(t.getIs_alarm() == ReceivedSignal.IS_NOT_ALARM) {
				temp.remove(i);
			}
			else {
				++i;
			}
		}
		result.clear();
		result.addAll(temp);
		return result;
	}
	*/
	
	
}




/*

// 서버로부터 받은 RAW_DATA 를 담는다.
class ReceivedSignal {
	
	//for type
	static final int TYPE_SIGNAL = 0;
	static final int TYPE_INFO = 1;
	//for signal_type
	static final int TYPE_SIGNAL_TOTAL = 0;
	static final int TYPE_SIGNAL_INDIV = 1;
	//for is_new;
	static final int OLD = 0;
	static final int NEW = 1;
	//for condition_type
	static final int EASY = 0;
	static final int HARD = 1;
	static final int CUSTOM = 2;
	//for is_alarm
	static final int IS_NOT_ALARM = 0;
	static final int IS_ALARM = 1;
	
	private int type;
	private int signal_type;
	private int is_new;
	private int condition_type;
	public int is_alarm = IS_NOT_ALARM;
	private String signal_name;
	private String in_out;
	public String stock_name;
	public String time;

	//INFO 를 표현할 멤버들을 추가할 것.





	// 자동 생성으로 만든 getter and setter
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSignal_type() {
		return signal_type;
	}
	public void setSignal_type(int signal_type) {
		this.signal_type = signal_type;
	}
	public int getIs_new() {
		return is_new;
	}
	public void setIs_new(int is_new) {
		this.is_new = is_new;
	}
	public int getCondition_type() {
		return condition_type;
	}
	public void setCondition_type(int condition_type) {
		this.condition_type = condition_type;
	}
	public int getIs_alarm() {
		return is_alarm;
	}
	public void setIs_alarm(int is_alarm) {
		this.is_alarm = is_alarm;
	}
	public String getSignal_name() {
		return signal_name;
	}
	public void setSignal_name(String signal_name) {
		this.signal_name = signal_name;
	}
	public String getIn_out() {
		return in_out;
	}
	public void setIn_out(String in_out) {
		this.in_out = in_out;
	}
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
*/

// 임시 test 코드
/*
class ForTest {
	public static ArrayList<ReceivedSignal> getRandReceivedSignal() {
		ArrayList<ReceivedSignal> mList = new ArrayList<ReceivedSignal>();
		for(int i=0; i<5; ++i) {
			ReceivedSignal signal = new ReceivedSignal();
			signal.setType(ReceivedSignal.TYPE_SIGNAL);
			signal.setSignal_type(ReceivedSignal.TYPE_SIGNAL_INDIV);
			signal.setIs_new(ReceivedSignal.NEW);
			signal.setCondition_type(ReceivedSignal.EASY);
			if(i % 2 == 1) {
				signal.setIs_alarm(ReceivedSignal.IS_ALARM);
			}
			else {
				signal.setIs_alarm(ReceivedSignal.IS_NOT_ALARM);
			}
			signal.setSignal_name("신호이름_t"+i);
			signal.setIn_out("진입");
			signal.setStock_name("ROTC_t");
			signal.setTime("10/04 16:20_t");
			mList.add(signal);
		}
		for(int i=0; i<5; ++i) {
			ReceivedSignal signal = new ReceivedSignal();
			signal.setType(ReceivedSignal.TYPE_SIGNAL);
			signal.setSignal_type(ReceivedSignal.TYPE_SIGNAL_TOTAL);
			signal.setIs_new(ReceivedSignal.NEW);
			signal.setCondition_type(ReceivedSignal.EASY);
			if(i % 2 == 1) {
				signal.setIs_alarm(ReceivedSignal.IS_ALARM);
			}
			else {
				signal.setIs_alarm(ReceivedSignal.IS_NOT_ALARM);
			}
			signal.setSignal_name("신호이름_tt"+(i+5));
			signal.setIn_out("진입");
			signal.setStock_name("ROTC_tt");
			signal.setTime("10/04 16:20_t");
			mList.add(signal);
		}
		return mList;
	}
}

*/





















/******************************
 * 
 * 각좀 클래스 정의
 * 
 ******************************/


/*
//종목별 정렬 데이터
class SignalSortByStock {	
	int total_cnt =0;
	int indiv_cnt = 0;
	String stock_name = "소마소마_t";
	String price = "0";
	String price_diff = "0";
	String price_diff_percent = "0";
	public ArrayList<SignalOfStock> list = new ArrayList<SignalOfStock>();
}

//특정 종목의 신호
//전체 신호보다 받는 데이터가 적다.
class SignalOfStock {
	static final int IS_ALARM = 1;
	static final int IS_NOT_ALARM = 0;
	static final int EASY = 0;
	static final int HARD = 1;
	static final int CUSTOM = 2;
	static final int TOTAL = 0;
	static final int INDIV = 1;
	String signal_name="상승주_t";
	int cond_type=EASY;
	int signal_type=TOTAL;
	String date="11/06 11:25_t";
	int is_alarm=IS_ALARM;
}


//종목의 상세 정보
class DetailOfStock {
	String price = "0";
	String price_diff = "0";
	String price_diff_percent = "0";
	String high_price = "0";
	String low_price = "0";
	String trading_volume="0";
	String trading_volume_avr="0";
}


//신호별 정렬 데이터
class SignalSortBySignal {	
	static final int IS_ALARM = 1;
	static final int IS_NOT_ALARM = 0;
	static final int EASY = 0;
	static final int HARD = 1;
	static final int CUSTOM = 2;
	static final int TOTAL = 0;
	static final int INDIV = 1;
	int cond_type=EASY;
	int signal_type=TOTAL;
	int total_cnt =0;
	int indiv_cnt = 0;
	String signal_name = "실적호전주_t";
	int is_alarm=IS_ALARM;
	public ArrayList<SignalOfSignal> list = new ArrayList<SignalOfSignal>();
}

//특정 신호의 신호
//전체 신호보다 받는 데이터가 적다.
class SignalOfSignal {
	static final int IS_ALARM = 1;
	static final int IS_NOT_ALARM = 0;
	String stock_name="대한항공_t";
	String price = "0";
	String date="11/06 11:25_t";
	int is_alarm=IS_ALARM;
}

// 설정한 조건(신호) 정보
class SettedCond {
	static final int IS_ALARM = 1;
	static final int IS_NOT_ALARM = 0;
	static final int EASY = 0;
	static final int HARD = 1;
	static final int CUSTOM = 2;
	static final int TOTAL = 0;
	static final int INDIV = 1;
	int signal_type=TOTAL;
	int cond_type=EASY;
	int is_alarm=IS_ALARM;
	String cond_name="상승주_t";	
}

//조건 목록
//초기 조건 받아올떄 쓰임
class ConditionGroup {
	static final int EASY = 0;
	static final int HARD = 1;
	static final int JAEMU = 0;
	static final int SISE = 1;
	static final int KISOOL = 2;
	static final int PATTERN = 3;
	int cond_type = EASY;
	int cond_hard_category = JAEMU;
	public ArrayList<ConditionAbstract> list = new ArrayList<ConditionAbstract>();	
}

//조건 abstract
class ConditionAbstract {
	static final int EASY = 0;
	static final int HARD = 1;
	int cond_type = EASY;
	String cond_name="낮은체결강도";
	String cond_compose="주가등락률, 일반조건 A, 일반조건 B, 일반조건 C, 일반조건 D, 일반조건 E";
	int user_cnt = 1;
	int love_cnt = 2;
	int rank = 1;
}

//조건
class Condition {
	//미정
	String cond_name;
	String description;
	String detail;
	
	//그 외 여러가지
}

//신호 상세
class SignalDetail {
	String signal_name = "상승주_t &amp; 신호설정주_t";
	public ArrayList<SimpleCond> compose_signal = new ArrayList<SimpleCond>();
}

//신호상세에 들어가는 포함조건
class SimpleCond {
	static final int EASY = 0;
	static final int HARD = 1;
	int cond_type = EASY;
	String cond_name = "상승주_t";
}
*/



















class Flag {
	//for type
	static final int TYPE_SIGNAL = 0;
	static final int TYPE_INFO = 1;
	//for signal_type
	static final int TYPE_SIGNAL_TOTAL = 0;
	static final int TYPE_SIGNAL_INDIV = 1;
	//for is_new;
	static final int OLD = 0;
	static final int NEW = 1;
	//for condition_type
	static final int EASY = 1;
	static final int HARD = 0;
	static final int CUSTOM = 2;
	//for is_alarm
	static final int IS_NOT_ALARM = 0;
	static final int IS_ALARM = 1;
	
	static final int TOTAL = 0;
	static final int INDIV = 1;
	
	static final int JAEMU = 0;
	static final int SISE = 1;
	static final int KISOOL = 2;
	static final int PATTERN = 3;
}




/* 서버에서 준 데이터 명세 */

// get 할때

class signal_results {
	ArrayList<signal_result> signal_results;
}

// 하나의 시그널 결과 정보
class signal_result {
	int signal_id;
	String date;
	int user_signal_condition_id;
	String user_signal_condition_name;
	int level; // 0 고급조건, 1 기본, 2 CUSTOM
	int applicable_range; // 0 전체종목, 1 특정종목
	int new_signal; // 0 과거 시그널 1 새로운 시그널
	String stock_item_name;
	int stock_item_id;
	int price;
	int entered; // 0 이탈 1 진입
	int alarm; // 0 알람 OFF 1 알람 ON
}

//추가된 시그널 조건 목록 얻기
class signal_conditions {
	ArrayList<user_signal_condition> signal_conditions;
}


// 사용자가 추가한 시그널 조건 정보
class user_signal_condition {
	int id;
	String name;
	int alarm; // 0 알람 OFF 1 알람 ON
	int level; // 0 고급조건, 1 기본조건, 2 CUSTOM
}

//first_filter 가 stock_item 인 경우.
class signal_results_of_stock_items {
	ArrayList<signal_results_of_stock_item> signal_results_of_stock_items;
}

//종목 별 시그널 결과 정보
class signal_results_of_stock_item {
	int stock_item_id;
	String stock_item_name;
	int price;
	int price_gap_of_previous_closing_price;
	double price_rate_of_previous_closing_price;
	int number_of_signals_to_all_stock_items;
	int number_of_signals_to_specific_stock_item;
	ArrayList<signal_result> signal_results;	
}

//first_filter 가 signal인 경우
class signal_results_signal_conditions {
	ArrayList<signal_results_signal_condition> signal_results_signal_conditions;
}

//시그널 조건 별 시그널 결과 정보
class signal_results_signal_condition {
	int user_signal_condition_id;
	String user_signal_condition_name;
	int applicable_range; // 0 전체종목 1 특정 종목
	int number_of_signals;
	int alarm; // 0 알람이 설정되지 않은 경우, 1 알람이 설정된 경우
	int level;
	ArrayList<signal_result> signal_results;
}

//시그널 조건 상세
class signal_condition_detail {
	String user_signal_condition_name;
	int level; // 0 일반조건 1 간단조건 2 조건 2개 이상
	ArrayList<String> included_signal_condition_names;
}

//이용 가능한 기본 조건 list
class available_predefined_conditions {
	ArrayList<predefined_condition_type> available_predefined_conditions;
}

//이용 가능한 기본 조건 
class predefined_condition_type {
	int id;
	String description;
	String name;
	int number_of_users_add_me;
	int number_of_users_like_me;
	ArrayList<predefined_condition_parameter_type> parameter_types;
}

//기본 조건 인자 타입 정보
class predefined_condition_parameter_type {
	int order;
	String name;
	String desc;
}

class available_conditions {
	ArrayList<condition_type> available_conditions;
}
//일반 조건
class condition_type {
	int id;
	String name;
	String description;
	String description_of_parameters;
	String category;
	int number_of_users_add_me;
	int number_of_users_like_me;
	ArrayList<condition_parameter_type> parameter_types;
}

//일반 조건 인자 타입 정보
class condition_parameter_type {
	int order;
	String type;
	String initial_value;
	String candidate_values;
}

//종목 상세
class stock_detail {
	String stock_item_name;
	int price;
	int price_gap_of_previous_closing_price;
	double price_rate_of_previous_closing_price;
	int high_price;
	int low_price;
	int volume;
	int average_volume;
	ArrayList<user_signal_condition> user_signal_conditions;
}

class stock_items {
	ArrayList<stock_item> stock_items;
}

//주식 종목 정보(목록 리스팅 용)
class stock_item {
	int id;
	String name;
	String code;
	String short_code;
}


