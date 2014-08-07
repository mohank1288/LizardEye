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
import android.widget.LinearLayout;
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

public class WorldModeSelectionActivity extends AbstractMainActivity implements
		OnItemClickListener, OnClickListener {

	private String selectedItemId;
	private GridView mGridViewSelection;
	private GridSelectionAdapter gridSelectionAdapter;
	private Integer[] mThumbIds;
	private ArrayList<SerializedColorSearchStorage> mSerializedColorSelectionStorages;

	private ArrayList<ColorSearchStorage> mColorSelectionStoragesList;
	private int clickCount = 0;
	private int answerCount = 0, failAnswerCount = 0;
	private ColorSearchStorage mColorSelectionStorage;
	private LinearLayout mLinearLayouttimeMode;
	private TextView mTextViewLevel;
	private TextView mTextViewPoints;
	private TextView mTextViewcount;
	private String playLevel, failPlayLevel, failAttempts;
	private int answer = 0, attempt = 0, pointsWon;
	// private Crouton infiniteCrouton;
	private Style INFINITE = new Style.Builder().setBackgroundColorValue(
			Style.holoBlueLight).build();
	private Configuration CONFIGURATION_INFINITE = new Configuration.Builder()
			.setDuration(Configuration.DURATION_INFINITE).build();
	private Button mButtonreplay;
	private RoundedImageView roundedImageView;
	private int totalattempt, worldModePointsWon, totalAnswer;
	private String TAG = WorldModeSelectionActivity.class.getName();
	private ArrayList<Integer> mArrayList = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.image_selection);
