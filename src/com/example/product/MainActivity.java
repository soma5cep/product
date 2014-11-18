package com.example.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends FragmentActivity {
	
	//for GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "962667824701";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Demo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;
    
    String regid;

	
	
	
	
	FragmentManager fm; //onCreate에서 초기화

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		MyDataBase.initialize(this);

		/* 프래그먼트 관리 */
		fm = getSupportFragmentManager();
		/* 부모의 id로 fragment를 찾기, fragment가 없으면 추가 */
		if(fm.findFragmentById(R.id.frame) == null) {
			FragmentSignal fg_sg = new FragmentSignal();
			fm.beginTransaction().add(R.id.frame, fg_sg, "fragment_signal").commit();
		}
		
		//GCM 관리 부분
	    context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
        
	}
	
	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
	    super.onResume();
	    checkPlayServices();
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(context);
	                }
	                regid = gcm.register(SENDER_ID);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	                sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the regID - no need to register again.
	                storeRegistrationId(context, regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	        	Log.i(TAG, msg);
	            //mDisplay.append(msg + "\n");
	        }
	    }.execute(null, null, null);
	}
	
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
	    // Your implementation here.
		String my_url = MyDataBase.URL+"users";
		/*
		StringRequest myReq = new StringRequest(Method.POST,
				my_url,
			    new Response.Listener<String>() 
			    {
			        @Override
			        public void onResponse(String response) {
			            // response
			            Log.d(TAG, response);
			        }
			    }, 
			    new Response.ErrorListener() 
			    {
			         @Override
			         public void onErrorResponse(VolleyError error) {
			             // error
			             Log.d(TAG, "error!  " + error.getMessage());
			       }
			    }) 
		{
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				//params.put("device_id", MyDataBase.device_id);
				//params.put("registration_id", regid);
				params.put("device_id", "1234");
				params.put("registration_id", "9999");
				return params;
			}
		    @Override
		    public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
		        HashMap<String, String> headers = new HashMap<String, String>();
		        headers.put("Content-Type", "application/json");
		        headers.put("User-agent", "My useragent");
		        return headers;
		    }
		};
		*/
		Map<String, String> jsonParams = new HashMap<String, String>();
		jsonParams.put("device_id", MyDataBase.device_id);
		jsonParams.put("registration_id", regid);
		 
		JsonObjectRequest myReq = new JsonObjectRequest(
		        Request.Method.POST,
		        my_url,
		        new JSONObject(jsonParams),
		 
		        new Response.Listener<JSONObject>() {
		            @Override
		            public void onResponse(JSONObject response) {
			            // response
			            Log.d(TAG, "success" + response.toString());
		            }
		        },
		        new Response.ErrorListener() {
		            @Override
		            public void onErrorResponse(VolleyError error) {
			             // error
			             Log.d(TAG, "error!  " + error.getMessage());
		            }
		        }) {
		 
		    @Override
		    public Map<String, String> getHeaders() throws AuthFailureError {
		        HashMap<String, String> headers = new HashMap<String, String>();
		        headers.put("Content-Type", "application/json; charset=utf-8");
		        headers.put("User-agent", "My useragent");
		        return headers;
		    }
		};
	
		MyDataBase.queue.add(myReq);		
	}
	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	

	
	public void tabButtonOnClick(View view) {
		
		Fragment fragment = fm.findFragmentById(R.id.frame);
		
		//여기로 진입하면 에러임
		if(fragment == null) {
			FragmentSignal fg_sg = new FragmentSignal();	
			fm.beginTransaction().add(R.id.frame, fg_sg, "fragment_signal").commit();
			return;
		}
		switch(view.getId()) {
			case R.id.signal_bt :
				if(fragment.getTag() == "fragment_signal") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					FragmentSignal fg_sg = new FragmentSignal();
					fm.beginTransaction().replace(R.id.frame, fg_sg, "fragment_signal").commit();
				}
				
				break;
				
				
			/* 검색 activity를 띄우기 */
			case R.id.search_bt :  
				Intent intent = new Intent(this, SearchItem2.class);
				startActivity(intent);
				

				break;
			
			case R.id.settings_bt :
				if(fragment.getTag() == "fragment_settings") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					
					FragmentSettings fg_st = new FragmentSettings();
					fm.beginTransaction().replace(R.id.frame, fg_st, "fragment_settings").commit();
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
}
