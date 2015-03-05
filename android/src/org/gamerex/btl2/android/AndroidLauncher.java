package org.gamerex.btl2.android;

import org.gamerex.btl2.BTL2;
import org.gamerex.btl2.states.ActionResolver;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication implements ActionResolver{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new BTL2(this), config);
	}

	@Override
	public void onResume () {
		super.onResume();

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
	public void setTrackerScreenName(String path) {}

	@Override
	public String getStringResourceByName(String aString) {
		String packageName = getPackageName();
		int resId = getResources().getIdentifier(aString, "string", packageName);
		return getString(resId);
	}
}