//			((LizardEyeApplication) getApplication())
//					.getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mButtonreplay = (Button) findViewById(R.id.my_view_btnreplay);
			mButtonreplay.setOnClickListener(this);
			mLinearLayouttimeMode = (LinearLayout) findViewById(R.id.my_view_lineartimemode);

			mLinearLayouttimeMode.setVisibility(View.GONE);

			mTextViewLevel = (TextView) findViewById(R.id.my_view_textLevel);
			mTextViewPoints = (TextView) findViewById(R.id.my_view_textpoints);
			mTextViewcount = (TextView) findViewById(R.id.my_view_textcount);
			mTextViewPoints.setText(String.valueOf(mSharedPreferences
					.getString(Model.getSingleton().POINTS_WON, "0")));

			mTextViewLevel.setText(mSharedPreferences.getString(
					Model.getSingleton().PLAY_LEVEL, "1"));

			clickCount = 0;
			answerCount = 0;
			mSerializedColorSelectionStorages = getIntent()
					.getParcelableArrayListExtra(
							Model.getSingleton().COLOR_SEARCH_LIST);

			selectedItemId = mSharedPreferences.getString(
					Model.getSingleton().SelectedItemId, "");

			mGridViewSelection = (GridView) findViewById(R.id.my_view_gridview);

			playLevel = mSharedPreferences.getString(
					Model.getSingleton().PLAY_LEVEL, "1");

			if (selectedItemId.contentEquals("1"))// Aztech world
			{
				mThumbIds = new Integer[] { R.drawable.ic_aztech1,
						R.drawable.ic_aztech2, R.drawable.ic_aztech3,
						R.drawable.ic_aztech4, R.drawable.ic_aztech5,
						R.drawable.ic_aztech6, R.drawable.ic_aztech7,
						R.drawable.ic_aztech8, R.drawable.ic_aztech9,
						R.drawable.ic_aztech10, R.drawable.ic_aztech11,
						R.drawable.ic_aztech12, R.drawable.ic_aztech13,
						R.drawable.ic_aztech14, R.drawable.ic_aztech15,
						R.drawable.ic_aztech16, R.drawable.ic_aztech17,
						R.drawable.ic_aztech18, R.drawable.ic_aztech19,
						R.drawable.ic_aztech20 };

				if (playLevel.contentEquals("1")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().AZTECH_FIRST_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);

				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().AZTECH_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().AZTECH_THIRD_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().AZTECH_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().AZTECH_FIFTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			}

			else if (selectedItemId.contentEquals("2"))// Ball world
			{
				mThumbIds = new Integer[] { R.drawable.ic_ball1,
						R.drawable.ic_ball2, R.drawable.ic_ball3,
						R.drawable.ic_ball4, R.drawable.ic_ball5,
						R.drawable.ic_ball6, R.drawable.ic_ball7,
						R.drawable.ic_ball8, R.drawable.ic_ball9,
						R.drawable.ic_ball10, R.drawable.ic_ball11,
						R.drawable.ic_ball12, R.drawable.ic_ball13,
						R.drawable.ic_ball14, R.drawable.ic_ball15,
						R.drawable.ic_ball16, R.drawable.ic_ball17,
						R.drawable.ic_ball18, R.drawable.ic_ball19,
						R.drawable.ic_ball20 };

				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BALL_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);

				} else if (playLevel.contentEquals("2")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BALL_SECOND_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BALL_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BALL_FOURTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BALL_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			} else if (selectedItemId.contains("3"))// Bruce World
			{
				mThumbIds = new Integer[] { R.drawable.ic_batman1,
						R.drawable.ic_batman2, R.drawable.ic_batman3,
						R.drawable.ic_batman4, R.drawable.ic_batman5,
						R.drawable.ic_batman6, R.drawable.ic_batman7,
						R.drawable.ic_batman8, R.drawable.ic_batman9,
						R.drawable.ic_batman10, R.drawable.ic_batman11,
						R.drawable.ic_batman12, R.drawable.ic_batman13,
						R.drawable.ic_batman14, R.drawable.ic_batman15,
						R.drawable.ic_batman16, R.drawable.ic_batman17,
						R.drawable.ic_batman18, R.drawable.ic_batman19,
						R.drawable.ic_batman20 };

				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BRUCE_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().BRUCE_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BRUCE_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().BRUCE_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().BRUCE_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}

			} else if (selectedItemId.contains("4"))// Christ world
			{
				mThumbIds = new Integer[] { R.drawable.ic_cross1,
						R.drawable.ic_cross2, R.drawable.ic_cross3,
						R.drawable.ic_cross4, R.drawable.ic_cross5,
						R.drawable.ic_cross6, R.drawable.ic_cross7,
						R.drawable.ic_cross8, R.drawable.ic_cross9,
						R.drawable.ic_cross10, R.drawable.ic_cross11,
						R.drawable.ic_cross12, R.drawable.ic_cross13,
						R.drawable.ic_cross14, R.drawable.ic_cross15,
						R.drawable.ic_cross16, R.drawable.ic_cross17,
						R.drawable.ic_cross18, R.drawable.ic_cross19,
						R.drawable.ic_cross20 };
				if (playLevel.contentEquals("1")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().CHRIST_FIRST_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().CHRIST_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().CHRIST_THIRD_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().CHRIST_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().CHRIST_FIFTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			} else if (selectedItemId.contains("5"))// Mexico World
			{
				mThumbIds = new Integer[] { R.drawable.ic_mexicanornament1,
						R.drawable.ic_mexicanornament2,
						R.drawable.ic_mexicanornament3,
						R.drawable.ic_mexicanornament4,
						R.drawable.ic_mexicanornament5,
						R.drawable.ic_mexicanornament6,
						R.drawable.ic_mexicanornament7,
						R.drawable.ic_mexicanornament8,
						R.drawable.ic_mexicanornament9,
						R.drawable.ic_mexicanornament10,
						R.drawable.ic_mexicanornament11,
						R.drawable.ic_mexicanornament12,
						R.drawable.ic_mexicanornament13,
						R.drawable.ic_mexicanornament14,
						R.drawable.ic_mexicanornament15,
						R.drawable.ic_mexicanornament16,
						R.drawable.ic_mexicanornament17,
						R.drawable.ic_mexicanornament18,
						R.drawable.ic_mexicanornament19,
						R.drawable.ic_mexicanornament20 };
				if (playLevel.contentEquals("1")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MEXICO_FIRST_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MEXICO_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MEXICO_THIRD_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MEXICO_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MEXICO_FIFTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			} else if (selectedItemId.contains("6"))// Money World
			{
				mThumbIds = new Integer[] { R.drawable.ic_currency1,
						R.drawable.ic_currency2, R.drawable.ic_currency3,
						R.drawable.ic_currency4, R.drawable.ic_currency5,
						R.drawable.ic_currency6, R.drawable.ic_currency7,
						R.drawable.ic_currency8, R.drawable.ic_currency9,
						R.drawable.ic_currency10, R.drawable.ic_currency11,
						R.drawable.ic_currency12, R.drawable.ic_currency13,
						R.drawable.ic_currency14, R.drawable.ic_currency15,
						R.drawable.ic_currency16, R.drawable.ic_currency17,
						R.drawable.ic_currency18, R.drawable.ic_currency19,
						R.drawable.ic_currency20 };
				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MONEY_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MONEY_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MONEY_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().MONEY_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MONEY_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			} else if (selectedItemId.contains("7"))// Myth World
			{
				mThumbIds = new Integer[] { R.drawable.ic_mythological1,
						R.drawable.ic_mythological2,
						R.drawable.ic_mythological3,
						R.drawable.ic_mythological4,
						R.drawable.ic_mythological5,
						R.drawable.ic_mythological6,
						R.drawable.ic_mythological7,
						R.drawable.ic_mythological8,
						R.drawable.ic_mythological9,
						R.drawable.ic_mythological10,
						R.drawable.ic_mythological11,
						R.drawable.ic_mythological12,
						R.drawable.ic_mythological13,
						R.drawable.ic_mythological14,
						R.drawable.ic_mythological15,
						R.drawable.ic_mythological16,
						R.drawable.ic_mythological17,
						R.drawable.ic_mythological18,
						R.drawable.ic_mythological19,
						R.drawable.ic_mythological20 };

				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MYTH_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MYTH_SECOND_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MYTH_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MYTH_FOURTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().MYTH_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}

			} else if (selectedItemId.contains("8"))// Robo World
			{
				mThumbIds = new Integer[] { R.drawable.ic_robo1,
						R.drawable.ic_robo2, R.drawable.ic_robo3,
						R.drawable.ic_robo4, R.drawable.ic_robo5,
						R.drawable.ic_robo6, R.drawable.ic_robo7,
						R.drawable.ic_robo8, R.drawable.ic_robo9,
						R.drawable.ic_robo10, R.drawable.ic_robo11,
						R.drawable.ic_robo12, R.drawable.ic_robo13,
						R.drawable.ic_robo14, R.drawable.ic_robo15,
						R.drawable.ic_robo16, R.drawable.ic_robo17,
						R.drawable.ic_robo18, R.drawable.ic_robo19,
						R.drawable.ic_robo20};
				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().ROBO_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().ROBO_SECOND_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().ROBO_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().ROBO_FOURTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().ROBO_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			} else if (selectedItemId.contains("9"))// Sports World
			{
				mThumbIds = new Integer[] { R.drawable.ic_riosport1,
						R.drawable.ic_riosport2, R.drawable.ic_riosport3,
						R.drawable.ic_riosport4, R.drawable.ic_riosport5,
						R.drawable.ic_riosport6, R.drawable.ic_riosport7,
						R.drawable.ic_riosport8, R.drawable.ic_riosport9,
						R.drawable.ic_riosport10, R.drawable.ic_riosport11,
						R.drawable.ic_riosport12, R.drawable.ic_riosport13,
						R.drawable.ic_riosport14, R.drawable.ic_riosport15,
						R.drawable.ic_riosport16, R.drawable.ic_riosport17,
						R.drawable.ic_riosport18, R.drawable.ic_riosport19,
						R.drawable.ic_riosport20};
				if (playLevel.contentEquals("1")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().SPORTS_FIRST_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().SPORTS_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().SPORTS_THIRD_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().SPORTS_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().SPORTS_FIFTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}
			}

			else if (selectedItemId.contains("10"))// Stick World
			{
				mThumbIds = new Integer[] { R.drawable.ic_creature1,
						R.drawable.ic_creature2, R.drawable.ic_creature3,
						R.drawable.ic_creature4, R.drawable.ic_creature5,
						R.drawable.ic_creature6, R.drawable.ic_creature7,
						R.drawable.ic_creature8, R.drawable.ic_creature9,
						R.drawable.ic_creature10, R.drawable.ic_creature11,
						R.drawable.ic_creature12, R.drawable.ic_creature13,
						R.drawable.ic_creature14, R.drawable.ic_creature15,
						R.drawable.ic_creature16, R.drawable.ic_creature17,
						R.drawable.ic_creature18, R.drawable.ic_creature19,
						R.drawable.ic_creature20 };

				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().STICK_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().STICK_SECOND_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().STICK_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(mSharedPreferences.getString(
							Model.getSingleton().STICK_FOURTH_LEVEL_ANSWERS,
							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().STICK_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}

			} else if (selectedItemId.contains("11"))// Tree World
			{
				mThumbIds = new Integer[] { R.drawable.ic_tree1,
						R.drawable.ic_tree2, R.drawable.ic_tree3,
						R.drawable.ic_tree4, R.drawable.ic_tree5,
						R.drawable.ic_tree6, R.drawable.ic_tree7,
						R.drawable.ic_tree8, R.drawable.ic_tree9,
						R.drawable.ic_tree10, R.drawable.ic_tree11,
						R.drawable.ic_tree12, R.drawable.ic_tree13,
						R.drawable.ic_tree14, R.drawable.ic_tree15,
						R.drawable.ic_tree16, R.drawable.ic_tree17,
						R.drawable.ic_tree18, R.drawable.ic_tree19,
						R.drawable.ic_tree20};
				if (playLevel.contentEquals("1")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().TREE_FIRST_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("2")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().TREE_SECOND_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("3")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().TREE_THIRD_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("4")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().TREE_FOURTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				} else if (playLevel.contentEquals("5")) {
					completedAnswers(
							mSharedPreferences.getString(
									Model.getSingleton().TREE_FIFTH_LEVEL_ANSWERS,
									"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
				}

			}
			
//			else if (selectedItemId.contains("12"))// Zodiac World
//			{
//				mThumbIds = new Integer[] { R.drawable.ic_chinesezodiac1,
//						R.drawable.ic_chinesezodiac2,
//						R.drawable.ic_chinesezodiac3,
//						R.drawable.ic_chinesezodiac4,
//						R.drawable.ic_chinesezodiac5,
//						R.drawable.ic_chinesezodiac6,
//						R.drawable.ic_chinesezodiac7,
//						R.drawable.ic_chinesezodiac8,
//						R.drawable.ic_chinesezodiac9,
//						R.drawable.ic_chinesezodiac10,
//						R.drawable.ic_chinesezodiac11,
//						R.drawable.ic_chinesezodiac12 };
//
//				if (playLevel.contentEquals("1")) {
//					completedAnswers(mSharedPreferences.getString(
//							Model.getSingleton().ZODIAC_FIRST_LEVEL_ANSWERS,
//							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
//				} else if (playLevel.contentEquals("2")) {
//					completedAnswers(mSharedPreferences.getString(
//							Model.getSingleton().ZODIAC_SECOND_LEVEL_ANSWERS,
//							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
//				} else if (playLevel.contentEquals("3")) {
//					completedAnswers(mSharedPreferences.getString(
//							Model.getSingleton().ZODIAC_THIRD_LEVEL_ANSWERS,
//							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
//				} else if (playLevel.contentEquals("4")) {
//					completedAnswers(mSharedPreferences.getString(
//							Model.getSingleton().ZODIAC_FOURTH_LEVEL_ANSWERS,
//							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
//				} else if (playLevel.contentEquals("5")) {
//					completedAnswers(mSharedPreferences.getString(
//							Model.getSingleton().ZODIAC_FIFTH_LEVEL_ANSWERS,
//							"0"), Model.getSingleton().EACH_LEVEL_ITEMS);
//				}
//
//			}
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
								WorldModeSelectionActivity.this, gens[i]);
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
								WorldModeSelectionActivity.this, gens[i]);
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
								WorldModeSelectionActivity.this, gens[i]);
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
								WorldModeSelectionActivity.this, gens[i]);
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
								WorldModeSelectionActivity.this, gens[i]);
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

			if (selectedItemId.contentEquals("1")) {// AZTECH WORLD
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().AZTECH_COMPLETED_LEVEL,
							Model.getSingleton().AZTECH_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().AZTECH_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().AZTECH_COMPLETED_LEVEL,
							Model.getSingleton().AZTECH_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().AZTECH_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().AZTECH_COMPLETED_LEVEL,
							Model.getSingleton().AZTECH_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().AZTECH_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().AZTECH_COMPLETED_LEVEL,
							Model.getSingleton().AZTECH_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().AZTECH_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().AZTECH_COMPLETED_LEVEL,
							Model.getSingleton().AZTECH_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().AZTECH_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("2")) {// BALL
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().BALL_COMPLETED_LEVEL,
							Model.getSingleton().BALL_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().BALL_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().BALL_COMPLETED_LEVEL,
							Model.getSingleton().BALL_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().BALL_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().BALL_COMPLETED_LEVEL,
							Model.getSingleton().BALL_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().BALL_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().BALL_COMPLETED_LEVEL,
							Model.getSingleton().BALL_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().BALL_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().BALL_COMPLETED_LEVEL,
							Model.getSingleton().BALL_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().BALL_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("3")) {// BRUCE
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().BRUCE_COMPLETED_LEVEL,
							Model.getSingleton().BRUCE_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().BRUCE_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().BRUCE_COMPLETED_LEVEL,
							Model.getSingleton().BRUCE_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().BRUCE_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().BRUCE_COMPLETED_LEVEL,
							Model.getSingleton().BRUCE_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().BRUCE_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().BRUCE_COMPLETED_LEVEL,
							Model.getSingleton().BRUCE_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().BRUCE_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().BRUCE_COMPLETED_LEVEL,
							Model.getSingleton().BRUCE_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().BRUCE_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("4")) {// CHRIST
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().CHRIST_COMPLETED_LEVEL,
							Model.getSingleton().CHRIST_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().CHRIST_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().CHRIST_COMPLETED_LEVEL,
							Model.getSingleton().CHRIST_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().CHRIST_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().CHRIST_COMPLETED_LEVEL,
							Model.getSingleton().CHRIST_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().CHRIST_THIRD_LEVEL_ATTEMPTS);

				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().CHRIST_COMPLETED_LEVEL,
							Model.getSingleton().CHRIST_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().CHRIST_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().CHRIST_COMPLETED_LEVEL,
							Model.getSingleton().CHRIST_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().CHRIST_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("5")) {// MEXICO
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().MEXICO_COMPLETED_LEVEL,
							Model.getSingleton().MEXICO_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().MEXICO_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().MEXICO_COMPLETED_LEVEL,
							Model.getSingleton().MEXICO_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().MEXICO_SECOND_LEVEL_ATTEMPTS);

				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().MEXICO_COMPLETED_LEVEL,
							Model.getSingleton().MEXICO_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().MEXICO_THIRD_LEVEL_ATTEMPTS);

				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().MEXICO_COMPLETED_LEVEL,
							Model.getSingleton().MEXICO_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().MEXICO_FOURTH_LEVEL_ATTEMPTS);

				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().MEXICO_COMPLETED_LEVEL,
							Model.getSingleton().MEXICO_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().MEXICO_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("6")) {// MONEY
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().MONEY_COMPLETED_LEVEL,
							Model.getSingleton().MONEY_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().MONEY_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().MONEY_COMPLETED_LEVEL,
							Model.getSingleton().MONEY_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().MONEY_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().MONEY_COMPLETED_LEVEL,
							Model.getSingleton().MONEY_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().MONEY_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().MONEY_COMPLETED_LEVEL,
							Model.getSingleton().MONEY_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().MONEY_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().MONEY_COMPLETED_LEVEL,
							Model.getSingleton().MONEY_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().MONEY_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("7")) {// MYTHOLOGICAL
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().MYTH_COMPLETED_LEVEL,
							Model.getSingleton().MYTH_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().MYTH_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().MYTH_COMPLETED_LEVEL,
							Model.getSingleton().MYTH_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().MYTH_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().MYTH_COMPLETED_LEVEL,
							Model.getSingleton().MYTH_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().MYTH_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().MYTH_COMPLETED_LEVEL,
							Model.getSingleton().MYTH_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().MYTH_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().MYTH_COMPLETED_LEVEL,
							Model.getSingleton().MYTH_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().MYTH_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("8")) {// ROBO
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().ROBO_COMPLETED_LEVEL,
							Model.getSingleton().ROBO_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().ROBO_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().ROBO_COMPLETED_LEVEL,
							Model.getSingleton().ROBO_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().ROBO_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().ROBO_COMPLETED_LEVEL,
							Model.getSingleton().ROBO_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().ROBO_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().ROBO_COMPLETED_LEVEL,
							Model.getSingleton().ROBO_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().ROBO_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().ROBO_COMPLETED_LEVEL,
							Model.getSingleton().ROBO_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().ROBO_FIFTH_LEVEL_ATTEMPTS);
				}
				// roboAnswerCheck(position);
			}

			else if (selectedItemId.contentEquals("9")) {// SPORTS
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().SPORTS_COMPLETED_LEVEL,
							Model.getSingleton().SPORTS_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().SPORTS_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().SPORTS_COMPLETED_LEVEL,
							Model.getSingleton().SPORTS_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().SPORTS_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().SPORTS_COMPLETED_LEVEL,
							Model.getSingleton().SPORTS_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().SPORTS_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().SPORTS_COMPLETED_LEVEL,
							Model.getSingleton().SPORTS_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().SPORTS_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().SPORTS_COMPLETED_LEVEL,
							Model.getSingleton().SPORTS_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().SPORTS_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("10")) {// STICK
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().STICK_COMPLETED_LEVEL,
							Model.getSingleton().STICK_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().STICK_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().STICK_COMPLETED_LEVEL,
							Model.getSingleton().STICK_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().STICK_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().STICK_COMPLETED_LEVEL,
							Model.getSingleton().STICK_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().STICK_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().STICK_COMPLETED_LEVEL,
							Model.getSingleton().STICK_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().STICK_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().STICK_COMPLETED_LEVEL,
							Model.getSingleton().STICK_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().STICK_FIFTH_LEVEL_ATTEMPTS);
				}
			}

			else if (selectedItemId.contentEquals("11")) {// TREE
				if (playLevel.contentEquals("1")) {
					answerCheck(position,
							Model.getSingleton().TREE_COMPLETED_LEVEL,
							Model.getSingleton().TREE_FIRST_LEVEL_ANSWERS,
							Model.getSingleton().TREE_FIRST_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("2")) {
					answerCheck(position,
							Model.getSingleton().TREE_COMPLETED_LEVEL,
							Model.getSingleton().TREE_SECOND_LEVEL_ANSWERS,
							Model.getSingleton().TREE_SECOND_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("3")) {
					answerCheck(position,
							Model.getSingleton().TREE_COMPLETED_LEVEL,
							Model.getSingleton().TREE_THIRD_LEVEL_ANSWERS,
							Model.getSingleton().TREE_THIRD_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("4")) {
					answerCheck(position,
							Model.getSingleton().TREE_COMPLETED_LEVEL,
							Model.getSingleton().TREE_FOURTH_LEVEL_ANSWERS,
							Model.getSingleton().TREE_FOURTH_LEVEL_ATTEMPTS);
				} else if (playLevel.contentEquals("5")) {
					answerCheck(position,
							Model.getSingleton().TREE_COMPLETED_LEVEL,
							Model.getSingleton().TREE_FIFTH_LEVEL_ANSWERS,
							Model.getSingleton().TREE_FIFTH_LEVEL_ATTEMPTS);
				}
			}

//			else if (selectedItemId.contentEquals("12")) {// ZODIAC
//				if (playLevel.contentEquals("1")) {
//					answerCheck(position,
//							Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//							Model.getSingleton().ZODIAC_FIRST_LEVEL_ANSWERS,
//							Model.getSingleton().ZODIAC_FIRST_LEVEL_ATTEMPTS);
//				} else if (playLevel.contentEquals("2")) {
//					answerCheck(position,
//							Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//							Model.getSingleton().ZODIAC_SECOND_LEVEL_ANSWERS,
//							Model.getSingleton().ZODIAC_SECOND_LEVEL_ATTEMPTS);
//
//				} else if (playLevel.contentEquals("3")) {
//					answerCheck(position,
//							Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//							Model.getSingleton().ZODIAC_THIRD_LEVEL_ANSWERS,
//							Model.getSingleton().ZODIAC_THIRD_LEVEL_ATTEMPTS);
//
//				} else if (playLevel.contentEquals("4")) {
//					answerCheck(position,
//							Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//							Model.getSingleton().ZODIAC_FOURTH_LEVEL_ANSWERS,
//							Model.getSingleton().ZODIAC_FOURTH_LEVEL_ATTEMPTS);
//
//				} else if (playLevel.contentEquals("5")) {
//					answerCheck(position,
//							Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//							Model.getSingleton().ZODIAC_FIFTH_LEVEL_ANSWERS,
//							Model.getSingleton().ZODIAC_FIFTH_LEVEL_ATTEMPTS);
//
//				}
//			}
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
				findViewById(R.id.my_view_lineartopheader).setVisibility(View.GONE);

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
			
			if(mArrayList.size() > 0)
			{
				if(mArrayList.contains(mThumbIds[position]))
				{
					--clickCount;
				}
				else
				{
					mArrayList.add(mThumbIds[position]);
				}
			}
			else
			{
				mArrayList.add(mThumbIds[position]);
			}
			if (clickCount >= 2) {
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
					findViewById(R.id.my_view_lineartopheader).setVisibility(View.GONE);

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
			
			if(mArrayList.size() > 0)
			{
				if(mArrayList.contains(mThumbIds[position]))
				{
					--clickCount;
				}
				else
				{
					mArrayList.add(mThumbIds[position]);
				}
			}
			else
			{
				mArrayList.add(mThumbIds[position]);
			}
			
			
			if (clickCount >= 3) {
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
					findViewById(R.id.my_view_lineartopheader).setVisibility(View.GONE);

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
			
			if(mArrayList.size() > 0)
			{
				if(mArrayList.contains(mThumbIds[position]))
				{
					--clickCount;
				}
				else
				{
					mArrayList.add(mThumbIds[position]);
				}
			}
			else
			{
				mArrayList.add(mThumbIds[position]);
			}
			
			if (clickCount >= 4) {
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
					findViewById(R.id.my_view_lineartopheader).setVisibility(View.GONE);

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

						else if (mThumbIds[i] == mSerializedColorSelectionStorages
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
			
			if(mArrayList.size() > 0)
			{
				if(mArrayList.contains(mThumbIds[position]))
				{
					--clickCount;
				}
				else
				{
					mArrayList.add(mThumbIds[position]);
				}
			}
			else
			{
				mArrayList.add(mThumbIds[position]);
			}
			
			if (clickCount >= 5) {
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
					findViewById(R.id.my_view_lineartopheader).setVisibility(View.GONE);

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
					Model.getSingleton().WORLD_MODE_TOTAL_ATTEMPTS, "0"));
			totalattempt += 1;
			saveSharePreference(Model.getSingleton().WORLD_MODE_TOTAL_ATTEMPTS,
					String.valueOf(totalattempt));

			pointsWon = Integer.parseInt(mSharedPreferences.getString(
					Model.getSingleton().POINTS_WON, "0"));
			if (level.contentEquals("1")) {
				if (answerCount == 1) {
					pointsWon += Model.getSingleton().POINTS_FIRST_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));
					worldModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().WORLD_MODE_POINTS_WON,
									"0"));
					worldModePointsWon += Model.getSingleton().POINTS_FIRST_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_POINTS_WON,
							String.valueOf(worldModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

				}
			} else if (level.contentEquals("2")) {
				if (answerCount == 2) {
					pointsWon += Model.getSingleton().POINTS_SECOND_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					worldModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().WORLD_MODE_POINTS_WON,
									"0"));
					worldModePointsWon += Model.getSingleton().POINTS_SECOND_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_POINTS_WON,
							String.valueOf(worldModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));

				}
			}

			else if (level.contentEquals("3")) {
				if (answerCount == 3) {
					pointsWon += Model.getSingleton().POINTS_THIRD_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					worldModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().WORLD_MODE_POINTS_WON,
									"0"));
					worldModePointsWon += Model.getSingleton().POINTS_THIRD_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_POINTS_WON,
							String.valueOf(worldModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));
				}
			}

			else if (level.contentEquals("4")) {
				if (answerCount == 4) {
					pointsWon += Model.getSingleton().POINTS_FOURTH_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					worldModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().WORLD_MODE_POINTS_WON,
									"0"));
					worldModePointsWon += Model.getSingleton().POINTS_FOURTH_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_POINTS_WON,
							String.valueOf(worldModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));
				}
			}

			else if (level.contentEquals("5")) {
				if (answerCount == 5) {
					pointsWon += Model.getSingleton().POINTS_FIFTH_LEVEL_ANSWER;
					saveSharePreference(Model.getSingleton().POINTS_WON,
							String.valueOf(pointsWon));

					worldModePointsWon = Integer.parseInt(mSharedPreferences
							.getString(
									Model.getSingleton().WORLD_MODE_POINTS_WON,
									"0"));
					worldModePointsWon += Model.getSingleton().POINTS_FIFTH_LEVEL_ANSWER;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_POINTS_WON,
							String.valueOf(worldModePointsWon));

					totalAnswer = Integer
							.parseInt(mSharedPreferences.getString(
									Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
									"0"));
					totalAnswer += 1;
					saveSharePreference(
							Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS,
							String.valueOf(totalAnswer));
				}
			}

			Intent intent;
			if (levelOrImage) {
				intent = new Intent(WorldModeSelectionActivity.this,
						LevelSelectionActivity.class);
			} else {
				intent = new Intent(WorldModeSelectionActivity.this,
						WorldModeSearchActivity.class);
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
			crouton = Crouton.makeText(WorldModeSelectionActivity.this,
					croutonText, croutonStyle, R.id.alternate_view_group);
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
			Intent helpIntent = new Intent(WorldModeSelectionActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(WorldModeSelectionActivity.this,
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
