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
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.lizard.eyes.R;
import com.lizard.eye.circularimageview.RoundedImageView;
import com.lizard.eye.model.ColorGeneration;
import com.lizard.eye.model.Model;
import com.lizard.eye.model.SerializedColorSearchStorage;
import com.lizard.eye.ui.abstractactivity.AbstractMainActivity;

public class WorldModeSearchActivity extends AbstractMainActivity implements
		OnClickListener {

	private String selectedItemId, selectedItemName, selectedItemLength;
	private Button mButtonGo;
	private int[] IMAGE_IDS;
	private TextView mTextViewLevel, mTextViewPoints, mTextViewcount;
	private LinearLayout mLinearLayouttimeMode;
	private int[] searchID;
	private int[] mColorCode;
	private ArrayList<SerializedColorSearchStorage> mArrayList;
	private String playLevel;
	private int[] randomGeneratedID;
	private RoundedImageView mRoundedImageView1, mRoundedImageView2,
			mRoundedImageView3, mRoundedImageView4, mRoundedImageView5;
	private String TAG = WorldModeSearchActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.image_search);
			// ((LizardEyeApplication) getApplication())
			// .getTracker(LizardEyeApplication.TrackerName.APP_TRACKER);
			mLinearLayouttimeMode = (LinearLayout) findViewById(R.id.my_view_lineartimemode);
			mLinearLayouttimeMode.setVisibility(View.GONE);

			mTextViewLevel = (TextView) findViewById(R.id.my_view_textLevel);
			mTextViewPoints = (TextView) findViewById(R.id.my_view_textpoints);
			mTextViewcount = (TextView) findViewById(R.id.my_view_textcount);
			mTextViewPoints.setText(String.valueOf(mSharedPreferences
					.getString(Model.getSingleton().POINTS_WON, "0")));
			mButtonGo = (Button) findViewById(R.id.my_view_btngo);
			mButtonGo.setOnClickListener(this);
			mRoundedImageView1 = (RoundedImageView) findViewById(R.id.my_view_imgsearch1);
			mRoundedImageView2 = (RoundedImageView) findViewById(R.id.my_view_imgsearch2);
			mRoundedImageView3 = (RoundedImageView) findViewById(R.id.my_view_imgsearch3);
			mRoundedImageView4 = (RoundedImageView) findViewById(R.id.my_view_imgsearch4);
			mRoundedImageView5 = (RoundedImageView) findViewById(R.id.my_view_imgsearch5);

			selectedItemId = mSharedPreferences.getString(
					Model.getSingleton().SelectedItemId, "");
			selectedItemName = mSharedPreferences.getString(
					Model.getSingleton().SelectedItemName, "");
			selectedItemLength = mSharedPreferences.getString(
					Model.getSingleton().SelectedItemLength, "");

			playLevel = mSharedPreferences.getString(
					Model.getSingleton().PLAY_LEVEL, "1");
			if (selectedItemId.contentEquals("1"))// Aztech world
			{
				IMAGE_IDS = new int[] { R.drawable.ic_aztech1,
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
			} else if (selectedItemId.contains("2"))// Ball World
			{
				IMAGE_IDS = new int[] { R.drawable.ic_ball1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_batman1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_cross1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_mexicanornament1,
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
						R.drawable.ic_mexicanornament20};
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
				IMAGE_IDS = new int[] { R.drawable.ic_currency1,
						R.drawable.ic_currency2, R.drawable.ic_currency3,
						R.drawable.ic_currency4, R.drawable.ic_currency5,
						R.drawable.ic_currency6, R.drawable.ic_currency7,
						R.drawable.ic_currency8, R.drawable.ic_currency9,
						R.drawable.ic_currency10, R.drawable.ic_currency11,
						R.drawable.ic_currency12, R.drawable.ic_currency13,
						R.drawable.ic_currency14, R.drawable.ic_currency15,
						R.drawable.ic_currency16, R.drawable.ic_currency17,
						R.drawable.ic_currency18, R.drawable.ic_currency19,
						R.drawable.ic_currency20};
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
				IMAGE_IDS = new int[] { R.drawable.ic_mythological1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_robo1,
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
			}

			else if (selectedItemId.contains("9"))// Sports World
			{
				IMAGE_IDS = new int[] { R.drawable.ic_riosport1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_creature1,
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
				IMAGE_IDS = new int[] { R.drawable.ic_tree1,
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
//				IMAGE_IDS = new int[] { R.drawable.ic_chinesezodiac1,
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
//			}

			mTextViewLevel.setText(playLevel);
			randomGeneratedID = ColorGeneration.randomList(1,
					Integer.parseInt(selectedItemLength),
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

			int[] gen = ColorGeneration.randomList(1,
					Integer.parseInt(selectedItemLength),
					Integer.parseInt(playLevel));

			if (playLevel.contentEquals("1")) {
				int color1 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[0]);
				mColorCode = new int[] { color1 };
			} else if (playLevel.contentEquals("2")) {
				int color1 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[1]);
				mColorCode = new int[] { color1, color2 };

			} else if (playLevel.contentEquals("3")) {
				int color1 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[2]);
				mColorCode = new int[] { color1, color2, color3 };

			} else if (playLevel.contentEquals("4")) {
				int color1 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[2]);
				int color4 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[3]);
				mColorCode = new int[] { color1, color2, color3, color4 };

			} else if (playLevel.contentEquals("5")) {
				int color1 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[0]);
				int color2 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[1]);
				int color3 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[2]);
				int color4 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[3]);
				int color5 = ColorGeneration.getRandomColorCode(
						WorldModeSearchActivity.this, gen[4]);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_view_btngo:
			if (selectedItemId.contentEquals("1")) {
				mEditor.putString(Model.getSingleton().AZTECH_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("2")) {
				mEditor.putString(Model.getSingleton().BALL_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("3")) {
				mEditor.putString(Model.getSingleton().BRUCE_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("4")) {
				mEditor.putString(Model.getSingleton().CHRIST_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("5")) {
				mEditor.putString(Model.getSingleton().MEXICO_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("6")) {
				mEditor.putString(Model.getSingleton().MONEY_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("7")) {
				mEditor.putString(Model.getSingleton().MYTH_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("8")) {
				mEditor.putString(Model.getSingleton().ROBO_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("9")) {
				mEditor.putString(Model.getSingleton().SPORTS_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("10")) {
				mEditor.putString(Model.getSingleton().STICK_COMPLETED_LEVEL,
						playLevel);
			} else if (selectedItemId.contentEquals("11")) {
				mEditor.putString(Model.getSingleton().TREE_COMPLETED_LEVEL,
						playLevel);
			} 
			
//			else if (selectedItemId.contentEquals("12")) {
//				mEditor.putString(Model.getSingleton().ZODIAC_COMPLETED_LEVEL,
//						playLevel);
//			}
			mEditor.putString(Model.getSingleton().SelectedItemId,
					selectedItemId);
			mEditor.putString(Model.getSingleton().SelectedItemName,
					selectedItemName);
			mEditor.putString(Model.getSingleton().SelectedItemLength,
					selectedItemLength);

			mEditor.commit();
			Intent intent = new Intent(WorldModeSearchActivity.this,
					WorldModeSelectionActivity.class);
			intent.putParcelableArrayListExtra(
					Model.getSingleton().COLOR_SEARCH_LIST, mArrayList);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
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
			Intent helpIntent = new Intent(WorldModeSearchActivity.this,
					HelpActivity.class);
			startActivity(helpIntent);
			break;

		case R.id.my_view_menupoint:
			Intent settingsIntent = new Intent(WorldModeSearchActivity.this,
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