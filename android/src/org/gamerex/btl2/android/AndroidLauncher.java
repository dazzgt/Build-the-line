package org.gamerex.btl2.android;

import java.util.HashMap;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;
import org.gamerex.buildthelines.R;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class AndroidLauncher extends AndroidApplication implements ActionResolver{

	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new BTL2(this), config);
	}
	synchronized Tracker getTracker(TrackerName trackerId) {
	    if (!mTrackers.containsKey(trackerId)) {

	      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	      Tracker t = analytics.newTracker(R.xml.app_tracker);
	      mTrackers.put(trackerId, t);

	    }
	    return mTrackers.get(trackerId);
	  }
	@Override
	public void showInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
				}
			});
		} catch (Exception e) {
		}
	}

	
	@Override
	public void setTrackerScreenName(String path) {
        Tracker t = getTracker(TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public String getStringResourceByName(String aString) {
		String packageName = getPackageName();
		int resId = getResources().getIdentifier(aString, "string", packageName);
		return getString(resId);
	}
	
	public void onStart(){
		super.onStart();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}
	public void onStop(){
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}
}
