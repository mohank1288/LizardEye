package com.lizard.eye.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lizard.eyes.R;
import com.lizard.eye.adapter.MenuAdapter;
import com.lizard.eye.model.Menus;
import com.lizard.eye.model.Model;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;
import com.lizard.eye.util.IabHelper;
import com.lizard.eye.util.IabResult;
import com.lizard.eye.util.Inventory;
import com.lizard.eye.util.Purchase;

public class WorldSelectionActivity extends AbstractMainActivity implements
		OnItemClickListener {

	private GridView mListViewTypes;
	private Menus menus;
	private MenuAdapter mAdapter;
	private AdView mAdView;
	private ArrayList<Menus> menu;
	private IabHelper mHelper;
	private String TAG = WorldSelectionActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.world_mode);
			// ((LizardEyeApplication) getApplication())
			// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mAdView = (AdView) findViewById(R.id.my_view_adView);

			mListViewTypes = (GridView) findViewById(R.id.my_view_gridview);
			mListViewTypes.setOnItemClickListener(this);

			if (mSharedPreferences.getBoolean(
					Model.getSingleton().premiumVersion, false)) {
				mAdView.setVisibility(View.GONE);
				addMenu(false);
			} else {
				mHelper = new IabHelper(this,
						Model.getSingleton().base64EncodedPublicKey);

				mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {

					public void onIabSetupFinished(IabResult result) {
						Log.d(TAG, "Setup finished.");

						if (!result.isSuccess()) {
							// Oh noes, there was a problem.
							Log.d(TAG, "In-app Billing setup failed: " + result);
							return;
						}

						// Have we been disposed of in the meantime? If so,
						// quit.
						if (mHelper == null)
							return;

						// IAB is fully set up. Now, let's get an inventory of
						// stuff
						// we own.
						Log.d(TAG, "Setup successful. Querying inventory.");
						mHelper.queryInventoryAsync(mGotAllInventoryListener);
					}
				});

			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private IabHelper.QueryInventoryFinishedListener mGotAllInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		private boolean premiumVersionAll;

		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {

			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				Log.d(TAG, "Failed to query inventory: " + result);

				mEditor.putBoolean(Model.getSingleton().premiumVersion, false);
				mEditor.commit();

				if (mSharedPreferences.getBoolean(
						Model.getSingleton().premiumVersion, false)) {
					mAdView.setVisibility(View.GONE);
				} else {
					AdRequest request = new AdRequest.Builder().build();
					mAdView.loadAd(request);
				}
				addMenu(true);
				return;
			} else {
				Log.d(TAG, "Query inventory was successful.");
				premiumVersionAll = inventory
						.hasPurchase(Model.getSingleton().SKU_PREMIUM_ALL);

				if (premiumVersionAll) {
					mEditor.putBoolean(Model.getSingleton().premiumVersion,
							true);
					mEditor.commit();
					mAdView.setVisibility(View.GONE);
					addMenu(false);
				} else {
					mEditor.putBoolean(Model.getSingleton().premiumVersion,
							false);
					mEditor.commit();

					AdRequest request = new AdRequest.Builder().build();
					mAdView.loadAd(request);
					addMenu(true);
				}
				Log.d(TAG, "User is "
						+ (premiumVersionAll ? "PREMIUM" : "NOT PREMIUM"));
			}
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			if (menu.get(position).getInAppPurchaseRequired()) {
				if (mHelper != null)
					mHelper.flagEndAsync();
				mHelper.launchPurchaseFlow(WorldSelectionActivity.this,
						Model.getSingleton().SKU_PREMIUM_ALL,
						Model.getSingleton().RC_REQUEST,
						mPurchaseFinishedListener);
			} else {

				mEditor.putString(Model.getSingleton().SelectedItemId, menu
						.get(position).getId());
				mEditor.putString(Model.getSingleton().SelectedItemName, menu
						.get(position).getMenuName());
				mEditor.putString(Model.getSingleton().SelectedItemLength, menu
						.get(position).getNoofIcons());

				mEditor.commit();

				Intent intent = new Intent(WorldSelectionActivity.this,
						LevelSelectionActivity.class);
				startActivity(intent);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure() || result == null || purchase == null) {
				Log.d(TAG, "Error purchasing: " + result);

				mEditor.putBoolean(Model.getSingleton().premiumVersion, false);
				mEditor.commit();

				if (mSharedPreferences.getBoolean(
						Model.getSingleton().premiumVersion, false)) {
					mAdView.setVisibility(View.GONE);
				} else {
					AdRequest request = new AdRequest.Builder().build();
					mAdView.loadAd(request);
				}
				addMenu(true);

				return;
			} else if (result.getResponse() == 7
					|| purchase.getSku().equals(
							Model.getSingleton().SKU_PREMIUM_ALL)) {

				mEditor.putBoolean(Model.getSingleton().premiumVersion, true);
				mEditor.commit();

				if (mSharedPreferences.getBoolean(
						Model.getSingleton().premiumVersion, false)) {
					mAdView.setVisibility(View.GONE);
				} else {
					AdRequest request = new AdRequest.Builder().build();
					mAdView.loadAd(request);
				}
				addMenu(false);
				return;
			}
		}
	};

	private void addMenu(boolean fullversion)// fullVersion = false(InApp
												// purchase Not Required) , true
												// ( InApp purchase Required)
	{
		try {
			menu = new ArrayList<Menus>();

			// 1, Aztech world
			menus = new Menus("1", getResources().getString(
					R.string.world_menu_aztechworld), getResources().getString(
					R.string.world_menu_aztechworld),
					Model.getSingleton().AZTECH_ICONS, false);
			menu.add(menus);

			// 2,Ball World
			menus = new Menus("2", getResources().getString(
					R.string.world_menu_ballworld), getResources().getString(
					R.string.world_menu_ballworld),
					Model.getSingleton().BALL_ICONS, fullversion);
			menu.add(menus);

			// 3, BruceWorld
			menus = new Menus("3", getResources().getString(
					R.string.world_menu_bruceworld), getResources().getString(
					R.string.world_menu_bruceworld),
					Model.getSingleton().BRUCE_ICONS, fullversion);
			menu.add(menus);

			// 4, ChristWorld
			menus = new Menus("4", getResources().getString(
					R.string.world_menu_christworld), getResources().getString(
					R.string.world_menu_christworld),
					Model.getSingleton().CHRIST_ICONS, fullversion);
			menu.add(menus);

			// 5, MexicoWorld
			menus = new Menus("5", getResources().getString(
					R.string.world_menu_mexicoworld), getResources().getString(
					R.string.world_menu_mexicoworld),
					Model.getSingleton().MEXICO_ICONS, fullversion);
			menu.add(menus);

			// 6, MoneyWorld
			menus = new Menus("6", getResources().getString(
					R.string.world_menu_moneyworld), getResources().getString(
					R.string.world_menu_moneyworld),
					Model.getSingleton().MONEY_ICONS, fullversion);
			menu.add(menus);

			// 7, MythWorld
			menus = new Menus("7", getResources().getString(
					R.string.world_menu_mythworld), getResources().getString(
					R.string.world_menu_mythworld),
					Model.getSingleton().MYTH_ICONS, fullversion);
			menu.add(menus);

			// 8,RoboWorld
			menus = new Menus("8", getResources().getString(
					R.string.world_menu_roboworld), getResources().getString(
					R.string.world_menu_roboworld),
					Model.getSingleton().ROBO_ICONS, fullversion);
			menu.add(menus);

			// 9, SportsWorld
			menus = new Menus("9", getResources().getString(
					R.string.world_menu_sportsworld), getResources().getString(
					R.string.world_menu_sportsworld),
					Model.getSingleton().STICK_ICONS, fullversion);
			menu.add(menus);

			// 10, StickWorld
			menus = new Menus("10", getResources().getString(
					R.string.world_menu_stickworld), getResources().getString(
					R.string.world_menu_stickworld),
					Model.getSingleton().STICK_ICONS, fullversion);
			menu.add(menus);

			// 11,TreeWorld
			menus = new Menus("11", getResources().getString(
					R.string.world_menu_treeworld), getResources().getString(
					R.string.world_menu_treeworld),
					Model.getSingleton().TREE_ICONS, fullversion);
			menu.add(menus);

			// 12,ZodiacWorld
//			menus = new Menus("12", getResources().getString(
//					R.string.world_menu_zodiacworld), getResources().getString(
//					R.string.world_menu_zodiacworld),
//					Model.getSingleton().ZODIAC_ICONS, fullversion);
//			menu.add(menus);

			mAdapter = new MenuAdapter(WorldSelectionActivity.this, menu);
			mListViewTypes.setAdapter(mAdapter);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
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
			Intent helpIntent = new Intent(WorldSelectionActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(WorldSelectionActivity.this,
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
