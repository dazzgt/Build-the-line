package org.gamerex.btl2.android;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	//ADS
	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6049553525908152/3441613621";
	private InterstitialAd interstitialAd;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		adsInit();
		initialize(new BTL2(this), cfg);
	}

	private void adsInit() {
		AdRequest interstitialRequest = new AdRequest.Builder().build();
		interstitialAd.loadAd(interstitialRequest);
		interstitialAd.setAdListener(new AdListener() {
			public void onAdLoaded() {}
			public void onAdClosed() {
				AdRequest interstitialRequest = new AdRequest.Builder().build();
				interstitialAd.loadAd(interstitialRequest);
			}
		});
	}

	public void showInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded()) {
						interstitialAd.show();
					}
					AdRequest interstitialRequest = new AdRequest.Builder().build();
					interstitialAd.loadAd(interstitialRequest);
				}
			});
		} catch (Exception e) {
		}
	}
	public void setTrackerScreenName(String path) {}

	public String getStringResourceByName(String aString) {
		String packageName = getPackageName();
		int resId = getResources().getIdentifier(aString, "string", packageName);
		return getString(resId);
	}
}

/*package org.gamerex.btl2.android;

import java.util.HashMap;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.R;
import org.gamerex.btl2.states.ActionResolver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	//BILLING
	IInAppBillingService mService;

	ServiceConnection mServiceConn = new ServiceConnection() {
	   @Override
	   public void onServiceDisconnected(ComponentName name) {
	       mService = null;
	   }

	   @Override
	   public void onServiceConnected(ComponentName name, 
	      IBinder service) {
	       mService = IInAppBillingService.Stub.asInterface(service);
	   }
	};

	//ADS
	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6049553525908152/3441613621";
	protected View gameView;
	private InterstitialAd interstitialAd;

	//Analize
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
						// roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
							// company.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	private AndroidLauncher myApplication;

	private Tracker tracker;

	private Tracker globalTracker;

	synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) 
					: analytics.newTracker(R.xml.ecommerce_tracker);
			mTrackers.put(trackerId, t);
		}
		return mTrackers.get(trackerId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		View gameView = createGameView(cfg);
		layout.addView(gameView);

		setContentView(layout);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		AdRequest interstitialRequest = new AdRequest.Builder().build();
		interstitialAd.loadAd(interstitialRequest);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				//Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onAdClosed() {
				AdRequest interstitialRequest = new AdRequest.Builder().build();
				interstitialAd.loadAd(interstitialRequest);
				//Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
			}
		});

		Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
		  serviceIntent.setPackage("com.android.vending");
		  bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

		  myApplication = (AndroidLauncher) Gdx.app;
		  tracker = myApplication.getTracker(TrackerName.APP_TRACKER);
		  globalTracker = myApplication.getTracker(TrackerName.GLOBAL_TRACKER);
	}

	private View createGameView(AndroidApplicationConfiguration cfg) {
		gameView = initializeForView(new BTL2(this), cfg);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		//params.addRule(RelativeLayout.BELOW, adView.getId());
		gameView.setLayoutParams(params);
		return gameView;
	}


	@Override
	public void showInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded()) {
						interstitialAd.show();
						//Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
					}
					AdRequest interstitialRequest = new AdRequest.Builder().build();
					interstitialAd.loadAd(interstitialRequest);
				}
			});
		} catch (Exception e) {
		}
	}

	public void onStart() {
		super.onStart();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}
	public void setTrackerScreenName(String path) {
		// Set screen name.
		// Where path is a String representing the screen name.
		//tracker.setScreenName(path);
		//tracker.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    if (mService != null) {
	        unbindService(mServiceConn);
	    }   
	}


}
 */