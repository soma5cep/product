package com.example.product;


import java.util.ArrayList;

import android.content.Context;


// 데이터 베이스 코드는 전부 Static으로 선언하여 어디서든지 접근할 수 있게 한다.
public class MyDataBase {
	private static ArrayList<ReceivedSignal> received_signal_list;
	private static ArrayList<ReceivedSignal> result;
	private static Context main_activity;
	
	//이 코드는 Application 에서 onCreate할떄 실행
	public static void initialize(Context context) {
		//이 코드를 Realm 에서 불러 오는 코드로 바꿔야 한다.
		received_signal_list = new ArrayList<ReceivedSignal>();
		result = new ArrayList<ReceivedSignal>();
		main_activity = context;
	}
	
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
}





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

// 임시 test 코드
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























/******************************
 * 
 * 각좀 클래스 정의
 * 
 ******************************/


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
	static final int EASY = 0;
	static final int HARD = 1;
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