package org.gamerex.btl2.android;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import android.os.Bundle;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	//ADS
	String appKey = "3ef26a6d2cc47d8289566ddabb220919bea44864f09b7b85";


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		Appodeal.initialize(this, appKey);

		initialize(new BTL2(this), cfg);
	}


	public void showInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if(Appodeal.isLoaded())
					 Appodeal.showBanner(AndroidLauncher.this);
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
