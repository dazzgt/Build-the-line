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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.splash.SplashConfig;


public class AndroidLauncher extends AndroidApplication implements ActionResolver,GameHelperListener{

	private GameHelper gameHelper ;
	private static final String ADS = "Ads";
	protected static final String SKU_REMOVE_ADS = "remove_ads";
	SharedPreferences settings;// = getSharedPreferences("UserInfo", 0);
	private StartAppAd startAppAd;// = new StartAppAd(this);


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
				SaveSettings(ADS, "false");
			}
		}

	};
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d("IAB", "Query inventory finished.");
			if (mHelper == null) return;			
			if (result.isFailure()) {
				return;
			}

			// Do we have the premium upgrade?
			Purchase removeAdPurchase = inventory.getPurchase(SKU_REMOVE_ADS);
			if(removeAdPurchase != null)
				setAds(false);
		}
	};






	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startAppAd = new StartAppAd(this);
		settings = getSharedPreferences("UserInfo", 0);
		StartAppSDK.init(this, "102224766", "202671335", true);
		//if(getAds()){
		StartAppAd.showSplash(this, savedInstanceState, 
				new SplashConfig()
		.setTheme(SplashConfig.Theme.SKY)
		.setAppName("Build The Lines")  
		.setLogo(R.drawable.ic_launcher)   // resource ID
		.setOrientation(SplashConfig.Orientation.PORTRAIT));
		//}

		String base64EncodedPublicKey = Base64.encode(String.valueOf(new java.util.Random().nextInt()*12134.7389457).getBytes());

		// compute your public key and store it in base64EncodedPublicKey
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if(result==null){
					Log.d("IAB", "null result: " + result);
				}
				if (!result.isSuccess()) {
					Log.d("IAB", "Problem setting up In-app Billing: " + result);
				}            
				Log.d("IAB", "Billing Success: " + result);
			}
		});

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BTL2(this), config);
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
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
					startAppAd.showAd(); // show the ad
					startAppAd.loadAd();
				}
			});
		} catch (Exception e) {
		}
	}

	public void bill(String arg) {
		mHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, 10001,
				null/*mPurchaseFinishedListener*/, "HANDLE_PAYLOADS");
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
		int i = settings.getInt(name, 1);
		return i;
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

	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}
	public void setAds(boolean ads){
		SaveSettings(ADS, "false");
	}
	public boolean getAds(){return getBoolSettings(ADS);}
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(),getStringResourceByName("leaderboard_highscore"), score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getStringResourceByName("leaderboard_highscore")), 100);
		}
		else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		}
		else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

}
