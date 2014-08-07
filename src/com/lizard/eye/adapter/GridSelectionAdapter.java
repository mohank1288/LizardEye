package com.lizard.eye.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lizard.eyes.R;
import com.lizard.eye.circularimageview.RoundedImageView;
import com.lizard.eye.model.ColorSearchStorage;

public class GridSelectionAdapter extends ArrayAdapter<ColorSearchStorage> {

	private Context mContext;
	private ArrayList<ColorSearchStorage> mColorSelectionStoragesList;

	public GridSelectionAdapter(Context context,
			ArrayList<ColorSearchStorage> mColorSelectionStoragesList) {
		super(context, R.layout.row_grid_selection, mColorSelectionStoragesList);
		this.mContext = context;
		this.mColorSelectionStoragesList = mColorSelectionStoragesList;
	}

	@Override
	public int getCount() {
		return mColorSelectionStoragesList.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.row_grid_selection, null);

			holder = new ViewHolder();

			holder.imageView = (RoundedImageView) convertView
					.findViewById(R.id.my_view_imgsearch);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView.setOval(true);
		holder.imageView.setImageBitmap(getBitmap(mContext, mColorSelectionStoragesList.get(position).getimageId()));


		holder.imageView.setColorFilter(
				mColorSelectionStoragesList.get(position).getColorCode(),
				Mode.SRC_ATOP);

		return convertView;
	}

	static class ViewHolder {
		RoundedImageView imageView;
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
}
