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

public class TimeModeFragment extends Fragment {

	private TextView mTextViewAttempts, mTextViewAnswers, mTextViewAnswerper,
			mTextViewTotalAvgTime, mTextViewTotalCorAnsTime,mTextViewTotalPoints,mTextViewTimePoints;
	private String attempts, answers, totalAvgTime, totalCorrectAvgTime,totalPoints,timeModePoints;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview = inflater
				.inflate(R.layout.points_time, container, false);
		mTextViewAttempts = (TextView) rootview
				.findViewById(R.id.my_view_textattempts);
		mTextViewAnswers = (TextView) rootview
				.findViewById(R.id.my_view_textanswers);
		mTextViewAnswerper = (TextView) rootview
				.findViewById(R.id.my_view_textansper);
		mTextViewTotalAvgTime = (TextView) rootview
				.findViewById(R.id.my_view_texttotalavgtime);
		mTextViewTotalCorAnsTime = (TextView) rootview
				.findViewById(R.id.my_view_texttotalavganstime);
		mTextViewTotalPoints = (TextView) rootview
				.findViewById(R.id.my_view_texttotalpoint);
		mTextViewTimePoints = (TextView) rootview
				.findViewById(R.id.my_view_texttimemodepoints);
		
		attempts = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().TIME_MODE_TOTAL_ATTEMPTS, "0");
		answers = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().TIME_MODE_TOTAL_ANSWERS, "0");
		totalAvgTime = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().TIME_MODE_TOTAL_AVERAGE_TIME,
						"0");

		totalCorrectAvgTime = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(
						Model.getSingleton().TIME_MODE_CORRECT_ANSWER_AVERAGE_TIME,
						"0");
		
		
		totalPoints =  LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(
						Model.getSingleton().POINTS_WON,
						"0");
		
		timeModePoints =  LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(
						Model.getSingleton().TIME_MODE_POINTS_WON,
						"0");
		mTextViewAttempts.setText(attempts);
		mTextViewAnswers.setText(answers);
		if (Float.parseFloat(attempts) > 0 || Float.parseFloat(answers) > 0) {
			float percentage = (Float.parseFloat(answers) / Float
					.parseFloat(attempts)) * 100;
			mTextViewAnswerper.setText(String.valueOf(Math.round(percentage)));
			
			

			float avgtime = (Float.parseFloat(totalAvgTime) / Float
					.parseFloat(attempts));

			mTextViewTotalAvgTime.setText(String.valueOf(Math.round(avgtime)));
			
			float correctAnswerAvgTime = (Float.parseFloat(totalCorrectAvgTime) / Float
					.parseFloat(answers));
			
			mTextViewTotalCorAnsTime.setText(String.valueOf(Math.round(correctAnswerAvgTime)));


		} else {
			mTextViewAnswerper.setText("0");
			mTextViewTotalAvgTime.setText("0");
			mTextViewTotalCorAnsTime.setText("0");

		}
		
		mTextViewTotalPoints.setText(totalPoints);
		mTextViewTimePoints.setText(timeModePoints);
		
		return rootview;
	}
}
