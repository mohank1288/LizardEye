package com.lizard.eye.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lizard.eye.LizardEyeApplication;
import com.lizard.eyes.R;
import com.lizard.eye.model.Model;

public class WorldModeFragment extends Fragment {

	private TextView mTextViewAttempts, mTextViewAnswers, mTextViewAnswerper,mTextViewTotalPoints,mTextViewWorldPoints;
	private String attempts, answers,totalPoints,worldModePoints;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.points_world, container,
				false);
		mTextViewAttempts = (TextView) rootview
				.findViewById(R.id.my_view_textattempts);
		mTextViewAnswers = (TextView) rootview
				.findViewById(R.id.my_view_textanswers);
		mTextViewAnswerper = (TextView) rootview
				.findViewById(R.id.my_view_textansper);
		mTextViewTotalPoints = (TextView) rootview
				.findViewById(R.id.my_view_texttotalpoint);
		mTextViewWorldPoints = (TextView) rootview
				.findViewById(R.id.my_view_textworldmodepoints);
		
		
		totalPoints =  LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(
						Model.getSingleton().POINTS_WON,
						"0");
		
		worldModePoints =  LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(
						Model.getSingleton().WORLD_MODE_POINTS_WON,
						"0");		
		attempts = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().WORLD_MODE_TOTAL_ATTEMPTS, "0");
		answers = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().WORLD_MODE_TOTAL_ANSWERS, "0");
		mTextViewAttempts.setText(attempts);
		mTextViewAnswers.setText(answers);
		if (Float.parseFloat(attempts) > 0 || Float.parseFloat(answers) > 0) {
			float percentage = (Float.parseFloat(answers) / Float
					.parseFloat(attempts)) * 100;
			mTextViewAnswerper.setText(String.valueOf(Math.round(percentage)));
		} else {
			mTextViewAnswerper.setText("0");
		}
		
		mTextViewTotalPoints.setText(totalPoints);
		mTextViewWorldPoints.setText(worldModePoints);
		
		return rootview;
	}

}
