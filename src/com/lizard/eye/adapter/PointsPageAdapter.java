package com.lizard.eye.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lizard.eyes.R;
import com.lizard.eye.fragment.RelaxModeragment;
import com.lizard.eye.fragment.TimeModeFragment;
import com.lizard.eye.fragment.WorldModeFragment;
import com.lizard.eye.pagerslidingtabstrip.PagerSlidingTabStrip.TabCustomViewProvider;

public class PointsPageAdapter extends FragmentStatePagerAdapter implements
		TabCustomViewProvider {
	private Context context;

	public PointsPageAdapter(final FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public int getCount() {
		return 3;
	}

	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.relaxmode);

		case 1:
			return context.getString(R.string.worldmode);

		case 2:
			return context.getString(R.string.timemode);

		default:
			break;
		}
		return null;
	}

	@Override
	public Fragment getItem(final int position) {
		switch (position) {
		case 0:
			return new RelaxModeragment();

		case 1:
			return new WorldModeFragment();
			
		case 2:
			return new TimeModeFragment();

		default:
			break;
		}
		return null;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override
	public View getPageTabCustomView(int position) {
		View v = View.inflate(context, R.layout.tabview, null);
		TextView mTabText = (TextView) v.findViewById(R.id.mTabText);
		mTabText.setText(getPageTitle(position));
		return v;
	}
}
