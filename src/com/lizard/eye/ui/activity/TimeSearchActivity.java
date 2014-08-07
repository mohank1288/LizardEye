package com.lizard.eye.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.lizard.eyes.R;
import com.lizard.eye.circularimageview.RoundedImageView;
import com.lizard.eye.model.ColorGeneration;
import com.lizard.eye.model.Model;
import com.lizard.eye.model.SerializedColorSearchStorage;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;

public class TimeSearchActivity extends AbstractMainActivity implements
		OnClickListener {
	private Button mButtonGo;
	private int[] IMAGE_IDS;
	private TextView mTextViewLevel, mTextViewPoints, mTextViewtime,
			mTextViewcount;
	private int[] searchID;
	private int[] mColorCode;
	private ArrayList<SerializedColorSearchStorage> mArrayList;
	private String playLevel;
	private int[] randomGeneratedID;
	private RoundedImageView mRoundedImageView1, mRoundedImageView2,
			mRoundedImageView3, mRoundedImageView4, mRoundedImageView5;
	private int timeUpdate = 0;
	private Handler handler = new Handler();
	private String TAG = TimeSearchActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.image_search);
			// ((LizardEyeApplication) getApplication())
			// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mTextViewLevel = (TextView) findViewById(R.id.my_view_textLevel);
			mTextViewPoints = (TextView) findViewById(R.id.my_view_textpoints);
			mTextViewtime = (TextView) findViewById(R.id.my_view_texttime);
			mTextViewcount = (TextView) findViewById(R.id.my_view_textcount);
			mTextViewPoints.setText(String.valueOf(mSharedPreferences
					.getString(Model.getSingleton().POINTS_WON, "0")));
			mTextViewcount.post(updateTextRunnable);

			mButtonGo = (Button) findViewById(R.id.my_view_btngo);
			mButtonGo.setOnClickListener(this);
			mRoundedImageView1 = (RoundedImageView) findViewById(R.id.my_view_imgsearch1);
			mRoundedImageView2 = (RoundedImageView) findViewById(R.id.my_view_imgsearch2);
			mRoundedImageView3 = (RoundedImageView) findViewById(R.id.my_view_imgsearch3);
			mRoundedImageView4 = (RoundedImageView) findViewById(R.id.my_view_imgsearch4);
			mRoundedImageView5 = (RoundedImageView) findViewById(R.id.my_view_imgsearch5);

			playLevel = mSharedPreferences.getString(
					Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "1");

			IMAGE_IDS = new int[] { R.drawable.ic_shape1, R.drawable.ic_shape2,
					R.drawable.ic_shape3, R.drawable.ic_shape4,
					R.drawable.ic_shape5, R.drawable.ic_shape6,
					R.drawable.ic_shape7, R.drawable.ic_shape8,
					R.drawable.ic_shape9, R.drawable.ic_shape10,
					R.drawable.ic_shape11, R.drawable.ic_shape12,
					R.drawable.ic_shape13, R.drawable.ic_shape14,
					R.drawable.ic_shape15, R.drawable.ic_shape16,
					R.drawable.ic_shape17, R.drawable.ic_shape18,
					R.drawable.ic_shape19, R.drawable.ic_shape20};

			if (playLevel.contentEquals("1")) {
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_FIRST_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);

			} else if (playLevel.contentEquals("2")) {
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_SECOND_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
			} else if (playLevel.contentEquals("3")) {
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_THIRD_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
			} else if (playLevel.contentEquals("4")) {
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_FOURTH_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
			} else if (playLevel.contentEquals("5")) {
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_FIFTH_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
			}
			else
			{
				completedAnswers(mSharedPreferences.getString(
						Model.getSingleton().TIME_SHAPE_FIFTH_LEVEL_ANSWERS,
						"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
			}

			mTextViewLevel.setText(playLevel);
			randomGeneratedID = ColorGeneration.randomList(1, IMAGE_IDS.length,
					Integer.parseInt(playLevel));
			if (playLevel.contentEquals("1")) {

				int gen1 = IMAGE_IDS[randomGeneratedID[0] - 1];

				searchID = new int[] { gen1 };

			} else if (playLevel.contentEquals("2")) {
				int gen1 = IMAGE_IDS[randomGeneratedID[0] - 1];
				int gen2 = IMAGE_IDS[randomGeneratedID[1] - 1];

				searchID = new int[] { gen1, gen2 };
			}

			else if (playLevel.contentEquals("3")) {
				int gen1 = IMAGE_IDS[randomGeneratedID[0] - 1];
				int gen2 = IMAGE_IDS[randomGeneratedID[1] - 1];
				int gen3 = IMAGE_IDS[randomGeneratedID[2] - 1];

				searchID = new int[] { gen1, gen2, gen3 };
			}

			else if (playLevel.contentEquals("4")) {
				int gen1 = IMAGE_IDS[randomGeneratedID[0] - 1];
				int gen2 = IMAGE_IDS[randomGeneratedID[1] - 1];
				int gen3 = IMAGE_IDS[randomGeneratedID[2] - 1];
				int gen4 = IMAGE_IDS[randomGeneratedID[3] - 1];

				searchID = new int[] { gen1, gen2, gen3, gen4 };
			}

			else if (playLevel.contentEquals("5")) {
				int gen1 = IMAGE_IDS[randomGeneratedID[0] - 1];
				int gen2 = IMAGE_IDS[randomGeneratedID[1] - 1];
				int gen3 = IMAGE_IDS[randomGeneratedID[2] - 1];
				int gen4 = IMAGE_IDS[randomGeneratedID[3] - 1];
				int gen5 = IMAGE_IDS[randomGeneratedID[4] - 1];

				searchID = new int[] { gen1, gen2, gen3, gen4, gen5 };

			}

			int[] gen = ColorGeneration.randomList(1, IMAGE_IDS.length,
					Integer.parseInt(playLevel));

			if (playLevel.contentEquals("1")) {
				int color1 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[0]);
				mColorCode = new int[] { color1 };
			} else if (playLevel.contentEquals("2")) {
				int color1 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[1]);
				mColorCode = new int[] { color1, color2 };

			} else if (playLevel.contentEquals("3")) {
				int color1 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[2]);
				mColorCode = new int[] { color1, color2, color3 };

			} else if (playLevel.contentEquals("4")) {
				int color1 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[2]);
				int color4 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[3]);
				mColorCode = new int[] { color1, color2, color3, color4 };

			} else if (playLevel.contentEquals("5")) {
				int color1 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[2]);
				int color4 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[3]);
				int color5 = ColorGeneration.getRandomColorCode(
						TimeSearchActivity.this, gen[4]);
				mColorCode = new int[] { color1, color2, color3, color4, color5 };

			}

			mArrayList = new ArrayList<SerializedColorSearchStorage>();

			for (int i = 0; i < searchID.length; i++) {
				SerializedColorSearchStorage serializedColorSearchStorage = new SerializedColorSearchStorage(
						mColorCode[i], searchID[i]);
				mArrayList.add(serializedColorSearchStorage);
			}

			if (mColorCode.length == 1) {

				mRoundedImageView1.setOval(true);

				mRoundedImageView1.setImageBitmap(getBitmap(this, mArrayList
						.get(0).getImageId()));

				mRoundedImageView1.setColorFilter(mArrayList.get(0)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView2.setVisibility(View.GONE);
				mRoundedImageView3.setVisibility(View.GONE);
				mRoundedImageView4.setVisibility(View.GONE);
				mRoundedImageView5.setVisibility(View.GONE);

			} else if (mColorCode.length == 2) {
				mRoundedImageView1.setOval(true);

				mRoundedImageView1.setImageBitmap(getBitmap(this, mArrayList
						.get(0).getImageId()));

				mRoundedImageView1.setColorFilter(mArrayList.get(0)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView2.setOval(true);

				mRoundedImageView2.setImageBitmap(getBitmap(this, mArrayList
						.get(1).getImageId()));

				mRoundedImageView2.setColorFilter(mArrayList.get(1)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView3.setVisibility(View.GONE);
				mRoundedImageView4.setVisibility(View.GONE);
				mRoundedImageView5.setVisibility(View.GONE);
			} else if (mColorCode.length == 3) {

				mRoundedImageView1.setOval(true);

				mRoundedImageView1.setImageBitmap(getBitmap(this, mArrayList
						.get(0).getImageId()));

				mRoundedImageView1.setColorFilter(mArrayList.get(0)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView2.setOval(true);

				mRoundedImageView2.setImageBitmap(getBitmap(this, mArrayList
						.get(1).getImageId()));

				mRoundedImageView2.setColorFilter(mArrayList.get(1)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView3.setOval(true);

				mRoundedImageView3.setImageBitmap(getBitmap(this, mArrayList
						.get(2).getImageId()));

				mRoundedImageView3.setColorFilter(mArrayList.get(2)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView4.setVisibility(View.GONE);
				mRoundedImageView5.setVisibility(View.GONE);

			} else if (mColorCode.length == 4) {
				mRoundedImageView1.setOval(true);

				mRoundedImageView1.setImageBitmap(getBitmap(this, mArrayList
						.get(0).getImageId()));

				mRoundedImageView1.setColorFilter(mArrayList.get(0)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView2.setOval(true);

				mRoundedImageView2.setImageBitmap(getBitmap(this, mArrayList
						.get(1).getImageId()));

				mRoundedImageView2.setColorFilter(mArrayList.get(1)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView3.setOval(true);

				mRoundedImageView3.setImageBitmap(getBitmap(this, mArrayList
						.get(2).getImageId()));

				mRoundedImageView3.setColorFilter(mArrayList.get(2)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView4.setOval(true);

				mRoundedImageView4.setImageBitmap(getBitmap(this, mArrayList
						.get(3).getImageId()));

				mRoundedImageView4.setColorFilter(mArrayList.get(3)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView5.setVisibility(View.GONE);
			} else if (mColorCode.length == 5) {
				mRoundedImageView1.setOval(true);

				mRoundedImageView1.setImageBitmap(getBitmap(this, mArrayList
						.get(0).getImageId()));

				mRoundedImageView1.setColorFilter(mArrayList.get(0)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView2.setOval(true);

				mRoundedImageView2.setImageBitmap(getBitmap(this, mArrayList
						.get(1).getImageId()));

				mRoundedImageView2.setColorFilter(mArrayList.get(1)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView3.setOval(true);

				mRoundedImageView3.setImageBitmap(getBitmap(this, mArrayList
						.get(2).getImageId()));

				mRoundedImageView3.setColorFilter(mArrayList.get(2)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView4.setOval(true);

				mRoundedImageView4.setImageBitmap(getBitmap(this, mArrayList
						.get(3).getImageId()));

				mRoundedImageView4.setColorFilter(mArrayList.get(3)
						.getColorCode(), Mode.SRC_ATOP);

				mRoundedImageView5.setOval(true);

				mRoundedImageView5.setImageBitmap(getBitmap(this, mArrayList
						.get(4).getImageId()));

				mRoundedImageView5.setColorFilter(mArrayList.get(4)
						.getColorCode(), Mode.SRC_ATOP);

			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private Bitmap getBitmap(Context ctx, int resid) {
		// Get the source image's dimensions
		int desiredWidth = 1000;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(ctx.getResources(), resid, options);

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

		sampledSrcBitmap = BitmapFactory.decodeResource(ctx.getResources(),
				resid, options);

		return sampledSrcBitmap;
	}

	private Runnable updateTextRunnable = new Runnable() {
		public void run() {
			mTextViewtime.setText(String.valueOf(timeUpdate));
			timeUpdate++;
			handler.postDelayed(this, Model.getSingleton().TIME_SECOND_UPDATE);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_view_btngo:

			mEditor.putString(Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
					playLevel);

			mEditor.putString(Model.getSingleton().SelectedItemLength,
					String.valueOf(IMAGE_IDS.length));

			mEditor.putString(Model.getSingleton().TIME_TAKEN,
					String.valueOf(timeUpdate));

			mEditor.commit();
			Intent intent = new Intent(TimeSearchActivity.this,
					TimeSelectionActivity.class);
			intent.putParcelableArrayListExtra(
					Model.getSingleton().COLOR_SEARCH_LIST, mArrayList);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	private void completedAnswers(String correctAnswer, String totalCount) {
		try {
			if (Integer.parseInt(correctAnswer) > Integer.parseInt(Model
					.getSingleton().EACH_LEVEL_ITEMS)) {

			} else {
				mTextViewcount.setText(correctAnswer + " / " + totalCount);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
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
			Intent helpIntent = new Intent(TimeSearchActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(TimeSearchActivity.this,
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
