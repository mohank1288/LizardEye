package com.lizard.eye.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.lizard.eyes.R;
import com.lizard.eye.model.Menus;

public class MenuAdapter extends ArrayAdapter<Menus> {
	private Context context;

	public MenuAdapter(Context context, ArrayList<Menus> users) {
		super(context, R.layout.row_main_menu, users);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.row_main_menu, null);

			holder = new ViewHolder();

			holder.imgMenu = (ImageView) convertView
					.findViewById(R.id.my_view_imgmenu);

			holder.unavailable = (ImageView)convertView.findViewById(R.id.unavailable01);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// Get the data item for this position
		Menus menu = getItem(position);
		if (menu.getMenuUrl() != null) {
			holder.imgMenu.setImageBitmap(getBitmap(context, context.getResources().getIdentifier(menu.getMenuUrl(),
							"drawable", context.getPackageName())));
		}
		
		if(menu.getInAppPurchaseRequired())
		{
			holder.unavailable.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.unavailable.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	public Bitmap getBitmap(Context ctx,int resid)  {       

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


	static class ViewHolder {
		ImageView imgMenu,unavailable;
	}

}
