package com.lizard.eye.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.flurry.android.FlurryAgent;
import com.lizard.eyes.R;
import com.lizard.eye.model.Model;

public class SplashActivity extends Activity {
	private int SPLASH_TIME_OUT = 3000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		// ((LizardEyeApplication) getApplication())
		// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						ModeSelectionActivity.class);
				startActivity(intent);

				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, Model.getSingleton().FLURRY_API_KEY);
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
