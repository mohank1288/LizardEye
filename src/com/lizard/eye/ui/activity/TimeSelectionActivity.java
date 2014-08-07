package com.lizard.eye.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.lizard.eyes.R;
import com.lizard.eye.adapter.GridSelectionAdapter;
import com.lizard.eye.circularimageview.RoundedImageView;
import com.lizard.eye.model.ColorGeneration;
import com.lizard.eye.model.ColorSearchStorage;
import com.lizard.eye.model.Model;
import com.lizard.eye.model.SerializedColorSearchStorage;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;
import com.lizard.eye.widget.crouton.Configuration;
import com.lizard.eye.widget.crouton.Crouton;
import com.lizard.eye.widget.crouton.Style;

public class TimeSelectionActivity extends AbstractMainActivity implements
		OnClickListener, OnItemClickListener {
	private GridView mGridViewSelection;
	private GridSelectionAdapter gridSelectionAdapter;
	private Integer[] mThumbIds;
	private ArrayList<SerializedColorSearchStorage> mSerializedColorSelectionStorages;

	private ArrayList<ColorSearchStorage> mColorSelectionStoragesList;
	private int clickCount = 0;
	private int answerCount = 0, failAnswerCount = 0;
	private ColorSearchStorage mColorSelectionStorage;
	private TextView mTextViewLevel;
	private TextView mTextViewPoints;
	private TextView mTextViewtime;
	private TextView mTextViewcount;
	private String playLevel, failPlayLevel, failAttempts;
	private int answer = 0, attempt = 0, pointsWon = 0, totalAnswer = 0,
			totalattempt = 0, averageTime = 0, correctAnswerAverageTime = 0,
			timeModePointsWon = 0;
	// private Crouton infiniteCrouton;
	private Style INFINITE = new Style.Builder().setBackgroundColorValue(
			Style.holoBlueLight).build();
	private Configuration CONFIGURATION_INFINITE = new Configuration.Builder()
			.setDuration(Configuration.DURATION_INFINITE).build();
	private Button mButtonreplay;
	private int timeUpdate = 0;
	private Handler handler = new Handler();
	private RoundedImageView roundedImageView;
	private String TAG = TimeSelectionActivity.class.getName();
	private ArrayList<Integer> mArrayList = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.image_selection);
			// ((LizardEyeApplication) getApplication())
			// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mButtonreplay = (Button) findViewById(R.id.my_view_btnreplay);
			mButtonreplay.setOnClickListener(this);

			mTextViewLevel = (TextView) findViewById(R.id.my_view_textLevel);
			mTextViewPoints = (TextView) findViewById(R.id.my_view_textpoints);
			mTextViewtime = (TextView) findViewById(R.id.my_view_texttime);
			mTextViewcount = (TextView) findViewById(R.id.my_view_textcount);
			mTextViewPoints.setText(String.valueOf(mSharedPreferences
					.getString(Model.getSingleton().POINTS_WON, "0")));

			mTextViewLevel.setText(mSharedPreferences.getString(
					Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "1"));

			clickCount = 0;
			answerCount = 0;
			mSerializedColorSelectionStorages = getIntent()
					.getParcelableArrayListExtra(
							Model.getSingleton().COLOR_SEARCH_LIST);

			mGridViewSelection = (GridView) findViewById(R.id.my_view_gridview);

			playLevel = mSharedPreferences.getString(
					Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL, "1");

			mThumbIds = new Integer[] { R.drawable.ic_shape1,
					R.drawable.ic_shape2, R.drawable.ic_shape3,
					R.drawable.ic_shape4, R.drawable.ic_shape5,
					R.drawable.ic_shape6, R.drawable.ic_shape7,
					R.drawable.ic_shape8, R.drawable.ic_shape9,
					R.drawable.ic_shape10, R.drawable.ic_shape11,
					R.drawable.ic_shape12, R.drawable.ic_shape13,
					R.drawable.ic_shape14, R.drawable.ic_shape15,
					R.drawable.ic_shape16, R.drawable.ic_shape17,
					R.drawable.ic_shape18, R.drawable.ic_shape19,
					R.drawable.ic_shape20};

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

			mThumbIds = RandomizeArray(mThumbIds);

			int colorFilter;
			int[] gens = ColorGeneration.randomList(1, mThumbIds.length,
					mThumbIds.length);

			mColorSelectionStoragesList = new ArrayList<>();
			for (int i = 0; i < mThumbIds.length; i++) {
				if (playLevel.contentEquals("1")) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(0)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else {
						colorFilter = ColorGeneration.getRandomColorCode(
								TimeSelectionActivity.this, gens[i]);
						mColorSelectionStorage = new ColorSearchStorage(
								colorFilter, mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}
				}

				else if (playLevel.contentEquals("2")) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(0)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(1).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(1)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else {
						colorFilter = ColorGeneration.getRandomColorCode(
								TimeSelectionActivity.this, gens[i]);
						mColorSelectionStorage = new ColorSearchStorage(
								colorFilter, mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}
				}

				else if (playLevel.contentEquals("3")) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(0)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(1).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(1)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}

					else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(2).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(2)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else {
						colorFilter = ColorGeneration.getRandomColorCode(
								TimeSelectionActivity.this, gens[i]);
						mColorSelectionStorage = new ColorSearchStorage(
								colorFilter, mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					}
				}

				else if (playLevel.contentEquals("4")) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(0)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(1).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(1)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}

					else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(2).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(2)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(3).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(3)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else {
						colorFilter = ColorGeneration.getRandomColorCode(
								TimeSelectionActivity.this, gens[i]);
						mColorSelectionStorage = new ColorSearchStorage(
								colorFilter, mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					}
				}

				else if (playLevel.contentEquals("5")) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(0)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);

					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(1).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(1)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}

					else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(2).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(2)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(3).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(3)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(4).getImageId()) {
						mColorSelectionStorage = new ColorSearchStorage(
								mSerializedColorSelectionStorages.get(4)
										.getColorCode(), mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					} else {
						colorFilter = ColorGeneration.getRandomColorCode(
								TimeSelectionActivity.this, gens[i]);
						mColorSelectionStorage = new ColorSearchStorage(
								colorFilter, mThumbIds[i]);
						mColorSelectionStoragesList.add(mColorSelectionStorage);
					}
				}
			}
			gridSelectionAdapter = new GridSelectionAdapter(this,
					mColorSelectionStoragesList);
			mGridViewSelection.setOnItemClickListener(this);

			// Instance of ImageAdapter Class
			mGridViewSelection.setAdapter(gridSelectionAdapter);

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(updateTextRunnable);

		mEditor.putString(Model.getSingleton().TIME_TAKEN,
				String.valueOf(timeUpdate));
		mEditor.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		timeUpdate = Integer.parseInt(mSharedPreferences.getString(
				Model.getSingleton().TIME_TAKEN, "0"));
		mTextViewcount.post(updateTextRunnable);
	}

	private Runnable updateTextRunnable = new Runnable() {
		public void run() {
			mTextViewtime.setText(String.valueOf(timeUpdate));
			timeUpdate++;
			handler.postDelayed(this, Model.getSingleton().TIME_SECOND_UPDATE);
		}
	};

	/** Shuffling of Int Array **/
	private Integer[] RandomizeArray(Integer[] mThumbIds2) {
		Random rgen = new Random(); // Random number generator

		for (int i = 0; i < mThumbIds2.length; i++) {
			int randomPosition = rgen.nextInt(mThumbIds2.length);
			int temp = mThumbIds2[i];
			mThumbIds2[i] = mThumbIds2[randomPosition];
			mThumbIds2[randomPosition] = temp;
		}
		return mThumbIds2;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			roundedImageView = (RoundedImageView) view
					.findViewById(R.id.my_view_imgsearch);

			roundedImageView.setBorderWidth(8);

			if (playLevel.contentEquals("1")) {
				answerCheck(position,
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
						Model.getSingleton().TIME_SHAPE_FIRST_LEVEL_ANSWERS,
						Model.getSingleton().TIME_SHAPE_FIRST_LEVEL_ATTEMPTS);
			} else if (playLevel.contentEquals("2")) {
				answerCheck(position,
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
						Model.getSingleton().TIME_SHAPE_SECOND_LEVEL_ANSWERS,
						Model.getSingleton().TIME_SHAPE_SECOND_LEVEL_ATTEMPTS);

			} else if (playLevel.contentEquals("3")) {
				answerCheck(position,
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
						Model.getSingleton().TIME_SHAPE_THIRD_LEVEL_ANSWERS,
						Model.getSingleton().TIME_SHAPE_THIRD_LEVEL_ATTEMPTS);

			} else if (playLevel.contentEquals("4")) {
				answerCheck(position,
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
						Model.getSingleton().TIME_SHAPE_FOURTH_LEVEL_ANSWERS,
						Model.getSingleton().TIME_SHAPE_FOURTH_LEVEL_ATTEMPTS);

			} else if (playLevel.contentEquals("5")) {
				answerCheck(position,
						Model.getSingleton().TIME_SHAPE_COMPLETED_LEVEL,
						Model.getSingleton().TIME_SHAPE_FIFTH_LEVEL_ANSWERS,
						Model.getSingleton().TIME_SHAPE_FIFTH_LEVEL_ATTEMPTS);

			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void answerCheck(int position, String compltedLevel,
			String answers, String attempts) {
		try {
			if (playLevel.contentEquals("1")) {
				firstLevelAnswerCheck(position, compltedLevel, answers,
						attempts);
			} else if (playLevel.contentEquals("2")) {
				secondLevelAnswerCheck(position, compltedLevel, answers,
						attempts);
			} else if (playLevel.contentEquals("3")) {
				thirdLevelAnswerCheck(position, compltedLevel, answers,
						attempts);
			} else if (playLevel.contentEquals("4")) {
				fourthLevelAnswerCheck(position, compltedLevel, answers,
						attempts);
			} else if (playLevel.contentEquals("5")) {
				fifthLevelAnswerCheck(position, compltedLevel, answers,
						attempts);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void firstLevelAnswerCheck(int position, String completedLevel,
			String answers, String attempts) {
		try {
			handler.removeCallbacks(updateTextRunnable);

			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(0)
					.getImageId()) {
				answerCount++;
				answer = Integer.parseInt(mSharedPreferences.getString(answers,
						"0"));
				answer += 1;

				if (answer == 10) {
					saveSharePreference(completedLevel, String.valueOf("2"));
					toast(getResources().getString(R.string.firstlevelcomplted));
				}
				saveSharePreference(answers, String.valueOf(answer));
				if (answer == 10) {
					finishLevel(true, playLevel, answerCount, attempts);// true->Level,false-Image
				} else {
					finishLevel(false, playLevel, answerCount, attempts);// true->Level,false-Image
				}
			} else {
				mGridViewSelection.setOnItemClickListener(onItemClickListener);
				findViewById(R.id.my_view_lineartopheader).setVisibility(
						View.GONE);

				failAnswerCount = answerCount;
				failPlayLevel = playLevel;
				failAttempts = attempts;

				for (int i = 0; i < mThumbIds.length; i++) {
					if (mThumbIds[i] == mSerializedColorSelectionStorages
							.get(0).getImageId()) {
						roundedImageView = (RoundedImageView) mGridViewSelection
								.getChildAt(position);
						roundedImageView.setBorderWidth(2);
						roundedImageView.requestLayout();

						roundedImageView = (RoundedImageView) mGridViewSelection
								.getChildAt(i);
						roundedImageView.setBorderWidth(10);
						roundedImageView.setBorderColor(getResources()
								.getColor(android.R.color.black));
						roundedImageView.requestLayout();

					}
				}
				mButtonreplay.setVisibility(View.VISIBLE);
				showCrouton(getResources().getString(R.string.wronganswer),
						Style.ALERT, Configuration.INFINITE);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void secondLevelAnswerCheck(int position, String completedLevel,
			String answers, String attempts) {
		try {
			clickCount++;
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(0)
					.getImageId()) {
				answerCount++;
			} else {
			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(1)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mArrayList.size() > 0) {
				if (mArrayList.contains(mThumbIds[position])) {
					--clickCount;
				} else {
					mArrayList.add(mThumbIds[position]);
				}
			} else {
				mArrayList.add(mThumbIds[position]);
			}
			if (clickCount >= 2) {
				handler.removeCallbacks(updateTextRunnable);

				if (answerCount == 2) {
					answer = Integer.parseInt(mSharedPreferences.getString(
							answers, "0"));
					answer += 1;

					if (answer == 10) {
						saveSharePreference(completedLevel, String.valueOf("3"));

						toast(getResources().getString(
								R.string.secondlevelcomplted));
					}
					saveSharePreference(answers, String.valueOf(answer));
					if (answer == 10) {
						finishLevel(true, playLevel, answerCount, attempts);// true->Level,false-Image
					} else {
						finishLevel(false, playLevel, answerCount, attempts);// true->Level,false-Image
					}
				} else {
					mGridViewSelection
							.setOnItemClickListener(onItemClickListener);
					findViewById(R.id.my_view_lineartopheader).setVisibility(
							View.GONE);

					failAnswerCount = answerCount;
					failPlayLevel = playLevel;
					failAttempts = attempts;
					for (int i = 0; i < mThumbIds.length; i++) {
						if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(0).getImageId()) {

							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);

							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));

							roundedImageView.requestLayout();
						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(1).getImageId()) {

							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));

							roundedImageView.requestLayout();

						} else {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(2);
							roundedImageView.requestLayout();

						}
					}
					mButtonreplay.setVisibility(View.VISIBLE);
					showCrouton(getResources().getString(R.string.wronganswer),
							Style.ALERT, Configuration.INFINITE);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void thirdLevelAnswerCheck(int position, String completedLevel,
			String answers, String attempts) {
		try {
			clickCount++;
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(0)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(1)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(2)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mArrayList.size() > 0) {
				if (mArrayList.contains(mThumbIds[position])) {
					--clickCount;
				} else {
					mArrayList.add(mThumbIds[position]);
				}
			} else {
				mArrayList.add(mThumbIds[position]);
			}
			if (clickCount >= 3) {
				handler.removeCallbacks(updateTextRunnable);

				if (answerCount == 3) {
					answer = Integer.parseInt(mSharedPreferences.getString(
							answers, "0"));
					answer += 1;

					if (answer == 10) {
						saveSharePreference(completedLevel, String.valueOf("4"));

						toast(getResources().getString(
								R.string.thirdlevelcomplted));
					}
					saveSharePreference(answers, String.valueOf(answer));
					if (answer == 10) {
						finishLevel(true, playLevel, answerCount, attempts);// true->Level,false-Image
					} else {
						finishLevel(false, playLevel, answerCount, attempts);// true->Level,false-Image
					}
				} else {
					mGridViewSelection
							.setOnItemClickListener(onItemClickListener);
					findViewById(R.id.my_view_lineartopheader).setVisibility(
							View.GONE);

					failAnswerCount = answerCount;
					failPlayLevel = playLevel;
					failAttempts = attempts;
					for (int i = 0; i < mThumbIds.length; i++) {
						if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(0).getImageId()) {

							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(1).getImageId()) {

							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();
						}

						else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(2).getImageId()) {

							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();
						}

						else {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(2);

							roundedImageView.requestLayout();
						}
					}
					mButtonreplay.setVisibility(View.VISIBLE);
					showCrouton(getResources().getString(R.string.wronganswer),
							Style.ALERT, Configuration.INFINITE);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void fourthLevelAnswerCheck(int position, String completedLevel,
			String answers, String attempts) {
		try {
			clickCount++;
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(0)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(1)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(2)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(3)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mArrayList.size() > 0) {
				if (mArrayList.contains(mThumbIds[position])) {
					--clickCount;
				} else {
					mArrayList.add(mThumbIds[position]);
				}
			} else {
				mArrayList.add(mThumbIds[position]);
			}
			if (clickCount >= 4) {
				handler.removeCallbacks(updateTextRunnable);

				if (answerCount == 4) {
					answer = Integer.parseInt(mSharedPreferences.getString(
							answers, "0"));
					answer += 1;

					if (answer == 10) {
						saveSharePreference(completedLevel, String.valueOf("5"));

						toast(getResources().getString(
								R.string.fourthlevelcomplted));
					}
					saveSharePreference(answers, String.valueOf(answer));
					if (answer == 10) {
						finishLevel(true, playLevel, answerCount, attempts);// true->Level,false-Image
					} else {
						finishLevel(false, playLevel, answerCount, attempts);// true->Level,false-Image
					}
				} else {
					mGridViewSelection
							.setOnItemClickListener(onItemClickListener);
					findViewById(R.id.my_view_lineartopheader).setVisibility(
							View.GONE);

					failAnswerCount = answerCount;
					failPlayLevel = playLevel;
					failAttempts = attempts;
					for (int i = 0; i < mThumbIds.length; i++) {
						if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(0).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(1).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(2).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(3).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(2);
							roundedImageView.requestLayout();
						}
					}
					mButtonreplay.setVisibility(View.VISIBLE);
					showCrouton(getResources().getString(R.string.wronganswer),
							Style.ALERT, Configuration.INFINITE);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void fifthLevelAnswerCheck(int position, String completedLevel,
			String answers, String attempts) {
		try {
			clickCount++;
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(0)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(1)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(2)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(3)
					.getImageId()) {
				answerCount++;
			} else {

			}
			if (mThumbIds[position] == mSerializedColorSelectionStorages.get(4)
					.getImageId()) {
				answerCount++;
			} else {

			}

			if (mArrayList.size() > 0) {
				if (mArrayList.contains(mThumbIds[position])) {
					--clickCount;
				} else {
					mArrayList.add(mThumbIds[position]);
				}
			} else {
				mArrayList.add(mThumbIds[position]);
			}
			if (clickCount >= 5) {
				handler.removeCallbacks(updateTextRunnable);
				if (answerCount == 5) {
					answer = Integer.parseInt(mSharedPreferences.getString(
							answers, "0"));
					answer += 1;

					if (answer == 10) {
						saveSharePreference(completedLevel, String.valueOf("5"));

						toast(getResources().getString(
								R.string.fifthlevelcomplted));
					}
					saveSharePreference(answers, String.valueOf(answer));
					if (answer == 10) {
						finishLevel(true, playLevel, answerCount, attempts);// true->Level,false-Image
					} else {
						finishLevel(false, playLevel, answerCount, attempts);// true->Level,false-Image
					}
				} else {
					mGridViewSelection
							.setOnItemClickListener(onItemClickListener);
					findViewById(R.id.my_view_lineartopheader).setVisibility(
							View.GONE);

					failAnswerCount = answerCount;
					failPlayLevel = playLevel;
					failAttempts = attempts;

					for (int i = 0; i < mThumbIds.length; i++) {
						if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(0).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(1).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();
						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(2).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();
						}

						else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(3).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else if (mThumbIds[i] == mSerializedColorSelectionStorages
								.get(4).getImageId()) {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(10);
							roundedImageView.setBorderColor(getResources()
									.getColor(android.R.color.black));
							roundedImageView.requestLayout();

						} else {
							roundedImageView = (RoundedImageView) mGridViewSelection
									.getChildAt(i);
							roundedImageView.setBorderWidth(2);
							roundedImageView.requestLayout();

						}
					}
					mButtonreplay.setVisibility(View.VISIBLE);
					showCrouton(getResources().getString(R.string.wronganswer),
							Style.ALERT, Configuration.INFINITE);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void saveSharePreference(String levelanswer, String answercount) {
		try {
			mEditor.putString(levelanswer, answercount);
			mEditor.commit();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	private void toast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}
	};

	private void finishLevel(boolean levelOrImage, String level,
			int answerCount, String attempts) // true-Level,false-Image
	{
		try {
			attempt = Integer.parseInt(mSharedPreferences.getString(attempts,
					"0"));
			attempt += 1;
			saveSharePreference(attempts, String.valueOf(attempt));

			totalattempt = Integer.parseInt(mSharedPreferences.getString(
					Model.getSingleton().TIME_MODE_TOTAL_ATTEMPTS, "0"));
			totalattempt += 1;
			saveSharePreference(Model.getSingleton().TIME_MODE_TOTAL_ATTEMPTS,
					String.valueOf(totalattempt));

			averageTime = Integer.parseInt(mSharedPreferences.getString(
					Model.getSingleton().TIME_MODE_TOTAL_AVERAGE_TIME, "0"));
			averageTime += timeUpdate;
			saveSharePreference(
					Model.getSingleton().TIME_MODE_TOTAL_AVERAGE_TIME,
					String.valueOf(averageTime));

			pointsWon = Integer.parseInt(mSharedPreferences.getString(
					Model.getSingleton().POINTS_WON, "0"));
			if (level.contentEquals("1")) {
				if (answerCount == 1) {
					pointsWon += Model.getSingleton().POINTS_FIRST_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					timeModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().TIME_MODE_POINTS_WON,
									"0"));
					timeModePointsWon += Model.getSingleton().POINTS_FIRST_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_POINTS_WON,
							String.valueOf(timeModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

					correctAnswerAverageTime = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
									"0"));
					correctAnswerAverageTime += timeUpdate;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
							String.valueOf(correctAnswerAverageTime));
				}
			} else if (level.contentEquals("2")) {
				if (answerCount == 2) {
					pointsWon += Model.getSingleton().POINTS_SECOND_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					timeModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().TIME_MODE_POINTS_WON,
									"0"));
					timeModePointsWon += Model.getSingleton().POINTS_SECOND_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_POINTS_WON,
							String.valueOf(timeModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

					correctAnswerAverageTime = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
									"0"));
					correctAnswerAverageTime += timeUpdate;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
							String.valueOf(correctAnswerAverageTime));
				}
			}

			else if (level.contentEquals("3")) {
				if (answerCount == 3) {
					pointsWon += Model.getSingleton().POINTS_THIRD_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					timeModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().TIME_MODE_POINTS_WON,
									"0"));
					timeModePointsWon += Model.getSingleton().POINTS_THIRD_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_POINTS_WON,
							String.valueOf(timeModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

					correctAnswerAverageTime = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
									"0"));
					correctAnswerAverageTime += timeUpdate;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
							String.valueOf(correctAnswerAverageTime));
				}
			}

			else if (level.contentEquals("4")) {
				if (answerCount == 4) {
					pointsWon += Model.getSingleton().POINTS_FOURTH_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					timeModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().TIME_MODE_POINTS_WON,
									"0"));
					timeModePointsWon += Model.getSingleton().POINTS_FOURTH_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_POINTS_WON,
							String.valueOf(timeModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

					correctAnswerAverageTime = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
									"0"));
					correctAnswerAverageTime += timeUpdate;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
							String.valueOf(correctAnswerAverageTime));
				}
			}

			else if (level.contentEquals("5")) {
				if (answerCount == 5) {
					pointsWon += Model.getSingleton().POINTS_FIFTH_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					timeModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().TIME_MODE_POINTS_WON,
									"0"));
					timeModePointsWon += Model.getSingleton().POINTS_FIFTH_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_POINTS_WON,
							String.valueOf(timeModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

					correctAnswerAverageTime = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
									"0"));
					correctAnswerAverageTime += timeUpdate;
					saveSharePreference(
							Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
							String.valueOf(correctAnswerAverageTime));
				}
			}

			Intent intent;
			if (levelOrImage) {
				intent = new Intent(TimeSelectionActivity.this,
						TimeLevelSelectionActivity.class);
			} else {
				intent = new Intent(TimeSelectionActivity.this,
						TimeSearchActivity.class);
			}
			startActivity(intent);
			finish();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Crouton.clearCroutonsForActivity(this);
	}

	private void showCrouton(String croutonText, Style croutonStyle,
			Configuration configuration) {
		try {
			final boolean infinite = INFINITE == croutonStyle;

			final Crouton crouton;
			findViewById(R.id.alternate_view_group).setVisibility(View.VISIBLE);
			crouton = Crouton.makeText(TimeSelectionActivity.this, croutonText,
					croutonStyle, R.id.alternate_view_group);
			// if (!infinite) {
			// infiniteCrouton = crouton;
			// }
			crouton.setOnClickListener(this)
					.setConfiguration(
							infinite ? CONFIGURATION_INFINITE : configuration)
					.show();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.my_view_btnreplay:
			// infiniteCrouton = null;
			finishLevel(false, failPlayLevel, failAnswerCount, failAttempts);
			break;

		default:
			// if (infiniteCrouton != null) {
			// // Crouton.hide(infiniteCrouton);
			// infiniteCrouton = null;
			//
			// finishLevel(false, failPlayLevel, failAnswerCount, failAttempts);
			// }
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
			Intent helpIntent = new Intent(TimeSelectionActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(TimeSelectionActivity.this,
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
