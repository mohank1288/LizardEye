package com.lizard.eye.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lizard.eyes.R;

public class ViewPagerAdapter extends PagerAdapter {

	// Declare Variables
	private Context context;
	private int[] flag;
	private LayoutInflater inflater;

	public ViewPagerAdapter(Context context, int[] flag) {
		this.context = context;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return flag.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		// Declare Variables
		ImageView imgflag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.row_help, container, false);

		imgflag = (ImageView) itemView.findViewById(R.id.my_view_imghelp);
		imgflag.setImageBitmap(getBitmap(context,flag[position]));
		// Capture position and set to the ImageView
		//imgflag.setImageResource(flag[position]);

		((ViewPager) container).addView(itemView);

		return itemView;
	}

	
	private Bitmap getBitmap(Context ctx,int resid)  {       

		// Get the source image's dimensions
		int desiredWidth = 1000;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(ctx.getResources(), resid , options);

		int srcWidth = options.outWidth;
		//int srcHeight = options.outHeight;

		// Only scale if the source is big enough. This code is just trying
		// to fit a image into a certain width.
		if (desiredWidth > srcWidth)
			desiredWidth = srcWidth;

		// Calculate the correct inSampleSize/scale value. This helps reduce
		// memory use. It should be a power of 2
		int inSampleSize = 1;
		while (srcWidth / 2 > desiredWidth) {
			srcWidth /= 2;
			//srcHeight /= 2;
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

		sampledSrcBitmap =  BitmapFactory.decodeResource(ctx.getResources(), resid , options);

		return sampledSrcBitmap;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((LinearLayout) object);

	}
}
