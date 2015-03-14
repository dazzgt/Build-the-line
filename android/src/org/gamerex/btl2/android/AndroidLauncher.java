package org.gamerex.btl2.android;

import java.util.HashMap;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.android.util.Base64;
import org.gamerex.btl2.android.util.IabHelper;
import org.gamerex.btl2.android.util.IabResult;
import org.gamerex.btl2.android.util.Inventory;
import org.gamerex.btl2.android.util.Purchase;
import org.gamerex.btl2.states.ActionResolver;
import org.gamerex.buildthelines.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.purplebrain.adbuddiz.sdk.AdBuddizDelegate;
import com.purplebrain.adbuddiz.sdk.AdBuddizError;
import com.purplebrain.adbuddiz.sdk.AdBuddizLogLevel;


public class AndroidLauncher extends AndroidApplication implements ActionResolver,AdBuddizDelegate{

	boolean Ads;
	private static final String ADS = "Ads";
	protected static final String SKU_REMOVE_ADS = "remove_ads";
	SharedPreferences settings;
	
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	}
	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	
	IabHelper mHelper;
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if ( purchase == null) return;
            if (mHelper == null) return;

            if (result.isFailure()) {
                return;
            }

            Log.d("IAB", "Purchase successful.");

            if (purchase.getSku().equals(SKU_REMOVE_ADS)) {
                Ads = false;
                SaveSettings(ADS, "false");
            }
        }

    };
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d("IAB", "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                // handle failure here
                return;
            }

            // Do we have the premium upgrade?
            Purchase removeAdPurchase = inventory.getPurchase(SKU_REMOVE_ADS);
            Ads = (removeAdPurchase != null);
        }
    };
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		settings = getSharedPreferences("UserInfo", 0);
		Ads = settings.getBoolean(ADS, true);
		
		String base64EncodedPublicKey = Base64.encode(String.valueOf(new java.util.Random().nextInt()*12134.7389457).getBytes());

	    // compute your public key and store it in base64EncodedPublicKey
	    mHelper = new IabHelper(this, base64EncodedPublicKey);

	    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
	           public void onIabSetupFinished(IabResult result) {
	              if (!result.isSuccess()) {
	                 Log.d("IAB", "Problem setting up In-app Billing: " + result);
	              }            
	              Log.d("IAB", "Billing Success: " + result);
	           }
	        });
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BTL2(this), config);

		//AdBuddiz.setPublisherKey("TEST_PUBLISHER_KEY");
		AdBuddiz.setPublisherKey("1c45ffae-805e-48fd-ae0c-f77237d2f394");
		AdBuddiz.setLogLevel(AdBuddizLogLevel.Info);
		AdBuddiz.cacheAds(this);
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
					if (AdBuddiz.isReadyToShowAd(AndroidLauncher.this)&&Ads) 
						AdBuddiz.showAd(AndroidLauncher.this);
				}
			});
		} catch (Exception e) {
		}
	}
	public boolean getAds(){return Ads;}
	public void bill(String arg) {
		mHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, 10001,
			     mPurchaseFinishedListener, "HANDLE_PAYLOADS");
	}
	public void SaveSettings(String name,String Value){
	    SharedPreferences.Editor editor = settings.edit();
		if(name==ADS)
			editor.putBoolean(ADS, Value=="true");
		if(name=="Speed")
			editor.putInt("Speed", Integer.parseInt(Value));
		editor.commit();		
	}
	public int getIntSettings(String name){
		return settings.getInt(name, 1);
	}
	public boolean getBoolSettings(String name){
		return settings.getBoolean(name, true);
	}

	public void setTrackerScreenName(String path) {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(path);
		t.send(new HitBuilders.AppViewBuilder().build());
	}

	public String getStringResourceByName(String aString) {
		String packageName = getPackageName();
		int resId = getResources().getIdentifier(aString, "string", packageName);
		return getString(resId);
	}

	//Billing
	public void onDestroy() {
		   super.onDestroy();
		   if (mHelper != null) mHelper.dispose();
		   mHelper = null;
		}
	
	//AdBuddiz
	public void onStart(){
		super.onStart();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}
	public void onStop(){
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}


	public void didCacheAd() {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.send(new HitBuilders.AppViewBuilder()
        .setCustomDimension(2, "didCacheAd")
        .build()
    );
	}
	
	public void didClick() {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.send(new HitBuilders.AppViewBuilder()
        .setCustomDimension(4, "didClick")
        .build()
    );
	}
	
	public void didFailToShowAd(AdBuddizError arg0) {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.send(new HitBuilders.AppViewBuilder()
        .setCustomDimension(3, "didFailToShowAd:"+arg0.name())
        .build()
    );
	}
	
	public void didHideAd() {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.send(new HitBuilders.AppViewBuilder()
        .setCustomDimension(5, "didHideAd")
        .build()
    );
	}
	
	public void didShowAd() {
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		t.send(new HitBuilders.AppViewBuilder()
        .setCustomDimension(1, "didShowAd")
        .build()
    );
	}
	
}
