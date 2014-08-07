package com.lizard.eye.ui.abstractactivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.lizard.eyes.R;
import com.lizard.eye.model.Model;

public class AbstractActivity extends ActionBarActivity {

	protected ActionBar mActionBar;
	protected SharedPreferences mSharedPreferences;
	protected Editor mEditor;
	private String TAG = AbstractActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.indianred)));
		//mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_light));
		// mActionBar.setti
		mSharedPreferences = getSharedPreferences(
				Model.getSingleton().SHARED_PREFS, MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = null;
		// Define a bitmap with the same size as the view
		try {
			returnedBitmap = Bitmap.createBitmap(view.getWidth(),
					view.getHeight(), Bitmap.Config.ARGB_8888);
			// Bind a canvas to it
			Canvas canvas = new Canvas(returnedBitmap);
			// Get the view's background
			Drawable bgDrawable = view.getBackground();
			if (bgDrawable != null) {
				// has background drawable, then draw it on the canvas
				bgDrawable.draw(canvas);
			} else {// does not have background drawable, then draw white
					// background
					// on the canvas
				canvas.drawARGB(255, 238, 228, 216);
			}// draw the view on the canvas
			view.draw(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		// return the bitmap
		return returnedBitmap;
	}
}
