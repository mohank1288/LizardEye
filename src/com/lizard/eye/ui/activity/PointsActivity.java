package com.lizard.eye.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lizard.eyes.R;
import com.lizard.eye.adapter.PointsPageAdapter;
import com.lizard.eye.model.Model;
import com.lizard.eye.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;

public class PointsActivity extends AbstractMainActivity {
	private ViewPager mViewPager;
	private PointsPageAdapter mPageAdapter;
	private PagerSlidingTabStrip mTabs;
	private String TAG = PointsActivity.class.getName();
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.points);
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

			mViewPager = (ViewPager) findViewById(R.id.myview_pager);
			mViewPager.setOffscreenPageLimit(2);
			mTabs = (PagerSlidingTabStrip) findViewById(R.id.myview_tabs);
			mTabs.setAllCaps(false);
			mTabs.setIndicatorColor(getResources().getColor(R.color.red));
			mTabs.setUnderlineColor(getResources().getColor(R.color.red));
			mTabs.setShouldExpand(true);
			mTabs.getLayoutParams().width = LayoutParams.MATCH_PARENT;

			mPageAdapter = new PointsPageAdapter(getSupportFragmentManager(),
					this);
			mViewPager.setAdapter(mPageAdapter);
			mTabs.setViewPager(mViewPager);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_help_points, menu);

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