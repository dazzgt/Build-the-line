package org.gamerex.buildthelines2.android;

import org.gamerex.buildthelines2.BTL2;
import org.gamerex.buildthelines2.states.ActionResolver;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {


  private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6049553525908152/3441613621";

  protected View gameView;
  private InterstitialAd interstitialAd;

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
  public void showOrLoadInterstital() {
    try {
      runOnUiThread(new Runnable() {
        public void run() {
          if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            //Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
          }
          else {
            AdRequest interstitialRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(interstitialRequest);
            //Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
          }
        }
      });
    } catch (Exception e) {
    }
  }
  @Override
  public void onStart() {
    super.onStart();
    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
  }

  @Override
  public void onStop() {
    super.onStop();
    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
  }

}
