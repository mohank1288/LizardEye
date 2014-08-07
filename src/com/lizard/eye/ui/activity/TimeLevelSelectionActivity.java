package com.lizard.eye.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lizard.eyes.R;
import com.lizard.eye.model.Model;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;

public class TimeLevelSelectionActivity extends AbstractMainActivity implements
		OnClickListener {
	private ImageView mImageViewTimeLevel;
	private TextView mTextViewTimeLevelmsg;
	private String timeCompletedLevel;
	private String TAG = TimeLevelSelectionActivity.class.getName();
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.time_level_selection);
			// ((LizardEyeApplication) getApplication())
			// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mAdView = (AdView) findViewById(R.id.my_view_adView);

			if (mSharedPreferences.getBoolean(
					Model.getSingleton().premiumVersion, false)) {
				mAdView.setVisibility(View.GONE);
			} else {
				AdRequest request = new AdRequest.Builder().build();
				mAdView.loadAd(request);
			}

			mImageViewTimeLevel = (ImageView) findViewById(R.id.my_view_imagtimelevel);
			mImageViewTimeLevel.setOnClickListener(this);

			mTextViewTimeLevelmsg = (TextView) findViewById(R.id.my_view_texttimelevel);
			mTextViewTimeLevelmsg.setOnClickListener(this);

			timeCompletedLevel = mSharedPreferences.getString(
					Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "2");

			if (timeCompletedLevel.contentEquals("2")) {
				mEditor.putString(
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "2");
				mEditor.commit();
				mImageViewTimeLevel.setImageBitmap(getBitmap(R.drawable.time2));

				mTextViewTimeLevelmsg.setText(getResources().getString(
						R.string.time2play));
			} else if (timeCompletedLevel.contentEquals("3")) {
				mEditor.putString(
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "3");
				mEditor.commit();
				mImageViewTimeLevel.setImageBitmap(getBitmap(R.drawable.time3));

				mTextViewTimeLevelmsg.setText(getResources().getString(
						R.string.time3play));
			} else if (timeCompletedLevel.contentEquals("4")) {
				mEditor.putString(
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "4");
				mEditor.commit();
				mImageViewTimeLevel.setImageBitmap(getBitmap(R.drawable.time4));

				mTextViewTimeLevelmsg.setText(getResources().getString(
						R.string.time4play));
			} else if (timeCompletedLevel.contentEquals("5")) {
				mEditor.putString(
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "5");
				mEditor.commit();

				mImageViewTimeLevel.setImageBitmap(getBitmap(R.drawable.time5));
				mTextViewTimeLevelmsg.setText(getResources().getString(
						R.string.time5play));
			}
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private Bitmap getBitmap(int resid) {

		// Get the source image's dimensions
		int desiredWidth = 1000;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(getResources(), resid, options);

		int srcWidth = options.outWidth;
		// int srcHeight = options.outHeight;

		// Only scale if the source is big enough. This code is just trying
		// to fit a image into a certain width.
		if (desiredWidth > srcWidth)
			desiredWidth = srcWidth;

		// Calculate the correct inSampleSize/scale value. This helps reduce
		// memory use. It should be a power of 2
		int inSampleSize = 1;
		while (srcWidth / 2 > desiredWidth) {
			srcWidth /= 2;
			// srcHeight /= 2;
			inSampleSize *= 2;
		}
		// Decode with inSampleSize
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inSampleSize = inSampleSize;
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inPurgeable = true;
		Bitmap sampledSrcBitmap;

		sampledSrcBitmap = BitmapFactory.decodeResource(getResources(), resid,
				options);

		return sampledSrcBitmap;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.my_view_texttimelevel:
			intent = new Intent(TimeLevelSelectionActivity.this,
					TimeSearchActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.my_view_imagtimelevel:
			intent = new Intent(TimeLevelSelectionActivity.this,
					TimeSearchActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem shareItem = menu.findItem(R.id.my_view_menushare);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(shareItem);
		mShareActionProvider.setShareIntent(getShareIntent());
		mShareActionProvider
				.setOnShareTargetSelectedListener(new OnShareTargetSelectedListener() {
					@Override
					public boolean onShareTargetSelected(
							ShareActionProvider actionProvider, Intent intent) {
						file = new File(Environment
								.getExternalStorageDirectory()
								+ "/LizardEye/"
								+ "lizardshare.png");
						if (file.exists()) {
							file.delete();
						}
						new ExportAsyncTask().execute();

						return false;
					}
				});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.my_view_menuhelp:
			Intent helpIntent = new Intent(TimeLevelSelectionActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(TimeLevelSelectionActivity.this,
					PointsActivity.class);
			startActivity(settingsIntent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("SdCardPath")
	private Intent getShareIntent() {
		String path = "file:///sdcard/LizardEye/lizardshare.png";
		Uri phototUri = Uri.parse(path);
		String playStoreLink = "https://play.google.com/store/apps/details?id="
				+ getPackageName();
		String yourShareText = getResources().getString(
				R.string.installfromplaystore)
				+ " " + playStoreLink;
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/png");

		try {
			shareIntent.putExtra(Intent.EXTRA_TEXT,
					getResources().getString(R.string.lizardshare));
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources()
					.getString(R.string.app_name));
			shareIntent.putExtra(Intent.EXTRA_TEXT, yourShareText);
			shareIntent.putExtra(Intent.EXTRA_STREAM, phototUri);
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}
		return shareIntent;
	}

	private class ExportAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						File dir = new File(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ "/LizardEye/");
						if (!dir.exists()) {
							dir.mkdirs();
						}
						file = new File(
								Environment.getExternalStorageDirectory()
										+ "/LizardEye/" + "lizardshare.png");
						if (file.exists()) {
							file.delete();
						}
						Bitmap bm = getBitmapFromView(findViewById(R.id.my_view_linear));
						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);

						file.createNewFile();
						// write the bytes in file
						FileOutputStream fo = new FileOutputStream(file);
						fo.write(bytes.toByteArray());
						fo.close();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
			});
			return null;
		}
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