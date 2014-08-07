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

public class RelaxModeragment extends Fragment {

	private TextView mTextViewAttempts, mTextViewAnswers, mTextViewAnswerper;
	private String attempts, answers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.points_relax, container,
				false);
		mTextViewAttempts = (TextView) rootview
				.findViewById(R.id.my_view_textattempts);
		mTextViewAnswers = (TextView) rootview
				.findViewById(R.id.my_view_textanswers);
		mTextViewAnswerper = (TextView) rootview
				.findViewById(R.id.my_view_textansper);
		attempts = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().RELAX_MODE_ATTEMPTS, "0");
		answers = LizardEyeApplication.getSingleton().mSharedPreferences
				.getString(Model.getSingleton().RELAX_MODE_ANSWERS, "0");
		mTextViewAttempts.setText(attempts);
		mTextViewAnswers.setText(answers);
		if (Float.parseFloat(attempts) > 0 || Float.parseFloat(answers) > 0) {
			float percentage = (Float.parseFloat(answers) / Float
					.parseFloat(attempts)) * 100;
			mTextViewAnswerper.setText(String.valueOf(Math.round(percentage)));
		} else {
			mTextViewAnswerper.setText("0");
		}
		return rootview;
	}

}
