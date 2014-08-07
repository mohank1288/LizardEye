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
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lizard.eyes.R;
import com.lizard.eye.model.Model;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;

public class LevelSelectionActivity extends AbstractMainActivity implements
		OnClickListener {

	private Button mButtonFirstLevel, mButtonSecondLevel, mButtonThirdLevel,
			mButtonFourthLevel, mButtonFifthLevel;
	private String level, type;
	private String TAG = LevelSelectionActivity.class.getName();
	private AdView mAdView;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.level_selection);
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

			mButtonFirstLevel = (Button) findViewById(R.id.my_view_btnfirstlevel);
			mButtonFirstLevel.setOnClickListener(this);

			mButtonSecondLevel = (Button) findViewById(R.id.my_view_btnsecondlevel);
			mButtonSecondLevel.setOnClickListener(this);

			mButtonThirdLevel = (Button) findViewById(R.id.my_view_btnthirdlevel);
			mButtonThirdLevel.setOnClickListener(this);

			mButtonFourthLevel = (Button) findViewById(R.id.my_view_btnfourthlevel);
			mButtonFourthLevel.setOnClickListener(this);

			mButtonFifthLevel = (Button) findViewById(R.id.my_view_btnfifthlevel);
			mButtonFifthLevel.setOnClickListener(this);

			type = mSharedPreferences.getString(
					Model.getSingleton().SelectedItemId, "1");
			if (type.contentEquals("1")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().AZTECH_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("2")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().BALL_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("3")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().BRUCE_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("4")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().CHRIST_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("5")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().MEXICO_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("6")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().MONEY_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("7")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().MYTH_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("8")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().ROBO_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("9")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().SPORTS_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("10")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().STICK_COMPLETED_LEVEL, "1");
			} else if (type.contentEquals("11")) {
				level = mSharedPreferences.getString(
						Model.getSingleton().TREE_COMPLETED_LEVEL, "1");
			} 
//			else if (type.contentEquals("12")) {
//				level = mSharedPreferences.getString(
//						Model.getSingleton().ZODIAC_COMPLETED_LEVEL, "1");
//			}
			int sdk = android.os.Build.VERSION.SDK_INT;

			if (level.contentEquals("1")) {

				mButtonFifthLevel.setEnabled(false);
				mButtonFourthLevel.setEnabled(false);
				mButtonSecondLevel.setEnabled(false);
				mButtonThirdLevel.setEnabled(false);
				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					mButtonFifthLevel.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.level_disable));
					mButtonFourthLevel.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.level_disable));
					mButtonSecondLevel.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.level_disable));
					mButtonThirdLevel.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.level_disable));

				} else {
					mButtonFifthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonFourthLevel.setBackground(getResources()
							.getDrawable(R.drawable.level_disable));
					mButtonSecondLevel.setBackground(getResources()
							.getDrawable(R.drawable.level_disable));
					mButtonThirdLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
				}

			} else if (level.contentEquals("2")) {
				mButtonFifthLevel.setEnabled(false);
				mButtonFourthLevel.setEnabled(false);
				mButtonThirdLevel.setEnabled(false);

				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					mButtonFifthLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonFourthLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonThirdLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));

				} else {
					mButtonFifthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonFourthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonThirdLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
				}	

			} else if (level.contentEquals("3")) {
				mButtonFifthLevel.setEnabled(false);
				mButtonFourthLevel.setEnabled(false);

				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					mButtonFifthLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonFourthLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));
				
				} else {
					mButtonFifthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
					mButtonFourthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
				}	


			} else if (level.contentEquals("4")) {
				mButtonFifthLevel.setEnabled(false);

				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					mButtonFifthLevel.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.level_disable));
					

				} else {
					mButtonFifthLevel.setBackground(getResources().getDrawable(
							R.drawable.level_disable));
				}	
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.my_view_btnfirstlevel:
			intent = new Intent(LevelSelectionActivity.this,
					WorldModeSearchActivity.class);
			mEditor.putString(Model.getSingleton().PLAY_LEVEL, "1");
			mEditor.commit();
			startActivity(intent);
			finish();
			break;

		case R.id.my_view_btnsecondlevel:
			mEditor.putString(Model.getSingleton().PLAY_LEVEL, "2");
			mEditor.commit();
			intent = new Intent(LevelSelectionActivity.this,
					WorldModeSearchActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.my_view_btnthirdlevel:
			mEditor.putString(Model.getSingleton().PLAY_LEVEL, "3");
			mEditor.commit();
			intent = new Intent(LevelSelectionActivity.this,
					WorldModeSearchActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.my_view_btnfourthlevel:
			mEditor.putString(Model.getSingleton().PLAY_LEVEL, "4");
			mEditor.commit();
			intent = new Intent(LevelSelectionActivity.this,
					WorldModeSearchActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.my_view_btnfifthlevel:
			mEditor.putString(Model.getSingleton().PLAY_LEVEL, "5");
			mEditor.commit();
			intent = new Intent(LevelSelectionActivity.this,
					WorldModeSearchActivity.class);
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
			Intent helpIntent = new Intent(LevelSelectionActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(LevelSelectionActivity.this,
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
